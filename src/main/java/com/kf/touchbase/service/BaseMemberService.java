package com.kf.touchbase.service;

import com.kf.touchbase.domain.BaseMember;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.service.dto.BaseMemberDTO;
import com.kf.touchbase.service.mapper.BaseMemberMapper;
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
 * Service Implementation for managing {@link BaseMember}.
 */
@Singleton
@Transactional
public class BaseMemberService {

    private final Logger log = LoggerFactory.getLogger(BaseMemberService.class);

    private final BaseMemberRepository baseMemberRepository;

    private final BaseMemberMapper baseMemberMapper;

    public BaseMemberService(BaseMemberRepository baseMemberRepository, BaseMemberMapper baseMemberMapper) {
        this.baseMemberRepository = baseMemberRepository;
        this.baseMemberMapper = baseMemberMapper;
    }

    /**
     * Save a baseMember.
     *
     * @param baseMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public BaseMemberDTO save(BaseMemberDTO baseMemberDTO) {
        log.debug("Request to save BaseMember : {}", baseMemberDTO);
        BaseMember baseMember = baseMemberMapper.toEntity(baseMemberDTO);
        baseMember = baseMemberRepository.mergeAndSave(baseMember);
        return baseMemberMapper.toDto(baseMember);
    }

    /**
     * Get all the baseMembers.
     *
     * @return the list of entities.
     */
    @ReadOnly
    @Transactional
    public List<BaseMemberDTO> findAll() {
        log.debug("Request to get all BaseMembers");
        return baseMemberRepository.findAll().stream()
            .map(baseMemberMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one baseMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @ReadOnly
    @Transactional
    public Optional<BaseMemberDTO> findOne(Long id) {
        log.debug("Request to get BaseMember : {}", id);
        return baseMemberRepository.findById(id)
            .map(baseMemberMapper::toDto);
    }

    /**
     * Delete the baseMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BaseMember : {}", id);
        baseMemberRepository.deleteById(id);
    }
}
