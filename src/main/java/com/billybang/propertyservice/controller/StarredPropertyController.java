package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.model.dto.request.PropertyIdRequestDto;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.service.StarredPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StarredPropertyController {

    private StarredPropertyService starredPropertyService;

    @PostMapping("/properties/stars")
    public ResponseEntity<ApiResult<?>> addStarredProperty(@RequestBody PropertyIdRequestDto requestDto){
        starredPropertyService.addStarredProperty(requestDto);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/properties/stars")
    public ResponseEntity<ApiResult<List<Property>>> searchStarredProperty(){
        List<Property> starredPropertyList = starredPropertyService.searchStarredProperty();
        return ResponseEntity.ok(ApiUtils.success(starredPropertyList));
    }

    @DeleteMapping("/properties/stars")
    public ResponseEntity<ApiResult<?>> deleteStarredProperty(@RequestBody PropertyIdRequestDto requestDto){
        starredPropertyService.deleteStarredProperty(requestDto);
        return ResponseEntity.ok(null);
    }
}
