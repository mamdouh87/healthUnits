package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorsUnitMapperTest {

    private DoctorsUnitMapper doctorsUnitMapper;

    @BeforeEach
    public void setUp() {
        doctorsUnitMapper = new DoctorsUnitMapperImpl();
    }
}
