package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtraPassUnitMapperTest {

    private ExtraPassUnitMapper extraPassUnitMapper;

    @BeforeEach
    public void setUp() {
        extraPassUnitMapper = new ExtraPassUnitMapperImpl();
    }
}
