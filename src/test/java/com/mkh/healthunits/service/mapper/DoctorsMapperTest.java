package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorsMapperTest {

    private DoctorsMapper doctorsMapper;

    @BeforeEach
    public void setUp() {
        doctorsMapper = new DoctorsMapperImpl();
    }
}
