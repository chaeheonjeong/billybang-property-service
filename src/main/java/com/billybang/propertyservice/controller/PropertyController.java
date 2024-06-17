package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.api.PropertyApi;
import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.model.dto.request.SearchPropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.SearchPropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.SearchPropertyResponseDto;
import com.billybang.propertyservice.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PropertyController implements PropertyApi {

    private PropertyService propertyService;

    public ResponseEntity<ApiResult<List<SearchPropertyResponseDto>>> findProperties(SearchPropertyRequestDto requestDto){
        List<SearchPropertyResponseDto> properties = propertyService.findPropertyList(requestDto);
        return ResponseEntity.ok(ApiUtils.success(properties));
    }

    public ResponseEntity<ApiResult<List<Property>>> findPropertyDetail(SearchPropertyDetailRequestDto requestDto){
        List<Property> properties = propertyService.findPropertyDetailList(requestDto);
        return ResponseEntity.ok(ApiUtils.success(properties));
    }

}
