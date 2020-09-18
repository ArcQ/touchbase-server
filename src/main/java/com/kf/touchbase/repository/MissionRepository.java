package com.kf.touchbase.repository;

import com.kf.touchbase.domain.Mission;


import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Micronaut Data  repository for the Mission entity.
 */
@SuppressWarnings("unused")
@Repository
public abstract class MissionRepository implements JpaRepository<Mission, Long> {
    
    private final EntityManager entityManager;


    public MissionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Mission mergeAndSave(Mission mission) {
        mission = entityManager.merge(mission);
        return save(mission);
    }

}
