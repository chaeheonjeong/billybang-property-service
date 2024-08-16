package com.billybang.propertyservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PropertyDetailRequestDto {
    @NotNull(message = "real estate  is required.")
    private String realEstateType;
    @NotNull(message = "trade type is required.")
    private String tradeType;
    private Integer dealPriceMin;
    private Integer dealPriceMax;
    private Integer leasePriceMin;
    private Integer leasePriceMax;
    @NotNull(message = "latitude is required.")
    private double latitude;
    @NotNull(message = "longitude is required.")
    private double longitude;
}
