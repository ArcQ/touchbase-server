package com.kf.touchbase.service.mapper;


import com.kf.touchbase.domain.*;
import com.kf.touchbase.service.dto.MissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mission} and its DTO {@link MissionDTO}.
 */
@Mapper(componentModel = "jsr330", uses = {})
public interface MissionMapper extends EntityMapper<MissionDTO, Mission> {



    default Mission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mission mission = new Mission();
        mission.setId(id);
        return mission;
    }
}
