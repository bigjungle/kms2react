package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaRoleService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaRoleDTO;
import com.aerothinker.kms.service.dto.ParaRoleCriteria;
import com.aerothinker.kms.service.ParaRoleQueryService;
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
 * REST controller for managing ParaRole.
 */
@RestController
@RequestMapping("/api")
public class ParaRoleResource {

    private final Logger log = LoggerFactory.getLogger(ParaRoleResource.class);

    private static final String ENTITY_NAME = "paraRole";

    private final ParaRoleService paraRoleService;

    private final ParaRoleQueryService paraRoleQueryService;

    public ParaRoleResource(ParaRoleService paraRoleService, ParaRoleQueryService paraRoleQueryService) {
        this.paraRoleService = paraRoleService;
        this.paraRoleQueryService = paraRoleQueryService;
    }

    /**
     * POST  /para-roles : Create a new paraRole.
     *
     * @param paraRoleDTO the paraRoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraRoleDTO, or with status 400 (Bad Request) if the paraRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-roles")
    public ResponseEntity<ParaRoleDTO> createParaRole(@Valid @RequestBody ParaRoleDTO paraRoleDTO) throws URISyntaxException {
        log.debug("REST request to save ParaRole : {}", paraRoleDTO);
        if (paraRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaRoleDTO result = paraRoleService.save(paraRoleDTO);
        return ResponseEntity.created(new URI("/api/para-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-roles : Updates an existing paraRole.
     *
     * @param paraRoleDTO the paraRoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraRoleDTO,
     * or with status 400 (Bad Request) if the paraRoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraRoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-roles")
    public ResponseEntity<ParaRoleDTO> updateParaRole(@Valid @RequestBody ParaRoleDTO paraRoleDTO) throws URISyntaxException {
        log.debug("REST request to update ParaRole : {}", paraRoleDTO);
        if (paraRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaRoleDTO result = paraRoleService.save(paraRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-roles : get all the paraRoles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraRoles in body
     */
    @GetMapping("/para-roles")
    public ResponseEntity<List<ParaRoleDTO>> getAllParaRoles(ParaRoleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaRoles by criteria: {}", criteria);
        Page<ParaRoleDTO> page = paraRoleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-roles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-roles/count : count all the paraRoles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-roles/count")
    public ResponseEntity<Long> countParaRoles(ParaRoleCriteria criteria) {
        log.debug("REST request to count ParaRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraRoleQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-roles/:id : get the "id" paraRole.
     *
     * @param id the id of the paraRoleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraRoleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-roles/{id}")
    public ResponseEntity<ParaRoleDTO> getParaRole(@PathVariable Long id) {
        log.debug("REST request to get ParaRole : {}", id);
        Optional<ParaRoleDTO> paraRoleDTO = paraRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraRoleDTO);
    }

    /**
     * DELETE  /para-roles/:id : delete the "id" paraRole.
     *
     * @param id the id of the paraRoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-roles/{id}")
    public ResponseEntity<Void> deleteParaRole(@PathVariable Long id) {
        log.debug("REST request to delete ParaRole : {}", id);
        paraRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-roles?query=:query : search for the paraRole corresponding
     * to the query.
     *
     * @param query the query of the paraRole search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-roles")
    public ResponseEntity<List<ParaRoleDTO>> searchParaRoles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaRoles for query {}", query);
        Page<ParaRoleDTO> page = paraRoleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-roles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
