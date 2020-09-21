package com.kf.touchbase.service.mapper;


import com.kf.touchbase.domain.*;
import com.kf.touchbase.service.dto.BaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Base} and its DTO {@link BaseDTO}.
 */
@Mapper(componentModel = "jsr330", uses = {UserMapper.class})
public interface BaseMapper extends EntityMapper<BaseDTO, Base> {

    @Mapping(source = "creator.id", target = "creatorId")
    BaseDTO toDto(Base base);

    @Mapping(source = "creatorId", target = "creator")
    @Mapping(target = "chats", ignore = true)
    @Mapping(target = "removeChats", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "removeMembers", ignore = true)
    Base toEntity(BaseDTO baseDTO);

    default Base fromId(Long id) {
        if (id == null) {
            return null;
        }
        Base base = new Base();
        base.setId(id);
        return base;
    }
}
