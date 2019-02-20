package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaClassService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaClassDTO;
import com.aerothinker.kms.service.dto.ParaClassCriteria;
import com.aerothinker.kms.service.ParaClassQueryService;
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
 * REST controller for managing ParaClass.
 */
@RestController
@RequestMapping("/api")
public class ParaClassResource {

    private final Logger log = LoggerFactory.getLogger(ParaClassResource.class);

    private static final String ENTITY_NAME = "paraClass";

    private final ParaClassService paraClassService;

    private final ParaClassQueryService paraClassQueryService;

    public ParaClassResource(ParaClassService paraClassService, ParaClassQueryService paraClassQueryService) {
        this.paraClassService = paraClassService;
        this.paraClassQueryService = paraClassQueryService;
    }

    /**
     * POST  /para-classes : Create a new paraClass.
     *
     * @param paraClassDTO the paraClassDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraClassDTO, or with status 400 (Bad Request) if the paraClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-classes")
    public ResponseEntity<ParaClassDTO> createParaClass(@Valid @RequestBody ParaClassDTO paraClassDTO) throws URISyntaxException {
        log.debug("REST request to save ParaClass : {}", paraClassDTO);
        if (paraClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaClassDTO result = paraClassService.save(paraClassDTO);
        return ResponseEntity.created(new URI("/api/para-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-classes : Updates an existing paraClass.
     *
     * @param paraClassDTO the paraClassDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraClassDTO,
     * or with status 400 (Bad Request) if the paraClassDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraClassDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-classes")
    public ResponseEntity<ParaClassDTO> updateParaClass(@Valid @RequestBody ParaClassDTO paraClassDTO) throws URISyntaxException {
        log.debug("REST request to update ParaClass : {}", paraClassDTO);
        if (paraClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaClassDTO result = paraClassService.save(paraClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-classes : get all the paraClasses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraClasses in body
     */
    @GetMapping("/para-classes")
    public ResponseEntity<List<ParaClassDTO>> getAllParaClasses(ParaClassCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaClasses by criteria: {}", criteria);
        Page<ParaClassDTO> page = paraClassQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-classes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-classes/count : count all the paraClasses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-classes/count")
    public ResponseEntity<Long> countParaClasses(ParaClassCriteria criteria) {
        log.debug("REST request to count ParaClasses by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraClassQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-classes/:id : get the "id" paraClass.
     *
     * @param id the id of the paraClassDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraClassDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-classes/{id}")
    public ResponseEntity<ParaClassDTO> getParaClass(@PathVariable Long id) {
        log.debug("REST request to get ParaClass : {}", id);
        Optional<ParaClassDTO> paraClassDTO = paraClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraClassDTO);
    }

    /**
     * DELETE  /para-classes/:id : delete the "id" paraClass.
     *
     * @param id the id of the paraClassDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-classes/{id}")
    public ResponseEntity<Void> deleteParaClass(@PathVariable Long id) {
        log.debug("REST request to delete ParaClass : {}", id);
        paraClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-classes?query=:query : search for the paraClass corresponding
     * to the query.
     *
     * @param query the query of the paraClass search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-classes")
    public ResponseEntity<List<ParaClassDTO>> searchParaClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaClasses for query {}", query);
        Page<ParaClassDTO> page = paraClassService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-classes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
