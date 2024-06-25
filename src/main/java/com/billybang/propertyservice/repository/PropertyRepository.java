package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long>{

    @Query("select p from Property p where p.latitude <= :topLat " +
            "and p.latitude >= :bottomLat " +
            "and p.longitude <= :rightLon " +
            "and p.longitude >= :leftLon")
    List<Property> findPropertiesByRange(@Param("topLat") double topLat,
                                         @Param("bottomLat") double bottomLat,
                                         @Param("rightLon") double rightLon,
                                         @Param("leftLon") double leftLon);


    @Query("select p from Property p where p.districtId in :districtIds")
    List<Property> findByDistrictIds(@Param("districtIds") List<Long> districtIds);
}
