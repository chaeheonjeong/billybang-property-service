package com.billybang.propertyservice.util;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataProcessor {

    @Autowired
    private EntityManager em;
}
