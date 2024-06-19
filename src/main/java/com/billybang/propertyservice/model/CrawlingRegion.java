package com.billybang.propertyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CrawlingRegion {
    private String cortarNo;
    private String districtName;
    private Long districtId;
}
