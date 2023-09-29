package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.UnitImgs;
import com.mkh.healthunits.repository.UnitImgsRepository;
import com.mkh.healthunits.service.dto.UnitImgsDTO;
import com.mkh.healthunits.service.mapper.UnitImgsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UnitImgs}.
 */
@Service
@Transactional
public class UnitImgsService {

    private final Logger log = LoggerFactory.getLogger(UnitImgsService.class);

    private final UnitImgsRepository unitImgsRepository;

    private final UnitImgsMapper unitImgsMapper;

    public UnitImgsService(UnitImgsRepository unitImgsRepository, UnitImgsMapper unitImgsMapper) {
        this.unitImgsRepository = unitImgsRepository;
        this.unitImgsMapper = unitImgsMapper;
    }

    /**
     * Save a unitImgs.
     *
     * @param unitImgsDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitImgsDTO save(UnitImgsDTO unitImgsDTO) {
        log.debug("Request to save UnitImgs : {}", unitImgsDTO);
        UnitImgs unitImgs = unitImgsMapper.toEntity(unitImgsDTO);
        unitImgs = unitImgsRepository.save(unitImgs);
        return unitImgsMapper.toDto(unitImgs);
    }

    /**
     * Update a unitImgs.
     *
     * @param unitImgsDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitImgsDTO update(UnitImgsDTO unitImgsDTO) {
        log.debug("Request to update UnitImgs : {}", unitImgsDTO);
        UnitImgs unitImgs = unitImgsMapper.toEntity(unitImgsDTO);
        unitImgs = unitImgsRepository.save(unitImgs);
        return unitImgsMapper.toDto(unitImgs);
    }

    /**
     * Partially update a unitImgs.
     *
     * @param unitImgsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UnitImgsDTO> partialUpdate(UnitImgsDTO unitImgsDTO) {
        log.debug("Request to partially update UnitImgs : {}", unitImgsDTO);

        return unitImgsRepository
            .findById(unitImgsDTO.getId())
            .map(existingUnitImgs -> {
                unitImgsMapper.partialUpdate(existingUnitImgs, unitImgsDTO);

                return existingUnitImgs;
            })
            .map(unitImgsRepository::save)
            .map(unitImgsMapper::toDto);
    }

    /**
     * Get all the unitImgs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnitImgsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnitImgs");
        return unitImgsRepository.findAll(pageable).map(unitImgsMapper::toDto);
    }

    /**
     * Get one unitImgs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnitImgsDTO> findOne(Long id) {
        log.debug("Request to get UnitImgs : {}", id);
        return unitImgsRepository.findById(id).map(unitImgsMapper::toDto);
    }

    /**
     * Delete the unitImgs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnitImgs : {}", id);
        unitImgsRepository.deleteById(id);
    }
}
