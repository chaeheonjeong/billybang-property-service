package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PropertyController {
    private PropertyService propertyService;

//    @GetMapping("/properties")
//    public ApiUtils.ApiResult findProperty(){
//
//    }
}
