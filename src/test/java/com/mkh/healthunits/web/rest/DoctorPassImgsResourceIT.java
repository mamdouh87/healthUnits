package com.mkh.healthunits.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.DoctorPassImgs;
import com.mkh.healthunits.repository.DoctorPassImgsRepository;
import com.mkh.healthunits.service.dto.DoctorPassImgsDTO;
import com.mkh.healthunits.service.mapper.DoctorPassImgsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DoctorPassImgsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorPassImgsResourceIT {

    private static final Integer DEFAULT_DAY_VALUE = 1;
    private static final Integer UPDATED_DAY_VALUE = 2;

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/doctor-pass-imgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoctorPassImgsRepository doctorPassImgsRepository;

    @Autowired
    private DoctorPassImgsMapper doctorPassImgsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorPassImgsMockMvc;

    private DoctorPassImgs doctorPassImgs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorPassImgs createEntity(EntityManager em) {
        DoctorPassImgs doctorPassImgs = new DoctorPassImgs()
            .dayValue(DEFAULT_DAY_VALUE)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return doctorPassImgs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorPassImgs createUpdatedEntity(EntityManager em) {
        DoctorPassImgs doctorPassImgs = new DoctorPassImgs()
            .dayValue(UPDATED_DAY_VALUE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return doctorPassImgs;
    }

    @BeforeEach
    public void initTest() {
        doctorPassImgs = createEntity(em);
    }

    @Test
    @Transactional
    void createDoctorPassImgs() throws Exception {
        int databaseSizeBeforeCreate = doctorPassImgsRepository.findAll().size();
        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);
        restDoctorPassImgsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorPassImgs testDoctorPassImgs = doctorPassImgsList.get(doctorPassImgsList.size() - 1);
        assertThat(testDoctorPassImgs.getDayValue()).isEqualTo(DEFAULT_DAY_VALUE);
        assertThat(testDoctorPassImgs.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testDoctorPassImgs.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createDoctorPassImgsWithExistingId() throws Exception {
        // Create the DoctorPassImgs with an existing ID
        doctorPassImgs.setId(1L);
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        int databaseSizeBeforeCreate = doctorPassImgsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorPassImgsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctorPassImgs() throws Exception {
        // Initialize the database
        doctorPassImgsRepository.saveAndFlush(doctorPassImgs);

        // Get all the doctorPassImgsList
        restDoctorPassImgsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorPassImgs.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayValue").value(hasItem(DEFAULT_DAY_VALUE)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    void getDoctorPassImgs() throws Exception {
        // Initialize the database
        doctorPassImgsRepository.saveAndFlush(doctorPassImgs);

        // Get the doctorPassImgs
        restDoctorPassImgsMockMvc
            .perform(get(ENTITY_API_URL_ID, doctorPassImgs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctorPassImgs.getId().intValue()))
            .andExpect(jsonPath("$.dayValue").value(DEFAULT_DAY_VALUE))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }

    @Test
    @Transactional
    void getNonExistingDoctorPassImgs() throws Exception {
        // Get the doctorPassImgs
        restDoctorPassImgsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctorPassImgs() throws Exception {
        // Initialize the database
        doctorPassImgsRepository.saveAndFlush(doctorPassImgs);

        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();

        // Update the doctorPassImgs
        DoctorPassImgs updatedDoctorPassImgs = doctorPassImgsRepository.findById(doctorPassImgs.getId()).get();
        // Disconnect from session so that the updates on updatedDoctorPassImgs are not directly saved in db
        em.detach(updatedDoctorPassImgs);
        updatedDoctorPassImgs.dayValue(UPDATED_DAY_VALUE).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(updatedDoctorPassImgs);

        restDoctorPassImgsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorPassImgsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
        DoctorPassImgs testDoctorPassImgs = doctorPassImgsList.get(doctorPassImgsList.size() - 1);
        assertThat(testDoctorPassImgs.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testDoctorPassImgs.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testDoctorPassImgs.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingDoctorPassImgs() throws Exception {
        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();
        doctorPassImgs.setId(count.incrementAndGet());

        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorPassImgsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorPassImgsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctorPassImgs() throws Exception {
        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();
        doctorPassImgs.setId(count.incrementAndGet());

        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorPassImgsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctorPassImgs() throws Exception {
        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();
        doctorPassImgs.setId(count.incrementAndGet());

        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorPassImgsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorPassImgsWithPatch() throws Exception {
        // Initialize the database
        doctorPassImgsRepository.saveAndFlush(doctorPassImgs);

        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();

        // Update the doctorPassImgs using partial update
        DoctorPassImgs partialUpdatedDoctorPassImgs = new DoctorPassImgs();
        partialUpdatedDoctorPassImgs.setId(doctorPassImgs.getId());

        partialUpdatedDoctorPassImgs.dayValue(UPDATED_DAY_VALUE).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restDoctorPassImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorPassImgs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctorPassImgs))
            )
            .andExpect(status().isOk());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
        DoctorPassImgs testDoctorPassImgs = doctorPassImgsList.get(doctorPassImgsList.size() - 1);
        assertThat(testDoctorPassImgs.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testDoctorPassImgs.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testDoctorPassImgs.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDoctorPassImgsWithPatch() throws Exception {
        // Initialize the database
        doctorPassImgsRepository.saveAndFlush(doctorPassImgs);

        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();

        // Update the doctorPassImgs using partial update
        DoctorPassImgs partialUpdatedDoctorPassImgs = new DoctorPassImgs();
        partialUpdatedDoctorPassImgs.setId(doctorPassImgs.getId());

        partialUpdatedDoctorPassImgs.dayValue(UPDATED_DAY_VALUE).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restDoctorPassImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorPassImgs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctorPassImgs))
            )
            .andExpect(status().isOk());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
        DoctorPassImgs testDoctorPassImgs = doctorPassImgsList.get(doctorPassImgsList.size() - 1);
        assertThat(testDoctorPassImgs.getDayValue()).isEqualTo(UPDATED_DAY_VALUE);
        assertThat(testDoctorPassImgs.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testDoctorPassImgs.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingDoctorPassImgs() throws Exception {
        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();
        doctorPassImgs.setId(count.incrementAndGet());

        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorPassImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorPassImgsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctorPassImgs() throws Exception {
        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();
        doctorPassImgs.setId(count.incrementAndGet());

        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorPassImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctorPassImgs() throws Exception {
        int databaseSizeBeforeUpdate = doctorPassImgsRepository.findAll().size();
        doctorPassImgs.setId(count.incrementAndGet());

        // Create the DoctorPassImgs
        DoctorPassImgsDTO doctorPassImgsDTO = doctorPassImgsMapper.toDto(doctorPassImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorPassImgsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorPassImgsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorPassImgs in the database
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctorPassImgs() throws Exception {
        // Initialize the database
        doctorPassImgsRepository.saveAndFlush(doctorPassImgs);

        int databaseSizeBeforeDelete = doctorPassImgsRepository.findAll().size();

        // Delete the doctorPassImgs
        restDoctorPassImgsMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctorPassImgs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorPassImgs> doctorPassImgsList = doctorPassImgsRepository.findAll();
        assertThat(doctorPassImgsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
