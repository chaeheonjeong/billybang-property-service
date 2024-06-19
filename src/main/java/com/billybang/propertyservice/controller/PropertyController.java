package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.api.PropertyApi;
import com.billybang.propertyservice.model.dto.request.PropertyIdRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyAreaPriceResponseDto;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import com.billybang.propertyservice.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PropertyController implements PropertyApi {

    private PropertyService propertyService;

    public ResponseEntity<ApiResult<List<PropertyResponseDto>>> findProperties(PropertyRequestDto requestDto){
        List<PropertyResponseDto> properties = propertyService.findPropertyList(requestDto);
        return ResponseEntity.ok(ApiUtils.success(properties));
    }

    public ResponseEntity<ApiResult<List<?>>> findPropertyDetail(PropertyDetailRequestDto requestDto){
        List<?> properties = propertyService.findPropertyDetailList(requestDto);
        return ResponseEntity.ok(ApiUtils.success(properties));
    }

    public ResponseEntity<ApiResult<PropertyAreaPriceResponseDto>> findPropertyAreaPrice(PropertyIdRequestDto requestDto){
        PropertyAreaPriceResponseDto res = propertyService.findPropertyAreaPrice(requestDto);
        return ResponseEntity.ok(ApiUtils.success(res));
    }
}
