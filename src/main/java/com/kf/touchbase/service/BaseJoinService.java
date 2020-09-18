package com.kf.touchbase.service;

import com.kf.touchbase.domain.BaseJoin;
import com.kf.touchbase.repository.BaseJoinRepository;
import com.kf.touchbase.service.dto.BaseJoinDTO;
import com.kf.touchbase.service.mapper.BaseJoinMapper;
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
 * Service Implementation for managing {@link BaseJoin}.
 */
@Singleton
@Transactional
public class BaseJoinService {

    private final Logger log = LoggerFactory.getLogger(BaseJoinService.class);

    private final BaseJoinRepository baseJoinRepository;

    private final BaseJoinMapper baseJoinMapper;

    public BaseJoinService(BaseJoinRepository baseJoinRepository, BaseJoinMapper baseJoinMapper) {
        this.baseJoinRepository = baseJoinRepository;
        this.baseJoinMapper = baseJoinMapper;
    }

    /**
     * Save a baseJoin.
     *
     * @param baseJoinDTO the entity to save.
     * @return the persisted entity.
     */
    public BaseJoinDTO save(BaseJoinDTO baseJoinDTO) {
        log.debug("Request to save BaseJoin : {}", baseJoinDTO);
        BaseJoin baseJoin = baseJoinMapper.toEntity(baseJoinDTO);
        baseJoin = baseJoinRepository.mergeAndSave(baseJoin);
        return baseJoinMapper.toDto(baseJoin);
    }

    /**
     * Get all the baseJoins.
     *
     * @return the list of entities.
     */
    @ReadOnly
    @Transactional
    public List<BaseJoinDTO> findAll() {
        log.debug("Request to get all BaseJoins");
        return baseJoinRepository.findAll().stream()
            .map(baseJoinMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one baseJoin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @ReadOnly
    @Transactional
    public Optional<BaseJoinDTO> findOne(Long id) {
        log.debug("Request to get BaseJoin : {}", id);
        return baseJoinRepository.findById(id)
            .map(baseJoinMapper::toDto);
    }

    /**
     * Delete the baseJoin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BaseJoin : {}", id);
        baseJoinRepository.deleteById(id);
    }
}
