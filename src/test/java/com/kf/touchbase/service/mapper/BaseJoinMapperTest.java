package com.kf.touchbase.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseJoinMapperTest {

    private BaseJoinMapper baseJoinMapper;

    @BeforeEach
    public void setUp() {
        baseJoinMapper = new BaseJoinMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(baseJoinMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(baseJoinMapper.fromId(null)).isNull();
    }
}
