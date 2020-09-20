package com.kf.touchbase.repository;

import com.kf.touchbase.domain.Base;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Micronaut Data  repository for the Base entity.
 */
@SuppressWarnings("unused")
@Repository
public abstract class BaseRepository implements JpaRepository<Base, Long> {

    private final EntityManager entityManager;


    public BaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Base mergeAndSave(Base base) {
        base = entityManager.merge(base);
        return save(base);
    }

}
