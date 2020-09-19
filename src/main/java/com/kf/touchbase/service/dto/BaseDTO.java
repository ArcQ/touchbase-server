package com.kf.touchbase.service.dto;

import io.micronaut.core.annotation.Introspected;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kf.touchbase.domain.Base} entity.
 */
@Introspected
public class BaseDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    @NotNull
    private String name;

    private Double score;

    @NotNull
    private String imageUrl;

    private Boolean isActive;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseDTO baseDTO = (BaseDTO) o;
        if (baseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", name='" + getName() + "'" +
            ", score=" + getScore() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
