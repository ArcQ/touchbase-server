package com.kf.touchbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kf.touchbase.web.rest.TestUtil;

public class BaseJoinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseJoin.class);
        BaseJoin baseJoin1 = new BaseJoin();
        baseJoin1.setId(1L);
        BaseJoin baseJoin2 = new BaseJoin();
        baseJoin2.setId(baseJoin1.getId());
        assertThat(baseJoin1).isEqualTo(baseJoin2);
        baseJoin2.setId(2L);
        assertThat(baseJoin1).isNotEqualTo(baseJoin2);
        baseJoin1.setId(null);
        assertThat(baseJoin1).isNotEqualTo(baseJoin2);
    }
}
