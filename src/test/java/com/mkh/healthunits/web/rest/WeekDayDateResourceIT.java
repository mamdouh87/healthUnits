package com.mkh.healthunits.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.WeekDayDate;
import com.mkh.healthunits.repository.WeekDayDateRepository;
import com.mkh.healthunits.service.dto.WeekDayDateDTO;
import com.mkh.healthunits.service.mapper.WeekDayDateMapper;
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
 * Integration tests for the {@link WeekDayDateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeekDayDateResourceIT {

    private static final Integer DEFAULT_DAY_VALUE = 1;
    private static final Integer UPDATED_DAY_VALUE = 2;

    private static final String DEFAULT_DAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DAY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DAY_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/week-day-dates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WeekDayDateRepository weekDayDateRepository;

    @Autowired
    private WeekDayDateMapper weekDayDateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeekDayDateMockMvc;

    private WeekDayDate weekDayDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeekDayDate createEntity(EntityManager em) {
        WeekDayDate weekDayDate = new WeekDayDate().dayValue(DEFAULT_DAY_VALUE).dayName(DEFAULT_DAY_NAME).dayDate(DEFAULT_DAY_DATE);
        return weekDayDate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeekDayDate createUpdatedEntity(EntityManager em) {
        WeekDayDate weekDayDate = new WeekDayDate().dayValue(UPDATED_DAY_VALUE).dayName(UPDATED_DAY_NAME).dayDate(UPDATED_DAY_DATE);
        return weekDayDate;
    }

    @BeforeEach
    public void initTest() {
        weekDayDate = createEntity(em);
    }

    @Test
    @Transactional
    void createWeekDayDate() throws Exception {
        int databaseSizeBeforeCreate = weekDayDateRepository.findAll().size();
        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);
        restWeekDayDateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeCreate + 1);
        WeekDayDate testWeekDayDate = weekDayDateList.get(weekDayDateList.size() - 1);
        assertThat(testWeekDayDate.getDayValue()).isEqualTo(DEFAULT_DAY_VALUE);
        assertThat(testWeekDayDate.getDayName()).isEqualTo(DEFAULT_DAY_NAME);
        assertThat(testWeekDayDate.getDayDate()).isEqualTo(DEFAULT_DAY_DATE);
    }

    @Test
    @Transactional
    void createWeekDayDateWithExistingId() throws Exception {
        // Create the WeekDayDate with an existing ID
        weekDayDate.setId(1L);
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        int databaseSizeBeforeCreate = weekDayDateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekDayDateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeekDayDates() throws Exception {
        // Initialize the database
        weekDayDateRepository.saveAndFlush(weekDayDate);

        // Get all the weekDayDateList
        restWeekDayDateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekDayDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayValue").value(hasItem(DEFAULT_DAY_VALUE)))
            .andExpect(jsonPath("$.[*].dayName").value(hasItem(DEFAULT_DAY_NAME)))
            .andExpect(jsonPath("$.[*].dayDate").value(hasItem(DEFAULT_DAY_DATE)));
    }

    @Test
    @Transactional
    void getWeekDayDate() throws Exception {
        // Initialize the database
        weekDayDateRepository.saveAndFlush(weekDayDate);

        // Get the weekDayDate
        restWeekDayDateMockMvc
            .perform(get(ENTITY_API_URL_ID, weekDayDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weekDayDate.getId().intValue()))
            .andExpect(jsonPath("$.dayValue").value(DEFAULT_DAY_VALUE))
            .andExpect(jsonPath("$.dayName").value(DEFAULT_DAY_NAME))
            .andExpect(jsonPath("$.dayDate").value(DEFAULT_DAY_DATE));
    }

    @Test
    @Transactional
    void getNonExistingWeekDayDate() throws Exception {
        // Get the weekDayDate
        restWeekDayDateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWeekDayDate() throws Exception {
        // Initialize the database
        weekDayDateRepository.saveAndFlush(weekDayDate);

        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();

        // Update the weekDayDate
        WeekDayDate updatedWeekDayDate = weekDayDateRepository.findById(weekDayDate.getId()).get();
        // Disconnect from session so that the updates on updatedWeekDayDate are not directly saved in db
        em.detach(updatedWeekDayDate);
        updatedWeekDayDate.dayValue(UPDATED_DAY_VALUE).dayName(UPDATED_DAY_NAME).dayDate(UPDATED_DAY_DATE);
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(updatedWeekDayDate);

        restWeekDayDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weekDayDateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isOk());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
        WeekDayDate testWeekDayDate = weekDayDateList.get(weekDayDateList.size() - 1);
        assertThat(testWeekDayDate.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testWeekDayDate.getDayName()).isEqualTo(UPDATED_DAY_NAME);
        assertThat(testWeekDayDate.getDayDate()).isEqualTo(UPDATED_DAY_DATE);
    }

    @Test
    @Transactional
    void putNonExistingWeekDayDate() throws Exception {
        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();
        weekDayDate.setId(count.incrementAndGet());

        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekDayDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weekDayDateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeekDayDate() throws Exception {
        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();
        weekDayDate.setId(count.incrementAndGet());

        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeekDayDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeekDayDate() throws Exception {
        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();
        weekDayDate.setId(count.incrementAndGet());

        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeekDayDateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeekDayDateWithPatch() throws Exception {
        // Initialize the database
        weekDayDateRepository.saveAndFlush(weekDayDate);

        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();

        // Update the weekDayDate using partial update
        WeekDayDate partialUpdatedWeekDayDate = new WeekDayDate();
        partialUpdatedWeekDayDate.setId(weekDayDate.getId());

        partialUpdatedWeekDayDate.dayValue(UPDATED_DAY_VALUE);

        restWeekDayDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeekDayDate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeekDayDate))
            )
            .andExpect(status().isOk());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
        WeekDayDate testWeekDayDate = weekDayDateList.get(weekDayDateList.size() - 1);
        assertThat(testWeekDayDate.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testWeekDayDate.getDayName()).isEqualTo(DEFAULT_DAY_NAME);
        assertThat(testWeekDayDate.getDayDate()).isEqualTo(DEFAULT_DAY_DATE);
    }

    @Test
    @Transactional
    void fullUpdateWeekDayDateWithPatch() throws Exception {
        // Initialize the database
        weekDayDateRepository.saveAndFlush(weekDayDate);

        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();

        // Update the weekDayDate using partial update
        WeekDayDate partialUpdatedWeekDayDate = new WeekDayDate();
        partialUpdatedWeekDayDate.setId(weekDayDate.getId());

        partialUpdatedWeekDayDate.dayValue(UPDATED_DAY_VALUE).dayName(UPDATED_DAY_NAME).dayDate(UPDATED_DAY_DATE);

        restWeekDayDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeekDayDate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeekDayDate))
            )
            .andExpect(status().isOk());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
        WeekDayDate testWeekDayDate = weekDayDateList.get(weekDayDateList.size() - 1);
        assertThat(testWeekDayDate.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testWeekDayDate.getDayName()).isEqualTo(UPDATED_DAY_NAME);
        assertThat(testWeekDayDate.getDayDate()).isEqualTo(UPDATED_DAY_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingWeekDayDate() throws Exception {
        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();
        weekDayDate.setId(count.incrementAndGet());

        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekDayDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weekDayDateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeekDayDate() throws Exception {
        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();
        weekDayDate.setId(count.incrementAndGet());

        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeekDayDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeekDayDate() throws Exception {
        int databaseSizeBeforeUpdate = weekDayDateRepository.findAll().size();
        weekDayDate.setId(count.incrementAndGet());

        // Create the WeekDayDate
        WeekDayDateDTO weekDayDateDTO = weekDayDateMapper.toDto(weekDayDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeekDayDateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(weekDayDateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeekDayDate in the database
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeekDayDate() throws Exception {
        // Initialize the database
        weekDayDateRepository.saveAndFlush(weekDayDate);

        int databaseSizeBeforeDelete = weekDayDateRepository.findAll().size();

        // Delete the weekDayDate
        restWeekDayDateMockMvc
            .perform(delete(ENTITY_API_URL_ID, weekDayDate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WeekDayDate> weekDayDateList = weekDayDateRepository.findAll();
        assertThat(weekDayDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
