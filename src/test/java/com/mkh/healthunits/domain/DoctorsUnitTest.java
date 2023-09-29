package com.mkh.healthunits.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorsUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorsUnit.class);
        DoctorsUnit doctorsUnit1 = new DoctorsUnit();
        doctorsUnit1.setId(1L);
        DoctorsUnit doctorsUnit2 = new DoctorsUnit();
        doctorsUnit2.setId(doctorsUnit1.getId());
        assertThat(doctorsUnit1).isEqualTo(doctorsUnit2);
        doctorsUnit2.setId(2L);
        assertThat(doctorsUnit1).isNotEqualTo(doctorsUnit2);
        doctorsUnit1.setId(null);
        assertThat(doctorsUnit1).isNotEqualTo(doctorsUnit2);
    }
}
