package com.kf.touchbase.repository;

import com.kf.touchbase.domain.BaseJoin;


import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Micronaut Data  repository for the BaseJoin entity.
 */
@SuppressWarnings("unused")
@Repository
public abstract class BaseJoinRepository implements JpaRepository<BaseJoin, Long> {
    
    private final EntityManager entityManager;


    public BaseJoinRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public BaseJoin mergeAndSave(BaseJoin baseJoin) {
        baseJoin = entityManager.merge(baseJoin);
        return save(baseJoin);
    }

}
