package com.mkh.healthunits.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnitImgsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitImgs.class);
        UnitImgs unitImgs1 = new UnitImgs();
        unitImgs1.setId(1L);
        UnitImgs unitImgs2 = new UnitImgs();
        unitImgs2.setId(unitImgs1.getId());
        assertThat(unitImgs1).isEqualTo(unitImgs2);
        unitImgs2.setId(2L);
        assertThat(unitImgs1).isNotEqualTo(unitImgs2);
        unitImgs1.setId(null);
        assertThat(unitImgs1).isNotEqualTo(unitImgs2);
    }
}
