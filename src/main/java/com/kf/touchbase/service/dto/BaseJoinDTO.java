package com.kf.touchbase.service.dto;

import io.micronaut.core.annotation.Introspected;
import java.io.Serializable;
import java.util.Objects;
import com.kf.touchbase.domain.enumeration.BaseJoinAction;

/**
 * A DTO for the {@link com.kf.touchbase.domain.BaseJoin} entity.
 */
@Introspected
public class BaseJoinDTO implements Serializable {

    private Long id;

    private BaseJoinAction baseJoin;


    private Long baseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseJoinAction getBaseJoin() {
        return baseJoin;
    }

    public void setBaseJoin(BaseJoinAction baseJoin) {
        this.baseJoin = baseJoin;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseJoinDTO baseJoinDTO = (BaseJoinDTO) o;
        if (baseJoinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseJoinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseJoinDTO{" +
            "id=" + getId() +
            ", baseJoin='" + getBaseJoin() + "'" +
            ", baseId=" + getBaseId() +
            "}";
    }
}
