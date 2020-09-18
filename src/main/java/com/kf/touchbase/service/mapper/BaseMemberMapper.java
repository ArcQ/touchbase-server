package com.kf.touchbase.service.mapper;


import com.kf.touchbase.domain.*;
import com.kf.touchbase.service.dto.BaseMemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BaseMember} and its DTO {@link BaseMemberDTO}.
 */
@Mapper(componentModel = "jsr330", uses = {UserMapper.class, BaseMapper.class})
public interface BaseMemberMapper extends EntityMapper<BaseMemberDTO, BaseMember> {

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "base.id", target = "baseId")
    BaseMemberDTO toDto(BaseMember baseMember);

    @Mapping(source = "memberId", target = "member")
    @Mapping(source = "baseId", target = "base")
    BaseMember toEntity(BaseMemberDTO baseMemberDTO);

    default BaseMember fromId(Long id) {
        if (id == null) {
            return null;
        }
        BaseMember baseMember = new BaseMember();
        baseMember.setId(id);
        return baseMember;
    }
}
