package com.kf.touchbase.service;

import com.kf.touchbase.domain.Base;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.service.dto.BaseDTO;
import com.kf.touchbase.service.mapper.BaseMapper;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.transaction.annotation.ReadOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Base}.
 */
@Primary
@Singleton
@Transactional
public class BaseService {

    private final Logger log = LoggerFactory.getLogger(BaseService.class);

    private final BaseRepository baseRepository;

    private final BaseMapper baseMapper;

    public BaseService(BaseRepository baseRepository, BaseMapper baseMapper) {
        this.baseRepository = baseRepository;
        this.baseMapper = baseMapper;
    }

    /**
     * Save a base.
     *
     * @param baseDTO the entity to save.
     * @return the persisted entity.
     */
    public BaseDTO save(BaseDTO baseDTO) {
        log.debug("Request to save Base : {}", baseDTO);
        Base base = baseMapper.toEntity(baseDTO);
        base = baseRepository.mergeAndSave(base);
        return baseMapper.toDto(base);
    }

    /**
     * Get all the bases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @ReadOnly
    @Transactional
    public Page<BaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bases");
        return baseRepository.findAll(pageable)
            .map(baseMapper::toDto);
    }


    /**
     * Get one base by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @ReadOnly
    @Transactional
    public Optional<BaseDTO> findOne(Long id) {
        log.debug("Request to get Base : {}", id);
        return baseRepository.findById(id)
            .map(baseMapper::toDto);
    }

    /**
     * Delete the base by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Base : {}", id);
        baseRepository.deleteById(id);
    }
}
