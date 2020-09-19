package com.kf.touchbase.service.dto;

import io.micronaut.core.annotation.Introspected;
import java.io.Serializable;
import java.util.Objects;
import com.kf.touchbase.domain.enumeration.MissionType;

/**
 * A DTO for the {@link com.kf.touchbase.domain.Mission} entity.
 */
@Introspected
public class MissionDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String scoreReward;

    private MissionType missionType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScoreReward() {
        return scoreReward;
    }

    public void setScoreReward(String scoreReward) {
        this.scoreReward = scoreReward;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MissionDTO missionDTO = (MissionDTO) o;
        if (missionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), missionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MissionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", scoreReward='" + getScoreReward() + "'" +
            ", missionType='" + getMissionType() + "'" +
            "}";
    }
}
