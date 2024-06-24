package com.billybang.propertyservice.repository;

import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.model.entity.QProperty;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PropertyRepositoryCustomImpl implements PropertyRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;
    private final QProperty property = QProperty.property;

    @Override
    public List<Property> findPropertiesByRange(
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
    ) {

        BooleanBuilder builder = new BooleanBuilder();
        List<String> tradeTypeList = Arrays.asList(tradeTypes);
        builder.and(property.realEstateType.in(realEstateTypes));
        builder.and(property.longitude.between(leftLon, rightLon));
        builder.and(property.latitude.between(bottomLat, topLat));
        addPriceCondition(builder, tradeTypeList, leasePriceMin, leasePriceMax, dealPriceMin, dealPriceMax);

        return queryFactory.selectFrom(property)
                .where(builder)
                .fetch();
    }

    @Override
    public Slice<Property> findPropertiesByExactLocation(
            String[] realEstateTypes,
            String[] tradeTypes,
            Integer leasePriceMin,
            Integer leasePriceMax,
            Integer dealPriceMin,
            Integer dealPriceMax,
            double longitude,
            double latitude,
            Pageable pageable
    ) {

        BooleanBuilder builder = new BooleanBuilder();
        List<String> tradeTypeList = Arrays.asList(tradeTypes);
        builder.and(property.realEstateType.in(realEstateTypes));
        builder.and(property.longitude.eq(longitude));
        builder.and(property.latitude.eq(latitude));
        addPriceCondition(builder, tradeTypeList, leasePriceMin, leasePriceMax, dealPriceMin, dealPriceMax);

        List<Property> content = queryFactory
                .selectFrom(property)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(content.size() - 1);
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public List<Tuple> findStat(
            List<Long> ids,
            String[] realEstateTypes,
            String[] tradeTypes,
            Integer leasePriceMin,
            Integer leasePriceMax,
            Integer dealPriceMin,
            Integer dealPriceMax,
            String type
    ) {

        BooleanBuilder builder = new BooleanBuilder();
        List<String> tradeTypeList = Arrays.asList(tradeTypes);
        if ("area".equals(type)) {
            builder.and(property.areaId.in(ids));
            builder.and(property.realEstateType.in(realEstateTypes));
            addPriceCondition(builder, tradeTypeList, leasePriceMin, leasePriceMax, dealPriceMin, dealPriceMax);

            return queryFactory
                    .select(property.areaId, property.count(), property.price.avg())
                    .from(property)
                    .where(builder)
                    .groupBy(property.areaId)
                    .fetch();
        } else {
            builder.and(property.districtId.in(ids));
            builder.and(property.realEstateType.in(realEstateTypes));
            addPriceCondition(builder, tradeTypeList, leasePriceMin, leasePriceMax, dealPriceMin, dealPriceMax);

            return queryFactory
                    .select(property.districtId, property.count(), property.price.avg())
                    .from(property)
                    .where(builder)
                    .groupBy(property.districtId)
                    .fetch();
        }
    }

    private void addPriceCondition(BooleanBuilder builder, List<String> tradeTypeList,
                                   Integer leasePriceMin, Integer leasePriceMax,
                                   Integer dealPriceMin, Integer dealPriceMax) {
        if (tradeTypeList.contains("DEAL") && tradeTypeList.contains("LEASE")) {
            // Contains both DEAL and LEASE
            builder.andAnyOf(
                    property.tradeType.eq("LEASE").and(property.price.between(leasePriceMin, leasePriceMax)),
                    property.tradeType.eq("DEAL").and(property.price.between(dealPriceMin, dealPriceMax))
            );
        } else if (tradeTypeList.contains("DEAL")) {
            // Contains only DEAL
            builder.and(property.tradeType.eq("DEAL"))
                    .and(property.price.between(dealPriceMin, dealPriceMax));
        } else if (tradeTypeList.contains("LEASE")) {
            // Contains only LEASE
            builder.and(property.tradeType.eq("LEASE"))
                    .and(property.price.between(leasePriceMin, leasePriceMax));
        }
    }
}
