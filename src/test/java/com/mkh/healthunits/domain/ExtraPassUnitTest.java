package com.mkh.healthunits.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mkh.healthunits.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExtraPassUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraPassUnit.class);
        ExtraPassUnit extraPassUnit1 = new ExtraPassUnit();
        extraPassUnit1.setId(1L);
        ExtraPassUnit extraPassUnit2 = new ExtraPassUnit();
        extraPassUnit2.setId(extraPassUnit1.getId());
        assertThat(extraPassUnit1).isEqualTo(extraPassUnit2);
        extraPassUnit2.setId(2L);
        assertThat(extraPassUnit1).isNotEqualTo(extraPassUnit2);
        extraPassUnit1.setId(null);
        assertThat(extraPassUnit1).isNotEqualTo(extraPassUnit2);
    }
}
