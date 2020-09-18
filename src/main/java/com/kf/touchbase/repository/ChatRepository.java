package com.kf.touchbase.repository;

import com.kf.touchbase.domain.Chat;


import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Micronaut Data  repository for the Chat entity.
 */
@SuppressWarnings("unused")
@Repository
public abstract class ChatRepository implements JpaRepository<Chat, Long> {
    
    private final EntityManager entityManager;


    public ChatRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Chat mergeAndSave(Chat chat) {
        chat = entityManager.merge(chat);
        return save(chat);
    }

}
