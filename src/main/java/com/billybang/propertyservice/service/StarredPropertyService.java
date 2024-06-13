package com.billybang.propertyservice.service;

import com.billybang.propertyservice.dto.request.StarredPropertyReqeustDto;
import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.model.StarredProperty;
import com.billybang.propertyservice.repository.PropertyRepository;
import com.billybang.propertyservice.repository.StarredPropertyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StarredPropertyService {
    private StarredPropertyRepository starredPropertyRepository;
    private PropertyRepository propertyRepository;

    public void addStarredProperty(StarredPropertyReqeustDto starredPropertyReqeustDto, Long userId){
        StarredProperty starredProperty = new StarredProperty();
        starredProperty.setPropertyId(starredPropertyReqeustDto.getPropertyId());
        starredProperty.setUserId(userId);
        starredPropertyRepository.save(starredProperty);
    }

    public List<Property> searchStarredProperty(Long userId){
        List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);
        List<Long> propertyIds = starredProperties.stream()
                .map(StarredProperty::getPropertyId)
                .toList();

        return propertyRepository.findAllById(propertyIds);
    }

    public void deleteStarredProperty(StarredPropertyReqeustDto starredPropertyReqeustDto, Long userId){
        starredPropertyRepository.deleteByUserIdAndPropertyId(userId, starredPropertyReqeustDto.getPropertyId());
    }
}
