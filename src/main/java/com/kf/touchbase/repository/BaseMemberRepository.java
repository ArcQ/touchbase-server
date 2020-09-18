package com.kf.touchbase.repository;

import com.kf.touchbase.domain.BaseMember;


import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

/**
 * Micronaut Data  repository for the BaseMember entity.
 */
@SuppressWarnings("unused")
@Repository
public abstract class BaseMemberRepository implements JpaRepository<BaseMember, Long> {
    
    private final EntityManager entityManager;


    @Query("select baseMember from BaseMember baseMember where baseMember.member.login = :username ")
    abstract List<BaseMember> findByMemberIsCurrentUser(String username);

    public BaseMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public BaseMember mergeAndSave(BaseMember baseMember) {
        baseMember = entityManager.merge(baseMember);
        return save(baseMember);
    }

}
