package com.billybang.propertyservice.service;

import com.billybang.propertyservice.client.UserServiceClient;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import com.billybang.propertyservice.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PropertyService {
    private PropertyRepository propertyRepository;
    private UserServiceClient userServiceClient;

    public List<PropertyResponseDto> findPropertyList(PropertyRequestDto propertyRequestDto){
        String[] realEstateTypes = splitTypes(propertyRequestDto.getRealEstateType());
        String[] tradeTypes = splitTypes(propertyRequestDto.getTradeType());

        return propertyRepository.findPropertyList(
                realEstateTypes,
                tradeTypes,
                propertyRequestDto.getPriceMin(),
                propertyRequestDto.getPriceMax(),
                propertyRequestDto.getLeftLon(),
                propertyRequestDto.getRightLon(),
                propertyRequestDto.getTopLat(),
                propertyRequestDto.getBottomLat()
        );
    }

    public List<Property> findPropertyDetailList(PropertyDetailRequestDto PropertyDetailRequestDto){
        String[] realEstateTypes = splitTypes(PropertyDetailRequestDto.getRealEstateType());
        String[] tradeTypes = splitTypes(PropertyDetailRequestDto.getTradeType());

        return propertyRepository.findPropertyDetailList(
                realEstateTypes,
                tradeTypes,
                PropertyDetailRequestDto.getPriceMin(),
                PropertyDetailRequestDto.getPriceMax(),
                PropertyDetailRequestDto.getLatitude(),
                PropertyDetailRequestDto.getLongitude()
        );
    }

    private String[] splitTypes(String types) {
        return types.split(":");
    }
}
