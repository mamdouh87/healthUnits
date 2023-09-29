package com.mkh.healthunits.web.rest;

import com.mkh.healthunits.repository.DoctorPassImgsRepository;
import com.mkh.healthunits.service.DoctorPassImgsService;
import com.mkh.healthunits.service.dto.DoctorPassImgsDTO;
import com.mkh.healthunits.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mkh.healthunits.domain.DoctorPassImgs}.
 */
@RestController
@RequestMapping("/api")
public class DoctorPassImgsResource {

    private final Logger log = LoggerFactory.getLogger(DoctorPassImgsResource.class);

    private static final String ENTITY_NAME = "doctorPassImgs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorPassImgsService doctorPassImgsService;

    private final DoctorPassImgsRepository doctorPassImgsRepository;

    public DoctorPassImgsResource(DoctorPassImgsService doctorPassImgsService, DoctorPassImgsRepository doctorPassImgsRepository) {
        this.doctorPassImgsService = doctorPassImgsService;
        this.doctorPassImgsRepository = doctorPassImgsRepository;
    }

    /**
     * {@code POST  /doctor-pass-imgs} : Create a new doctorPassImgs.
     *
     * @param doctorPassImgsDTO the doctorPassImgsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorPassImgsDTO, or with status {@code 400 (Bad Request)} if the doctorPassImgs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctor-pass-imgs")
    public ResponseEntity<DoctorPassImgsDTO> createDoctorPassImgs(@RequestBody DoctorPassImgsDTO doctorPassImgsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DoctorPassImgs : {}", doctorPassImgsDTO);
        if (doctorPassImgsDTO.getId() != null) {
            throw new BadRequestAlertException("A new doctorPassImgs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorPassImgsDTO result = doctorPassImgsService.save(doctorPassImgsDTO);
        return ResponseEntity
            .created(new URI("/api/doctor-pass-imgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctor-pass-imgs/:id} : Updates an existing doctorPassImgs.
     *
     * @param id the id of the doctorPassImgsDTO to save.
     * @param doctorPassImgsDTO the doctorPassImgsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorPassImgsDTO,
     * or with status {@code 400 (Bad Request)} if the doctorPassImgsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorPassImgsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctor-pass-imgs/{id}")
    public ResponseEntity<DoctorPassImgsDTO> updateDoctorPassImgs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorPassImgsDTO doctorPassImgsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DoctorPassImgs : {}, {}", id, doctorPassImgsDTO);
        if (doctorPassImgsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorPassImgsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorPassImgsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DoctorPassImgsDTO result = doctorPassImgsService.update(doctorPassImgsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorPassImgsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /doctor-pass-imgs/:id} : Partial updates given fields of an existing doctorPassImgs, field will ignore if it is null
     *
     * @param id the id of the doctorPassImgsDTO to save.
     * @param doctorPassImgsDTO the doctorPassImgsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorPassImgsDTO,
     * or with status {@code 400 (Bad Request)} if the doctorPassImgsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the doctorPassImgsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the doctorPassImgsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/doctor-pass-imgs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DoctorPassImgsDTO> partialUpdateDoctorPassImgs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorPassImgsDTO doctorPassImgsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DoctorPassImgs partially : {}, {}", id, doctorPassImgsDTO);
        if (doctorPassImgsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorPassImgsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorPassImgsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DoctorPassImgsDTO> result = doctorPassImgsService.partialUpdate(doctorPassImgsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorPassImgsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /doctor-pass-imgs} : get all the doctorPassImgs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorPassImgs in body.
     */
    @GetMapping("/doctor-pass-imgs")
    public ResponseEntity<List<DoctorPassImgsDTO>> getAllDoctorPassImgs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DoctorPassImgs");
        Page<DoctorPassImgsDTO> page = doctorPassImgsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctor-pass-imgs/:id} : get the "id" doctorPassImgs.
     *
     * @param id the id of the doctorPassImgsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorPassImgsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctor-pass-imgs/{id}")
    public ResponseEntity<DoctorPassImgsDTO> getDoctorPassImgs(@PathVariable Long id) {
        log.debug("REST request to get DoctorPassImgs : {}", id);
        Optional<DoctorPassImgsDTO> doctorPassImgsDTO = doctorPassImgsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorPassImgsDTO);
    }

    /**
     * {@code DELETE  /doctor-pass-imgs/:id} : delete the "id" doctorPassImgs.
     *
     * @param id the id of the doctorPassImgsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctor-pass-imgs/{id}")
    public ResponseEntity<Void> deleteDoctorPassImgs(@PathVariable Long id) {
        log.debug("REST request to delete DoctorPassImgs : {}", id);
        doctorPassImgsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
