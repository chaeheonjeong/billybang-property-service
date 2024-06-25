package com.billybang.propertyservice.service;
import org.springframework.core.io.ResourceLoader;
import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.client.UserServiceClient;
import com.billybang.propertyservice.model.DistrictAreaInfo;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.*;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.model.entity.StarredProperty;
import com.billybang.propertyservice.model.mapper.PropertyMapper;
import com.billybang.propertyservice.repository.PropertyRepository;
import com.billybang.propertyservice.repository.PropertyRepositoryCustom;
import com.billybang.propertyservice.repository.StarredPropertyRepository;
import com.querydsl.core.Tuple;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final StarredPropertyRepository starredPropertyRepository;
    private final UserServiceClient userServiceClient;
    private final PropertyMapper propertyMapper;
    private final PropertyRepositoryCustom propertyRepositoryCustom;
    private final ResourceLoader resourceLoader;
    private static  final Map<Long, DistrictAreaInfo> areaInfo = new HashMap<>();
    private static final Map<Long, DistrictAreaInfo> districtInfo = new HashMap<>();

    @Transactional
    public List<PropertyResponseDto> findProperties(PropertyRequestDto requestDto) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        List<String> tradeTypes = Arrays.asList(requestDto.getTradeType().split(":"));
        Integer leasePriceMin = requestDto.getLeasePriceMin();
        Integer leasePriceMax = requestDto.getLeasePriceMax();
        Integer dealPriceMin = requestDto.getDealPriceMin();
        Integer dealPriceMax = requestDto.getDealPriceMax();
        double leftLon = requestDto.getLeftLon();
        double rightLon = requestDto.getRightLon();
        double topLat = requestDto.getTopLat();
        double bottomLat = requestDto.getBottomLat();


        List<PropertyResponseDto> propertyResponseDtos = new ArrayList<>();
        if (requestDto.getZoom() >= 1 && requestDto.getZoom() <= 5) {
            List<Property> propertiesByRange = propertyRepository.findPropertiesByRange(topLat, bottomLat, rightLon, leftLon);
            List<Property> filteredProperties = propertiesByRange.stream()
                    .filter(property -> containsRealEstateType(property.getRealEstateType(), realEstateTypes))
                    .filter(property -> isWithinPriceRange(property, tradeTypes, leasePriceMin, leasePriceMax, dealPriceMin, dealPriceMax))
                    .collect(Collectors.toList());
            propertyResponseDtos = getProperties(filteredProperties);
        } else { // gu, dong
            List<Long> districtIds = districtInfo.entrySet().stream()
                    .filter(entry -> {
                        double latitude = entry.getValue().getLatitude();
                        double longitude = entry.getValue().getLongitude();
                        return longitude >= leftLon && longitude <= rightLon &&
                                latitude >= bottomLat && latitude <= topLat;
                    }).map(Map.Entry::getKey)
                    .toList();
            List<Property> propertiesByDis = propertyRepository.findByDistrictIds(districtIds);
            List<Property> filteredProperties = propertiesByDis.stream()
                    .filter(property -> containsRealEstateType(property.getRealEstateType(), realEstateTypes))
                    .filter(property -> isWithinPriceRange(property, tradeTypes, leasePriceMin, leasePriceMax, dealPriceMin, dealPriceMax))
                    .collect(Collectors.toList());

            if (requestDto.getZoom() == 6) {
                propertyResponseDtos = getGuDongProperties(filteredProperties, "area");
            } else if (requestDto.getZoom() >= 7) {
                propertyResponseDtos = getGuDongProperties(filteredProperties, "district");
            }
        }
        return propertyResponseDtos;
    }

    private boolean containsRealEstateType(String propertyRealEstateType, String[] targetTypes) {
        for (String type : targetTypes) {
            if (type.equals(propertyRealEstateType)) {
                return true;
            }
        }
        return false;
    }


    private boolean isWithinPriceRange(Property property, List<String> tradeTypes, Integer leasePriceMin, Integer leasePriceMax, Integer dealPriceMin, Integer dealPriceMax) {
        String tradeType = property.getTradeType();
        int price = property.getPrice();

        if ("DEAL".equals(tradeType) && tradeTypes.contains("DEAL")) {
            return price >= dealPriceMin && price <= dealPriceMax;
        } else if ("LEASE".equals(tradeType) && tradeTypes.contains("LEASE")) {
            return price >= leasePriceMin && price <= leasePriceMax;
        }
        return false;
    }


    @Transactional
    public Slice<PropertyDetailResponseDto> findPropertyDetails(PropertyDetailRequestDto requestDto, int page, int size) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");
        Integer leasePriceMin = requestDto.getLeasePriceMin();
        Integer leasePriceMax = requestDto.getLeasePriceMax();
        Integer dealPriceMin = requestDto.getDealPriceMin();
        Integer dealPriceMax = requestDto.getDealPriceMax();
        double longitude = requestDto.getLongitude();
        double latitude = requestDto.getLatitude();
        Pageable pageable = PageRequest.of(page, size);

        ApiResult<ValidateTokenResponseDto> validateTokenResult = userServiceClient.validateToken();
        ValidateTokenResponseDto response = validateTokenResult.getResponse();

        Slice<Property> properties = propertyRepositoryCustom.findPropertiesByExactLocation(
                realEstateTypes, tradeTypes, leasePriceMin, leasePriceMax,
                dealPriceMin, dealPriceMax, longitude, latitude, pageable);
        Slice<PropertyDetailResponseDto> propertyDetails = properties.map(property ->
                new PropertyDetailResponseDto(property, false));

        if (response.getIsValid()) {
            Long userId = getUserId();
            List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);
            Set<Long> starredPropertyIds = starredProperties.stream()
                    .map(StarredProperty::getPropertyId)
                    .collect(Collectors.toSet());

            propertyDetails.getContent().forEach(propertyDetail ->
                    propertyDetail.setIsStarred(starredPropertyIds.contains(propertyDetail.getPropertyId())));
        }

        return propertyDetails;
    }

    @Transactional
    public PropertyAreaPriceResponseDto findPropertyAreaPrice(Long propertyId) {
        Optional<Property> optProperty = propertyRepository.findById(propertyId);
        Property property = optProperty.get();
        return propertyMapper.toPropertyAreaPriceResponseDto(property);
    }

    @Transactional
    public PropertyDetailResponseDto findPropertyById(Long propertyId){
        Optional<Property> optProperty = propertyRepository.findById(propertyId);
        Property property = optProperty.get();

        PropertyDetailResponseDto responseDto = new PropertyDetailResponseDto(property, false);

        ApiResult<ValidateTokenResponseDto> validateTokenResult = userServiceClient.validateToken();
        ValidateTokenResponseDto response = validateTokenResult.getResponse();

        if (response.getIsValid()) {
            Long userId = getUserId();
            List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);
            Set<Long> starredPropertyIds = starredProperties.stream()
                    .map(StarredProperty::getPropertyId)
                    .collect(Collectors.toSet());

            if (starredPropertyIds.contains(propertyId)) {
                responseDto.setIsStarred(true);
            }
        }

        return responseDto;
    }

    @PostConstruct
    public void initDistrictAreaInfo(){
        String filePath = "data/districtAreaInfo.csv";
        Resource resource = resourceLoader.getResource("classpath:" + filePath);

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String code = values[0];
                String name = values[3];
                double latitude = Double.parseDouble(values[5]);
                double longitude = Double.parseDouble(values[6]);
                areaInfo.put(Long.valueOf(code), new DistrictAreaInfo(latitude, longitude, name));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        districtInfo.put(1111000000L,new DistrictAreaInfo(37.58482, 126.98606, "종로구"));
        districtInfo.put(1114000000L,new DistrictAreaInfo(37.56138, 126.98962, "중구"));
        districtInfo.put(1117000000L,new DistrictAreaInfo(37.533429, 126.975777, "용산구"));
        districtInfo.put(1120000000L,new DistrictAreaInfo(37.55628, 127.04115, "성동구"));
        districtInfo.put(1121500000L,new DistrictAreaInfo(37.53505, 127.08582, "광진구"));
        districtInfo.put(1123000000L,new DistrictAreaInfo(37.58176, 127.05130, "동대문구"));
        districtInfo.put(1126000000L,new DistrictAreaInfo(37.60656, 127.08724, "중랑구"));
        districtInfo.put(1129000000L,new DistrictAreaInfo(37.60107, 127.03605, "성북구"));
        districtInfo.put(1130500000L,new DistrictAreaInfo(37.63738, 127.02364, "강북구"));
        districtInfo.put(1132000000L,new DistrictAreaInfo(37.66416, 127.03776, "도봉구"));
        districtInfo.put(1135000000L,new DistrictAreaInfo(37.64895, 127.06416, "노원구"));
        districtInfo.put(1138000000L,new DistrictAreaInfo(37.60446, 126.91720, "은평구"));
        districtInfo.put(1141000000L,new DistrictAreaInfo(37.5792250, 126.9368000, "서대문구"));
        districtInfo.put(1144000000L,new DistrictAreaInfo(37.55448, 126.93195, "마포구"));
        districtInfo.put(1147000000L,new DistrictAreaInfo(37.52772, 126.85191, "양천구"));
        districtInfo.put(1150000000L,new DistrictAreaInfo(37.56033, 126.83971, "강서구"));
        districtInfo.put(1153000000L,new DistrictAreaInfo(37.49962, 126.86203, "구로구"));
        districtInfo.put(1154500000L,new DistrictAreaInfo(37.4570783, 126.8957011, "금천구"));
        districtInfo.put(1156000000L,new DistrictAreaInfo(37.51787, 126.90746, "영등포구"));
        districtInfo.put(1159000000L,new DistrictAreaInfo(37.49562, 126.94433, "동작구"));
        districtInfo.put(1162000000L,new DistrictAreaInfo(37.4781548, 126.9514847, "관악구"));
        districtInfo.put(1165000000L,new DistrictAreaInfo(37.49115, 127.00832, "서초구"));
        districtInfo.put(1168000000L,new DistrictAreaInfo(37.50520, 127.04872, "강남구"));
        districtInfo.put(1171000000L,new DistrictAreaInfo( 37.51335, 127.11706, "송파구"));
        districtInfo.put(1174000000L,new DistrictAreaInfo(37.54144, 127.14410, "강동구"));
    }


    private String[] makeNewTypes(String types) {
        String[] typeList = types.split(":");
        List<String> newList = new ArrayList<>();

        for (String type : typeList) {
            if (type.equals("JT")) {
                newList.add("DDDGG");
                newList.add("SGJT");
                newList.add("HOJT");
                newList.add("JWJT");
            } else {
                newList.add(type);
            }
        }

        return newList.toArray(new String[0]);
    }

    public List<PropertyResponseDto> getProperties(List<Property> properties){
        Map<AbstractMap.SimpleEntry<Double, Double>, List<Property>> groupedProperties = properties.stream()
                .collect(Collectors.groupingBy(
                        property -> new AbstractMap.SimpleEntry<>(property.getLatitude(), property.getLongitude())
                ));

        return groupedProperties.entrySet().stream()
                .map(PropertyService::convertPropertyResponseDto)
                .toList();
    }

    public List<PropertyResponseDto> getGuDongProperties(List<Property> stats, String type) {
        Map<Long, List<Property>> groupedById = new HashMap<>();

        if("district".equals(type)) {
            groupedById = stats.stream().collect(Collectors.groupingBy(Property::getDistrictId));
        } else if("area".equals(type)){
            groupedById = stats.stream().collect(Collectors.groupingBy(Property::getAreaId));
        }

        return groupedById.entrySet().stream()
                .map(entry -> {
                    Long id = entry.getKey();
                    List<Property> properties = entry.getValue();

                    long propertyCount = properties.size();
                    double averagePrice = properties.stream()
                            .collect(Collectors.averagingDouble(Property::getPrice));

                    DistrictAreaInfo info;
                    if (type.equals("district")) {
                        info = districtInfo.get(id);
                    } else {
                        info = areaInfo.get(id);
                    }

                    return new PropertyResponseDto(
                            id,
                            (int) propertyCount,
                            (int) averagePrice,
                            null,
                            info.getName(),
                            info.getLatitude(),
                            info.getLongitude()
                    );
                })
                .collect(Collectors.toList());
    }

    private static PropertyResponseDto convertPropertyResponseDto(Map.Entry<AbstractMap.SimpleEntry<Double, Double>, List<Property>> entry) {
        List<Property> groupedList = entry.getValue();
        Property bestProperty = groupedList.stream()
                .min(Comparator.comparingInt(Property::getPrice)
                        .thenComparingInt(Property::getArea1).reversed())
                .orElse(null);

        if (bestProperty == null) {
            throw new IllegalArgumentException("No properties found in group");
        }

        Long representativeId = bestProperty.getId();
        Integer count = groupedList.size();
        Integer minPrice = bestProperty.getPrice();
        Double latitude = entry.getKey().getKey();
        Double longitude = entry.getKey().getValue();
        Integer area1 = bestProperty.getArea1();

        return new PropertyResponseDto(representativeId, count, minPrice, area1, null, latitude, longitude);
    }

    private Long getUserId() {
        return userServiceClient.getUserInfo().getResponse().getUserId();
    }
}
