package com.billybang.propertyservice.api;

import com.billybang.propertyservice.model.entity.Property;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "StarredProperty API", description = "즐겨찾기 매물 API")
@RequestMapping("/properties/stars")
public interface StarredPropertyApi {

    @Operation(summary = "즐겨찾기 매물 추가", description = "즐겨찾기 매물을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @PostMapping("/{propertyId}")
    ResponseEntity<ApiResult<?>> addStarredProperty(@PathVariable Long propertyId);

    @Operation(summary = "즐겨찾기 매물 조회", description = "즐겨찾기 매물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping()
    ResponseEntity<ApiResult<List<Property>>> searchStarredProperty();

    @Operation(summary = "즐겨찾기 매물 삭제", description = "즐겨찾기 매물을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @DeleteMapping("/{propertyId}")
    ResponseEntity<ApiResult<?>> deleteStarredProperty(@PathVariable Long propertyId);
}
