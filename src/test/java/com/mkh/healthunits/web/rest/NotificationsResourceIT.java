package com.mkh.healthunits.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.Notifications;
import com.mkh.healthunits.repository.NotificationsRepository;
import com.mkh.healthunits.service.dto.NotificationsDTO;
import com.mkh.healthunits.service.mapper.NotificationsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NotificationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_DAY_VALUE = 1;
    private static final Integer UPDATED_DAY_VALUE = 2;

    private static final String ENTITY_API_URL = "/api/notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationsMockMvc;

    private Notifications notifications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notifications createEntity(EntityManager em) {
        Notifications notifications = new Notifications()
            .title(DEFAULT_TITLE)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .dayValue(DEFAULT_DAY_VALUE);
        return notifications;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notifications createUpdatedEntity(EntityManager em) {
        Notifications notifications = new Notifications()
            .title(UPDATED_TITLE)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .dayValue(UPDATED_DAY_VALUE);
        return notifications;
    }

    @BeforeEach
    public void initTest() {
        notifications = createEntity(em);
    }

    @Test
    @Transactional
    void createNotifications() throws Exception {
        int databaseSizeBeforeCreate = notificationsRepository.findAll().size();
        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);
        restNotificationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeCreate + 1);
        Notifications testNotifications = notificationsList.get(notificationsList.size() - 1);
        assertThat(testNotifications.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotifications.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testNotifications.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotifications.getDayValue()).isEqualTo(DEFAULT_DAY_VALUE);
    }

    @Test
    @Transactional
    void createNotificationsWithExistingId() throws Exception {
        // Create the Notifications with an existing ID
        notifications.setId(1L);
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        int databaseSizeBeforeCreate = notificationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        // Get all the notificationsList
        restNotificationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notifications.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dayValue").value(hasItem(DEFAULT_DAY_VALUE)));
    }

    @Test
    @Transactional
    void getNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        // Get the notifications
        restNotificationsMockMvc
            .perform(get(ENTITY_API_URL_ID, notifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notifications.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dayValue").value(DEFAULT_DAY_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingNotifications() throws Exception {
        // Get the notifications
        restNotificationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();

        // Update the notifications
        Notifications updatedNotifications = notificationsRepository.findById(notifications.getId()).get();
        // Disconnect from session so that the updates on updatedNotifications are not directly saved in db
        em.detach(updatedNotifications);
        updatedNotifications.title(UPDATED_TITLE).message(UPDATED_MESSAGE).status(UPDATED_STATUS).dayValue(UPDATED_DAY_VALUE);
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(updatedNotifications);

        restNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
        Notifications testNotifications = notificationsList.get(notificationsList.size() - 1);
        assertThat(testNotifications.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotifications.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotifications.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotifications.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();
        notifications.setId(count.incrementAndGet());

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();
        notifications.setId(count.incrementAndGet());

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();
        notifications.setId(count.incrementAndGet());

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationsWithPatch() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();

        // Update the notifications using partial update
        Notifications partialUpdatedNotifications = new Notifications();
        partialUpdatedNotifications.setId(notifications.getId());

        partialUpdatedNotifications.message(UPDATED_MESSAGE).status(UPDATED_STATUS);

        restNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotifications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotifications))
            )
            .andExpect(status().isOk());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
        Notifications testNotifications = notificationsList.get(notificationsList.size() - 1);
        assertThat(testNotifications.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotifications.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotifications.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotifications.getDayValue()).isEqualTo(DEFAULT_DAY_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateNotificationsWithPatch() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();

        // Update the notifications using partial update
        Notifications partialUpdatedNotifications = new Notifications();
        partialUpdatedNotifications.setId(notifications.getId());

        partialUpdatedNotifications.title(UPDATED_TITLE).message(UPDATED_MESSAGE).status(UPDATED_STATUS).dayValue(UPDATED_DAY_VALUE);

        restNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotifications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotifications))
            )
            .andExpect(status().isOk());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
        Notifications testNotifications = notificationsList.get(notificationsList.size() - 1);
        assertThat(testNotifications.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotifications.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotifications.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotifications.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();
        notifications.setId(count.incrementAndGet());

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();
        notifications.setId(count.incrementAndGet());

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotifications() throws Exception {
        int databaseSizeBeforeUpdate = notificationsRepository.findAll().size();
        notifications.setId(count.incrementAndGet());

        // Create the Notifications
        NotificationsDTO notificationsDTO = notificationsMapper.toDto(notifications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notifications in the database
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotifications() throws Exception {
        // Initialize the database
        notificationsRepository.saveAndFlush(notifications);

        int databaseSizeBeforeDelete = notificationsRepository.findAll().size();

        // Delete the notifications
        restNotificationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, notifications.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notifications> notificationsList = notificationsRepository.findAll();
        assertThat(notificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
