package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.api.PageResult;
import com.billybang.propertyservice.api.PropertyApi;
import com.billybang.propertyservice.model.PaginationInfo;
import com.billybang.propertyservice.model.dto.response.PropertyAreaPriceResponseDto;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyDetailResponseDto;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import com.billybang.propertyservice.service.PropertyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class PropertyController implements PropertyApi {

    private PropertyService propertyService;

    public ResponseEntity<ApiResult<List<PropertyResponseDto>>> findProperties(PropertyRequestDto requestDto) {
        List<PropertyResponseDto> properties = propertyService.findProperties(requestDto);
        return ResponseEntity.ok(ApiUtils.success(properties));
    }

    public ResponseEntity<ApiResult<PageResult<PropertyDetailResponseDto>>> findPropertyDetail(PropertyDetailRequestDto requestDto,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size) {
        Slice<PropertyDetailResponseDto> sliceProperties = propertyService.findPropertyDetails(requestDto, page, size);
        PaginationInfo paginationInfo = new PaginationInfo(sliceProperties.hasNext(), sliceProperties.getNumber(),
                sliceProperties.getSize(),
                sliceProperties.getNumberOfElements());
        return ResponseEntity.ok(ApiUtils.success(new PageResult(sliceProperties.getContent(), paginationInfo)));
    }

    public ResponseEntity<ApiResult<PropertyAreaPriceResponseDto>> findPropertyAreaPrice(Long propertyId) {
        PropertyAreaPriceResponseDto res = propertyService.findPropertyAreaPrice(propertyId);
        return ResponseEntity.ok(ApiUtils.success(res));
    }

    public ResponseEntity<ApiResult<PropertyDetailResponseDto>> findPropertyById(Long propertyId) {
        PropertyDetailResponseDto res = propertyService.findPropertyById(propertyId);
        return ResponseEntity.ok(ApiUtils.success(res));
    }
}
