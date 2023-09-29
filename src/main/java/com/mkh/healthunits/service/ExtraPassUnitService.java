package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.ExtraPassUnit;
import com.mkh.healthunits.repository.ExtraPassUnitRepository;
import com.mkh.healthunits.service.dto.ExtraPassUnitDTO;
import com.mkh.healthunits.service.mapper.ExtraPassUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExtraPassUnit}.
 */
@Service
@Transactional
public class ExtraPassUnitService {

    private final Logger log = LoggerFactory.getLogger(ExtraPassUnitService.class);

    private final ExtraPassUnitRepository extraPassUnitRepository;

    private final ExtraPassUnitMapper extraPassUnitMapper;

    public ExtraPassUnitService(ExtraPassUnitRepository extraPassUnitRepository, ExtraPassUnitMapper extraPassUnitMapper) {
        this.extraPassUnitRepository = extraPassUnitRepository;
        this.extraPassUnitMapper = extraPassUnitMapper;
    }

    /**
     * Save a extraPassUnit.
     *
     * @param extraPassUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public ExtraPassUnitDTO save(ExtraPassUnitDTO extraPassUnitDTO) {
        log.debug("Request to save ExtraPassUnit : {}", extraPassUnitDTO);
        ExtraPassUnit extraPassUnit = extraPassUnitMapper.toEntity(extraPassUnitDTO);
        extraPassUnit = extraPassUnitRepository.save(extraPassUnit);
        return extraPassUnitMapper.toDto(extraPassUnit);
    }

    /**
     * Update a extraPassUnit.
     *
     * @param extraPassUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public ExtraPassUnitDTO update(ExtraPassUnitDTO extraPassUnitDTO) {
        log.debug("Request to update ExtraPassUnit : {}", extraPassUnitDTO);
        ExtraPassUnit extraPassUnit = extraPassUnitMapper.toEntity(extraPassUnitDTO);
        extraPassUnit = extraPassUnitRepository.save(extraPassUnit);
        return extraPassUnitMapper.toDto(extraPassUnit);
    }

    /**
     * Partially update a extraPassUnit.
     *
     * @param extraPassUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExtraPassUnitDTO> partialUpdate(ExtraPassUnitDTO extraPassUnitDTO) {
        log.debug("Request to partially update ExtraPassUnit : {}", extraPassUnitDTO);

        return extraPassUnitRepository
            .findById(extraPassUnitDTO.getId())
            .map(existingExtraPassUnit -> {
                extraPassUnitMapper.partialUpdate(existingExtraPassUnit, extraPassUnitDTO);

                return existingExtraPassUnit;
            })
            .map(extraPassUnitRepository::save)
            .map(extraPassUnitMapper::toDto);
    }

    /**
     * Get all the extraPassUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraPassUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtraPassUnits");
        return extraPassUnitRepository.findAll(pageable).map(extraPassUnitMapper::toDto);
    }

    /**
     * Get one extraPassUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExtraPassUnitDTO> findOne(Long id) {
        log.debug("Request to get ExtraPassUnit : {}", id);
        return extraPassUnitRepository.findById(id).map(extraPassUnitMapper::toDto);
    }

    /**
     * Delete the extraPassUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExtraPassUnit : {}", id);
        extraPassUnitRepository.deleteById(id);
    }
}
