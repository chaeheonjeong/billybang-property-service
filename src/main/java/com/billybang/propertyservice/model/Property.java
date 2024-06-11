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
@ToString
@Table(name = "properties")
public class Property {
    @Id
    @Column(unique = true, nullable = false)
    private Long propertyId;
    @Column(length = 30, nullable = false)
    private String articleName;
    @Column(length = 10, nullable = true)
    private String buildingName;
    @Column(length = 10, nullable = false)
    private String realEstateType;
    @Column(length = 10, nullable = false)
    private String tradeType;
    @Column(length = 50, nullable = true)
    private String articleFeatureDesc;
    @Column(length = 8, nullable = false)
    private String articleConfirmYmd;
    @Column(length = 10, nullable = true)
    private String areaName;
    @Column(nullable = false)
    private int area1;
    @Column(nullable = false)
    private int area2;
    @Column(length = 10, nullable = false)
    private String floorInfo;
    @Column(nullable = false)
    private int price;
    @Column(length = 50, nullable = true)
    private String jibeonAddress;
    @Column(length = 50, nullable = true)
    private String roadAddress;
    @Column(nullable = false)
    private float latitude;
    @Column(nullable = false)
    private float longitude;
    @Column(length = 200, nullable = false)
    private String articleUrl;
    @Column(length = 500, nullable = true)
    private String representativeImgUrl;
    @Column(nullable = false)
    private int sameAddrCnt;
    @Column(length = 100, nullable = false)
    private String realtorName;
    @Column(length = 30, nullable = false)
    private String cpName;
}
