package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.QueryCommon10Service;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.QueryCommon10DTO;
import com.aerothinker.kms.service.dto.QueryCommon10Criteria;
import com.aerothinker.kms.service.QueryCommon10QueryService;
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
 * REST controller for managing QueryCommon10.
 */
@RestController
@RequestMapping("/api")
public class QueryCommon10Resource {

    private final Logger log = LoggerFactory.getLogger(QueryCommon10Resource.class);

    private static final String ENTITY_NAME = "queryCommon10";

    private final QueryCommon10Service queryCommon10Service;

    private final QueryCommon10QueryService queryCommon10QueryService;

    public QueryCommon10Resource(QueryCommon10Service queryCommon10Service, QueryCommon10QueryService queryCommon10QueryService) {
        this.queryCommon10Service = queryCommon10Service;
        this.queryCommon10QueryService = queryCommon10QueryService;
    }

    /**
     * POST  /query-common-10-s : Create a new queryCommon10.
     *
     * @param queryCommon10DTO the queryCommon10DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new queryCommon10DTO, or with status 400 (Bad Request) if the queryCommon10 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/query-common-10-s")
    public ResponseEntity<QueryCommon10DTO> createQueryCommon10(@Valid @RequestBody QueryCommon10DTO queryCommon10DTO) throws URISyntaxException {
        log.debug("REST request to save QueryCommon10 : {}", queryCommon10DTO);
        if (queryCommon10DTO.getId() != null) {
            throw new BadRequestAlertException("A new queryCommon10 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QueryCommon10DTO result = queryCommon10Service.save(queryCommon10DTO);
        return ResponseEntity.created(new URI("/api/query-common-10-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /query-common-10-s : Updates an existing queryCommon10.
     *
     * @param queryCommon10DTO the queryCommon10DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated queryCommon10DTO,
     * or with status 400 (Bad Request) if the queryCommon10DTO is not valid,
     * or with status 500 (Internal Server Error) if the queryCommon10DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/query-common-10-s")
    public ResponseEntity<QueryCommon10DTO> updateQueryCommon10(@Valid @RequestBody QueryCommon10DTO queryCommon10DTO) throws URISyntaxException {
        log.debug("REST request to update QueryCommon10 : {}", queryCommon10DTO);
        if (queryCommon10DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QueryCommon10DTO result = queryCommon10Service.save(queryCommon10DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, queryCommon10DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /query-common-10-s : get all the queryCommon10S.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of queryCommon10S in body
     */
    @GetMapping("/query-common-10-s")
    public ResponseEntity<List<QueryCommon10DTO>> getAllQueryCommon10S(QueryCommon10Criteria criteria, Pageable pageable) {
        log.debug("REST request to get QueryCommon10S by criteria: {}", criteria);
        Page<QueryCommon10DTO> page = queryCommon10QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/query-common-10-s");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /query-common-10-s/count : count all the queryCommon10S.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/query-common-10-s/count")
    public ResponseEntity<Long> countQueryCommon10S(QueryCommon10Criteria criteria) {
        log.debug("REST request to count QueryCommon10S by criteria: {}", criteria);
        return ResponseEntity.ok().body(queryCommon10QueryService.countByCriteria(criteria));
    }

    /**
     * GET  /query-common-10-s/:id : get the "id" queryCommon10.
     *
     * @param id the id of the queryCommon10DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the queryCommon10DTO, or with status 404 (Not Found)
     */
    @GetMapping("/query-common-10-s/{id}")
    public ResponseEntity<QueryCommon10DTO> getQueryCommon10(@PathVariable Long id) {
        log.debug("REST request to get QueryCommon10 : {}", id);
        Optional<QueryCommon10DTO> queryCommon10DTO = queryCommon10Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(queryCommon10DTO);
    }

    /**
     * DELETE  /query-common-10-s/:id : delete the "id" queryCommon10.
     *
     * @param id the id of the queryCommon10DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/query-common-10-s/{id}")
    public ResponseEntity<Void> deleteQueryCommon10(@PathVariable Long id) {
        log.debug("REST request to delete QueryCommon10 : {}", id);
        queryCommon10Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/query-common-10-s?query=:query : search for the queryCommon10 corresponding
     * to the query.
     *
     * @param query the query of the queryCommon10 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/query-common-10-s")
    public ResponseEntity<List<QueryCommon10DTO>> searchQueryCommon10S(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of QueryCommon10S for query {}", query);
        Page<QueryCommon10DTO> page = queryCommon10Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/query-common-10-s");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
