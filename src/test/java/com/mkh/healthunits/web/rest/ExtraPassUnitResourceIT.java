package com.mkh.healthunits.web.rest;

import static com.mkh.healthunits.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.ExtraPassUnit;
import com.mkh.healthunits.repository.ExtraPassUnitRepository;
import com.mkh.healthunits.service.dto.ExtraPassUnitDTO;
import com.mkh.healthunits.service.mapper.ExtraPassUnitMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link ExtraPassUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExtraPassUnitResourceIT {

    private static final Integer DEFAULT_DAY_VALUE = 1;
    private static final Integer UPDATED_DAY_VALUE = 2;

    private static final Integer DEFAULT_SHIFT_TYPE = 1;
    private static final Integer UPDATED_SHIFT_TYPE = 2;

    private static final Integer DEFAULT_ACTIVE_WEEK = 1;
    private static final Integer UPDATED_ACTIVE_WEEK = 2;

    private static final Integer DEFAULT_DONE_PASS = 1;
    private static final Integer UPDATED_DONE_PASS = 2;

    private static final ZonedDateTime DEFAULT_PASS_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASS_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_UNIT_PASSED = 1;
    private static final Integer UPDATED_UNIT_PASSED = 2;

    private static final String ENTITY_API_URL = "/api/extra-pass-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExtraPassUnitRepository extraPassUnitRepository;

    @Autowired
    private ExtraPassUnitMapper extraPassUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExtraPassUnitMockMvc;

    private ExtraPassUnit extraPassUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraPassUnit createEntity(EntityManager em) {
        ExtraPassUnit extraPassUnit = new ExtraPassUnit()
            .dayValue(DEFAULT_DAY_VALUE)
            .shiftType(DEFAULT_SHIFT_TYPE)
            .activeWeek(DEFAULT_ACTIVE_WEEK)
            .donePass(DEFAULT_DONE_PASS)
            .passDate(DEFAULT_PASS_DATE)
            .unitPassed(DEFAULT_UNIT_PASSED);
        return extraPassUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraPassUnit createUpdatedEntity(EntityManager em) {
        ExtraPassUnit extraPassUnit = new ExtraPassUnit()
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE)
            .unitPassed(UPDATED_UNIT_PASSED);
        return extraPassUnit;
    }

    @BeforeEach
    public void initTest() {
        extraPassUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createExtraPassUnit() throws Exception {
        int databaseSizeBeforeCreate = extraPassUnitRepository.findAll().size();
        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);
        restExtraPassUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeCreate + 1);
        ExtraPassUnit testExtraPassUnit = extraPassUnitList.get(extraPassUnitList.size() - 1);
        assertThat(testExtraPassUnit.getDayValue()).isEqualTo(DEFAULT_DAY_VALUE);
        assertThat(testExtraPassUnit.getShiftType()).isEqualTo(DEFAULT_SHIFT_TYPE);
        assertThat(testExtraPassUnit.getActiveWeek()).isEqualTo(DEFAULT_ACTIVE_WEEK);
        assertThat(testExtraPassUnit.getDonePass()).isEqualTo(DEFAULT_DONE_PASS);
        assertThat(testExtraPassUnit.getPassDate()).isEqualTo(DEFAULT_PASS_DATE);
        assertThat(testExtraPassUnit.getUnitPassed()).isEqualTo(DEFAULT_UNIT_PASSED);
    }

    @Test
    @Transactional
    void createExtraPassUnitWithExistingId() throws Exception {
        // Create the ExtraPassUnit with an existing ID
        extraPassUnit.setId(1L);
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        int databaseSizeBeforeCreate = extraPassUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraPassUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExtraPassUnits() throws Exception {
        // Initialize the database
        extraPassUnitRepository.saveAndFlush(extraPassUnit);

        // Get all the extraPassUnitList
        restExtraPassUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraPassUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayValue").value(hasItem(DEFAULT_DAY_VALUE)))
            .andExpect(jsonPath("$.[*].shiftType").value(hasItem(DEFAULT_SHIFT_TYPE)))
            .andExpect(jsonPath("$.[*].activeWeek").value(hasItem(DEFAULT_ACTIVE_WEEK)))
            .andExpect(jsonPath("$.[*].donePass").value(hasItem(DEFAULT_DONE_PASS)))
            .andExpect(jsonPath("$.[*].passDate").value(hasItem(sameInstant(DEFAULT_PASS_DATE))))
            .andExpect(jsonPath("$.[*].unitPassed").value(hasItem(DEFAULT_UNIT_PASSED)));
    }

    @Test
    @Transactional
    void getExtraPassUnit() throws Exception {
        // Initialize the database
        extraPassUnitRepository.saveAndFlush(extraPassUnit);

        // Get the extraPassUnit
        restExtraPassUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, extraPassUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extraPassUnit.getId().intValue()))
            .andExpect(jsonPath("$.dayValue").value(DEFAULT_DAY_VALUE))
            .andExpect(jsonPath("$.shiftType").value(DEFAULT_SHIFT_TYPE))
            .andExpect(jsonPath("$.activeWeek").value(DEFAULT_ACTIVE_WEEK))
            .andExpect(jsonPath("$.donePass").value(DEFAULT_DONE_PASS))
            .andExpect(jsonPath("$.passDate").value(sameInstant(DEFAULT_PASS_DATE)))
            .andExpect(jsonPath("$.unitPassed").value(DEFAULT_UNIT_PASSED));
    }

    @Test
    @Transactional
    void getNonExistingExtraPassUnit() throws Exception {
        // Get the extraPassUnit
        restExtraPassUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExtraPassUnit() throws Exception {
        // Initialize the database
        extraPassUnitRepository.saveAndFlush(extraPassUnit);

        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();

        // Update the extraPassUnit
        ExtraPassUnit updatedExtraPassUnit = extraPassUnitRepository.findById(extraPassUnit.getId()).get();
        // Disconnect from session so that the updates on updatedExtraPassUnit are not directly saved in db
        em.detach(updatedExtraPassUnit);
        updatedExtraPassUnit
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE)
            .unitPassed(UPDATED_UNIT_PASSED);
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(updatedExtraPassUnit);

        restExtraPassUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, extraPassUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
        ExtraPassUnit testExtraPassUnit = extraPassUnitList.get(extraPassUnitList.size() - 1);
        assertThat(testExtraPassUnit.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testExtraPassUnit.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testExtraPassUnit.getActiveWeek()).isEqualTo(UPDATED_ACTIVE_WEEK);
        assertThat(testExtraPassUnit.getDonePass()).isEqualTo(UPDATED_DONE_PASS);
        assertThat(testExtraPassUnit.getPassDate()).isEqualTo(UPDATED_PASS_DATE);
        assertThat(testExtraPassUnit.getUnitPassed()).isEqualTo(UPDATED_UNIT_PASSED);
    }

    @Test
    @Transactional
    void putNonExistingExtraPassUnit() throws Exception {
        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();
        extraPassUnit.setId(count.incrementAndGet());

        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtraPassUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, extraPassUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExtraPassUnit() throws Exception {
        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();
        extraPassUnit.setId(count.incrementAndGet());

        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtraPassUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExtraPassUnit() throws Exception {
        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();
        extraPassUnit.setId(count.incrementAndGet());

        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtraPassUnitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExtraPassUnitWithPatch() throws Exception {
        // Initialize the database
        extraPassUnitRepository.saveAndFlush(extraPassUnit);

        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();

        // Update the extraPassUnit using partial update
        ExtraPassUnit partialUpdatedExtraPassUnit = new ExtraPassUnit();
        partialUpdatedExtraPassUnit.setId(extraPassUnit.getId());

        partialUpdatedExtraPassUnit
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE);

        restExtraPassUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExtraPassUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExtraPassUnit))
            )
            .andExpect(status().isOk());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
        ExtraPassUnit testExtraPassUnit = extraPassUnitList.get(extraPassUnitList.size() - 1);
        assertThat(testExtraPassUnit.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testExtraPassUnit.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testExtraPassUnit.getActiveWeek()).isEqualTo(DEFAULT_ACTIVE_WEEK);
        assertThat(testExtraPassUnit.getDonePass()).isEqualTo(UPDATED_DONE_PASS);
        assertThat(testExtraPassUnit.getPassDate()).isEqualTo(UPDATED_PASS_DATE);
        assertThat(testExtraPassUnit.getUnitPassed()).isEqualTo(DEFAULT_UNIT_PASSED);
    }

    @Test
    @Transactional
    void fullUpdateExtraPassUnitWithPatch() throws Exception {
        // Initialize the database
        extraPassUnitRepository.saveAndFlush(extraPassUnit);

        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();

        // Update the extraPassUnit using partial update
        ExtraPassUnit partialUpdatedExtraPassUnit = new ExtraPassUnit();
        partialUpdatedExtraPassUnit.setId(extraPassUnit.getId());

        partialUpdatedExtraPassUnit
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE)
            .unitPassed(UPDATED_UNIT_PASSED);

        restExtraPassUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExtraPassUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExtraPassUnit))
            )
            .andExpect(status().isOk());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
        ExtraPassUnit testExtraPassUnit = extraPassUnitList.get(extraPassUnitList.size() - 1);
        assertThat(testExtraPassUnit.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testExtraPassUnit.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testExtraPassUnit.getActiveWeek()).isEqualTo(UPDATED_ACTIVE_WEEK);
        assertThat(testExtraPassUnit.getDonePass()).isEqualTo(UPDATED_DONE_PASS);
        assertThat(testExtraPassUnit.getPassDate()).isEqualTo(UPDATED_PASS_DATE);
        assertThat(testExtraPassUnit.getUnitPassed()).isEqualTo(UPDATED_UNIT_PASSED);
    }

    @Test
    @Transactional
    void patchNonExistingExtraPassUnit() throws Exception {
        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();
        extraPassUnit.setId(count.incrementAndGet());

        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtraPassUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, extraPassUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExtraPassUnit() throws Exception {
        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();
        extraPassUnit.setId(count.incrementAndGet());

        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtraPassUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExtraPassUnit() throws Exception {
        int databaseSizeBeforeUpdate = extraPassUnitRepository.findAll().size();
        extraPassUnit.setId(count.incrementAndGet());

        // Create the ExtraPassUnit
        ExtraPassUnitDTO extraPassUnitDTO = extraPassUnitMapper.toDto(extraPassUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtraPassUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(extraPassUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExtraPassUnit in the database
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExtraPassUnit() throws Exception {
        // Initialize the database
        extraPassUnitRepository.saveAndFlush(extraPassUnit);

        int databaseSizeBeforeDelete = extraPassUnitRepository.findAll().size();

        // Delete the extraPassUnit
        restExtraPassUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, extraPassUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtraPassUnit> extraPassUnitList = extraPassUnitRepository.findAll();
        assertThat(extraPassUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
