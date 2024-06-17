package com.billybang.propertyservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "starred_properties")
public class StarredProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "starred_property_id")
    private Long id;
    private Long userId;
    private Long propertyId;
}