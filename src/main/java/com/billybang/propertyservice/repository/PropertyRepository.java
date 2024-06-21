package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.dto.response.PropertyDetailResponseDto;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.model.dto.response.PropertyResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query(value = "SELECT p " +
            "FROM Property p WHERE " +
            "p.realEstateType IN :realEstateTypes AND " +
            "p.tradeType IN :tradeTypes AND " +
            "p.price >= :priceMin AND " +
            "p.price <= :priceMax AND " +
            "p.longitude >= :leftLon AND " +
            "p.longitude <= :rightLon AND " +
            "p.latitude <= :topLat AND " +
            "p.latitude >= :bottomLat")
    List<Property> findPropertiesByRange(
            @Param("realEstateTypes") String[] realEstateType,
            @Param("tradeTypes") String[] tradeType,
            @Param("priceMin") int priceMin,
            @Param("priceMax") int priceMax,
            @Param("leftLon") double leftLon,
            @Param("rightLon") double rightLon,
            @Param("topLat") double topLat,
            @Param("bottomLat") double bottomLat
    );

    @Query("SELECT p " +
            "FROM Property p WHERE " +
            "p.realEstateType IN :realEstateTypes AND " +
            "p.tradeType IN :tradeTypes AND " +
            "p.price >= :priceMin AND " +
            "p.price <= :priceMax AND " +
            "p.longitude = :longitude AND " +
            "p.latitude = :latitude ")
    Slice<Property> findPropertiesByExactLocation(
            @Param("realEstateTypes") String[] realEstateTypes,
            @Param("tradeTypes") String[] tradeTypes,
            @Param("priceMin") int priceMin,
            @Param("priceMax") int priceMax,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            Pageable pageable);
}
