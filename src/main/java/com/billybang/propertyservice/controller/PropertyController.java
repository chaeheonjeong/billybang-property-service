package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.api.PageResult;
import com.billybang.propertyservice.api.PropertyApi;
import com.billybang.propertyservice.exception.common.BError;
import com.billybang.propertyservice.exception.common.CommonException;
import com.billybang.propertyservice.model.PaginationInfo;
import com.billybang.propertyservice.model.dto.request.PropertyIdRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyAreaPriceResponseDto;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyDetailResponseDto;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import com.billybang.propertyservice.service.PropertyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PropertyController implements PropertyApi {

    private PropertyService propertyService;

    public ResponseEntity<ApiResult<List<PropertyResponseDto>>> findProperties(PropertyRequestDto requestDto) {
        List<PropertyResponseDto> properties = propertyService.findPropertyList(requestDto);
        return ResponseEntity.ok(ApiUtils.success(properties));
    }

    public ResponseEntity<ApiResult<PageResult<PropertyDetailResponseDto>>> findPropertyDetail(PropertyDetailRequestDto requestDto,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size) {
        Slice<PropertyDetailResponseDto> sliceProperties = propertyService.findPropertyDetailList(requestDto, page, size);
        PaginationInfo paginationInfo = new PaginationInfo(sliceProperties.hasNext(), sliceProperties.getNumber(),
                sliceProperties.getSize(),
                sliceProperties.getNumberOfElements());
        return ResponseEntity.ok(ApiUtils.success(new PageResult(sliceProperties.getContent(), paginationInfo)));
    }

    public ResponseEntity<ApiResult<PropertyAreaPriceResponseDto>> findPropertyAreaPrice(PropertyIdRequestDto requestDto) {
        PropertyAreaPriceResponseDto res = propertyService.findPropertyAreaPrice(requestDto);
        return ResponseEntity.ok(ApiUtils.success(res));
    }
}
