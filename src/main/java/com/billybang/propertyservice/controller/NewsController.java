package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.model.news.News;
import com.billybang.propertyservice.model.dto.request.NewsRequestDto;
import com.billybang.propertyservice.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/districts/news")
    public ResponseEntity<ApiResult<List<News>>> findNews(NewsRequestDto newsRequestDto){
        List<News> newsList = newsService.findNews(newsRequestDto);
        return ResponseEntity.ok(ApiUtils.success(newsList));
    }
}
