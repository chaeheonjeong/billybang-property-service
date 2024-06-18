package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
