package com.billybang.propertyservice.repository;


import com.billybang.propertyservice.model.entity.DistrictNews;
import feign.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictNewsRepository extends JpaRepository<DistrictNews, Long> {
    @Query("SELECT dn.newsId FROM DistrictNews dn JOIN News n ON dn.newsId = n.id WHERE dn.districtId = :districtId ORDER BY n.date DESC")
    List<Long> findLatestNewsIdsByDistrictId(@Param("districtId") Long districtId, Pageable pageable);
}
