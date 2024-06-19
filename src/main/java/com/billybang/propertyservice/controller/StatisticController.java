package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.api.StatisticApi;
import com.billybang.propertyservice.model.dto.request.StatisticRequestDto;
import com.billybang.propertyservice.model.dto.response.StatisticResponseDto;
import com.billybang.propertyservice.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StatisticController implements StatisticApi {

    private StatisticService statisticService;

    public ResponseEntity<ApiResult<StatisticResponseDto>> findStatisticInfo(StatisticRequestDto requestDto){
        StatisticResponseDto result = statisticService.findStatisticInfo(requestDto);
        return ResponseEntity.ok(ApiUtils.success(result));
    }
}
