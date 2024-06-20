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

    public PropertyDetailResponseDto(Long id, String realEstateType, String tradeType, int price, double latitude, double longitude) {
        this.propertyId = id;
        this.realEstateType = realEstateType;
        this.tradeType = tradeType;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
