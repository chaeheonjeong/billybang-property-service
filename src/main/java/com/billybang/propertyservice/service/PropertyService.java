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
        String[] tradeTypes = requestDto.getTradeType().split(":");
        Integer leasePriceMin = requestDto.getLeasePriceMin();
        Integer leasePriceMax = requestDto.getLeasePriceMax();
        Integer dealPriceMin = requestDto.getDealPriceMin();
        Integer dealPriceMax = requestDto.getDealPriceMax();
        double leftLon = requestDto.getLeftLon();
        double rightLon = requestDto.getRightLon();
        double topLat = requestDto.getTopLat();
        double bottomLat = requestDto.getBottomLat();

        List<Property> properties = propertyRepositoryCustom.findPropertiesByRange(
                realEstateTypes, tradeTypes, leasePriceMin, leasePriceMax,
                dealPriceMin, dealPriceMax, leftLon, rightLon, topLat, bottomLat
        );

        List<PropertyResponseDto> propertyResponseDtos = new ArrayList<>();
        if(requestDto.getZoom() >= 1 && requestDto.getZoom() <= 5){
            propertyResponseDtos = getProperties(properties);

        } else if(requestDto.getZoom() == 6 || requestDto.getZoom() == 7) {
            List<Long> uniqueAreaIds = properties.stream()
                    .map(Property::getAreaId)
                    .distinct()
                    .toList();

            List<Tuple> areaStats = propertyRepositoryCustom.findStat(
                    uniqueAreaIds, realEstateTypes, tradeTypes, leasePriceMin,
                    leasePriceMax, dealPriceMin, dealPriceMax, "area");
            propertyResponseDtos = getGuDongProperties(areaStats, "area");

        } else if(requestDto.getZoom() >= 8){
            List<Long> uniqueDistrictIds = properties.stream()
                    .map(Property::getDistrictId)
                    .distinct()
                    .toList();

            List<Tuple> districtStats = propertyRepositoryCustom.findStat(
                    uniqueDistrictIds, realEstateTypes, tradeTypes, leasePriceMin,
                    leasePriceMax, dealPriceMin, dealPriceMax, "district");
            propertyResponseDtos = getGuDongProperties(districtStats, "district");
        }

        return propertyResponseDtos;
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
                String code = values[0].substring(5);
                String name = values[3];
                double latitude = Double.parseDouble(values[5]);
                double longitude = Double.parseDouble(values[6]);
                areaInfo.put(Long.valueOf(code), new DistrictAreaInfo(latitude, longitude, name));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        districtInfo.put(11110L,new DistrictAreaInfo(37.58482, 126.98606, "종로구"));
        districtInfo.put(11140L,new DistrictAreaInfo(37.56138, 126.98962, "중구"));
        districtInfo.put(11170L,new DistrictAreaInfo(37.533429, 126.975777, "용산구"));
        districtInfo.put(11200L,new DistrictAreaInfo(37.55628, 127.04115, "성동구"));
        districtInfo.put(11215L,new DistrictAreaInfo(37.53505, 127.08582, "광진구"));
        districtInfo.put(11230L,new DistrictAreaInfo(37.58176, 127.05130, "동대문구"));
        districtInfo.put(11260L,new DistrictAreaInfo(37.60656, 127.08724, "중랑구"));
        districtInfo.put(11290L,new DistrictAreaInfo(37.60107, 127.03605, "성북구"));
        districtInfo.put(11305L,new DistrictAreaInfo(37.63738, 127.02364, "강북구"));
        districtInfo.put(11320L,new DistrictAreaInfo(37.66416, 127.03776, "도봉구"));
        districtInfo.put(11350L,new DistrictAreaInfo(37.64895, 127.06416, "노원구"));
        districtInfo.put(11380L,new DistrictAreaInfo(37.60446, 126.91720, "은평구"));
        districtInfo.put(11410L,new DistrictAreaInfo(37.5792250, 126.9368000, "서대문구"));
        districtInfo.put(11440L,new DistrictAreaInfo(37.55448, 126.93195, "마포구"));
        districtInfo.put(11470L,new DistrictAreaInfo(37.52772, 126.85191, "양천구"));
        districtInfo.put(11500L,new DistrictAreaInfo(37.56033, 126.83971, "강서구"));
        districtInfo.put(11530L,new DistrictAreaInfo(37.49962, 126.86203, "구로구"));
        districtInfo.put(11545L,new DistrictAreaInfo(37.4570783, 126.8957011, "금천구"));
        districtInfo.put(11560L,new DistrictAreaInfo(37.51787, 126.90746, "영등포구"));
        districtInfo.put(11590L,new DistrictAreaInfo(37.49562, 126.94433, "동작구"));
        districtInfo.put(11620L,new DistrictAreaInfo(37.4781548, 126.9514847, "관악구"));
        districtInfo.put(11650L,new DistrictAreaInfo(37.49115, 127.00832, "서초구"));
        districtInfo.put(11680L,new DistrictAreaInfo(37.50520, 127.04872, "강남구"));
        districtInfo.put(11710L,new DistrictAreaInfo( 37.51335, 127.11706, "송파구"));
        districtInfo.put(11740L,new DistrictAreaInfo(37.54144, 127.14410, "강동구"));
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

    public List<PropertyResponseDto> getGuDongProperties(List<Tuple> stats, String type){
        return stats.stream()
                .map(stat -> {
                    Long id = stat.get(0, Long.class);
                    Long propertyCount = stat.get(1, Long.class);;
                    Double averagePrice = stat.get(2, Double.class);
                    DistrictAreaInfo info;
                    if (type.equals("district")) {
                        info = districtInfo.get(id);
                    } else {
                        info = areaInfo.get(id);
                    }

                    return new PropertyResponseDto(
                            id,
                            propertyCount.intValue(),
                            averagePrice.intValue(),
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
