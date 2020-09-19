package com.kf.touchbase.service.dto;

import com.kf.touchbase.domain.enumeration.Role;
import io.micronaut.core.annotation.Introspected;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.kf.touchbase.domain.BaseMember} entity.
 */
@Introspected
public class BaseMemberDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Role role;


    private Long baseId;

    private Long memberId;

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

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long userId) {
        this.memberId = userId;
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
            ", baseId=" + getBaseId() +
            ", memberId=" + getMemberId() +
            ", baseId=" + getBaseId() +
            "}";
    }
}
