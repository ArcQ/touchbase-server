package com.kf.touchbase.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kf.touchbase.web.rest.TestUtil;

public class BaseMemberDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseMemberDTO.class);
        BaseMemberDTO baseMemberDTO1 = new BaseMemberDTO();
        baseMemberDTO1.setId(1L);
        BaseMemberDTO baseMemberDTO2 = new BaseMemberDTO();
        assertThat(baseMemberDTO1).isNotEqualTo(baseMemberDTO2);
        baseMemberDTO2.setId(baseMemberDTO1.getId());
        assertThat(baseMemberDTO1).isEqualTo(baseMemberDTO2);
        baseMemberDTO2.setId(2L);
        assertThat(baseMemberDTO1).isNotEqualTo(baseMemberDTO2);
        baseMemberDTO1.setId(null);
        assertThat(baseMemberDTO1).isNotEqualTo(baseMemberDTO2);
    }
}
