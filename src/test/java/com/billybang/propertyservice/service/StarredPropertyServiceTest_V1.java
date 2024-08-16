package com.billybang.propertyservice.service;

import com.billybang.propertyservice.client.UserServiceClient;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.model.entity.StarredProperty;
import com.billybang.propertyservice.repository.PropertyRepository;
import com.billybang.propertyservice.repository.StarredPropertyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class StarredPropertyServiceTest_V1 {

    @Autowired
    private StarredPropertyService starredPropertyService;
    @Autowired
    private StarredPropertyRepository starredPropertyRepository;

    @BeforeEach
    void setUp(){
        Long userId = 1234L;
        StarredProperty p1 = new StarredProperty();
        p1.setUserId(userId);
        p1.setPropertyId(2424015513L);

        StarredProperty p2 = new StarredProperty();
        p2.setUserId(userId);
        p2.setPropertyId(2424341619L);

        starredPropertyRepository.save(p1);
        starredPropertyRepository.save(p2);
    }

    @Test
    @DisplayName("매물 즐겨찾기 추가")
    void addStarredProperty(){
        //given
        Long userId = 1234L;
        Long propertyId = 2424573914L;

        //when
        starredPropertyService.addStarredProperty(propertyId);
        List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);

        //then
        assertThat(starredProperties)
                .isNotEmpty()
                .anyMatch(starredProperty -> starredProperty.getPropertyId().equals(propertyId));
    }

    @Test
    @DisplayName("매물 즐겨찾기 조회")
    void searchStarredProperty(){
        List<Property> starredProperties = starredPropertyService.searchStarredProperty();

        assertThat(starredProperties)
                .isNotEmpty()
                .extracting(Property::getId)
                .contains(2424015513L, 2424341619L);
    }

    @Test
    @DisplayName("매물 즐겨찾기 삭제")
    void deleteStarredProperty(){
        //given
        Long userId = 1234L;
        Long propertyId = 2424015513L;

        //when
        starredPropertyService.deleteStarredProperty(2424015513L);

        List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);
        assertThat(starredProperties)
                .extracting(StarredProperty::getPropertyId)
                .doesNotContain(propertyId);
    }
}
