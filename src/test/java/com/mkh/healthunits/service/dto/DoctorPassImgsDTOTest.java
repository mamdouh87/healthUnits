package com.mkh.healthunits.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorPassImgsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorPassImgsDTO.class);
        DoctorPassImgsDTO doctorPassImgsDTO1 = new DoctorPassImgsDTO();
        doctorPassImgsDTO1.setId(1L);
        DoctorPassImgsDTO doctorPassImgsDTO2 = new DoctorPassImgsDTO();
        assertThat(doctorPassImgsDTO1).isNotEqualTo(doctorPassImgsDTO2);
        doctorPassImgsDTO2.setId(doctorPassImgsDTO1.getId());
        assertThat(doctorPassImgsDTO1).isEqualTo(doctorPassImgsDTO2);
        doctorPassImgsDTO2.setId(2L);
        assertThat(doctorPassImgsDTO1).isNotEqualTo(doctorPassImgsDTO2);
        doctorPassImgsDTO1.setId(null);
        assertThat(doctorPassImgsDTO1).isNotEqualTo(doctorPassImgsDTO2);
    }
}
