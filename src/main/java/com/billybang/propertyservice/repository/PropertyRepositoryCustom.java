package com.billybang.propertyservice.repository;
import com.billybang.propertyservice.model.entity.Property;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PropertyRepositoryCustom {
    List<Property> findPropertiesByRange(
            String[] realEstateTypes,
            String[] tradeTypes,
            Integer leasePriceMin,
            Integer leasePriceMax,
            Integer dealPriceMin,
            Integer dealPriceMax,
            double leftLon,
            double rightLon,
            double topLat,
            double bottomLat
    );

    Slice<Property> findPropertiesByExactLocation(
            String[] realEstateTypes,
            String[] tradeTypes,
            Integer leasePriceMin,
            Integer leasePriceMax,
            Integer dealPriceMin,
            Integer dealPriceMax,
            double longitude,
            double latitude,
            Pageable pageable
    );

    List<Tuple> findStat(
            List<Long> ids,
            String[] realEstateTypes,
            String[] tradeTypes,
            Integer leasePriceMin,
            Integer leasePriceMax,
            Integer dealPriceMin,
            Integer dealPriceMax,
            String type
    );
}
