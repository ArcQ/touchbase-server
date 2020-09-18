package com.kf.touchbase.service.mapper;


import com.kf.touchbase.domain.*;
import com.kf.touchbase.service.dto.BaseJoinDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BaseJoin} and its DTO {@link BaseJoinDTO}.
 */
@Mapper(componentModel = "jsr330", uses = {BaseMapper.class})
public interface BaseJoinMapper extends EntityMapper<BaseJoinDTO, BaseJoin> {

    @Mapping(source = "base.id", target = "baseId")
    BaseJoinDTO toDto(BaseJoin baseJoin);

    @Mapping(source = "baseId", target = "base")
    BaseJoin toEntity(BaseJoinDTO baseJoinDTO);

    default BaseJoin fromId(Long id) {
        if (id == null) {
            return null;
        }
        BaseJoin baseJoin = new BaseJoin();
        baseJoin.setId(id);
        return baseJoin;
    }
}
