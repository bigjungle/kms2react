package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaTypeService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaTypeDTO;
import com.aerothinker.kms.service.dto.ParaTypeCriteria;
import com.aerothinker.kms.service.ParaTypeQueryService;
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
 * REST controller for managing ParaType.
 */
@RestController
@RequestMapping("/api")
public class ParaTypeResource {

    private final Logger log = LoggerFactory.getLogger(ParaTypeResource.class);

    private static final String ENTITY_NAME = "paraType";

    private final ParaTypeService paraTypeService;

    private final ParaTypeQueryService paraTypeQueryService;

    public ParaTypeResource(ParaTypeService paraTypeService, ParaTypeQueryService paraTypeQueryService) {
        this.paraTypeService = paraTypeService;
        this.paraTypeQueryService = paraTypeQueryService;
    }

    /**
     * POST  /para-types : Create a new paraType.
     *
     * @param paraTypeDTO the paraTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraTypeDTO, or with status 400 (Bad Request) if the paraType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-types")
    public ResponseEntity<ParaTypeDTO> createParaType(@Valid @RequestBody ParaTypeDTO paraTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ParaType : {}", paraTypeDTO);
        if (paraTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaTypeDTO result = paraTypeService.save(paraTypeDTO);
        return ResponseEntity.created(new URI("/api/para-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-types : Updates an existing paraType.
     *
     * @param paraTypeDTO the paraTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraTypeDTO,
     * or with status 400 (Bad Request) if the paraTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-types")
    public ResponseEntity<ParaTypeDTO> updateParaType(@Valid @RequestBody ParaTypeDTO paraTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ParaType : {}", paraTypeDTO);
        if (paraTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaTypeDTO result = paraTypeService.save(paraTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-types : get all the paraTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraTypes in body
     */
    @GetMapping("/para-types")
    public ResponseEntity<List<ParaTypeDTO>> getAllParaTypes(ParaTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaTypes by criteria: {}", criteria);
        Page<ParaTypeDTO> page = paraTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-types/count : count all the paraTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-types/count")
    public ResponseEntity<Long> countParaTypes(ParaTypeCriteria criteria) {
        log.debug("REST request to count ParaTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-types/:id : get the "id" paraType.
     *
     * @param id the id of the paraTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-types/{id}")
    public ResponseEntity<ParaTypeDTO> getParaType(@PathVariable Long id) {
        log.debug("REST request to get ParaType : {}", id);
        Optional<ParaTypeDTO> paraTypeDTO = paraTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraTypeDTO);
    }

    /**
     * DELETE  /para-types/:id : delete the "id" paraType.
     *
     * @param id the id of the paraTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-types/{id}")
    public ResponseEntity<Void> deleteParaType(@PathVariable Long id) {
        log.debug("REST request to delete ParaType : {}", id);
        paraTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-types?query=:query : search for the paraType corresponding
     * to the query.
     *
     * @param query the query of the paraType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-types")
    public ResponseEntity<List<ParaTypeDTO>> searchParaTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaTypes for query {}", query);
        Page<ParaTypeDTO> page = paraTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
