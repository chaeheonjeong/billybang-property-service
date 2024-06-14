package com.billybang.propertyservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchPropertyDetailRequestDto {
    private String realEstateType;
    private String tradeType;
    private int priceMin;
    private int priceMax;
    private double latitude;
    private double longitude;
}
