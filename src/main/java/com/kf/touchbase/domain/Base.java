package com.kf.touchbase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Base.
 */
@Entity
@Table(name = "base")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Base implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score")
    private Double score;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(unique = true)
    private User creator;

    @OneToMany(mappedBy = "base")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chat> chats = new HashSet<>();

    @OneToMany(mappedBy = "base")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BaseMember> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Base createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Base lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public Base name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public Base score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Base imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Base isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public User getCreator() {
        return creator;
    }

    public Base creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public Base chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public Base addChats(Chat chat) {
        this.chats.add(chat);
        chat.setBase(this);
        return this;
    }

    public Base removeChats(Chat chat) {
        this.chats.remove(chat);
        chat.setBase(null);
        return this;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Set<BaseMember> getMembers() {
        return members;
    }

    public Base members(Set<BaseMember> baseMembers) {
        this.members = baseMembers;
        return this;
    }

    public Base addMembers(BaseMember baseMember) {
        this.members.add(baseMember);
        baseMember.setBase(this);
        return this;
    }

    public Base removeMembers(BaseMember baseMember) {
        this.members.remove(baseMember);
        baseMember.setBase(null);
        return this;
    }

    public void setMembers(Set<BaseMember> baseMembers) {
        this.members = baseMembers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Base)) {
            return false;
        }
        return id != null && id.equals(((Base) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Base{" +
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
