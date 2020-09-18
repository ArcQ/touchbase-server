package com.kf.touchbase.service.dto;

import io.micronaut.core.annotation.Introspected;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.kf.touchbase.domain.enumeration.Role;

/**
 * A DTO for the {@link com.kf.touchbase.domain.BaseMember} entity.
 */
@Introspected
public class BaseMemberDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Role role;


    private Long memberId;

    private Long baseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long userId) {
        this.memberId = userId;
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

        BaseMemberDTO baseMemberDTO = (BaseMemberDTO) o;
        if (baseMemberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseMemberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseMemberDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", role='" + getRole() + "'" +
            ", memberId=" + getMemberId() +
            ", baseId=" + getBaseId() +
            "}";
    }
}
