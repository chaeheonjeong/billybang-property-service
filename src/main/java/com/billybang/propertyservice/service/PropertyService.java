package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.dto.request.SearchPropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.SearchPropertyRequestDto;
import com.billybang.propertyservice.dto.response.SearchPropertyResponseDto;
import com.billybang.propertyservice.model.Property;
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

    public List<SearchPropertyResponseDto> findPropertyList(SearchPropertyRequestDto searchPropertyRequestDto){
        String[] realEstateTypes = splitTypes(searchPropertyRequestDto.getRealEstateType());
        String[] tradeTypes = splitTypes(searchPropertyRequestDto.getTradeType());

        return propertyRepository.findPropertyList(
                realEstateTypes,
                tradeTypes,
                searchPropertyRequestDto.getPriceMin(),
                searchPropertyRequestDto.getPriceMax(),
                searchPropertyRequestDto.getLeftLon(),
                searchPropertyRequestDto.getRightLon(),
                searchPropertyRequestDto.getTopLat(),
                searchPropertyRequestDto.getBottomLat()
        );
    }

    public List<Property> findPropertyDetailList(SearchPropertyDetailRequestDto searchPropertyDetailRequestDto){
        String[] realEstateTypes = splitTypes(searchPropertyDetailRequestDto.getRealEstateType());
        String[] tradeTypes = splitTypes(searchPropertyDetailRequestDto.getTradeType());

        return propertyRepository.findPropertyDetailList(
                realEstateTypes,
                tradeTypes,
                searchPropertyDetailRequestDto.getPriceMin(),
                searchPropertyDetailRequestDto.getPriceMax(),
                searchPropertyDetailRequestDto.getLatitude(),
                searchPropertyDetailRequestDto.getLongitude()
        );
    }

    private String[] splitTypes(String types) {
        return types.split(":");
    }
}
