package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyDataSaverRepository extends JpaRepository<Property, Long> {
    List<Property> findByRoadAddressIsNull();
    List<Property> findByDistrictIdIsNull();
}
