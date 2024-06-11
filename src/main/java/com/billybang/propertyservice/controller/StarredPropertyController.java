package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.service.StarredPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StarredPropertyController {
    private StarredPropertyService starredPropertyService;


}
