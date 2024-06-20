package com.billybang.propertyservice.model.dto.response;

import com.billybang.propertyservice.model.entity.Property;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyAreaPriceResponseDto {
    String tradeType;
    int area2;
    int price;
    String articleName;

    public static PropertyAreaPriceResponseDto toDto(Property property) {
        return PropertyAreaPriceResponseDto.builder()
                .tradeType(property.getTradeType())
                .area2(property.getArea2())
                .price(property.getPrice())
                .articleName(property.getArticleName())
                .build();
    }
}
