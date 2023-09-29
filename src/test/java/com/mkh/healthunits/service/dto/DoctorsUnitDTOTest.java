package com.mkh.healthunits.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorsUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorsUnitDTO.class);
        DoctorsUnitDTO doctorsUnitDTO1 = new DoctorsUnitDTO();
        doctorsUnitDTO1.setId(1L);
        DoctorsUnitDTO doctorsUnitDTO2 = new DoctorsUnitDTO();
        assertThat(doctorsUnitDTO1).isNotEqualTo(doctorsUnitDTO2);
        doctorsUnitDTO2.setId(doctorsUnitDTO1.getId());
        assertThat(doctorsUnitDTO1).isEqualTo(doctorsUnitDTO2);
        doctorsUnitDTO2.setId(2L);
        assertThat(doctorsUnitDTO1).isNotEqualTo(doctorsUnitDTO2);
        doctorsUnitDTO1.setId(null);
        assertThat(doctorsUnitDTO1).isNotEqualTo(doctorsUnitDTO2);
    }
}
