package com.billybang.propertyservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest_V1 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("zoom이 5이하일 때 매물찾기")
    void findPropertiesZoom5() throws Exception {

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("realEstateType", "APT");
        request.add("tradeType", "DEAL");
        request.add("dealPriceMin", "5700");
        request.add("dealPriceMax", "6000");
        request.add("leftLon", "127.058642");
        request.add("rightLon", "127.158642");
        request.add("topLat", "37.51852");
        request.add("bottomLat", "37.41852");
        request.add("zoom", "5");

        mockMvc.perform(MockMvcRequestBuilders.get("/properties")
                        .params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response", hasSize(6)))
                .andExpect(jsonPath("$.response[0].name", equalTo(null)));
    }

    @Test
    @DisplayName("zoom이 6일 때 매물 찾기")
    void findPropertiesZoom6() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("realEstateType", "APT");
        request.add("tradeType", "DEAL");
        request.add("dealPriceMin", "5700");
        request.add("dealPriceMax", "6000");
        request.add("leftLon", "127.058642");
        request.add("rightLon", "127.558642");
        request.add("topLat", "37.51852");
        request.add("bottomLat", "37.01852");
        request.add("zoom", "6");

        mockMvc.perform(MockMvcRequestBuilders.get("/properties")
                .params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response", hasSize(2)))
                .andExpect(jsonPath("$.response[0].area", equalTo(null)));
    }

    @Test
    @DisplayName("zoom이 7일 때 매물 찾기")
    void findPropertiesZoom7() throws Exception{
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("realEstateType", "APT");
        request.add("tradeType", "DEAL");
        request.add("dealPriceMin", "5700");
        request.add("dealPriceMax", "6000");
        request.add("leftLon", "127.058642");
        request.add("rightLon", "128.058642");
        request.add("topLat", "37.51852");
        request.add("bottomLat", "36.51852");
        request.add("zoom", "7");

        mockMvc.perform(MockMvcRequestBuilders.get("/properties")
                .params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response", hasSize(1)))
                .andExpect(jsonPath("$.response[0].area", equalTo(null)));
    }


    @Test
    @DisplayName("매물 상세 정보 조회")
    void findPropertyDetail() throws Exception{
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("realEstateType", "APT");
        request.add("tradeType", "DEAL");
        request.add("dealPriceMin", "5000");
        request.add("dealPriceMax", "5700");
        request.add("latitude", "37.51852");
        request.add("longitude", "127.058642");

        mockMvc.perform(MockMvcRequestBuilders.get("/properties/details")
                .params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.content").isArray())
                .andExpect(jsonPath("$.response.content", hasSize(9)))
                .andExpect(jsonPath("$.response.paginationInfo").exists());
    }

    @Test
    @DisplayName("매물 거래 방식, 면적, 가격 조회")
    void findPropertyAreaPrice() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/properties/{propertyId}/area-price", 2424015513L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.tradeType").value("DEAL"))
                .andExpect(jsonPath("$.response.area2").value(156))
                .andExpect(jsonPath("$.response.price").isNumber())
                .andExpect(jsonPath("$.response.articleName").value("아이파크삼성"));
    }

    @Test
    @DisplayName("매물 id로 조회")
    void findPropertyById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/properties/{propertyId}", 2424015513L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isNotEmpty());
    }
}
