package com.billybang.propertyservice.model.dto.response;

import com.billybang.propertyservice.model.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyDetailResponseDto {
    private Long propertyId;
    private String articleName;
    private String buildingName;
    private String realEstateType;
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
    private double latitude;
    private double longitude;
    private String articleUrl;
    private String representativeImgUrl;
    private int sameAddrCnt;
    private String realtorName;
    private String cpName;
    private String tags;
    private String direction;
    private boolean isStarred;

    public PropertyDetailResponseDto(Property property, boolean isStarred) {
        this.propertyId = property.getId();
        this.articleName = property.getArticleName();
        this.buildingName = property.getBuildingName();
        this.realEstateType = property.getRealEstateType();
        this.tradeType = property.getTradeType();
        this.price = property.getPrice();
        this.latitude = property.getLatitude();
        this.longitude = property.getLongitude();
        this.isStarred = isStarred;
    }
}
