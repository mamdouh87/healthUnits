package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.Units;
import com.mkh.healthunits.repository.UnitsRepository;
import com.mkh.healthunits.service.dto.UnitsDTO;
import com.mkh.healthunits.service.mapper.UnitsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Units}.
 */
@Service
@Transactional
public class UnitsService {

    private final Logger log = LoggerFactory.getLogger(UnitsService.class);

    private final UnitsRepository unitsRepository;

    private final UnitsMapper unitsMapper;

    public UnitsService(UnitsRepository unitsRepository, UnitsMapper unitsMapper) {
        this.unitsRepository = unitsRepository;
        this.unitsMapper = unitsMapper;
    }

    /**
     * Save a units.
     *
     * @param unitsDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitsDTO save(UnitsDTO unitsDTO) {
        log.debug("Request to save Units : {}", unitsDTO);
        Units units = unitsMapper.toEntity(unitsDTO);
        units = unitsRepository.save(units);
        return unitsMapper.toDto(units);
    }

    /**
     * Update a units.
     *
     * @param unitsDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitsDTO update(UnitsDTO unitsDTO) {
        log.debug("Request to update Units : {}", unitsDTO);
        Units units = unitsMapper.toEntity(unitsDTO);
        units = unitsRepository.save(units);
        return unitsMapper.toDto(units);
    }

    /**
     * Partially update a units.
     *
     * @param unitsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UnitsDTO> partialUpdate(UnitsDTO unitsDTO) {
        log.debug("Request to partially update Units : {}", unitsDTO);

        return unitsRepository
            .findById(unitsDTO.getId())
            .map(existingUnits -> {
                unitsMapper.partialUpdate(existingUnits, unitsDTO);

                return existingUnits;
            })
            .map(unitsRepository::save)
            .map(unitsMapper::toDto);
    }

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnitsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitsRepository.findAll(pageable).map(unitsMapper::toDto);
    }

    /**
     * Get one units by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnitsDTO> findOne(Long id) {
        log.debug("Request to get Units : {}", id);
        return unitsRepository.findById(id).map(unitsMapper::toDto);
    }

    /**
     * Delete the units by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Units : {}", id);
        unitsRepository.deleteById(id);
    }
}
