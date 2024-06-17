package com.billybang.propertyservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "areas")
public class Area {
    @Id
    @Column(name = "area_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long districtId;
    @Column(length = 30)
    private String areaName;
    private double populationDensity;
}
