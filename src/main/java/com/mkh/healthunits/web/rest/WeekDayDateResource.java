package com.mkh.healthunits.web.rest;

import com.mkh.healthunits.repository.WeekDayDateRepository;
import com.mkh.healthunits.service.WeekDayDateService;
import com.mkh.healthunits.service.dto.WeekDayDateDTO;
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
 * REST controller for managing {@link com.mkh.healthunits.domain.WeekDayDate}.
 */
@RestController
@RequestMapping("/api")
public class WeekDayDateResource {

    private final Logger log = LoggerFactory.getLogger(WeekDayDateResource.class);

    private static final String ENTITY_NAME = "weekDayDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeekDayDateService weekDayDateService;

    private final WeekDayDateRepository weekDayDateRepository;

    public WeekDayDateResource(WeekDayDateService weekDayDateService, WeekDayDateRepository weekDayDateRepository) {
        this.weekDayDateService = weekDayDateService;
        this.weekDayDateRepository = weekDayDateRepository;
    }

    /**
     * {@code POST  /week-day-dates} : Create a new weekDayDate.
     *
     * @param weekDayDateDTO the weekDayDateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weekDayDateDTO, or with status {@code 400 (Bad Request)} if the weekDayDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/week-day-dates")
    public ResponseEntity<WeekDayDateDTO> createWeekDayDate(@RequestBody WeekDayDateDTO weekDayDateDTO) throws URISyntaxException {
        log.debug("REST request to save WeekDayDate : {}", weekDayDateDTO);
        if (weekDayDateDTO.getId() != null) {
            throw new BadRequestAlertException("A new weekDayDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeekDayDateDTO result = weekDayDateService.save(weekDayDateDTO);
        return ResponseEntity
            .created(new URI("/api/week-day-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /week-day-dates/:id} : Updates an existing weekDayDate.
     *
     * @param id the id of the weekDayDateDTO to save.
     * @param weekDayDateDTO the weekDayDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weekDayDateDTO,
     * or with status {@code 400 (Bad Request)} if the weekDayDateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weekDayDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/week-day-dates/{id}")
    public ResponseEntity<WeekDayDateDTO> updateWeekDayDate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WeekDayDateDTO weekDayDateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WeekDayDate : {}, {}", id, weekDayDateDTO);
        if (weekDayDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weekDayDateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weekDayDateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WeekDayDateDTO result = weekDayDateService.update(weekDayDateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weekDayDateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /week-day-dates/:id} : Partial updates given fields of an existing weekDayDate, field will ignore if it is null
     *
     * @param id the id of the weekDayDateDTO to save.
     * @param weekDayDateDTO the weekDayDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weekDayDateDTO,
     * or with status {@code 400 (Bad Request)} if the weekDayDateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the weekDayDateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the weekDayDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/week-day-dates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WeekDayDateDTO> partialUpdateWeekDayDate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WeekDayDateDTO weekDayDateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WeekDayDate partially : {}, {}", id, weekDayDateDTO);
        if (weekDayDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weekDayDateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weekDayDateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WeekDayDateDTO> result = weekDayDateService.partialUpdate(weekDayDateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weekDayDateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /week-day-dates} : get all the weekDayDates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weekDayDates in body.
     */
    @GetMapping("/week-day-dates")
    public ResponseEntity<List<WeekDayDateDTO>> getAllWeekDayDates(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of WeekDayDates");
        Page<WeekDayDateDTO> page = weekDayDateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /week-day-dates/:id} : get the "id" weekDayDate.
     *
     * @param id the id of the weekDayDateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weekDayDateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/week-day-dates/{id}")
    public ResponseEntity<WeekDayDateDTO> getWeekDayDate(@PathVariable Long id) {
        log.debug("REST request to get WeekDayDate : {}", id);
        Optional<WeekDayDateDTO> weekDayDateDTO = weekDayDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weekDayDateDTO);
    }

    /**
     * {@code DELETE  /week-day-dates/:id} : delete the "id" weekDayDate.
     *
     * @param id the id of the weekDayDateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/week-day-dates/{id}")
    public ResponseEntity<Void> deleteWeekDayDate(@PathVariable Long id) {
        log.debug("REST request to delete WeekDayDate : {}", id);
        weekDayDateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
