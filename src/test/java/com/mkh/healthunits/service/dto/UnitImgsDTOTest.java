package com.mkh.healthunits.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnitImgsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitImgsDTO.class);
        UnitImgsDTO unitImgsDTO1 = new UnitImgsDTO();
        unitImgsDTO1.setId(1L);
        UnitImgsDTO unitImgsDTO2 = new UnitImgsDTO();
        assertThat(unitImgsDTO1).isNotEqualTo(unitImgsDTO2);
        unitImgsDTO2.setId(unitImgsDTO1.getId());
        assertThat(unitImgsDTO1).isEqualTo(unitImgsDTO2);
        unitImgsDTO2.setId(2L);
        assertThat(unitImgsDTO1).isNotEqualTo(unitImgsDTO2);
        unitImgsDTO1.setId(null);
        assertThat(unitImgsDTO1).isNotEqualTo(unitImgsDTO2);
    }
}
