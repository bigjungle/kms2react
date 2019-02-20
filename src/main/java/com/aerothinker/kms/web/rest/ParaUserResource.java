package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaUserService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaUserDTO;
import com.aerothinker.kms.service.dto.ParaUserCriteria;
import com.aerothinker.kms.service.ParaUserQueryService;
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
 * REST controller for managing ParaUser.
 */
@RestController
@RequestMapping("/api")
public class ParaUserResource {

    private final Logger log = LoggerFactory.getLogger(ParaUserResource.class);

    private static final String ENTITY_NAME = "paraUser";

    private final ParaUserService paraUserService;

    private final ParaUserQueryService paraUserQueryService;

    public ParaUserResource(ParaUserService paraUserService, ParaUserQueryService paraUserQueryService) {
        this.paraUserService = paraUserService;
        this.paraUserQueryService = paraUserQueryService;
    }

    /**
     * POST  /para-users : Create a new paraUser.
     *
     * @param paraUserDTO the paraUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraUserDTO, or with status 400 (Bad Request) if the paraUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-users")
    public ResponseEntity<ParaUserDTO> createParaUser(@Valid @RequestBody ParaUserDTO paraUserDTO) throws URISyntaxException {
        log.debug("REST request to save ParaUser : {}", paraUserDTO);
        if (paraUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaUserDTO result = paraUserService.save(paraUserDTO);
        return ResponseEntity.created(new URI("/api/para-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-users : Updates an existing paraUser.
     *
     * @param paraUserDTO the paraUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraUserDTO,
     * or with status 400 (Bad Request) if the paraUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-users")
    public ResponseEntity<ParaUserDTO> updateParaUser(@Valid @RequestBody ParaUserDTO paraUserDTO) throws URISyntaxException {
        log.debug("REST request to update ParaUser : {}", paraUserDTO);
        if (paraUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaUserDTO result = paraUserService.save(paraUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-users : get all the paraUsers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraUsers in body
     */
    @GetMapping("/para-users")
    public ResponseEntity<List<ParaUserDTO>> getAllParaUsers(ParaUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaUsers by criteria: {}", criteria);
        Page<ParaUserDTO> page = paraUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-users");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-users/count : count all the paraUsers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-users/count")
    public ResponseEntity<Long> countParaUsers(ParaUserCriteria criteria) {
        log.debug("REST request to count ParaUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraUserQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-users/:id : get the "id" paraUser.
     *
     * @param id the id of the paraUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-users/{id}")
    public ResponseEntity<ParaUserDTO> getParaUser(@PathVariable Long id) {
        log.debug("REST request to get ParaUser : {}", id);
        Optional<ParaUserDTO> paraUserDTO = paraUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraUserDTO);
    }

    /**
     * DELETE  /para-users/:id : delete the "id" paraUser.
     *
     * @param id the id of the paraUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-users/{id}")
    public ResponseEntity<Void> deleteParaUser(@PathVariable Long id) {
        log.debug("REST request to delete ParaUser : {}", id);
        paraUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-users?query=:query : search for the paraUser corresponding
     * to the query.
     *
     * @param query the query of the paraUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-users")
    public ResponseEntity<List<ParaUserDTO>> searchParaUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaUsers for query {}", query);
        Page<ParaUserDTO> page = paraUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-users");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
