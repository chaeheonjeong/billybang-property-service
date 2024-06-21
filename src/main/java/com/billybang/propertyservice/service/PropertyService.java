package com.billybang.propertyservice.service;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.client.UserServiceClient;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyIdRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyAreaPriceResponseDto;
import com.billybang.propertyservice.model.dto.response.PropertyDetailResponseDto;
import com.billybang.propertyservice.model.dto.response.ValidateTokenResponseDto;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import com.billybang.propertyservice.model.entity.StarredProperty;
import com.billybang.propertyservice.model.mapper.PropertyMapper;
import com.billybang.propertyservice.repository.PropertyRepository;
import com.billybang.propertyservice.repository.StarredPropertyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PropertyService {
    private PropertyRepository propertyRepository;
    private StarredPropertyRepository starredPropertyRepository;
    private UserServiceClient userServiceClient;
    private PropertyMapper propertyMapper;

    @Transactional
    public List<PropertyResponseDto> findProperties(PropertyRequestDto requestDto) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");

        List<Property> properties = propertyRepository.findPropertiesByRange(
                realEstateTypes,
                tradeTypes,
                requestDto.getPriceMin(),
                requestDto.getPriceMax(),
                requestDto.getLeftLon(),
                requestDto.getRightLon(),
                requestDto.getTopLat(),
                requestDto.getBottomLat());

        Map<AbstractMap.SimpleEntry<Double, Double>, List<Property>> groupedProperties = properties.stream()
                .collect(Collectors.groupingBy(
                        property -> new AbstractMap.SimpleEntry<>(property.getLatitude(), property.getLongitude())
                ));

        return groupedProperties.entrySet().stream()
                .map(PropertyService::convertPropertyResponseDto)
                .toList();
    }


    @Transactional
    public Slice<PropertyDetailResponseDto> findPropertyDetails(PropertyDetailRequestDto requestDto, int page, int size) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");
        Pageable pageable = PageRequest.of(page, size);

        Slice<Property> properties = propertyRepository.findPropertiesByExactLocation(
                realEstateTypes,
                tradeTypes,
                requestDto.getPriceMin(),
                requestDto.getPriceMax(),
                requestDto.getLatitude(),
                requestDto.getLongitude(),
                pageable
        );
        ApiResult<ValidateTokenResponseDto> validateTokenResult = userServiceClient.validateToken();
        ValidateTokenResponseDto response = validateTokenResult.getResponse();

        Slice<PropertyDetailResponseDto> propertyDetails = properties.map(property ->
                new PropertyDetailResponseDto(property, null));

        if (response.getIsValid()) {
            Long userId = userServiceClient.getUserInfo().getResponse().getUserId();
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
    public PropertyAreaPriceResponseDto findPropertyAreaPrice(PropertyIdRequestDto requestDto) {
        Optional<Property> optProperty = propertyRepository.findById(requestDto.getPropertyId());
        Property property = optProperty.get();
        return propertyMapper.toPropertyAreaPriceResponseDto(property);
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

    @NotNull
    private static PropertyResponseDto convertPropertyResponseDto(Map.Entry<AbstractMap.SimpleEntry<Double, Double>, List<Property>> entry) {
        List<Property> groupedList = entry.getValue();
        long count = groupedList.size();
        int minPrice = groupedList.stream().mapToInt(Property::getPrice).min().orElse(0);
        double latitude = entry.getKey().getKey();
        double longitude = entry.getKey().getValue();
        int area1 = groupedList.get(0).getArea1();

        return new PropertyResponseDto(count, minPrice, area1, latitude, longitude);
    }

    private Long getUserId() {
        return userServiceClient.getUserInfo().getResponse().getUserId();
    }
}
