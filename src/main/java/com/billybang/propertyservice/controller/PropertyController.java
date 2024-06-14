package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.model.dto.request.SearchPropertyDetailRequestDto;
import com.billybang.propertyservice.model.dto.request.SearchPropertyRequestDto;
import com.billybang.propertyservice.dto.response.SearchPropertyResponseDto;
import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PropertyController {
    private PropertyService propertyService;

    @GetMapping("/properties")
    public ResponseEntity<List<SearchPropertyResponseDto>> findProperty(@ModelAttribute SearchPropertyRequestDto searchPropertyRequestDto){
        List<SearchPropertyResponseDto> properties = propertyService.findPropertyList(searchPropertyRequestDto);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/properties/details")
    public ResponseEntity<List<Property>> findPropertyDetail(@ModelAttribute SearchPropertyDetailRequestDto searchPropertyDetailRequestDto){
        List<Property> properties = propertyService.findPropertyDetailList(searchPropertyDetailRequestDto);
        return ResponseEntity.ok(properties);
    }
}
