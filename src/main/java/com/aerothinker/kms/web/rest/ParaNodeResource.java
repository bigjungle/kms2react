package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaNodeService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaNodeDTO;
import com.aerothinker.kms.service.dto.ParaNodeCriteria;
import com.aerothinker.kms.service.ParaNodeQueryService;
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
 * REST controller for managing ParaNode.
 */
@RestController
@RequestMapping("/api")
public class ParaNodeResource {

    private final Logger log = LoggerFactory.getLogger(ParaNodeResource.class);

    private static final String ENTITY_NAME = "paraNode";

    private final ParaNodeService paraNodeService;

    private final ParaNodeQueryService paraNodeQueryService;

    public ParaNodeResource(ParaNodeService paraNodeService, ParaNodeQueryService paraNodeQueryService) {
        this.paraNodeService = paraNodeService;
        this.paraNodeQueryService = paraNodeQueryService;
    }

    /**
     * POST  /para-nodes : Create a new paraNode.
     *
     * @param paraNodeDTO the paraNodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraNodeDTO, or with status 400 (Bad Request) if the paraNode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-nodes")
    public ResponseEntity<ParaNodeDTO> createParaNode(@Valid @RequestBody ParaNodeDTO paraNodeDTO) throws URISyntaxException {
        log.debug("REST request to save ParaNode : {}", paraNodeDTO);
        if (paraNodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaNodeDTO result = paraNodeService.save(paraNodeDTO);
        return ResponseEntity.created(new URI("/api/para-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-nodes : Updates an existing paraNode.
     *
     * @param paraNodeDTO the paraNodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraNodeDTO,
     * or with status 400 (Bad Request) if the paraNodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraNodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-nodes")
    public ResponseEntity<ParaNodeDTO> updateParaNode(@Valid @RequestBody ParaNodeDTO paraNodeDTO) throws URISyntaxException {
        log.debug("REST request to update ParaNode : {}", paraNodeDTO);
        if (paraNodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaNodeDTO result = paraNodeService.save(paraNodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraNodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-nodes : get all the paraNodes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraNodes in body
     */
    @GetMapping("/para-nodes")
    public ResponseEntity<List<ParaNodeDTO>> getAllParaNodes(ParaNodeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaNodes by criteria: {}", criteria);
        Page<ParaNodeDTO> page = paraNodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-nodes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-nodes/count : count all the paraNodes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-nodes/count")
    public ResponseEntity<Long> countParaNodes(ParaNodeCriteria criteria) {
        log.debug("REST request to count ParaNodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraNodeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-nodes/:id : get the "id" paraNode.
     *
     * @param id the id of the paraNodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraNodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-nodes/{id}")
    public ResponseEntity<ParaNodeDTO> getParaNode(@PathVariable Long id) {
        log.debug("REST request to get ParaNode : {}", id);
        Optional<ParaNodeDTO> paraNodeDTO = paraNodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraNodeDTO);
    }

    /**
     * DELETE  /para-nodes/:id : delete the "id" paraNode.
     *
     * @param id the id of the paraNodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-nodes/{id}")
    public ResponseEntity<Void> deleteParaNode(@PathVariable Long id) {
        log.debug("REST request to delete ParaNode : {}", id);
        paraNodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-nodes?query=:query : search for the paraNode corresponding
     * to the query.
     *
     * @param query the query of the paraNode search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-nodes")
    public ResponseEntity<List<ParaNodeDTO>> searchParaNodes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaNodes for query {}", query);
        Page<ParaNodeDTO> page = paraNodeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-nodes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
