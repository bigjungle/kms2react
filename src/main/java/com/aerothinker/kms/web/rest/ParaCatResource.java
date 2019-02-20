package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaCatService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaCatDTO;
import com.aerothinker.kms.service.dto.ParaCatCriteria;
import com.aerothinker.kms.service.ParaCatQueryService;
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
 * REST controller for managing ParaCat.
 */
@RestController
@RequestMapping("/api")
public class ParaCatResource {

    private final Logger log = LoggerFactory.getLogger(ParaCatResource.class);

    private static final String ENTITY_NAME = "paraCat";

    private final ParaCatService paraCatService;

    private final ParaCatQueryService paraCatQueryService;

    public ParaCatResource(ParaCatService paraCatService, ParaCatQueryService paraCatQueryService) {
        this.paraCatService = paraCatService;
        this.paraCatQueryService = paraCatQueryService;
    }

    /**
     * POST  /para-cats : Create a new paraCat.
     *
     * @param paraCatDTO the paraCatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraCatDTO, or with status 400 (Bad Request) if the paraCat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-cats")
    public ResponseEntity<ParaCatDTO> createParaCat(@Valid @RequestBody ParaCatDTO paraCatDTO) throws URISyntaxException {
        log.debug("REST request to save ParaCat : {}", paraCatDTO);
        if (paraCatDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraCat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaCatDTO result = paraCatService.save(paraCatDTO);
        return ResponseEntity.created(new URI("/api/para-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-cats : Updates an existing paraCat.
     *
     * @param paraCatDTO the paraCatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraCatDTO,
     * or with status 400 (Bad Request) if the paraCatDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraCatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-cats")
    public ResponseEntity<ParaCatDTO> updateParaCat(@Valid @RequestBody ParaCatDTO paraCatDTO) throws URISyntaxException {
        log.debug("REST request to update ParaCat : {}", paraCatDTO);
        if (paraCatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaCatDTO result = paraCatService.save(paraCatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraCatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-cats : get all the paraCats.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraCats in body
     */
    @GetMapping("/para-cats")
    public ResponseEntity<List<ParaCatDTO>> getAllParaCats(ParaCatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaCats by criteria: {}", criteria);
        Page<ParaCatDTO> page = paraCatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-cats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-cats/count : count all the paraCats.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-cats/count")
    public ResponseEntity<Long> countParaCats(ParaCatCriteria criteria) {
        log.debug("REST request to count ParaCats by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraCatQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-cats/:id : get the "id" paraCat.
     *
     * @param id the id of the paraCatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraCatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-cats/{id}")
    public ResponseEntity<ParaCatDTO> getParaCat(@PathVariable Long id) {
        log.debug("REST request to get ParaCat : {}", id);
        Optional<ParaCatDTO> paraCatDTO = paraCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraCatDTO);
    }

    /**
     * DELETE  /para-cats/:id : delete the "id" paraCat.
     *
     * @param id the id of the paraCatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-cats/{id}")
    public ResponseEntity<Void> deleteParaCat(@PathVariable Long id) {
        log.debug("REST request to delete ParaCat : {}", id);
        paraCatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-cats?query=:query : search for the paraCat corresponding
     * to the query.
     *
     * @param query the query of the paraCat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-cats")
    public ResponseEntity<List<ParaCatDTO>> searchParaCats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaCats for query {}", query);
        Page<ParaCatDTO> page = paraCatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-cats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
