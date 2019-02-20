package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaSourceService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaSourceDTO;
import com.aerothinker.kms.service.dto.ParaSourceCriteria;
import com.aerothinker.kms.service.ParaSourceQueryService;
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
 * REST controller for managing ParaSource.
 */
@RestController
@RequestMapping("/api")
public class ParaSourceResource {

    private final Logger log = LoggerFactory.getLogger(ParaSourceResource.class);

    private static final String ENTITY_NAME = "paraSource";

    private final ParaSourceService paraSourceService;

    private final ParaSourceQueryService paraSourceQueryService;

    public ParaSourceResource(ParaSourceService paraSourceService, ParaSourceQueryService paraSourceQueryService) {
        this.paraSourceService = paraSourceService;
        this.paraSourceQueryService = paraSourceQueryService;
    }

    /**
     * POST  /para-sources : Create a new paraSource.
     *
     * @param paraSourceDTO the paraSourceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraSourceDTO, or with status 400 (Bad Request) if the paraSource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-sources")
    public ResponseEntity<ParaSourceDTO> createParaSource(@Valid @RequestBody ParaSourceDTO paraSourceDTO) throws URISyntaxException {
        log.debug("REST request to save ParaSource : {}", paraSourceDTO);
        if (paraSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaSourceDTO result = paraSourceService.save(paraSourceDTO);
        return ResponseEntity.created(new URI("/api/para-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-sources : Updates an existing paraSource.
     *
     * @param paraSourceDTO the paraSourceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraSourceDTO,
     * or with status 400 (Bad Request) if the paraSourceDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraSourceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-sources")
    public ResponseEntity<ParaSourceDTO> updateParaSource(@Valid @RequestBody ParaSourceDTO paraSourceDTO) throws URISyntaxException {
        log.debug("REST request to update ParaSource : {}", paraSourceDTO);
        if (paraSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaSourceDTO result = paraSourceService.save(paraSourceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-sources : get all the paraSources.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraSources in body
     */
    @GetMapping("/para-sources")
    public ResponseEntity<List<ParaSourceDTO>> getAllParaSources(ParaSourceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaSources by criteria: {}", criteria);
        Page<ParaSourceDTO> page = paraSourceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-sources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-sources/count : count all the paraSources.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-sources/count")
    public ResponseEntity<Long> countParaSources(ParaSourceCriteria criteria) {
        log.debug("REST request to count ParaSources by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraSourceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-sources/:id : get the "id" paraSource.
     *
     * @param id the id of the paraSourceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraSourceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-sources/{id}")
    public ResponseEntity<ParaSourceDTO> getParaSource(@PathVariable Long id) {
        log.debug("REST request to get ParaSource : {}", id);
        Optional<ParaSourceDTO> paraSourceDTO = paraSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraSourceDTO);
    }

    /**
     * DELETE  /para-sources/:id : delete the "id" paraSource.
     *
     * @param id the id of the paraSourceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-sources/{id}")
    public ResponseEntity<Void> deleteParaSource(@PathVariable Long id) {
        log.debug("REST request to delete ParaSource : {}", id);
        paraSourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-sources?query=:query : search for the paraSource corresponding
     * to the query.
     *
     * @param query the query of the paraSource search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-sources")
    public ResponseEntity<List<ParaSourceDTO>> searchParaSources(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaSources for query {}", query);
        Page<ParaSourceDTO> page = paraSourceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-sources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
