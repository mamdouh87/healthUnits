package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitImgsMapperTest {

    private UnitImgsMapper unitImgsMapper;

    @BeforeEach
    public void setUp() {
        unitImgsMapper = new UnitImgsMapperImpl();
    }
}
