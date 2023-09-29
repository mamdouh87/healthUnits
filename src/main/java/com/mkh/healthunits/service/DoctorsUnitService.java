package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.DoctorsUnit;
import com.mkh.healthunits.repository.DoctorsUnitRepository;
import com.mkh.healthunits.service.dto.DoctorsUnitDTO;
import com.mkh.healthunits.service.mapper.DoctorsUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DoctorsUnit}.
 */
@Service
@Transactional
public class DoctorsUnitService {

    private final Logger log = LoggerFactory.getLogger(DoctorsUnitService.class);

    private final DoctorsUnitRepository doctorsUnitRepository;

    private final DoctorsUnitMapper doctorsUnitMapper;

    public DoctorsUnitService(DoctorsUnitRepository doctorsUnitRepository, DoctorsUnitMapper doctorsUnitMapper) {
        this.doctorsUnitRepository = doctorsUnitRepository;
        this.doctorsUnitMapper = doctorsUnitMapper;
    }

    /**
     * Save a doctorsUnit.
     *
     * @param doctorsUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorsUnitDTO save(DoctorsUnitDTO doctorsUnitDTO) {
        log.debug("Request to save DoctorsUnit : {}", doctorsUnitDTO);
        DoctorsUnit doctorsUnit = doctorsUnitMapper.toEntity(doctorsUnitDTO);
        doctorsUnit = doctorsUnitRepository.save(doctorsUnit);
        return doctorsUnitMapper.toDto(doctorsUnit);
    }

    /**
     * Update a doctorsUnit.
     *
     * @param doctorsUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorsUnitDTO update(DoctorsUnitDTO doctorsUnitDTO) {
        log.debug("Request to update DoctorsUnit : {}", doctorsUnitDTO);
        DoctorsUnit doctorsUnit = doctorsUnitMapper.toEntity(doctorsUnitDTO);
        doctorsUnit = doctorsUnitRepository.save(doctorsUnit);
        return doctorsUnitMapper.toDto(doctorsUnit);
    }

    /**
     * Partially update a doctorsUnit.
     *
     * @param doctorsUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DoctorsUnitDTO> partialUpdate(DoctorsUnitDTO doctorsUnitDTO) {
        log.debug("Request to partially update DoctorsUnit : {}", doctorsUnitDTO);

        return doctorsUnitRepository
            .findById(doctorsUnitDTO.getId())
            .map(existingDoctorsUnit -> {
                doctorsUnitMapper.partialUpdate(existingDoctorsUnit, doctorsUnitDTO);

                return existingDoctorsUnit;
            })
            .map(doctorsUnitRepository::save)
            .map(doctorsUnitMapper::toDto);
    }

    /**
     * Get all the doctorsUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DoctorsUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DoctorsUnits");
        return doctorsUnitRepository.findAll(pageable).map(doctorsUnitMapper::toDto);
    }

    /**
     * Get one doctorsUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DoctorsUnitDTO> findOne(Long id) {
        log.debug("Request to get DoctorsUnit : {}", id);
        return doctorsUnitRepository.findById(id).map(doctorsUnitMapper::toDto);
    }

    /**
     * Delete the doctorsUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DoctorsUnit : {}", id);
        doctorsUnitRepository.deleteById(id);
    }
}
