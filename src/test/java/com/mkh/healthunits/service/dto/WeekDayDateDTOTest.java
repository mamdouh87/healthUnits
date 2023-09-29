package com.mkh.healthunits.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeekDayDateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeekDayDateDTO.class);
        WeekDayDateDTO weekDayDateDTO1 = new WeekDayDateDTO();
        weekDayDateDTO1.setId(1L);
        WeekDayDateDTO weekDayDateDTO2 = new WeekDayDateDTO();
        assertThat(weekDayDateDTO1).isNotEqualTo(weekDayDateDTO2);
        weekDayDateDTO2.setId(weekDayDateDTO1.getId());
        assertThat(weekDayDateDTO1).isEqualTo(weekDayDateDTO2);
        weekDayDateDTO2.setId(2L);
        assertThat(weekDayDateDTO1).isNotEqualTo(weekDayDateDTO2);
        weekDayDateDTO1.setId(null);
        assertThat(weekDayDateDTO1).isNotEqualTo(weekDayDateDTO2);
    }
}
