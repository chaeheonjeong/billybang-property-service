package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.statistic.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}
