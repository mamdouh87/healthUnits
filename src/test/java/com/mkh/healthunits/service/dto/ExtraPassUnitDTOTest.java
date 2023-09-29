package com.mkh.healthunits.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExtraPassUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraPassUnitDTO.class);
        ExtraPassUnitDTO extraPassUnitDTO1 = new ExtraPassUnitDTO();
        extraPassUnitDTO1.setId(1L);
        ExtraPassUnitDTO extraPassUnitDTO2 = new ExtraPassUnitDTO();
        assertThat(extraPassUnitDTO1).isNotEqualTo(extraPassUnitDTO2);
        extraPassUnitDTO2.setId(extraPassUnitDTO1.getId());
        assertThat(extraPassUnitDTO1).isEqualTo(extraPassUnitDTO2);
        extraPassUnitDTO2.setId(2L);
        assertThat(extraPassUnitDTO1).isNotEqualTo(extraPassUnitDTO2);
        extraPassUnitDTO1.setId(null);
        assertThat(extraPassUnitDTO1).isNotEqualTo(extraPassUnitDTO2);
    }
}
