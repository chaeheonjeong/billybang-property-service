package com.billybang.propertyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PopulationDensity {
    private String areaName;
    private double density;
}
