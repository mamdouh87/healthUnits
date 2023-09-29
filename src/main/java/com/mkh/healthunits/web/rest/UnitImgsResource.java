package com.mkh.healthunits.web.rest;

import com.mkh.healthunits.repository.UnitImgsRepository;
import com.mkh.healthunits.service.UnitImgsService;
import com.mkh.healthunits.service.dto.UnitImgsDTO;
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
 * REST controller for managing {@link com.mkh.healthunits.domain.UnitImgs}.
 */
@RestController
@RequestMapping("/api")
public class UnitImgsResource {

    private final Logger log = LoggerFactory.getLogger(UnitImgsResource.class);

    private static final String ENTITY_NAME = "unitImgs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitImgsService unitImgsService;

    private final UnitImgsRepository unitImgsRepository;

    public UnitImgsResource(UnitImgsService unitImgsService, UnitImgsRepository unitImgsRepository) {
        this.unitImgsService = unitImgsService;
        this.unitImgsRepository = unitImgsRepository;
    }

    /**
     * {@code POST  /unit-imgs} : Create a new unitImgs.
     *
     * @param unitImgsDTO the unitImgsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitImgsDTO, or with status {@code 400 (Bad Request)} if the unitImgs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unit-imgs")
    public ResponseEntity<UnitImgsDTO> createUnitImgs(@RequestBody UnitImgsDTO unitImgsDTO) throws URISyntaxException {
        log.debug("REST request to save UnitImgs : {}", unitImgsDTO);
        if (unitImgsDTO.getId() != null) {
            throw new BadRequestAlertException("A new unitImgs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitImgsDTO result = unitImgsService.save(unitImgsDTO);
        return ResponseEntity
            .created(new URI("/api/unit-imgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unit-imgs/:id} : Updates an existing unitImgs.
     *
     * @param id the id of the unitImgsDTO to save.
     * @param unitImgsDTO the unitImgsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitImgsDTO,
     * or with status {@code 400 (Bad Request)} if the unitImgsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitImgsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unit-imgs/{id}")
    public ResponseEntity<UnitImgsDTO> updateUnitImgs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnitImgsDTO unitImgsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UnitImgs : {}, {}", id, unitImgsDTO);
        if (unitImgsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unitImgsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unitImgsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnitImgsDTO result = unitImgsService.update(unitImgsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitImgsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /unit-imgs/:id} : Partial updates given fields of an existing unitImgs, field will ignore if it is null
     *
     * @param id the id of the unitImgsDTO to save.
     * @param unitImgsDTO the unitImgsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitImgsDTO,
     * or with status {@code 400 (Bad Request)} if the unitImgsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the unitImgsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the unitImgsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unit-imgs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UnitImgsDTO> partialUpdateUnitImgs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnitImgsDTO unitImgsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UnitImgs partially : {}, {}", id, unitImgsDTO);
        if (unitImgsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unitImgsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unitImgsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnitImgsDTO> result = unitImgsService.partialUpdate(unitImgsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitImgsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /unit-imgs} : get all the unitImgs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unitImgs in body.
     */
    @GetMapping("/unit-imgs")
    public ResponseEntity<List<UnitImgsDTO>> getAllUnitImgs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UnitImgs");
        Page<UnitImgsDTO> page = unitImgsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unit-imgs/:id} : get the "id" unitImgs.
     *
     * @param id the id of the unitImgsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitImgsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unit-imgs/{id}")
    public ResponseEntity<UnitImgsDTO> getUnitImgs(@PathVariable Long id) {
        log.debug("REST request to get UnitImgs : {}", id);
        Optional<UnitImgsDTO> unitImgsDTO = unitImgsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitImgsDTO);
    }

    /**
     * {@code DELETE  /unit-imgs/:id} : delete the "id" unitImgs.
     *
     * @param id the id of the unitImgsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unit-imgs/{id}")
    public ResponseEntity<Void> deleteUnitImgs(@PathVariable Long id) {
        log.debug("REST request to delete UnitImgs : {}", id);
        unitImgsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
