package com.kf.touchbase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.kf.touchbase.domain.enumeration.BaseJoinAction;

/**
 * A BaseJoin.
 */
@Entity
@Table(name = "base_join")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseJoin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_join")
    private BaseJoinAction baseJoin;

    @OneToOne
    @JoinColumn(unique = true)
    private Base base;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseJoinAction getBaseJoin() {
        return baseJoin;
    }

    public BaseJoin baseJoin(BaseJoinAction baseJoin) {
        this.baseJoin = baseJoin;
        return this;
    }

    public void setBaseJoin(BaseJoinAction baseJoin) {
        this.baseJoin = baseJoin;
    }

    public Base getBase() {
        return base;
    }

    public BaseJoin base(Base base) {
        this.base = base;
        return this;
    }

    public void setBase(Base base) {
        this.base = base;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseJoin)) {
            return false;
        }
        return id != null && id.equals(((BaseJoin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BaseJoin{" +
            "id=" + getId() +
            ", baseJoin='" + getBaseJoin() + "'" +
            "}";
    }
}
