package com.billybang.propertyservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="news_id")
    private Long id;
    private String imgUrl;
    private String newsUrl;
    private String newsTitle;
    @Column(length = 500)
    private String newsSummary;
    @Column(length = 50)
    private String company;
    @Column(length = 20)
    private String date;

    public News(String newsTitle, String imgUrl, String newsUrl, String newsSummary, String company, String date) {
        this.newsTitle = newsTitle;
        this.imgUrl = imgUrl;
        this.newsUrl = newsUrl;
        this.newsSummary = newsSummary;
        this.company = company;
        this.date = date;
    }

    public News() {

    }
}
