package com.billybang.propertyservice.model.news;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DistrictNews {
    @Id
    @Column(name="district_news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long districtId;
    private Long newsId;

    public DistrictNews(Long districtId, Long newsId) {
        this.districtId = districtId;
        this.newsId =newsId;
    }

    public DistrictNews() {

    }
}
