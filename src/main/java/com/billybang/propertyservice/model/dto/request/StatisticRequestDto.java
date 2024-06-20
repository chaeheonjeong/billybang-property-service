package com.billybang.propertyservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticRequestDto {
    @NotNull(message = "district id is required.")
    private Long districtId;
}
