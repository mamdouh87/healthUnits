package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeekDayDateMapperTest {

    private WeekDayDateMapper weekDayDateMapper;

    @BeforeEach
    public void setUp() {
        weekDayDateMapper = new WeekDayDateMapperImpl();
    }
}
