package com.kf.touchbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kf.touchbase.web.rest.TestUtil;

public class BaseMemberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseMember.class);
        BaseMember baseMember1 = new BaseMember();
        baseMember1.setId(1L);
        BaseMember baseMember2 = new BaseMember();
        baseMember2.setId(baseMember1.getId());
        assertThat(baseMember1).isEqualTo(baseMember2);
        baseMember2.setId(2L);
        assertThat(baseMember1).isNotEqualTo(baseMember2);
        baseMember1.setId(null);
        assertThat(baseMember1).isNotEqualTo(baseMember2);
    }
}
