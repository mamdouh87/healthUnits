package com.mkh.healthunits.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mkh.healthunits.IntegrationTest;
import com.mkh.healthunits.domain.UnitImgs;
import com.mkh.healthunits.repository.UnitImgsRepository;
import com.mkh.healthunits.service.dto.UnitImgsDTO;
import com.mkh.healthunits.service.mapper.UnitImgsMapper;
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
 * Integration tests for the {@link UnitImgsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnitImgsResourceIT {

    private static final String DEFAULT_FILE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/unit-imgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnitImgsRepository unitImgsRepository;

    @Autowired
    private UnitImgsMapper unitImgsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitImgsMockMvc;

    private UnitImgs unitImgs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitImgs createEntity(EntityManager em) {
        UnitImgs unitImgs = new UnitImgs()
            .fileDescription(DEFAULT_FILE_DESCRIPTION)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return unitImgs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitImgs createUpdatedEntity(EntityManager em) {
        UnitImgs unitImgs = new UnitImgs()
            .fileDescription(UPDATED_FILE_DESCRIPTION)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return unitImgs;
    }

    @BeforeEach
    public void initTest() {
        unitImgs = createEntity(em);
    }

    @Test
    @Transactional
    void createUnitImgs() throws Exception {
        int databaseSizeBeforeCreate = unitImgsRepository.findAll().size();
        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);
        restUnitImgsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitImgsDTO)))
            .andExpect(status().isCreated());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeCreate + 1);
        UnitImgs testUnitImgs = unitImgsList.get(unitImgsList.size() - 1);
        assertThat(testUnitImgs.getFileDescription()).isEqualTo(DEFAULT_FILE_DESCRIPTION);
        assertThat(testUnitImgs.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testUnitImgs.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createUnitImgsWithExistingId() throws Exception {
        // Create the UnitImgs with an existing ID
        unitImgs.setId(1L);
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        int databaseSizeBeforeCreate = unitImgsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitImgsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitImgsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnitImgs() throws Exception {
        // Initialize the database
        unitImgsRepository.saveAndFlush(unitImgs);

        // Get all the unitImgsList
        restUnitImgsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitImgs.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileDescription").value(hasItem(DEFAULT_FILE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    void getUnitImgs() throws Exception {
        // Initialize the database
        unitImgsRepository.saveAndFlush(unitImgs);

        // Get the unitImgs
        restUnitImgsMockMvc
            .perform(get(ENTITY_API_URL_ID, unitImgs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unitImgs.getId().intValue()))
            .andExpect(jsonPath("$.fileDescription").value(DEFAULT_FILE_DESCRIPTION))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }

    @Test
    @Transactional
    void getNonExistingUnitImgs() throws Exception {
        // Get the unitImgs
        restUnitImgsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUnitImgs() throws Exception {
        // Initialize the database
        unitImgsRepository.saveAndFlush(unitImgs);

        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();

        // Update the unitImgs
        UnitImgs updatedUnitImgs = unitImgsRepository.findById(unitImgs.getId()).get();
        // Disconnect from session so that the updates on updatedUnitImgs are not directly saved in db
        em.detach(updatedUnitImgs);
        updatedUnitImgs.fileDescription(UPDATED_FILE_DESCRIPTION).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(updatedUnitImgs);

        restUnitImgsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unitImgsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitImgsDTO))
            )
            .andExpect(status().isOk());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
        UnitImgs testUnitImgs = unitImgsList.get(unitImgsList.size() - 1);
        assertThat(testUnitImgs.getFileDescription()).isEqualTo(UPDATED_FILE_DESCRIPTION);
        assertThat(testUnitImgs.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testUnitImgs.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingUnitImgs() throws Exception {
        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();
        unitImgs.setId(count.incrementAndGet());

        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitImgsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unitImgsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnitImgs() throws Exception {
        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();
        unitImgs.setId(count.incrementAndGet());

        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitImgsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnitImgs() throws Exception {
        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();
        unitImgs.setId(count.incrementAndGet());

        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitImgsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitImgsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnitImgsWithPatch() throws Exception {
        // Initialize the database
        unitImgsRepository.saveAndFlush(unitImgs);

        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();

        // Update the unitImgs using partial update
        UnitImgs partialUpdatedUnitImgs = new UnitImgs();
        partialUpdatedUnitImgs.setId(unitImgs.getId());

        restUnitImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnitImgs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnitImgs))
            )
            .andExpect(status().isOk());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
        UnitImgs testUnitImgs = unitImgsList.get(unitImgsList.size() - 1);
        assertThat(testUnitImgs.getFileDescription()).isEqualTo(DEFAULT_FILE_DESCRIPTION);
        assertThat(testUnitImgs.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testUnitImgs.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateUnitImgsWithPatch() throws Exception {
        // Initialize the database
        unitImgsRepository.saveAndFlush(unitImgs);

        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();

        // Update the unitImgs using partial update
        UnitImgs partialUpdatedUnitImgs = new UnitImgs();
        partialUpdatedUnitImgs.setId(unitImgs.getId());

        partialUpdatedUnitImgs.fileDescription(UPDATED_FILE_DESCRIPTION).img(UPDATED_IMG).imgContentType(UPDATED_IMG_CONTENT_TYPE);

        restUnitImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnitImgs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnitImgs))
            )
            .andExpect(status().isOk());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
        UnitImgs testUnitImgs = unitImgsList.get(unitImgsList.size() - 1);
        assertThat(testUnitImgs.getFileDescription()).isEqualTo(UPDATED_FILE_DESCRIPTION);
        assertThat(testUnitImgs.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testUnitImgs.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingUnitImgs() throws Exception {
        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();
        unitImgs.setId(count.incrementAndGet());

        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unitImgsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unitImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnitImgs() throws Exception {
        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();
        unitImgs.setId(count.incrementAndGet());

        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitImgsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unitImgsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnitImgs() throws Exception {
        int databaseSizeBeforeUpdate = unitImgsRepository.findAll().size();
        unitImgs.setId(count.incrementAndGet());

        // Create the UnitImgs
        UnitImgsDTO unitImgsDTO = unitImgsMapper.toDto(unitImgs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitImgsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unitImgsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnitImgs in the database
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnitImgs() throws Exception {
        // Initialize the database
        unitImgsRepository.saveAndFlush(unitImgs);

        int databaseSizeBeforeDelete = unitImgsRepository.findAll().size();

        // Delete the unitImgs
        restUnitImgsMockMvc
            .perform(delete(ENTITY_API_URL_ID, unitImgs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnitImgs> unitImgsList = unitImgsRepository.findAll();
        assertThat(unitImgsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
