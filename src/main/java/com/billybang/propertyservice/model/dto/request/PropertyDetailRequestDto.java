package com.billybang.propertyservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyDetailRequestDto {
    @NotNull(message = "real estate  is required.")
    private String realEstateType;
    @NotNull(message = "trade type is required.")
    private String tradeType;
    @NotNull(message = "min price is required.")
    private int priceMin;
    @NotNull(message = "max price is required.")
    private int priceMax;
    @NotNull(message = "latitude is required.")
    private double latitude;
    @NotNull(message = "longitude is required.")
    private double longitude;
}
