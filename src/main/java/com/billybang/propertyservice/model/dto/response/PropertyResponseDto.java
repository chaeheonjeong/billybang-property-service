package com.billybang.propertyservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResponseDto {
    private Long cnt;
    private int price;
    private int area;
    private double latitude;
    private double longitude;
}
