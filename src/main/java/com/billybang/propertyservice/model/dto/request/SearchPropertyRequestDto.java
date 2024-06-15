package com.billybang.propertyservice.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPropertyRequestDto {
    private String realEstateType;
    private String tradeType;
    private int priceMin;
    private int priceMax;
    private double leftLon;
    private double rightLon;
    private double topLat;
    private double bottomLat;
}
