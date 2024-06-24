package com.billybang.propertyservice.api;

import com.billybang.propertyservice.model.dto.response.PropertyAreaPriceResponseDto;
import com.billybang.propertyservice.model.dto.response.PropertyDetailResponseDto;
import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Property API", description = "매물 API")
@RequestMapping("/properties")
public interface PropertyApi {
    @Operation(summary = "매물 조회", description = "조건에 맞는 매물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping
    ResponseEntity<ApiResult<List<PropertyResponseDto>>> findProperties(@Valid @ModelAttribute PropertyRequestDto requestDto);

    @Operation(summary = "매물 상세 조회", description = "매물의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping("/details")
    ResponseEntity<ApiResult<PageResult<PropertyDetailResponseDto>>> findPropertyDetail(@Valid @ModelAttribute PropertyDetailRequestDto requestDto,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "매물 거래 방식, 면적, 가격 조회", description = "매물의 거래 방식, 면적, 가격 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping("/{propertyId}/area-price")
    ResponseEntity<ApiResult<PropertyAreaPriceResponseDto>> findPropertyAreaPrice(@PathVariable Long propertyId);

    @Operation(summary = "매물 id로 매물 조회", description = "매물 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping("/{propertyId}")
    ResponseEntity<ApiResult<PropertyDetailResponseDto>> findPropertyById(@PathVariable Long propertyId);
}
