package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.ParaAttrService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.ParaAttrDTO;
import com.aerothinker.kms.service.dto.ParaAttrCriteria;
import com.aerothinker.kms.service.ParaAttrQueryService;
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
 * REST controller for managing ParaAttr.
 */
@RestController
@RequestMapping("/api")
public class ParaAttrResource {

    private final Logger log = LoggerFactory.getLogger(ParaAttrResource.class);

    private static final String ENTITY_NAME = "paraAttr";

    private final ParaAttrService paraAttrService;

    private final ParaAttrQueryService paraAttrQueryService;

    public ParaAttrResource(ParaAttrService paraAttrService, ParaAttrQueryService paraAttrQueryService) {
        this.paraAttrService = paraAttrService;
        this.paraAttrQueryService = paraAttrQueryService;
    }

    /**
     * POST  /para-attrs : Create a new paraAttr.
     *
     * @param paraAttrDTO the paraAttrDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paraAttrDTO, or with status 400 (Bad Request) if the paraAttr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/para-attrs")
    public ResponseEntity<ParaAttrDTO> createParaAttr(@Valid @RequestBody ParaAttrDTO paraAttrDTO) throws URISyntaxException {
        log.debug("REST request to save ParaAttr : {}", paraAttrDTO);
        if (paraAttrDTO.getId() != null) {
            throw new BadRequestAlertException("A new paraAttr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParaAttrDTO result = paraAttrService.save(paraAttrDTO);
        return ResponseEntity.created(new URI("/api/para-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /para-attrs : Updates an existing paraAttr.
     *
     * @param paraAttrDTO the paraAttrDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paraAttrDTO,
     * or with status 400 (Bad Request) if the paraAttrDTO is not valid,
     * or with status 500 (Internal Server Error) if the paraAttrDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/para-attrs")
    public ResponseEntity<ParaAttrDTO> updateParaAttr(@Valid @RequestBody ParaAttrDTO paraAttrDTO) throws URISyntaxException {
        log.debug("REST request to update ParaAttr : {}", paraAttrDTO);
        if (paraAttrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParaAttrDTO result = paraAttrService.save(paraAttrDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paraAttrDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /para-attrs : get all the paraAttrs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paraAttrs in body
     */
    @GetMapping("/para-attrs")
    public ResponseEntity<List<ParaAttrDTO>> getAllParaAttrs(ParaAttrCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParaAttrs by criteria: {}", criteria);
        Page<ParaAttrDTO> page = paraAttrQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/para-attrs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /para-attrs/count : count all the paraAttrs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/para-attrs/count")
    public ResponseEntity<Long> countParaAttrs(ParaAttrCriteria criteria) {
        log.debug("REST request to count ParaAttrs by criteria: {}", criteria);
        return ResponseEntity.ok().body(paraAttrQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /para-attrs/:id : get the "id" paraAttr.
     *
     * @param id the id of the paraAttrDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paraAttrDTO, or with status 404 (Not Found)
     */
    @GetMapping("/para-attrs/{id}")
    public ResponseEntity<ParaAttrDTO> getParaAttr(@PathVariable Long id) {
        log.debug("REST request to get ParaAttr : {}", id);
        Optional<ParaAttrDTO> paraAttrDTO = paraAttrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paraAttrDTO);
    }

    /**
     * DELETE  /para-attrs/:id : delete the "id" paraAttr.
     *
     * @param id the id of the paraAttrDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/para-attrs/{id}")
    public ResponseEntity<Void> deleteParaAttr(@PathVariable Long id) {
        log.debug("REST request to delete ParaAttr : {}", id);
        paraAttrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/para-attrs?query=:query : search for the paraAttr corresponding
     * to the query.
     *
     * @param query the query of the paraAttr search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/para-attrs")
    public ResponseEntity<List<ParaAttrDTO>> searchParaAttrs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParaAttrs for query {}", query);
        Page<ParaAttrDTO> page = paraAttrService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/para-attrs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
