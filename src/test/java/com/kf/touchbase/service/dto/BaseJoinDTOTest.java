package com.kf.touchbase.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kf.touchbase.web.rest.TestUtil;

public class BaseJoinDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseJoinDTO.class);
        BaseJoinDTO baseJoinDTO1 = new BaseJoinDTO();
        baseJoinDTO1.setId(1L);
        BaseJoinDTO baseJoinDTO2 = new BaseJoinDTO();
        assertThat(baseJoinDTO1).isNotEqualTo(baseJoinDTO2);
        baseJoinDTO2.setId(baseJoinDTO1.getId());
        assertThat(baseJoinDTO1).isEqualTo(baseJoinDTO2);
        baseJoinDTO2.setId(2L);
        assertThat(baseJoinDTO1).isNotEqualTo(baseJoinDTO2);
        baseJoinDTO1.setId(null);
        assertThat(baseJoinDTO1).isNotEqualTo(baseJoinDTO2);
    }
}
