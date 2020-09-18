package com.kf.touchbase.service;

import com.kf.touchbase.domain.Mission;
import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.service.dto.MissionDTO;
import com.kf.touchbase.service.mapper.MissionMapper;
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
 * Service Implementation for managing {@link Mission}.
 */
@Singleton
@Transactional
public class MissionService {

    private final Logger log = LoggerFactory.getLogger(MissionService.class);

    private final MissionRepository missionRepository;

    private final MissionMapper missionMapper;

    public MissionService(MissionRepository missionRepository, MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
    }

    /**
     * Save a mission.
     *
     * @param missionDTO the entity to save.
     * @return the persisted entity.
     */
    public MissionDTO save(MissionDTO missionDTO) {
        log.debug("Request to save Mission : {}", missionDTO);
        Mission mission = missionMapper.toEntity(missionDTO);
        mission = missionRepository.mergeAndSave(mission);
        return missionMapper.toDto(mission);
    }

    /**
     * Get all the missions.
     *
     * @return the list of entities.
     */
    @ReadOnly
    @Transactional
    public List<MissionDTO> findAll() {
        log.debug("Request to get all Missions");
        return missionRepository.findAll().stream()
            .map(missionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @ReadOnly
    @Transactional
    public Optional<MissionDTO> findOne(Long id) {
        log.debug("Request to get Mission : {}", id);
        return missionRepository.findById(id)
            .map(missionMapper::toDto);
    }

    /**
     * Delete the mission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mission : {}", id);
        missionRepository.deleteById(id);
    }
}
