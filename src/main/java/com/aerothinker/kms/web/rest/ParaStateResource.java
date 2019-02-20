package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaStateService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaStateDTO;
import com.aerothinker.kms.service.dto.ParaStateCriteria;
import com.aerothinker.kms.service.ParaStateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ParaState.
 */
@RestController
@RequestMapping("/api")
public class ParaStateResource {

    private final Logger log = LoggerFactory.getLogger(ParaStateResource.class);

    private static final String ENTITY_NAME = "paraState";

    private final ParaStateService paraStateService;

    private final ParaStateQueryService paraStateQueryService;

    public ParaStateResource(ParaStateService paraStateService, ParaStateQueryService paraStateQueryService) {
        this.paraStateService = paraStateService;
        this.paraStateQueryService = paraStateQueryService;
    }

    /**
     * POST  /para-states : Create a new paraState.
     *
     * @param paraStateDTO the paraStateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraStateDTO, or with status 400 (Bad Request) if the paraState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-states")
    public ResponseEntity<ParaStateDTO> createParaState(@Valid @RequestBody ParaStateDTO paraStateDTO) throws URISyntaxException {
        log.debug("REST request to save ParaState : {}", paraStateDTO);
        if (paraStateDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaStateDTO result = paraStateService.save(paraStateDTO);
        return ResponseEntity.created(new URI("/api/para-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-states : Updates an existing paraState.
     *
     * @param paraStateDTO the paraStateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraStateDTO,
     * or with status 400 (Bad Request) if the paraStateDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraStateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-states")
    public ResponseEntity<ParaStateDTO> updateParaState(@Valid @RequestBody ParaStateDTO paraStateDTO) throws URISyntaxException {
        log.debug("REST request to update ParaState : {}", paraStateDTO);
        if (paraStateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaStateDTO result = paraStateService.save(paraStateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraStateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-states : get all the paraStates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraStates in body
     */
    @GetMapping("/para-states")
    public ResponseEntity<List<ParaStateDTO>> getAllParaStates(ParaStateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaStates by criteria: {}", criteria);
        Page<ParaStateDTO> page = paraStateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-states");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-states/count : count all the paraStates.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-states/count")
    public ResponseEntity<Long> countParaStates(ParaStateCriteria criteria) {
        log.debug("REST request to count ParaStates by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraStateQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-states/:id : get the "id" paraState.
     *
     * @param id the id of the paraStateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraStateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-states/{id}")
    public ResponseEntity<ParaStateDTO> getParaState(@PathVariable Long id) {
        log.debug("REST request to get ParaState : {}", id);
        Optional<ParaStateDTO> paraStateDTO = paraStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraStateDTO);
    }

    /**
     * DELETE  /para-states/:id : delete the "id" paraState.
     *
     * @param id the id of the paraStateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-states/{id}")
    public ResponseEntity<Void> deleteParaState(@PathVariable Long id) {
        log.debug("REST request to delete ParaState : {}", id);
        paraStateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-states?query=:query : search for the paraState corresponding
     * to the query.
     *
     * @param query the query of the paraState search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-states")
    public ResponseEntity<List<ParaStateDTO>> searchParaStates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaStates for query {}", query);
        Page<ParaStateDTO> page = paraStateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-states");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
