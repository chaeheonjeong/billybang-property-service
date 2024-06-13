package com.billybang.propertyservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchPropertyResponseDto {
    private Long cnt;
    private int price;
    private int area;
    private double latitude;
    private double longitude;
}
