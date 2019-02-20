package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaDepService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaDepDTO;
import com.aerothinker.kms.service.dto.ParaDepCriteria;
import com.aerothinker.kms.service.ParaDepQueryService;
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
 * REST controller for managing ParaDep.
 */
@RestController
@RequestMapping("/api")
public class ParaDepResource {

    private final Logger log = LoggerFactory.getLogger(ParaDepResource.class);

    private static final String ENTITY_NAME = "paraDep";

    private final ParaDepService paraDepService;

    private final ParaDepQueryService paraDepQueryService;

    public ParaDepResource(ParaDepService paraDepService, ParaDepQueryService paraDepQueryService) {
        this.paraDepService = paraDepService;
        this.paraDepQueryService = paraDepQueryService;
    }

    /**
     * POST  /para-deps : Create a new paraDep.
     *
     * @param paraDepDTO the paraDepDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraDepDTO, or with status 400 (Bad Request) if the paraDep has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-deps")
    public ResponseEntity<ParaDepDTO> createParaDep(@Valid @RequestBody ParaDepDTO paraDepDTO) throws URISyntaxException {
        log.debug("REST request to save ParaDep : {}", paraDepDTO);
        if (paraDepDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraDep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaDepDTO result = paraDepService.save(paraDepDTO);
        return ResponseEntity.created(new URI("/api/para-deps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-deps : Updates an existing paraDep.
     *
     * @param paraDepDTO the paraDepDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraDepDTO,
     * or with status 400 (Bad Request) if the paraDepDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraDepDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-deps")
    public ResponseEntity<ParaDepDTO> updateParaDep(@Valid @RequestBody ParaDepDTO paraDepDTO) throws URISyntaxException {
        log.debug("REST request to update ParaDep : {}", paraDepDTO);
        if (paraDepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaDepDTO result = paraDepService.save(paraDepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraDepDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-deps : get all the paraDeps.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraDeps in body
     */
    @GetMapping("/para-deps")
    public ResponseEntity<List<ParaDepDTO>> getAllParaDeps(ParaDepCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaDeps by criteria: {}", criteria);
        Page<ParaDepDTO> page = paraDepQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-deps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-deps/count : count all the paraDeps.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-deps/count")
    public ResponseEntity<Long> countParaDeps(ParaDepCriteria criteria) {
        log.debug("REST request to count ParaDeps by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraDepQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-deps/:id : get the "id" paraDep.
     *
     * @param id the id of the paraDepDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraDepDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-deps/{id}")
    public ResponseEntity<ParaDepDTO> getParaDep(@PathVariable Long id) {
        log.debug("REST request to get ParaDep : {}", id);
        Optional<ParaDepDTO> paraDepDTO = paraDepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraDepDTO);
    }

    /**
     * DELETE  /para-deps/:id : delete the "id" paraDep.
     *
     * @param id the id of the paraDepDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-deps/{id}")
    public ResponseEntity<Void> deleteParaDep(@PathVariable Long id) {
        log.debug("REST request to delete ParaDep : {}", id);
        paraDepService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-deps?query=:query : search for the paraDep corresponding
     * to the query.
     *
     * @param query the query of the paraDep search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-deps")
    public ResponseEntity<List<ParaDepDTO>> searchParaDeps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaDeps for query {}", query);
        Page<ParaDepDTO> page = paraDepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-deps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
