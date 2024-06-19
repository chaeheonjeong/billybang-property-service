package com.billybang.propertyservice.api;

import com.billybang.propertyservice.model.dto.request.NewsRequestDto;
import com.billybang.propertyservice.model.entity.News;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Tag(name = "News Info API", description = "지역 뉴스 API")
public interface NewsApi {
    @Operation(summary = "뉴스 조회", description = "구에 해당하는 뉴스를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @GetMapping("/districts/news")
    ResponseEntity<ApiResult<List<News>>> findNews(@ModelAttribute NewsRequestDto newsRequestDto);
}
