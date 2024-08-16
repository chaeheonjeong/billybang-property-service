package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.dto.request.PropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.billybang.propertyservice.model.dto.response.PropertyAreaPriceResponseDto;
import com.billybang.propertyservice.model.dto.response.PropertyDetailResponseDto;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PropertyServiceTest_V1 {
    @Autowired
    private PropertyService propertyService;

    @Test
    @DisplayName("매물 조회")
    void findProperties(){
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                        .realEstateType("APT")
                        .tradeType("DEAL")
                        .dealPriceMin(5700)
                        .dealPriceMax(6000)
                        .leftLon(127.058642)
                        .rightLon(128.058642)
                        .topLat(37.51852)
                        .bottomLat(36.51852)
                        .zoom(7)
                        .build();

        List<PropertyResponseDto> response = propertyService.findProperties(requestDto);

        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getRepresentativeId()).isEqualTo(1171000000);
    }

    @Test
    @DisplayName("매물 상세 조회")
    @Transactional
    void findPropertyDetails(){
         PropertyDetailRequestDto requestDto = PropertyDetailRequestDto.builder()
                 .realEstateType("APT")
                 .tradeType("DEAL")
                 .dealPriceMin(5000)
                 .dealPriceMax(5700)
                 .latitude(37.51852)
                 .longitude(127.058642)
                 .build();

        Slice<PropertyDetailResponseDto> response = propertyService.findPropertyDetails(requestDto, 0, 10);

        assertThat(response.getContent().size()).isEqualTo(9);
        assertThat(response.getContent().get(0).getPropertyId()).isEqualTo(2424015513L);
    }

    @Test
    @DisplayName("매물 거래 방식, 면적, 가격 조회")
    void findPropertyAreaPrice(){
        PropertyAreaPriceResponseDto response = propertyService.findPropertyAreaPrice(2424015513L);

        assertThat(response.getTradeType()).isEqualTo("DEAL");
        assertThat(response.getArticleName()).isEqualTo("아이파크삼성");
    }

    @Test
    @DisplayName("매물 id로 조회")
    void findPropertyById(){
        PropertyDetailResponseDto response = propertyService.findPropertyById(2424015513L);

        assertThat(response.getPropertyId()).isEqualTo(2424015513L);
        assertThat(response.getArticleName()).isEqualTo("아이파크삼성");
    }

    @Test
    @DisplayName("거래 방식 포함 여부 확인")
    void containsRealEstateType(){
        String propertyRealEstateType = "DEAL";
        String[] targetTypes = {"DEAL", "LEASE"};
        boolean isContains = propertyService.containsRealEstateType(propertyRealEstateType, targetTypes);

        assertThat(isContains).isTrue();
    }

    @Test
    @DisplayName("거래 방식, 매물 종류 : 분리")
    void makeNewTypes(){
        String type = "JT:APT:OPST";
        String[] response = propertyService.makeNewTypes(type);

        assertThat(response).containsOnly("DDDGG", "SGJT", "HOJT", "JWJT", "APT", "OPST");

        type = "DEAL:LEASE";
        response = propertyService.makeNewTypes(type);

        assertThat(response).containsOnly("DEAL", "LEASE");
    }
}

