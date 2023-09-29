package com.mkh.healthunits.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeekDayDateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeekDayDate.class);
        WeekDayDate weekDayDate1 = new WeekDayDate();
        weekDayDate1.setId(1L);
        WeekDayDate weekDayDate2 = new WeekDayDate();
        weekDayDate2.setId(weekDayDate1.getId());
        assertThat(weekDayDate1).isEqualTo(weekDayDate2);
        weekDayDate2.setId(2L);
        assertThat(weekDayDate1).isNotEqualTo(weekDayDate2);
        weekDayDate1.setId(null);
        assertThat(weekDayDate1).isNotEqualTo(weekDayDate2);
    }
}
