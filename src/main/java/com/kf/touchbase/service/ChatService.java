package com.kf.touchbase.service;

import com.kf.touchbase.domain.Chat;
import com.kf.touchbase.repository.ChatRepository;
import com.kf.touchbase.service.dto.ChatDTO;
import com.kf.touchbase.service.mapper.ChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import io.micronaut.transaction.annotation.ReadOnly;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Chat}.
 */
@Singleton
@Transactional
public class ChatService {

    private final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;

    public ChatService(ChatRepository chatRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    /**
     * Save a chat.
     *
     * @param chatDTO the entity to save.
     * @return the persisted entity.
     */
    public ChatDTO save(ChatDTO chatDTO) {
        log.debug("Request to save Chat : {}", chatDTO);
        Chat chat = chatMapper.toEntity(chatDTO);
        chat = chatRepository.mergeAndSave(chat);
        return chatMapper.toDto(chat);
    }

    /**
     * Get all the chats.
     *
     * @return the list of entities.
     */
    @ReadOnly
    @Transactional
    public List<ChatDTO> findAll() {
        log.debug("Request to get all Chats");
        return chatRepository.findAll().stream()
            .map(chatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one chat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @ReadOnly
    @Transactional
    public Optional<ChatDTO> findOne(Long id) {
        log.debug("Request to get Chat : {}", id);
        return chatRepository.findById(id)
            .map(chatMapper::toDto);
    }

    /**
     * Delete the chat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Chat : {}", id);
        chatRepository.deleteById(id);
    }
}
