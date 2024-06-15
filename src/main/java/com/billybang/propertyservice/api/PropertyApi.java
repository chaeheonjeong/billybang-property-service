package com.billybang.propertyservice.api;

import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.model.dto.request.SearchPropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.SearchPropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.SearchPropertyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    ResponseEntity<ApiUtils.ApiResult<List<SearchPropertyResponseDto>>> findProperties(@ModelAttribute SearchPropertyRequestDto requestDto);

    @Operation(summary = "매물 상세 조회", description = "매물의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping("/details")
    ResponseEntity<ApiUtils.ApiResult<List<Property>>> findPropertyDetail(@ModelAttribute SearchPropertyDetailRequestDto requestDto);
}
