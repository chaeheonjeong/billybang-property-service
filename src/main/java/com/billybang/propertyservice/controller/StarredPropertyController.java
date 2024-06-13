package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.dto.request.StarredPropertyReqeustDto;
import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.service.StarredPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StarredPropertyController {
    private StarredPropertyService starredPropertyService;

    @Autowired
    public StarredPropertyController(StarredPropertyService starredPropertyService){
        this.starredPropertyService = starredPropertyService;
    }
    private Long userId = 888L;

    @PostMapping("/properties/stars")
    public ResponseEntity<?> addStarredProperty(@RequestBody StarredPropertyReqeustDto starredPropertyReqeustDto){
        starredPropertyService.addStarredProperty(starredPropertyReqeustDto, userId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/properties/stars")
    public ResponseEntity<List<Property>> searchStarredProperty(){
        List<Property> starredPropertyList = starredPropertyService.searchStarredProperty(userId);
        return ResponseEntity.ok(starredPropertyList);
    }

    @DeleteMapping("/properties/stars")
    public ResponseEntity<?> deleteStarredProperty(@RequestBody StarredPropertyReqeustDto starredPropertyReqeustDto){
        starredPropertyService.deleteStarredProperty(starredPropertyReqeustDto, userId);
        return ResponseEntity.ok(null);
    }
}
