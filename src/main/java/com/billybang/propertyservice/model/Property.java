package com.billybang.propertyservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "properties")
public class Property {
    @Id
    @Column(name = "property_id", unique = true)
    private Long id;
    @Column(length = 30)
    private String articleName;
    @Column(length = 10)
    private String buildingName;
    @Column(length = 10)
    private String realEstateType;
    @Column(length = 10)
    private String tradeType;
    @Column(length = 50)
    private String articleFeatureDesc;
    @Column(length = 8)
    private String articleConfirmYmd;
    @Column(length = 10)
    private String areaName;
    private int area1;
    private int area2;
    @Column(length = 10)
    private String floorInfo;
    private int price;
    @Column(length = 50)
    private String jibeonAddress;
    @Column(length = 50)
    private String roadAddress;
    private double latitude;
    private double longitude;
    @Column(length = 200)
    private String articleUrl;
    @Column(length = 500)
    private String representativeImgUrl;
    private int sameAddrCnt;
    @Column(length = 100)
    private String realtorName;
    @Column(length = 30)
    private String cpName;
}
