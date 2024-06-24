package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.model.dto.request.PropertyRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PropertyControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        objectMapper.registerModule(new JavaTimeModule());
    }

//    @Test
//    @Transactional
//    @DisplayName("매물 상품 API 테스트")
//    void findProperties() throws Exception{
//        String realEstateType = "APT:VL";
//        String tradeType = "LEASE:DEAL";
//        int priceMin = 0;
//        int priceMax = 1000;
//        double leftLon = 127.084395;
//        double rightLon = 127.084395;
//        double topLat = 37.568092;
//        double bottomLat = 37.568092;
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/properties")
//                        .param("realEstateType", realEstateType)
//                        .param("tradeType", tradeType)
//                        .param("priceMin", String.valueOf(priceMin))
//                        .param("priceMax", String.valueOf(priceMax))
//                        .param("leftLon", String.valueOf(leftLon))
//                        .param("rightLon", String.valueOf(rightLon))
//                        .param("topLat", String.valueOf(topLat))
//                        .param("bottomLat", String.valueOf(bottomLat))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.response[0].cnt").value(69))
//                .andExpect(jsonPath("$.response[0].price").value(329))
//                .andExpect(jsonPath("$.response[0].area").value(35))
//                .andExpect(jsonPath("$.response[0].latitude").value(37.568092))
//                .andExpect(jsonPath("$.response[0].longitude").value(127.084395));
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매물 상세 조회 API 테스트")
//    void findPropertyDetail() throws Exception{
//        String realEstateType = "VL";
//        String tradeType = "LEASE:DEAL";
//        int priceMin = 0;
//        int priceMax = 1000;
//        double longitude = 127.044173;
//        double latitude = 37.476033;
//        int page = 0;
//        int size = 5;
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/properties/details")
//                        .param("realEstateType", realEstateType)
//                        .param("tradeType", tradeType)
//                        .param("priceMin", String.valueOf(priceMin))
//                        .param("priceMax", String.valueOf(priceMax))
//                        .param("longitude", String.valueOf(longitude))
//                        .param("latitude", String.valueOf(latitude))
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size))
//                .contentType(MediaType.APPLICATION_JSON));
////                .andExpect(status().isOk());
////                .andExpect(jsonPath("$.response.content").isArray())
////                .andExpect(jsonPath("$.response.paginationInfo").exists())
////                .andExpect(jsonPath("$.response.paginationInfo.hasNext").value(true))
////                .andExpect(jsonPath("$.response.paginationInfo.pageNumber").value(0))
////                .andExpect(jsonPath("$.response.paginationInfo.pageSize").value(5))
////                .andExpect(jsonPath("$.response.paginationInfo.totalElements").value(5));
//    }

    @Test
    void findPropertyAreaPrice() {

    }
}