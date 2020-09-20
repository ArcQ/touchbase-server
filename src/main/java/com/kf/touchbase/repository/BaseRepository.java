package com.kf.touchbase.repository;

import com.kf.touchbase.domain.Base;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Query(value = "SELECT b.* FROM base b, base_member m, tb_user u WHERE m.base_id=b.id AND m.member_id=u.id AND u.auth_key=:userAuthKey", nativeQuery = true)
    public abstract List<Base> findAllByMembersUserAuthKey(String userAuthKey);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.id='?1' AND m.base_id=b.id AND m.member_id=u.id AND u.auth_key='?2'", nativeQuery = true)
    public abstract Optional<Base> findIfMember(Long baseId, String userAuthKey);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.uuid = ?1 AND m.base_uuid=b.uuid AND m.member_uuid=u.uuid AND u.auth_key='?2' AND m.role='ADMIN'", nativeQuery = true)
    public abstract Optional<Base> findIfMemberAdmin(Long baseId, String userAuthKey);

}
