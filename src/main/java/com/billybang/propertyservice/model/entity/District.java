package com.billybang.propertyservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "districts")
@ToString
public class District {
    @Id
    @Column(name = "district_id")
    private Long id;
    @Column(length = 10)
    private String districtName;
    private float individualIncome;
    @Column(name = "population_0s")
    private int population0s;
    @Column(name = "population_10s")
    private int population10s;
    @Column(name = "population_20s")
    private int population20s;
    @Column(name = "population_30s")
    private int population30s;
    @Column(name = "population_40s")
    private int population40s;
    @Column(name = "population_50s")
    private int population50s;
    @Column(name = "population_60s")
    private int population60s;
    @Column(name = "population_70_over")
    private int population70Over;
    private int crimeCount;
}
