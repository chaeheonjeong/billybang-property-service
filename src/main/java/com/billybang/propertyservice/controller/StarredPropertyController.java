package com.billybang.propertyservice.controller;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.api.StarredPropertyApi;
import com.billybang.propertyservice.exception.common.BError;
import com.billybang.propertyservice.exception.common.CommonException;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.service.StarredPropertyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StarredPropertyController implements StarredPropertyApi {

    private StarredPropertyService starredPropertyService;

    public ResponseEntity<ApiResult<?>> addStarredProperty(Long propertyId){
        starredPropertyService.addStarredProperty(propertyId);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    public ResponseEntity<ApiResult<List<Property>>> searchStarredProperty(){
        List<Property> starredPropertyList = starredPropertyService.searchStarredProperty();
        return ResponseEntity.ok(ApiUtils.success(starredPropertyList));
    }

    public ResponseEntity<ApiResult<?>> deleteStarredProperty(Long propertyId){
        try {
            starredPropertyService.deleteStarredProperty(propertyId);
            return ResponseEntity.ok(null);
        } catch(Exception e){
            throw new CommonException(BError.FAIL, "delete starred property");
        }
    }
}
