package com.billybang.propertyservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyAreaPriceResponseDto {
    String trade_type;
    int area2;
    int price;
}
