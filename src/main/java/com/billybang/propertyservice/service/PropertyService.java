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
    public List<PropertyResponseDto> findPropertyList(PropertyRequestDto requestDto) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");

        for (String realEstateType : realEstateTypes) {
            System.out.println(realEstateType);
        }

        for (String tradeType : tradeTypes) {
            System.out.println(tradeType);
        }

        List<PropertyResponseDto> propertyList = propertyRepository.findPropertyList(
                realEstateTypes,
                tradeTypes,
                requestDto.getPriceMin(),
                requestDto.getPriceMax(),
                requestDto.getLeftLon(),
                requestDto.getRightLon(),
                requestDto.getTopLat(),
                requestDto.getBottomLat()
        );

        log.info("properties, cnt: {}", propertyList.get(0).getCnt());
        return propertyList;
    }

    @Transactional
    public Slice<PropertyDetailResponseDto> findPropertyDetailList(PropertyDetailRequestDto requestDto, int page, int size) {
        String[] realEstateTypes = makeNewTypes(requestDto.getRealEstateType());
        String[] tradeTypes = requestDto.getTradeType().split(":");
        Pageable pageable = PageRequest.of(page, size);

        Slice<PropertyDetailResponseDto> properties = propertyRepository.findPropertyDetailList(
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

        if (response.getIsValid()) {
            Long userId = userServiceClient.getUserInfo().getResponse().getUserId();
            List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);
            Set<Long> starredPropertyIds = starredProperties.stream()
                    .map(StarredProperty::getPropertyId)
                    .collect(Collectors.toSet());

            properties.forEach(property -> property.setIsStarred(starredPropertyIds.contains(property.getPropertyId())));
        }
        return properties;
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

    private Long getUserId() {
        return userServiceClient.getUserInfo().getResponse().getUserId();
    }
}
