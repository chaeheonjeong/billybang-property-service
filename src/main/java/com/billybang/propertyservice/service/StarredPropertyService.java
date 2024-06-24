package com.billybang.propertyservice.service;

import com.billybang.propertyservice.client.UserServiceClient;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.model.entity.StarredProperty;
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
    private UserServiceClient userServiceClient;

    public void addStarredProperty(Long propertyId){
        Long userId = getUserId();
        StarredProperty starredProperty = new StarredProperty();
        starredProperty.setPropertyId(propertyId);
        starredProperty.setUserId(userId);
        starredPropertyRepository.save(starredProperty);
    }

    public List<Property> searchStarredProperty(){
        Long userId = getUserId();
        System.out.println(userId);
        List<StarredProperty> starredProperties = starredPropertyRepository.findByUserId(userId);
        List<Long> propertyIds = starredProperties.stream()
                .map(StarredProperty::getPropertyId)
                .toList();

        return propertyRepository.findAllById(propertyIds);
    }

    public void deleteStarredProperty(Long propertyId){
        Long userId = getUserId();
        starredPropertyRepository.deleteByUserIdAndPropertyId(userId, propertyId);
    }

    private Long getUserId() {
        return userServiceClient.getUserInfo().getResponse().getUserId();
    }
}
