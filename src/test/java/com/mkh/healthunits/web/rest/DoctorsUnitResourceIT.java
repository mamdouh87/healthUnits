package com.mkh.healthunits.web.rest;

import static com.mkh.healthunits.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.DoctorsUnit;
import com.mkh.healthunits.repository.DoctorsUnitRepository;
import com.mkh.healthunits.service.dto.DoctorsUnitDTO;
import com.mkh.healthunits.service.mapper.DoctorsUnitMapper;
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
 * Integration tests for the {@link DoctorsUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorsUnitResourceIT {

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

    private static final Integer DEFAULT_PASS_BLOCKED = 1;
    private static final Integer UPDATED_PASS_BLOCKED = 2;

    private static final Integer DEFAULT_JUST_VIEW = 1;
    private static final Integer UPDATED_JUST_VIEW = 2;

    private static final String ENTITY_API_URL = "/api/doctors-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoctorsUnitRepository doctorsUnitRepository;

    @Autowired
    private DoctorsUnitMapper doctorsUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorsUnitMockMvc;

    private DoctorsUnit doctorsUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorsUnit createEntity(EntityManager em) {
        DoctorsUnit doctorsUnit = new DoctorsUnit()
            .dayValue(DEFAULT_DAY_VALUE)
            .shiftType(DEFAULT_SHIFT_TYPE)
            .activeWeek(DEFAULT_ACTIVE_WEEK)
            .donePass(DEFAULT_DONE_PASS)
            .passDate(DEFAULT_PASS_DATE)
            .unitPassed(DEFAULT_UNIT_PASSED)
            .passBlocked(DEFAULT_PASS_BLOCKED)
            .justView(DEFAULT_JUST_VIEW);
        return doctorsUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorsUnit createUpdatedEntity(EntityManager em) {
        DoctorsUnit doctorsUnit = new DoctorsUnit()
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE)
            .unitPassed(UPDATED_UNIT_PASSED)
            .passBlocked(UPDATED_PASS_BLOCKED)
            .justView(UPDATED_JUST_VIEW);
        return doctorsUnit;
    }

    @BeforeEach
    public void initTest() {
        doctorsUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createDoctorsUnit() throws Exception {
        int databaseSizeBeforeCreate = doctorsUnitRepository.findAll().size();
        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);
        restDoctorsUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorsUnit testDoctorsUnit = doctorsUnitList.get(doctorsUnitList.size() - 1);
        assertThat(testDoctorsUnit.getDayValue()).isEqualTo(DEFAULT_DAY_VALUE);
        assertThat(testDoctorsUnit.getShiftType()).isEqualTo(DEFAULT_SHIFT_TYPE);
        assertThat(testDoctorsUnit.getActiveWeek()).isEqualTo(DEFAULT_ACTIVE_WEEK);
        assertThat(testDoctorsUnit.getDonePass()).isEqualTo(DEFAULT_DONE_PASS);
        assertThat(testDoctorsUnit.getPassDate()).isEqualTo(DEFAULT_PASS_DATE);
        assertThat(testDoctorsUnit.getUnitPassed()).isEqualTo(DEFAULT_UNIT_PASSED);
        assertThat(testDoctorsUnit.getPassBlocked()).isEqualTo(DEFAULT_PASS_BLOCKED);
        assertThat(testDoctorsUnit.getJustView()).isEqualTo(DEFAULT_JUST_VIEW);
    }

    @Test
    @Transactional
    void createDoctorsUnitWithExistingId() throws Exception {
        // Create the DoctorsUnit with an existing ID
        doctorsUnit.setId(1L);
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        int databaseSizeBeforeCreate = doctorsUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorsUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctorsUnits() throws Exception {
        // Initialize the database
        doctorsUnitRepository.saveAndFlush(doctorsUnit);

        // Get all the doctorsUnitList
        restDoctorsUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorsUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayValue").value(hasItem(DEFAULT_DAY_VALUE)))
            .andExpect(jsonPath("$.[*].shiftType").value(hasItem(DEFAULT_SHIFT_TYPE)))
            .andExpect(jsonPath("$.[*].activeWeek").value(hasItem(DEFAULT_ACTIVE_WEEK)))
            .andExpect(jsonPath("$.[*].donePass").value(hasItem(DEFAULT_DONE_PASS)))
            .andExpect(jsonPath("$.[*].passDate").value(hasItem(sameInstant(DEFAULT_PASS_DATE))))
            .andExpect(jsonPath("$.[*].unitPassed").value(hasItem(DEFAULT_UNIT_PASSED)))
            .andExpect(jsonPath("$.[*].passBlocked").value(hasItem(DEFAULT_PASS_BLOCKED)))
            .andExpect(jsonPath("$.[*].justView").value(hasItem(DEFAULT_JUST_VIEW)));
    }

    @Test
    @Transactional
    void getDoctorsUnit() throws Exception {
        // Initialize the database
        doctorsUnitRepository.saveAndFlush(doctorsUnit);

        // Get the doctorsUnit
        restDoctorsUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, doctorsUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctorsUnit.getId().intValue()))
            .andExpect(jsonPath("$.dayValue").value(DEFAULT_DAY_VALUE))
            .andExpect(jsonPath("$.shiftType").value(DEFAULT_SHIFT_TYPE))
            .andExpect(jsonPath("$.activeWeek").value(DEFAULT_ACTIVE_WEEK))
            .andExpect(jsonPath("$.donePass").value(DEFAULT_DONE_PASS))
            .andExpect(jsonPath("$.passDate").value(sameInstant(DEFAULT_PASS_DATE)))
            .andExpect(jsonPath("$.unitPassed").value(DEFAULT_UNIT_PASSED))
            .andExpect(jsonPath("$.passBlocked").value(DEFAULT_PASS_BLOCKED))
            .andExpect(jsonPath("$.justView").value(DEFAULT_JUST_VIEW));
    }

    @Test
    @Transactional
    void getNonExistingDoctorsUnit() throws Exception {
        // Get the doctorsUnit
        restDoctorsUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctorsUnit() throws Exception {
        // Initialize the database
        doctorsUnitRepository.saveAndFlush(doctorsUnit);

        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();

        // Update the doctorsUnit
        DoctorsUnit updatedDoctorsUnit = doctorsUnitRepository.findById(doctorsUnit.getId()).get();
        // Disconnect from session so that the updates on updatedDoctorsUnit are not directly saved in db
        em.detach(updatedDoctorsUnit);
        updatedDoctorsUnit
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE)
            .unitPassed(UPDATED_UNIT_PASSED)
            .passBlocked(UPDATED_PASS_BLOCKED)
            .justView(UPDATED_JUST_VIEW);
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(updatedDoctorsUnit);

        restDoctorsUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorsUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
        DoctorsUnit testDoctorsUnit = doctorsUnitList.get(doctorsUnitList.size() - 1);
        assertThat(testDoctorsUnit.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testDoctorsUnit.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testDoctorsUnit.getActiveWeek()).isEqualTo(UPDATED_ACTIVE_WEEK);
        assertThat(testDoctorsUnit.getDonePass()).isEqualTo(UPDATED_DONE_PASS);
        assertThat(testDoctorsUnit.getPassDate()).isEqualTo(UPDATED_PASS_DATE);
        assertThat(testDoctorsUnit.getUnitPassed()).isEqualTo(UPDATED_UNIT_PASSED);
        assertThat(testDoctorsUnit.getPassBlocked()).isEqualTo(UPDATED_PASS_BLOCKED);
        assertThat(testDoctorsUnit.getJustView()).isEqualTo(UPDATED_JUST_VIEW);
    }

    @Test
    @Transactional
    void putNonExistingDoctorsUnit() throws Exception {
        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();
        doctorsUnit.setId(count.incrementAndGet());

        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorsUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorsUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctorsUnit() throws Exception {
        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();
        doctorsUnit.setId(count.incrementAndGet());

        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctorsUnit() throws Exception {
        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();
        doctorsUnit.setId(count.incrementAndGet());

        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorsUnitWithPatch() throws Exception {
        // Initialize the database
        doctorsUnitRepository.saveAndFlush(doctorsUnit);

        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();

        // Update the doctorsUnit using partial update
        DoctorsUnit partialUpdatedDoctorsUnit = new DoctorsUnit();
        partialUpdatedDoctorsUnit.setId(doctorsUnit.getId());

        partialUpdatedDoctorsUnit
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .unitPassed(UPDATED_UNIT_PASSED)
            .passBlocked(UPDATED_PASS_BLOCKED)
            .justView(UPDATED_JUST_VIEW);

        restDoctorsUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorsUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctorsUnit))
            )
            .andExpect(status().isOk());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
        DoctorsUnit testDoctorsUnit = doctorsUnitList.get(doctorsUnitList.size() - 1);
        assertThat(testDoctorsUnit.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testDoctorsUnit.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testDoctorsUnit.getActiveWeek()).isEqualTo(UPDATED_ACTIVE_WEEK);
        assertThat(testDoctorsUnit.getDonePass()).isEqualTo(UPDATED_DONE_PASS);
        assertThat(testDoctorsUnit.getPassDate()).isEqualTo(DEFAULT_PASS_DATE);
        assertThat(testDoctorsUnit.getUnitPassed()).isEqualTo(UPDATED_UNIT_PASSED);
        assertThat(testDoctorsUnit.getPassBlocked()).isEqualTo(UPDATED_PASS_BLOCKED);
        assertThat(testDoctorsUnit.getJustView()).isEqualTo(UPDATED_JUST_VIEW);
    }

    @Test
    @Transactional
    void fullUpdateDoctorsUnitWithPatch() throws Exception {
        // Initialize the database
        doctorsUnitRepository.saveAndFlush(doctorsUnit);

        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();

        // Update the doctorsUnit using partial update
        DoctorsUnit partialUpdatedDoctorsUnit = new DoctorsUnit();
        partialUpdatedDoctorsUnit.setId(doctorsUnit.getId());

        partialUpdatedDoctorsUnit
            .dayValue(UPDATED_DAY_VALUE)
            .shiftType(UPDATED_SHIFT_TYPE)
            .activeWeek(UPDATED_ACTIVE_WEEK)
            .donePass(UPDATED_DONE_PASS)
            .passDate(UPDATED_PASS_DATE)
            .unitPassed(UPDATED_UNIT_PASSED)
            .passBlocked(UPDATED_PASS_BLOCKED)
            .justView(UPDATED_JUST_VIEW);

        restDoctorsUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorsUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctorsUnit))
            )
            .andExpect(status().isOk());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
        DoctorsUnit testDoctorsUnit = doctorsUnitList.get(doctorsUnitList.size() - 1);
        assertThat(testDoctorsUnit.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testDoctorsUnit.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testDoctorsUnit.getActiveWeek()).isEqualTo(UPDATED_ACTIVE_WEEK);
        assertThat(testDoctorsUnit.getDonePass()).isEqualTo(UPDATED_DONE_PASS);
        assertThat(testDoctorsUnit.getPassDate()).isEqualTo(UPDATED_PASS_DATE);
        assertThat(testDoctorsUnit.getUnitPassed()).isEqualTo(UPDATED_UNIT_PASSED);
        assertThat(testDoctorsUnit.getPassBlocked()).isEqualTo(UPDATED_PASS_BLOCKED);
        assertThat(testDoctorsUnit.getJustView()).isEqualTo(UPDATED_JUST_VIEW);
    }

    @Test
    @Transactional
    void patchNonExistingDoctorsUnit() throws Exception {
        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();
        doctorsUnit.setId(count.incrementAndGet());

        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorsUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorsUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctorsUnit() throws Exception {
        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();
        doctorsUnit.setId(count.incrementAndGet());

        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctorsUnit() throws Exception {
        int databaseSizeBeforeUpdate = doctorsUnitRepository.findAll().size();
        doctorsUnit.setId(count.incrementAndGet());

        // Create the DoctorsUnit
        DoctorsUnitDTO doctorsUnitDTO = doctorsUnitMapper.toDto(doctorsUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsUnitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(doctorsUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorsUnit in the database
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctorsUnit() throws Exception {
        // Initialize the database
        doctorsUnitRepository.saveAndFlush(doctorsUnit);

        int databaseSizeBeforeDelete = doctorsUnitRepository.findAll().size();

        // Delete the doctorsUnit
        restDoctorsUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctorsUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorsUnit> doctorsUnitList = doctorsUnitRepository.findAll();
        assertThat(doctorsUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
