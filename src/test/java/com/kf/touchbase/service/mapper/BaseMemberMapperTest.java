package com.kf.touchbase.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseMemberMapperTest {

    private BaseMemberMapper baseMemberMapper;

    @BeforeEach
    public void setUp() {
        baseMemberMapper = new BaseMemberMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(baseMemberMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(baseMemberMapper.fromId(null)).isNull();
    }
}
