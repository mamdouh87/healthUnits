package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.Doctors;
import com.mkh.healthunits.repository.DoctorsRepository;
import com.mkh.healthunits.service.dto.DoctorsDTO;
import com.mkh.healthunits.service.mapper.DoctorsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Doctors}.
 */
@Service
@Transactional
public class DoctorsService {

    private final Logger log = LoggerFactory.getLogger(DoctorsService.class);

    private final DoctorsRepository doctorsRepository;

    private final DoctorsMapper doctorsMapper;

    public DoctorsService(DoctorsRepository doctorsRepository, DoctorsMapper doctorsMapper) {
        this.doctorsRepository = doctorsRepository;
        this.doctorsMapper = doctorsMapper;
    }

    /**
     * Save a doctors.
     *
     * @param doctorsDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorsDTO save(DoctorsDTO doctorsDTO) {
        log.debug("Request to save Doctors : {}", doctorsDTO);
        Doctors doctors = doctorsMapper.toEntity(doctorsDTO);
        doctors = doctorsRepository.save(doctors);
        return doctorsMapper.toDto(doctors);
    }

    /**
     * Update a doctors.
     *
     * @param doctorsDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorsDTO update(DoctorsDTO doctorsDTO) {
        log.debug("Request to update Doctors : {}", doctorsDTO);
        Doctors doctors = doctorsMapper.toEntity(doctorsDTO);
        doctors = doctorsRepository.save(doctors);
        return doctorsMapper.toDto(doctors);
    }

    /**
     * Partially update a doctors.
     *
     * @param doctorsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DoctorsDTO> partialUpdate(DoctorsDTO doctorsDTO) {
        log.debug("Request to partially update Doctors : {}", doctorsDTO);

        return doctorsRepository
            .findById(doctorsDTO.getId())
            .map(existingDoctors -> {
                doctorsMapper.partialUpdate(existingDoctors, doctorsDTO);

                return existingDoctors;
            })
            .map(doctorsRepository::save)
            .map(doctorsMapper::toDto);
    }

    /**
     * Get all the doctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DoctorsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        return doctorsRepository.findAll(pageable).map(doctorsMapper::toDto);
    }

    /**
     * Get one doctors by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DoctorsDTO> findOne(Long id) {
        log.debug("Request to get Doctors : {}", id);
        return doctorsRepository.findById(id).map(doctorsMapper::toDto);
    }

    /**
     * Delete the doctors by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Doctors : {}", id);
        doctorsRepository.deleteById(id);
    }
}
