package com.billybang.propertyservice.model.dto.response;

import com.billybang.propertyservice.model.entity.Property;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
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
    private Integer area1;
    private Integer area2;
    private String floorInfo;
    private Integer price;
    private String jibeonAddress;
    private String roadAddress;
    private Double latitude;
    private Double longitude;
    private String articleUrl;
    private String representativeImgUrl;
    private Integer sameAddrCnt;
    private String realtorName;
    private String cpName;
    private String tags;
    private String direction;
    private Boolean isStarred;

    public PropertyDetailResponseDto(Property property, Boolean isStarred) {
        this.propertyId = property.getId();
        this.articleName = property.getArticleName();
        this.buildingName = property.getBuildingName();
        this.realEstateType = property.getRealEstateType();
        this.tradeType = property.getTradeType();
        this.articleFeatureDesc = property.getArticleFeatureDesc();
        this.articleConfirmYmd = property.getArticleConfirmYmd();
        this.areaName = property.getAreaName();
        this.area1 = property.getArea1();
        this.area2 = property.getArea2();
        this.floorInfo = property.getFloorInfo();
        this.price = property.getPrice();
        this.jibeonAddress = property.getJibeonAddress();
        this.roadAddress = property.getRoadAddress();
        this.latitude = property.getLatitude();
        this.longitude = property.getLongitude();
        this.articleUrl = property.getArticleUrl();
        this.representativeImgUrl = property.getRepresentativeImgUrl();
        this.sameAddrCnt = property.getSameAddrCnt();
        this.realtorName = property.getRealtorName();
        this.cpName = property.getCpName();
        this.tags = property.getTags();
        this.direction = property.getDirection();
        this.isStarred = isStarred;
    }
}
