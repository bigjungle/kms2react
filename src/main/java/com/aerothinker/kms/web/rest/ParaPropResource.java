package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaPropService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaPropDTO;
import com.aerothinker.kms.service.dto.ParaPropCriteria;
import com.aerothinker.kms.service.ParaPropQueryService;
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
 * REST controller for managing ParaProp.
 */
@RestController
@RequestMapping("/api")
public class ParaPropResource {

    private final Logger log = LoggerFactory.getLogger(ParaPropResource.class);

    private static final String ENTITY_NAME = "paraProp";

    private final ParaPropService paraPropService;

    private final ParaPropQueryService paraPropQueryService;

    public ParaPropResource(ParaPropService paraPropService, ParaPropQueryService paraPropQueryService) {
        this.paraPropService = paraPropService;
        this.paraPropQueryService = paraPropQueryService;
    }

    /**
     * POST  /para-props : Create a new paraProp.
     *
     * @param paraPropDTO the paraPropDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraPropDTO, or with status 400 (Bad Request) if the paraProp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-props")
    public ResponseEntity<ParaPropDTO> createParaProp(@Valid @RequestBody ParaPropDTO paraPropDTO) throws URISyntaxException {
        log.debug("REST request to save ParaProp : {}", paraPropDTO);
        if (paraPropDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraProp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaPropDTO result = paraPropService.save(paraPropDTO);
        return ResponseEntity.created(new URI("/api/para-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-props : Updates an existing paraProp.
     *
     * @param paraPropDTO the paraPropDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraPropDTO,
     * or with status 400 (Bad Request) if the paraPropDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraPropDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-props")
    public ResponseEntity<ParaPropDTO> updateParaProp(@Valid @RequestBody ParaPropDTO paraPropDTO) throws URISyntaxException {
        log.debug("REST request to update ParaProp : {}", paraPropDTO);
        if (paraPropDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaPropDTO result = paraPropService.save(paraPropDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraPropDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-props : get all the paraProps.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraProps in body
     */
    @GetMapping("/para-props")
    public ResponseEntity<List<ParaPropDTO>> getAllParaProps(ParaPropCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaProps by criteria: {}", criteria);
        Page<ParaPropDTO> page = paraPropQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-props");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-props/count : count all the paraProps.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-props/count")
    public ResponseEntity<Long> countParaProps(ParaPropCriteria criteria) {
        log.debug("REST request to count ParaProps by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraPropQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-props/:id : get the "id" paraProp.
     *
     * @param id the id of the paraPropDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraPropDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-props/{id}")
    public ResponseEntity<ParaPropDTO> getParaProp(@PathVariable Long id) {
        log.debug("REST request to get ParaProp : {}", id);
        Optional<ParaPropDTO> paraPropDTO = paraPropService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraPropDTO);
    }

    /**
     * DELETE  /para-props/:id : delete the "id" paraProp.
     *
     * @param id the id of the paraPropDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-props/{id}")
    public ResponseEntity<Void> deleteParaProp(@PathVariable Long id) {
        log.debug("REST request to delete ParaProp : {}", id);
        paraPropService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-props?query=:query : search for the paraProp corresponding
     * to the query.
     *
     * @param query the query of the paraProp search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-props")
    public ResponseEntity<List<ParaPropDTO>> searchParaProps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaProps for query {}", query);
        Page<ParaPropDTO> page = paraPropService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-props");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
