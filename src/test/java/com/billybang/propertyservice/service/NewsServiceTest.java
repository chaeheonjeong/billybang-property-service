package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.dto.request.NewsRequestDto;
import com.billybang.propertyservice.model.entity.News;
import com.billybang.propertyservice.repository.DistrictNewsRepository;
import com.billybang.propertyservice.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private DistrictNewsRepository districtNewsRepository;

    @InjectMocks
    private NewsService newsService;

    @Test
    public void testFindNews(){
        Long districtId = 1L;
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setDistrictId(districtId);

        Pageable pageable = PageRequest.of(0, 10);
        List<Long> newsIds = Arrays.asList(1L, 2L, 3L);
        List<News> newsList = Arrays.asList(new News(), new News(), new News());

        when(districtNewsRepository.findLatestNewsIdsByDistrictId(districtId, pageable)).thenReturn(newsIds);
        when(newsRepository.findAllById(newsIds)).thenReturn(newsList);

        // Act
        List<News> result = newsService.findNews(newsRequestDto);

        // Assert
        assertEquals(3, result.size());
        verify(districtNewsRepository, times(1)).findLatestNewsIdsByDistrictId(districtId, pageable);
        verify(newsRepository, times(1)).findAllById(newsIds);
    }
}