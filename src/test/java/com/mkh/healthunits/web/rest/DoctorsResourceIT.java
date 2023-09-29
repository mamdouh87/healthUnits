package com.mkh.healthunits.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.Doctors;
import com.mkh.healthunits.repository.DoctorsRepository;
import com.mkh.healthunits.service.dto.DoctorsDTO;
import com.mkh.healthunits.service.mapper.DoctorsMapper;
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
 * Integration tests for the {@link DoctorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PERFERED_DAY = "AAAAAAAAAA";
    private static final String UPDATED_PERFERED_DAY = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOUBLE_SHIFT = 1;
    private static final Integer UPDATED_DOUBLE_SHIFT = 2;

    private static final String DEFAULT_SHIFT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHIFT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PERFERED_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_PERFERED_UNIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doctors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoctorsRepository doctorsRepository;

    @Autowired
    private DoctorsMapper doctorsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorsMockMvc;

    private Doctors doctors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctors createEntity(EntityManager em) {
        Doctors doctors = new Doctors()
            .name(DEFAULT_NAME)
            .perferedDay(DEFAULT_PERFERED_DAY)
            .doubleShift(DEFAULT_DOUBLE_SHIFT)
            .shiftType(DEFAULT_SHIFT_TYPE)
            .perferedUnit(DEFAULT_PERFERED_UNIT);
        return doctors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctors createUpdatedEntity(EntityManager em) {
        Doctors doctors = new Doctors()
            .name(UPDATED_NAME)
            .perferedDay(UPDATED_PERFERED_DAY)
            .doubleShift(UPDATED_DOUBLE_SHIFT)
            .shiftType(UPDATED_SHIFT_TYPE)
            .perferedUnit(UPDATED_PERFERED_UNIT);
        return doctors;
    }

    @BeforeEach
    public void initTest() {
        doctors = createEntity(em);
    }

    @Test
    @Transactional
    void createDoctors() throws Exception {
        int databaseSizeBeforeCreate = doctorsRepository.findAll().size();
        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);
        restDoctorsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorsDTO)))
            .andExpect(status().isCreated());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeCreate + 1);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctors.getPerferedDay()).isEqualTo(DEFAULT_PERFERED_DAY);
        assertThat(testDoctors.getDoubleShift()).isEqualTo(DEFAULT_DOUBLE_SHIFT);
        assertThat(testDoctors.getShiftType()).isEqualTo(DEFAULT_SHIFT_TYPE);
        assertThat(testDoctors.getPerferedUnit()).isEqualTo(DEFAULT_PERFERED_UNIT);
    }

    @Test
    @Transactional
    void createDoctorsWithExistingId() throws Exception {
        // Create the Doctors with an existing ID
        doctors.setId(1L);
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        int databaseSizeBeforeCreate = doctorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.saveAndFlush(doctors);

        // Get all the doctorsList
        restDoctorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctors.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].perferedDay").value(hasItem(DEFAULT_PERFERED_DAY)))
            .andExpect(jsonPath("$.[*].doubleShift").value(hasItem(DEFAULT_DOUBLE_SHIFT)))
            .andExpect(jsonPath("$.[*].shiftType").value(hasItem(DEFAULT_SHIFT_TYPE)))
            .andExpect(jsonPath("$.[*].perferedUnit").value(hasItem(DEFAULT_PERFERED_UNIT)));
    }

    @Test
    @Transactional
    void getDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.saveAndFlush(doctors);

        // Get the doctors
        restDoctorsMockMvc
            .perform(get(ENTITY_API_URL_ID, doctors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctors.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.perferedDay").value(DEFAULT_PERFERED_DAY))
            .andExpect(jsonPath("$.doubleShift").value(DEFAULT_DOUBLE_SHIFT))
            .andExpect(jsonPath("$.shiftType").value(DEFAULT_SHIFT_TYPE))
            .andExpect(jsonPath("$.perferedUnit").value(DEFAULT_PERFERED_UNIT));
    }

    @Test
    @Transactional
    void getNonExistingDoctors() throws Exception {
        // Get the doctors
        restDoctorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.saveAndFlush(doctors);

        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();

        // Update the doctors
        Doctors updatedDoctors = doctorsRepository.findById(doctors.getId()).get();
        // Disconnect from session so that the updates on updatedDoctors are not directly saved in db
        em.detach(updatedDoctors);
        updatedDoctors
            .name(UPDATED_NAME)
            .perferedDay(UPDATED_PERFERED_DAY)
            .doubleShift(UPDATED_DOUBLE_SHIFT)
            .shiftType(UPDATED_SHIFT_TYPE)
            .perferedUnit(UPDATED_PERFERED_UNIT);
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(updatedDoctors);

        restDoctorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctors.getPerferedDay()).isEqualTo(UPDATED_PERFERED_DAY);
        assertThat(testDoctors.getDoubleShift()).isEqualTo(UPDATED_DOUBLE_SHIFT);
        assertThat(testDoctors.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testDoctors.getPerferedUnit()).isEqualTo(UPDATED_PERFERED_UNIT);
    }

    @Test
    @Transactional
    void putNonExistingDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(count.incrementAndGet());

        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(count.incrementAndGet());

        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(count.incrementAndGet());

        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorsWithPatch() throws Exception {
        // Initialize the database
        doctorsRepository.saveAndFlush(doctors);

        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();

        // Update the doctors using partial update
        Doctors partialUpdatedDoctors = new Doctors();
        partialUpdatedDoctors.setId(doctors.getId());

        partialUpdatedDoctors
            .perferedDay(UPDATED_PERFERED_DAY)
            .doubleShift(UPDATED_DOUBLE_SHIFT)
            .shiftType(UPDATED_SHIFT_TYPE)
            .perferedUnit(UPDATED_PERFERED_UNIT);

        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctors))
            )
            .andExpect(status().isOk());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctors.getPerferedDay()).isEqualTo(UPDATED_PERFERED_DAY);
        assertThat(testDoctors.getDoubleShift()).isEqualTo(UPDATED_DOUBLE_SHIFT);
        assertThat(testDoctors.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testDoctors.getPerferedUnit()).isEqualTo(UPDATED_PERFERED_UNIT);
    }

    @Test
    @Transactional
    void fullUpdateDoctorsWithPatch() throws Exception {
        // Initialize the database
        doctorsRepository.saveAndFlush(doctors);

        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();

        // Update the doctors using partial update
        Doctors partialUpdatedDoctors = new Doctors();
        partialUpdatedDoctors.setId(doctors.getId());

        partialUpdatedDoctors
            .name(UPDATED_NAME)
            .perferedDay(UPDATED_PERFERED_DAY)
            .doubleShift(UPDATED_DOUBLE_SHIFT)
            .shiftType(UPDATED_SHIFT_TYPE)
            .perferedUnit(UPDATED_PERFERED_UNIT);

        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctors))
            )
            .andExpect(status().isOk());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctors.getPerferedDay()).isEqualTo(UPDATED_PERFERED_DAY);
        assertThat(testDoctors.getDoubleShift()).isEqualTo(UPDATED_DOUBLE_SHIFT);
        assertThat(testDoctors.getShiftType()).isEqualTo(UPDATED_SHIFT_TYPE);
        assertThat(testDoctors.getPerferedUnit()).isEqualTo(UPDATED_PERFERED_UNIT);
    }

    @Test
    @Transactional
    void patchNonExistingDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(count.incrementAndGet());

        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(count.incrementAndGet());

        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(count.incrementAndGet());

        // Create the Doctors
        DoctorsDTO doctorsDTO = doctorsMapper.toDto(doctors);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(doctorsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.saveAndFlush(doctors);

        int databaseSizeBeforeDelete = doctorsRepository.findAll().size();

        // Delete the doctors
        restDoctorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctors.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
