package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.news.News;
import com.billybang.propertyservice.model.dto.request.NewsRequestDto;
import com.billybang.propertyservice.repository.DistrictNewsRepository;
import com.billybang.propertyservice.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {
    NewsRepository newsRepository;
    DistrictNewsRepository districtNewsRepository;

    public List<News> findNews(NewsRequestDto newsRequestDto){
        Long districtId = newsRequestDto.getDistrictId();
        Pageable pageable = PageRequest.of(0, 10);

        List<Long> newsIds = districtNewsRepository.findLatestNewsIdsByDistrictId(districtId,pageable);
        List<News> newsList = newsRepository.findAllById(newsIds);

        return newsList;
    }
}
