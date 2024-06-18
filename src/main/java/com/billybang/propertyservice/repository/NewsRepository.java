package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.News;
import com.billybang.propertyservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
