package com.billybang.propertyservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResponseDto {
    private Long representativeId;
    private Integer cnt;
    private Integer price;
    private Integer area;
    private Double latitude;
    private Double longitude;
}
