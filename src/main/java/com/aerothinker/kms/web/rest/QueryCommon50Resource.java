package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.QueryCommon50Service;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.QueryCommon50DTO;
import com.aerothinker.kms.service.dto.QueryCommon50Criteria;
import com.aerothinker.kms.service.QueryCommon50QueryService;
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
 * REST controller for managing QueryCommon50.
 */
@RestController
@RequestMapping("/api")
public class QueryCommon50Resource {

    private final Logger log = LoggerFactory.getLogger(QueryCommon50Resource.class);

    private static final String ENTITY_NAME = "queryCommon50";

    private final QueryCommon50Service queryCommon50Service;

    private final QueryCommon50QueryService queryCommon50QueryService;

    public QueryCommon50Resource(QueryCommon50Service queryCommon50Service, QueryCommon50QueryService queryCommon50QueryService) {
        this.queryCommon50Service = queryCommon50Service;
        this.queryCommon50QueryService = queryCommon50QueryService;
    }

    /**
     * POST  /query-common-50-s : Create a new queryCommon50.
     *
     * @param queryCommon50DTO the queryCommon50DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new queryCommon50DTO, or with status 400 (Bad Request) if the queryCommon50 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/query-common-50-s")
    public ResponseEntity<QueryCommon50DTO> createQueryCommon50(@Valid @RequestBody QueryCommon50DTO queryCommon50DTO) throws URISyntaxException {
        log.debug("REST request to save QueryCommon50 : {}", queryCommon50DTO);
        if (queryCommon50DTO.getId() != null) {
            throw new BadRequestAlertException("A new queryCommon50 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QueryCommon50DTO result = queryCommon50Service.save(queryCommon50DTO);
        return ResponseEntity.created(new URI("/api/query-common-50-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /query-common-50-s : Updates an existing queryCommon50.
     *
     * @param queryCommon50DTO the queryCommon50DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated queryCommon50DTO,
     * or with status 400 (Bad Request) if the queryCommon50DTO is not valid,
     * or with status 500 (Internal Server Error) if the queryCommon50DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/query-common-50-s")
    public ResponseEntity<QueryCommon50DTO> updateQueryCommon50(@Valid @RequestBody QueryCommon50DTO queryCommon50DTO) throws URISyntaxException {
        log.debug("REST request to update QueryCommon50 : {}", queryCommon50DTO);
        if (queryCommon50DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QueryCommon50DTO result = queryCommon50Service.save(queryCommon50DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, queryCommon50DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /query-common-50-s : get all the queryCommon50S.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of queryCommon50S in body
     */
    @GetMapping("/query-common-50-s")
    public ResponseEntity<List<QueryCommon50DTO>> getAllQueryCommon50S(QueryCommon50Criteria criteria, Pageable pageable) {
        log.debug("REST request to get QueryCommon50S by criteria: {}", criteria);
        Page<QueryCommon50DTO> page = queryCommon50QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/query-common-50-s");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /query-common-50-s/count : count all the queryCommon50S.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/query-common-50-s/count")
    public ResponseEntity<Long> countQueryCommon50S(QueryCommon50Criteria criteria) {
        log.debug("REST request to count QueryCommon50S by criteria: {}", criteria);
        return ResponseEntity.ok().body(queryCommon50QueryService.countByCriteria(criteria));
    }

    /**
     * GET  /query-common-50-s/:id : get the "id" queryCommon50.
     *
     * @param id the id of the queryCommon50DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the queryCommon50DTO, or with status 404 (Not Found)
     */
    @GetMapping("/query-common-50-s/{id}")
    public ResponseEntity<QueryCommon50DTO> getQueryCommon50(@PathVariable Long id) {
        log.debug("REST request to get QueryCommon50 : {}", id);
        Optional<QueryCommon50DTO> queryCommon50DTO = queryCommon50Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(queryCommon50DTO);
    }

    /**
     * DELETE  /query-common-50-s/:id : delete the "id" queryCommon50.
     *
     * @param id the id of the queryCommon50DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/query-common-50-s/{id}")
    public ResponseEntity<Void> deleteQueryCommon50(@PathVariable Long id) {
        log.debug("REST request to delete QueryCommon50 : {}", id);
        queryCommon50Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/query-common-50-s?query=:query : search for the queryCommon50 corresponding
     * to the query.
     *
     * @param query the query of the queryCommon50 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/query-common-50-s")
    public ResponseEntity<List<QueryCommon50DTO>> searchQueryCommon50S(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of QueryCommon50S for query {}", query);
        Page<QueryCommon50DTO> page = queryCommon50Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/query-common-50-s");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
