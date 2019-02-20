package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.VerifyRecService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.VerifyRecDTO;
import com.aerothinker.kms.service.dto.VerifyRecCriteria;
import com.aerothinker.kms.service.VerifyRecQueryService;
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
 * REST controller for managing VerifyRec.
 */
@RestController
@RequestMapping("/api")
public class VerifyRecResource {

    private final Logger log = LoggerFactory.getLogger(VerifyRecResource.class);

    private static final String ENTITY_NAME = "verifyRec";

    private final VerifyRecService verifyRecService;

    private final VerifyRecQueryService verifyRecQueryService;

    public VerifyRecResource(VerifyRecService verifyRecService, VerifyRecQueryService verifyRecQueryService) {
        this.verifyRecService = verifyRecService;
        this.verifyRecQueryService = verifyRecQueryService;
    }

    /**
     * POST  /verify-recs : Create a new verifyRec.
     *
     * @param verifyRecDTO the verifyRecDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new verifyRecDTO, or with status 400 (Bad Request) if the verifyRec has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/verify-recs")
    public ResponseEntity<VerifyRecDTO> createVerifyRec(@Valid @RequestBody VerifyRecDTO verifyRecDTO) throws URISyntaxException {
        log.debug("REST request to save VerifyRec : {}", verifyRecDTO);
        if (verifyRecDTO.getId() != null) {
            throw new BadRequestAlertException("A new verifyRec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VerifyRecDTO result = verifyRecService.save(verifyRecDTO);
        return ResponseEntity.created(new URI("/api/verify-recs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /verify-recs : Updates an existing verifyRec.
     *
     * @param verifyRecDTO the verifyRecDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated verifyRecDTO,
     * or with status 400 (Bad Request) if the verifyRecDTO is not valid,
     * or with status 500 (Internal Server Error) if the verifyRecDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/verify-recs")
    public ResponseEntity<VerifyRecDTO> updateVerifyRec(@Valid @RequestBody VerifyRecDTO verifyRecDTO) throws URISyntaxException {
        log.debug("REST request to update VerifyRec : {}", verifyRecDTO);
        if (verifyRecDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VerifyRecDTO result = verifyRecService.save(verifyRecDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, verifyRecDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /verify-recs : get all the verifyRecs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of verifyRecs in body
     */
    @GetMapping("/verify-recs")
    public ResponseEntity<List<VerifyRecDTO>> getAllVerifyRecs(VerifyRecCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VerifyRecs by criteria: {}", criteria);
        Page<VerifyRecDTO> page = verifyRecQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/verify-recs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /verify-recs/count : count all the verifyRecs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/verify-recs/count")
    public ResponseEntity<Long> countVerifyRecs(VerifyRecCriteria criteria) {
        log.debug("REST request to count VerifyRecs by criteria: {}", criteria);
        return ResponseEntity.ok().body(verifyRecQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /verify-recs/:id : get the "id" verifyRec.
     *
     * @param id the id of the verifyRecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the verifyRecDTO, or with status 404 (Not Found)
     */
    @GetMapping("/verify-recs/{id}")
    public ResponseEntity<VerifyRecDTO> getVerifyRec(@PathVariable Long id) {
        log.debug("REST request to get VerifyRec : {}", id);
        Optional<VerifyRecDTO> verifyRecDTO = verifyRecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(verifyRecDTO);
    }

    /**
     * DELETE  /verify-recs/:id : delete the "id" verifyRec.
     *
     * @param id the id of the verifyRecDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/verify-recs/{id}")
    public ResponseEntity<Void> deleteVerifyRec(@PathVariable Long id) {
        log.debug("REST request to delete VerifyRec : {}", id);
        verifyRecService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/verify-recs?query=:query : search for the verifyRec corresponding
     * to the query.
     *
     * @param query the query of the verifyRec search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/verify-recs")
    public ResponseEntity<List<VerifyRecDTO>> searchVerifyRecs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VerifyRecs for query {}", query);
        Page<VerifyRecDTO> page = verifyRecService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/verify-recs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
