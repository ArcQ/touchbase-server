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

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private Role role;


    private Long baseId;

    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", role='" + getRole() + "'" +
            ", baseId=" + getBaseId() +
            ", memberId=" + getMemberId() +
            "}";
    }
}
