package com.billybang.propertyservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestDto {
    @NotNull(message = "real estate type is required.")
    private String realEstateType;
    @NotNull(message = "trade type is required.")
    private String tradeType;
    private Integer dealPriceMin;
    private Integer dealPriceMax;
    private Integer leasePriceMin;
    private Integer leasePriceMax;
    @NotNull(message = "left longitude is required.")
    private Double leftLon;
    @NotNull(message = "right longitude is required.")
    private Double rightLon;
    @NotNull(message = "top latitude is required.")
    private Double topLat;
    @NotNull(message = "bottom latitude is required.")
    private Double bottomLat;
    @NotNull(message = "zoom is required.")
    private Integer zoom;
}
