package com.mkh.healthunits.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationsMapperTest {

    private NotificationsMapper notificationsMapper;

    @BeforeEach
    public void setUp() {
        notificationsMapper = new NotificationsMapperImpl();
    }
}
