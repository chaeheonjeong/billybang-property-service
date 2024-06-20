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
    @NotNull(message = "min price is required.")
    private Integer priceMin;
    @NotNull(message = "max price is required.")
    private Integer priceMax;
    @NotNull(message = "left longitude is required.")
    private Double leftLon;
    @NotNull(message = "right longitude is required.")
    private Double rightLon;
    @NotNull(message = "top latitude is required.")
    private Double topLat;
    @NotNull(message = "bottom latitude is required.")
    private Double bottomLat;
}
