package com.mkh.healthunits.web.rest;

import com.mkh.healthunits.repository.DoctorsUnitRepository;
import com.mkh.healthunits.service.DoctorsUnitService;
import com.mkh.healthunits.service.dto.DoctorsUnitDTO;
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
 * REST controller for managing {@link com.mkh.healthunits.domain.DoctorsUnit}.
 */
@RestController
@RequestMapping("/api")
public class DoctorsUnitResource {

    private final Logger log = LoggerFactory.getLogger(DoctorsUnitResource.class);

    private static final String ENTITY_NAME = "doctorsUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorsUnitService doctorsUnitService;

    private final DoctorsUnitRepository doctorsUnitRepository;

    public DoctorsUnitResource(DoctorsUnitService doctorsUnitService, DoctorsUnitRepository doctorsUnitRepository) {
        this.doctorsUnitService = doctorsUnitService;
        this.doctorsUnitRepository = doctorsUnitRepository;
    }

    /**
     * {@code POST  /doctors-units} : Create a new doctorsUnit.
     *
     * @param doctorsUnitDTO the doctorsUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorsUnitDTO, or with status {@code 400 (Bad Request)} if the doctorsUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctors-units")
    public ResponseEntity<DoctorsUnitDTO> createDoctorsUnit(@RequestBody DoctorsUnitDTO doctorsUnitDTO) throws URISyntaxException {
        log.debug("REST request to save DoctorsUnit : {}", doctorsUnitDTO);
        if (doctorsUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new doctorsUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorsUnitDTO result = doctorsUnitService.save(doctorsUnitDTO);
        return ResponseEntity
            .created(new URI("/api/doctors-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctors-units/:id} : Updates an existing doctorsUnit.
     *
     * @param id the id of the doctorsUnitDTO to save.
     * @param doctorsUnitDTO the doctorsUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorsUnitDTO,
     * or with status {@code 400 (Bad Request)} if the doctorsUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorsUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctors-units/{id}")
    public ResponseEntity<DoctorsUnitDTO> updateDoctorsUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorsUnitDTO doctorsUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DoctorsUnit : {}, {}", id, doctorsUnitDTO);
        if (doctorsUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorsUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorsUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DoctorsUnitDTO result = doctorsUnitService.update(doctorsUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorsUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /doctors-units/:id} : Partial updates given fields of an existing doctorsUnit, field will ignore if it is null
     *
     * @param id the id of the doctorsUnitDTO to save.
     * @param doctorsUnitDTO the doctorsUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorsUnitDTO,
     * or with status {@code 400 (Bad Request)} if the doctorsUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the doctorsUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the doctorsUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/doctors-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DoctorsUnitDTO> partialUpdateDoctorsUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorsUnitDTO doctorsUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DoctorsUnit partially : {}, {}", id, doctorsUnitDTO);
        if (doctorsUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorsUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorsUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DoctorsUnitDTO> result = doctorsUnitService.partialUpdate(doctorsUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorsUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /doctors-units} : get all the doctorsUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorsUnits in body.
     */
    @GetMapping("/doctors-units")
    public ResponseEntity<List<DoctorsUnitDTO>> getAllDoctorsUnits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DoctorsUnits");
        Page<DoctorsUnitDTO> page = doctorsUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctors-units/:id} : get the "id" doctorsUnit.
     *
     * @param id the id of the doctorsUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorsUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctors-units/{id}")
    public ResponseEntity<DoctorsUnitDTO> getDoctorsUnit(@PathVariable Long id) {
        log.debug("REST request to get DoctorsUnit : {}", id);
        Optional<DoctorsUnitDTO> doctorsUnitDTO = doctorsUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorsUnitDTO);
    }

    /**
     * {@code DELETE  /doctors-units/:id} : delete the "id" doctorsUnit.
     *
     * @param id the id of the doctorsUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctors-units/{id}")
    public ResponseEntity<Void> deleteDoctorsUnit(@PathVariable Long id) {
        log.debug("REST request to delete DoctorsUnit : {}", id);
        doctorsUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
