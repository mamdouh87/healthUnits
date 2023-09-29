package com.mkh.healthunits.web.rest;

import com.mkh.healthunits.repository.ExtraPassUnitRepository;
import com.mkh.healthunits.service.ExtraPassUnitService;
import com.mkh.healthunits.service.dto.ExtraPassUnitDTO;
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
 * REST controller for managing {@link com.mkh.healthunits.domain.ExtraPassUnit}.
 */
@RestController
@RequestMapping("/api")
public class ExtraPassUnitResource {

    private final Logger log = LoggerFactory.getLogger(ExtraPassUnitResource.class);

    private static final String ENTITY_NAME = "extraPassUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtraPassUnitService extraPassUnitService;

    private final ExtraPassUnitRepository extraPassUnitRepository;

    public ExtraPassUnitResource(ExtraPassUnitService extraPassUnitService, ExtraPassUnitRepository extraPassUnitRepository) {
        this.extraPassUnitService = extraPassUnitService;
        this.extraPassUnitRepository = extraPassUnitRepository;
    }

    /**
     * {@code POST  /extra-pass-units} : Create a new extraPassUnit.
     *
     * @param extraPassUnitDTO the extraPassUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extraPassUnitDTO, or with status {@code 400 (Bad Request)} if the extraPassUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extra-pass-units")
    public ResponseEntity<ExtraPassUnitDTO> createExtraPassUnit(@RequestBody ExtraPassUnitDTO extraPassUnitDTO) throws URISyntaxException {
        log.debug("REST request to save ExtraPassUnit : {}", extraPassUnitDTO);
        if (extraPassUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new extraPassUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtraPassUnitDTO result = extraPassUnitService.save(extraPassUnitDTO);
        return ResponseEntity
            .created(new URI("/api/extra-pass-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extra-pass-units/:id} : Updates an existing extraPassUnit.
     *
     * @param id the id of the extraPassUnitDTO to save.
     * @param extraPassUnitDTO the extraPassUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extraPassUnitDTO,
     * or with status {@code 400 (Bad Request)} if the extraPassUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extraPassUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extra-pass-units/{id}")
    public ResponseEntity<ExtraPassUnitDTO> updateExtraPassUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExtraPassUnitDTO extraPassUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExtraPassUnit : {}, {}", id, extraPassUnitDTO);
        if (extraPassUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extraPassUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!extraPassUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExtraPassUnitDTO result = extraPassUnitService.update(extraPassUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extraPassUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /extra-pass-units/:id} : Partial updates given fields of an existing extraPassUnit, field will ignore if it is null
     *
     * @param id the id of the extraPassUnitDTO to save.
     * @param extraPassUnitDTO the extraPassUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extraPassUnitDTO,
     * or with status {@code 400 (Bad Request)} if the extraPassUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the extraPassUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the extraPassUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/extra-pass-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExtraPassUnitDTO> partialUpdateExtraPassUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExtraPassUnitDTO extraPassUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExtraPassUnit partially : {}, {}", id, extraPassUnitDTO);
        if (extraPassUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extraPassUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!extraPassUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExtraPassUnitDTO> result = extraPassUnitService.partialUpdate(extraPassUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extraPassUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /extra-pass-units} : get all the extraPassUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extraPassUnits in body.
     */
    @GetMapping("/extra-pass-units")
    public ResponseEntity<List<ExtraPassUnitDTO>> getAllExtraPassUnits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ExtraPassUnits");
        Page<ExtraPassUnitDTO> page = extraPassUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /extra-pass-units/:id} : get the "id" extraPassUnit.
     *
     * @param id the id of the extraPassUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extraPassUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extra-pass-units/{id}")
    public ResponseEntity<ExtraPassUnitDTO> getExtraPassUnit(@PathVariable Long id) {
        log.debug("REST request to get ExtraPassUnit : {}", id);
        Optional<ExtraPassUnitDTO> extraPassUnitDTO = extraPassUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extraPassUnitDTO);
    }

    /**
     * {@code DELETE  /extra-pass-units/:id} : delete the "id" extraPassUnit.
     *
     * @param id the id of the extraPassUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extra-pass-units/{id}")
    public ResponseEntity<Void> deleteExtraPassUnit(@PathVariable Long id) {
        log.debug("REST request to delete ExtraPassUnit : {}", id);
        extraPassUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
