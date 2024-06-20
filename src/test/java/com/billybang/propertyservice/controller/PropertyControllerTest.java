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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PropertyControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockmvc;

    @BeforeEach
    void setUp(){
        objectMapper.registerModule(new JavaTimeModule());
    }

//    @Test
//    @Transactional
//    @DisplayName("매물 상품 API 테스트")
//    void findProperties() throws Exception{
//        PropertyRequestDto propertyRequestDto = PropertyRequestDto.builder();
//    }

    @Test
    void findPropertyDetail() {

    }

    @Test
    void findPropertyAreaPrice() {

    }
}