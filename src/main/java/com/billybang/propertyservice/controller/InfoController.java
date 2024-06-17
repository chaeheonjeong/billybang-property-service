package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.model.dto.request.SearchStatisticRequestDto;
import com.billybang.propertyservice.model.dto.response.SearchStatisticResponseDto;
import com.billybang.propertyservice.service.InfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/districts")
public class InfoController {

    private InfoService infoService;

    @GetMapping("/statistics")
    public ResponseEntity<ApiResult<SearchStatisticResponseDto>> findStatisticInfo(SearchStatisticRequestDto searchStatisticRequestDto){
        SearchStatisticResponseDto result = infoService.findStatisticInfo(searchStatisticRequestDto);
        return ResponseEntity.ok(ApiUtils.success(result));
    }
}
