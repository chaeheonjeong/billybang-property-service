package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.model.dto.request.StatisticRequestDto;
import com.billybang.propertyservice.model.dto.response.StatisticResponseDto;
import com.billybang.propertyservice.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StatisticController {

    private StatisticService statisticService;

    @GetMapping("/districts/statistics")
    public ResponseEntity<ApiResult<StatisticResponseDto>> findStatisticInfo(StatisticRequestDto statisticRequestDto){
        StatisticResponseDto result = statisticService.findStatisticInfo(statisticRequestDto);
        return ResponseEntity.ok(ApiUtils.success(result));
    }
}
