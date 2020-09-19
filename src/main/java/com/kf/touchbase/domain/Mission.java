package com.kf.touchbase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.kf.touchbase.domain.enumeration.MissionType;

/**
 * A Mission.
 */
@Entity
@Table(name = "mission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "score_reward")
    private String scoreReward;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_type")
    private MissionType missionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Mission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Mission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScoreReward() {
        return scoreReward;
    }

    public Mission scoreReward(String scoreReward) {
        this.scoreReward = scoreReward;
        return this;
    }

    public void setScoreReward(String scoreReward) {
        this.scoreReward = scoreReward;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public Mission missionType(MissionType missionType) {
        this.missionType = missionType;
        return this;
    }

    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mission)) {
            return false;
        }
        return id != null && id.equals(((Mission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", scoreReward='" + getScoreReward() + "'" +
            ", missionType='" + getMissionType() + "'" +
            "}";
    }
}
