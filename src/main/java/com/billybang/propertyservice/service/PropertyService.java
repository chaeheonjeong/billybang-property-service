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
import com.billybang.propertyservice.repository.PropertyRepository;
import com.billybang.propertyservice.repository.StarredPropertyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PropertyService {
    private PropertyRepository propertyRepository;
    private StarredPropertyRepository starredPropertyRepository;
    private UserServiceClient userServiceClient;

    public List<PropertyResponseDto> findPropertyList(PropertyRequestDto requestDto) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");

        return propertyRepository.findPropertyList(
                realEstateTypes,
                tradeTypes,
                requestDto.getPriceMin(),
                requestDto.getPriceMax(),
                requestDto.getLeftLon(),
                requestDto.getRightLon(),
                requestDto.getTopLat(),
                requestDto.getBottomLat()
        );
    }

    public List<?> findPropertyDetailList(PropertyDetailRequestDto requestDto, int page, int size) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");

        Pageable pageable = PageRequest.of(page, size);

        List<Property> properties = propertyRepository.findPropertyDetailList(
                realEstateTypes,
                tradeTypes,
                requestDto.getPriceMin(),
                requestDto.getPriceMax(),
                requestDto.getLatitude(),
                requestDto.getLongitude()
        );

        for (Property property : properties) {
            System.out.println("**");
            System.out.println(property);
        }

        ApiResult<ValidateTokenResponseDto> validateTokenResult = userServiceClient.validateToken();
        ValidateTokenResponseDto response = validateTokenResult.getResponse();

        if (response.getIsValid()) {
            List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(getUserId());
            List<Long> starredIds = starredProperties.stream()
                    .map(StarredProperty::getPropertyId)
                    .toList();
            return properties.stream()
                    .map(property -> new PropertyDetailResponseDto(property, starredIds.contains(property.getId())))
                    .collect(Collectors.toList());
        }
        return properties;
    }

    public PropertyAreaPriceResponseDto findPropertyAreaPrice(PropertyIdRequestDto requestDto) {
        Optional<Property> optProperty = propertyRepository.findById(requestDto.getPropertyId());
        Property property = optProperty.get();
        return new PropertyAreaPriceResponseDto(property.getTradeType(), property.getArea2(), property.getPrice(), property.getArticleName());
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

    private Long getUserId() {
        return userServiceClient.getUserInfo().getResponse().getUserId();
    }
}
