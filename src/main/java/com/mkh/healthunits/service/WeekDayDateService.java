package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.WeekDayDate;
import com.mkh.healthunits.repository.WeekDayDateRepository;
import com.mkh.healthunits.service.dto.WeekDayDateDTO;
import com.mkh.healthunits.service.mapper.WeekDayDateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WeekDayDate}.
 */
@Service
@Transactional
public class WeekDayDateService {

    private final Logger log = LoggerFactory.getLogger(WeekDayDateService.class);

    private final WeekDayDateRepository weekDayDateRepository;

    private final WeekDayDateMapper weekDayDateMapper;

    public WeekDayDateService(WeekDayDateRepository weekDayDateRepository, WeekDayDateMapper weekDayDateMapper) {
        this.weekDayDateRepository = weekDayDateRepository;
        this.weekDayDateMapper = weekDayDateMapper;
    }

    /**
     * Save a weekDayDate.
     *
     * @param weekDayDateDTO the entity to save.
     * @return the persisted entity.
     */
    public WeekDayDateDTO save(WeekDayDateDTO weekDayDateDTO) {
        log.debug("Request to save WeekDayDate : {}", weekDayDateDTO);
        WeekDayDate weekDayDate = weekDayDateMapper.toEntity(weekDayDateDTO);
        weekDayDate = weekDayDateRepository.save(weekDayDate);
        return weekDayDateMapper.toDto(weekDayDate);
    }

    /**
     * Update a weekDayDate.
     *
     * @param weekDayDateDTO the entity to save.
     * @return the persisted entity.
     */
    public WeekDayDateDTO update(WeekDayDateDTO weekDayDateDTO) {
        log.debug("Request to update WeekDayDate : {}", weekDayDateDTO);
        WeekDayDate weekDayDate = weekDayDateMapper.toEntity(weekDayDateDTO);
        weekDayDate = weekDayDateRepository.save(weekDayDate);
        return weekDayDateMapper.toDto(weekDayDate);
    }

    /**
     * Partially update a weekDayDate.
     *
     * @param weekDayDateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WeekDayDateDTO> partialUpdate(WeekDayDateDTO weekDayDateDTO) {
        log.debug("Request to partially update WeekDayDate : {}", weekDayDateDTO);

        return weekDayDateRepository
            .findById(weekDayDateDTO.getId())
            .map(existingWeekDayDate -> {
                weekDayDateMapper.partialUpdate(existingWeekDayDate, weekDayDateDTO);

                return existingWeekDayDate;
            })
            .map(weekDayDateRepository::save)
            .map(weekDayDateMapper::toDto);
    }

    /**
     * Get all the weekDayDates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WeekDayDateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeekDayDates");
        return weekDayDateRepository.findAll(pageable).map(weekDayDateMapper::toDto);
    }

    /**
     * Get one weekDayDate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WeekDayDateDTO> findOne(Long id) {
        log.debug("Request to get WeekDayDate : {}", id);
        return weekDayDateRepository.findById(id).map(weekDayDateMapper::toDto);
    }

    /**
     * Delete the weekDayDate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WeekDayDate : {}", id);
        weekDayDateRepository.deleteById(id);
    }
}
