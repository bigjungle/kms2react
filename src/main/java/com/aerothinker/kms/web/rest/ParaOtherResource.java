package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaOtherService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaOtherDTO;
import com.aerothinker.kms.service.dto.ParaOtherCriteria;
import com.aerothinker.kms.service.ParaOtherQueryService;
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
 * REST controller for managing ParaOther.
 */
@RestController
@RequestMapping("/api")
public class ParaOtherResource {

    private final Logger log = LoggerFactory.getLogger(ParaOtherResource.class);

    private static final String ENTITY_NAME = "paraOther";

    private final ParaOtherService paraOtherService;

    private final ParaOtherQueryService paraOtherQueryService;

    public ParaOtherResource(ParaOtherService paraOtherService, ParaOtherQueryService paraOtherQueryService) {
        this.paraOtherService = paraOtherService;
        this.paraOtherQueryService = paraOtherQueryService;
    }

    /**
     * POST  /para-others : Create a new paraOther.
     *
     * @param paraOtherDTO the paraOtherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraOtherDTO, or with status 400 (Bad Request) if the paraOther has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-others")
    public ResponseEntity<ParaOtherDTO> createParaOther(@Valid @RequestBody ParaOtherDTO paraOtherDTO) throws URISyntaxException {
        log.debug("REST request to save ParaOther : {}", paraOtherDTO);
        if (paraOtherDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraOther cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaOtherDTO result = paraOtherService.save(paraOtherDTO);
        return ResponseEntity.created(new URI("/api/para-others/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-others : Updates an existing paraOther.
     *
     * @param paraOtherDTO the paraOtherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraOtherDTO,
     * or with status 400 (Bad Request) if the paraOtherDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraOtherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-others")
    public ResponseEntity<ParaOtherDTO> updateParaOther(@Valid @RequestBody ParaOtherDTO paraOtherDTO) throws URISyntaxException {
        log.debug("REST request to update ParaOther : {}", paraOtherDTO);
        if (paraOtherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaOtherDTO result = paraOtherService.save(paraOtherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraOtherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-others : get all the paraOthers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraOthers in body
     */
    @GetMapping("/para-others")
    public ResponseEntity<List<ParaOtherDTO>> getAllParaOthers(ParaOtherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaOthers by criteria: {}", criteria);
        Page<ParaOtherDTO> page = paraOtherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-others");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-others/count : count all the paraOthers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-others/count")
    public ResponseEntity<Long> countParaOthers(ParaOtherCriteria criteria) {
        log.debug("REST request to count ParaOthers by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraOtherQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-others/:id : get the "id" paraOther.
     *
     * @param id the id of the paraOtherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraOtherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-others/{id}")
    public ResponseEntity<ParaOtherDTO> getParaOther(@PathVariable Long id) {
        log.debug("REST request to get ParaOther : {}", id);
        Optional<ParaOtherDTO> paraOtherDTO = paraOtherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraOtherDTO);
    }

    /**
     * DELETE  /para-others/:id : delete the "id" paraOther.
     *
     * @param id the id of the paraOtherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-others/{id}")
    public ResponseEntity<Void> deleteParaOther(@PathVariable Long id) {
        log.debug("REST request to delete ParaOther : {}", id);
        paraOtherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-others?query=:query : search for the paraOther corresponding
     * to the query.
     *
     * @param query the query of the paraOther search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-others")
    public ResponseEntity<List<ParaOtherDTO>> searchParaOthers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaOthers for query {}", query);
        Page<ParaOtherDTO> page = paraOtherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-others");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
