package com.mkh.healthunits.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorPassImgsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorPassImgs.class);
        DoctorPassImgs doctorPassImgs1 = new DoctorPassImgs();
        doctorPassImgs1.setId(1L);
        DoctorPassImgs doctorPassImgs2 = new DoctorPassImgs();
        doctorPassImgs2.setId(doctorPassImgs1.getId());
        assertThat(doctorPassImgs1).isEqualTo(doctorPassImgs2);
        doctorPassImgs2.setId(2L);
        assertThat(doctorPassImgs1).isNotEqualTo(doctorPassImgs2);
        doctorPassImgs1.setId(null);
        assertThat(doctorPassImgs1).isNotEqualTo(doctorPassImgs2);
    }
}
