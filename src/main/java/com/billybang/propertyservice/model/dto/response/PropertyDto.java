package com.billybang.propertyservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {
    private Long propertyId;
    private String articleName;
    private String buildingName;
    private String realEstate;
    private String tradeType;
    private String articleFeatureDesc;
    private String articleConfirmYmd;
    private String areaName;
    private int area1;
    private int area2;
    private String floorInfo;
    private int price;
    private String jibeonAddress;
    private String roadAddress;
    private float latitude;
    private float longitude;
    private String articleUrl;
    private String representativeImgUrl;
    private int sameAddrCnt;
    private String realtorName;
    private String cpName;
}
