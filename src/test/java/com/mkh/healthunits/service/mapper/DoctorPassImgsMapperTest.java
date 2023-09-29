package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorPassImgsMapperTest {

    private DoctorPassImgsMapper doctorPassImgsMapper;

    @BeforeEach
    public void setUp() {
        doctorPassImgsMapper = new DoctorPassImgsMapperImpl();
    }
}
