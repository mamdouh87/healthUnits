package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.DoctorPassImgs;
import com.mkh.healthunits.repository.DoctorPassImgsRepository;
import com.mkh.healthunits.service.dto.DoctorPassImgsDTO;
import com.mkh.healthunits.service.mapper.DoctorPassImgsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DoctorPassImgs}.
 */
@Service
@Transactional
public class DoctorPassImgsService {

    private final Logger log = LoggerFactory.getLogger(DoctorPassImgsService.class);

    private final DoctorPassImgsRepository doctorPassImgsRepository;

    private final DoctorPassImgsMapper doctorPassImgsMapper;

    public DoctorPassImgsService(DoctorPassImgsRepository doctorPassImgsRepository, DoctorPassImgsMapper doctorPassImgsMapper) {
        this.doctorPassImgsRepository = doctorPassImgsRepository;
        this.doctorPassImgsMapper = doctorPassImgsMapper;
    }

    /**
     * Save a doctorPassImgs.
     *
     * @param doctorPassImgsDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorPassImgsDTO save(DoctorPassImgsDTO doctorPassImgsDTO) {
        log.debug("Request to save DoctorPassImgs : {}", doctorPassImgsDTO);
        DoctorPassImgs doctorPassImgs = doctorPassImgsMapper.toEntity(doctorPassImgsDTO);
        doctorPassImgs = doctorPassImgsRepository.save(doctorPassImgs);
        return doctorPassImgsMapper.toDto(doctorPassImgs);
    }

    /**
     * Update a doctorPassImgs.
     *
     * @param doctorPassImgsDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorPassImgsDTO update(DoctorPassImgsDTO doctorPassImgsDTO) {
        log.debug("Request to update DoctorPassImgs : {}", doctorPassImgsDTO);
        DoctorPassImgs doctorPassImgs = doctorPassImgsMapper.toEntity(doctorPassImgsDTO);
        doctorPassImgs = doctorPassImgsRepository.save(doctorPassImgs);
        return doctorPassImgsMapper.toDto(doctorPassImgs);
    }

    /**
     * Partially update a doctorPassImgs.
     *
     * @param doctorPassImgsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DoctorPassImgsDTO> partialUpdate(DoctorPassImgsDTO doctorPassImgsDTO) {
        log.debug("Request to partially update DoctorPassImgs : {}", doctorPassImgsDTO);

        return doctorPassImgsRepository
            .findById(doctorPassImgsDTO.getId())
            .map(existingDoctorPassImgs -> {
                doctorPassImgsMapper.partialUpdate(existingDoctorPassImgs, doctorPassImgsDTO);

                return existingDoctorPassImgs;
            })
            .map(doctorPassImgsRepository::save)
            .map(doctorPassImgsMapper::toDto);
    }

    /**
     * Get all the doctorPassImgs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DoctorPassImgsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DoctorPassImgs");
        return doctorPassImgsRepository.findAll(pageable).map(doctorPassImgsMapper::toDto);
    }

    /**
     * Get one doctorPassImgs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DoctorPassImgsDTO> findOne(Long id) {
        log.debug("Request to get DoctorPassImgs : {}", id);
        return doctorPassImgsRepository.findById(id).map(doctorPassImgsMapper::toDto);
    }

    /**
     * Delete the doctorPassImgs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DoctorPassImgs : {}", id);
        doctorPassImgsRepository.deleteById(id);
    }
}
