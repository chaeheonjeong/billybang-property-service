package com.billybang.propertyservice.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StarredPropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private

    @AfterEach
    void rollback(){

    }

    @Test
    @DisplayName("매물 즐겨찾기 추가")
    @Transactional
    void addStarredProperty() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/properties/stars/{propertyId}", 2424015513L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response", nullValue()));
    }

    @Test
    @DisplayName("매물 즐겨찾기 조회")
    void searchStarredProperty() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/properties/stars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isArray());
    }

    @Test
    @DisplayName("매물 즐겨찾기 삭제")
    @Transactional
    void deleteStarredProperty() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/properties/stars/{propertyId}", 2424015513L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response", nullValue()));
    }
}
