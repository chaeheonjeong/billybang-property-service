package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.entity.StarredProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarredPropertyRepository extends JpaRepository<StarredProperty, Long> {
    List<StarredProperty> findByUserId(Long userId);
    void deleteByUserIdAndPropertyId(Long userId, Long propertyId);
}