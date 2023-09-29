package com.mkh.healthunits.service;

import com.mkh.healthunits.domain.Notifications;
import com.mkh.healthunits.repository.NotificationsRepository;
import com.mkh.healthunits.service.dto.NotificationsDTO;
import com.mkh.healthunits.service.mapper.NotificationsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notifications}.
 */
@Service
@Transactional
public class NotificationsService {

    private final Logger log = LoggerFactory.getLogger(NotificationsService.class);

    private final NotificationsRepository notificationsRepository;

    private final NotificationsMapper notificationsMapper;

    public NotificationsService(NotificationsRepository notificationsRepository, NotificationsMapper notificationsMapper) {
        this.notificationsRepository = notificationsRepository;
        this.notificationsMapper = notificationsMapper;
    }

    /**
     * Save a notifications.
     *
     * @param notificationsDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationsDTO save(NotificationsDTO notificationsDTO) {
        log.debug("Request to save Notifications : {}", notificationsDTO);
        Notifications notifications = notificationsMapper.toEntity(notificationsDTO);
        notifications = notificationsRepository.save(notifications);
        return notificationsMapper.toDto(notifications);
    }

    /**
     * Update a notifications.
     *
     * @param notificationsDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationsDTO update(NotificationsDTO notificationsDTO) {
        log.debug("Request to update Notifications : {}", notificationsDTO);
        Notifications notifications = notificationsMapper.toEntity(notificationsDTO);
        notifications = notificationsRepository.save(notifications);
        return notificationsMapper.toDto(notifications);
    }

    /**
     * Partially update a notifications.
     *
     * @param notificationsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationsDTO> partialUpdate(NotificationsDTO notificationsDTO) {
        log.debug("Request to partially update Notifications : {}", notificationsDTO);

        return notificationsRepository
            .findById(notificationsDTO.getId())
            .map(existingNotifications -> {
                notificationsMapper.partialUpdate(existingNotifications, notificationsDTO);

                return existingNotifications;
            })
            .map(notificationsRepository::save)
            .map(notificationsMapper::toDto);
    }

    /**
     * Get all the notifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notifications");
        return notificationsRepository.findAll(pageable).map(notificationsMapper::toDto);
    }

    /**
     * Get one notifications by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationsDTO> findOne(Long id) {
        log.debug("Request to get Notifications : {}", id);
        return notificationsRepository.findById(id).map(notificationsMapper::toDto);
    }

    /**
     * Delete the notifications by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notifications : {}", id);
        notificationsRepository.deleteById(id);
    }
}
