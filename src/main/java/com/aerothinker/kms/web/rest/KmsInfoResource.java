package com.aerothinker.kms.web.rest;
import com.aerothinker.kms.service.KmsInfoService;
import com.aerothinker.kms.web.rest.errors.BadRequestAlertException;
import com.aerothinker.kms.web.rest.util.HeaderUtil;
import com.aerothinker.kms.web.rest.util.PaginationUtil;
import com.aerothinker.kms.service.dto.KmsInfoDTO;
import com.aerothinker.kms.service.dto.KmsInfoCriteria;
import com.aerothinker.kms.service.KmsInfoQueryService;
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
 * REST controller for managing KmsInfo.
 */
@RestController
@RequestMapping("/api")
public class KmsInfoResource {

    private final Logger log = LoggerFactory.getLogger(KmsInfoResource.class);

    private static final String ENTITY_NAME = "kmsInfo";

    private final KmsInfoService kmsInfoService;

    private final KmsInfoQueryService kmsInfoQueryService;

    public KmsInfoResource(KmsInfoService kmsInfoService, KmsInfoQueryService kmsInfoQueryService) {
        this.kmsInfoService = kmsInfoService;
        this.kmsInfoQueryService = kmsInfoQueryService;
    }

    /**
     * POST  /kms-infos : Create a new kmsInfo.
     *
     * @param kmsInfoDTO the kmsInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kmsInfoDTO, or with status 400 (Bad Request) if the kmsInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kms-infos")
    public ResponseEntity<KmsInfoDTO> createKmsInfo(@Valid @RequestBody KmsInfoDTO kmsInfoDTO) throws URISyntaxException {
        log.debug("REST request to save KmsInfo : {}", kmsInfoDTO);
        if (kmsInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new kmsInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmsInfoDTO result = kmsInfoService.save(kmsInfoDTO);
        return ResponseEntity.created(new URI("/api/kms-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kms-infos : Updates an existing kmsInfo.
     *
     * @param kmsInfoDTO the kmsInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kmsInfoDTO,
     * or with status 400 (Bad Request) if the kmsInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the kmsInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kms-infos")
    public ResponseEntity<KmsInfoDTO> updateKmsInfo(@Valid @RequestBody KmsInfoDTO kmsInfoDTO) throws URISyntaxException {
        log.debug("REST request to update KmsInfo : {}", kmsInfoDTO);
        if (kmsInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KmsInfoDTO result = kmsInfoService.save(kmsInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kmsInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kms-infos : get all the kmsInfos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of kmsInfos in body
     */
    @GetMapping("/kms-infos")
    public ResponseEntity<List<KmsInfoDTO>> getAllKmsInfos(KmsInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get KmsInfos by criteria: {}", criteria);
        Page<KmsInfoDTO> page = kmsInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kms-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /kms-infos/count : count all the kmsInfos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/kms-infos/count")
    public ResponseEntity<Long> countKmsInfos(KmsInfoCriteria criteria) {
        log.debug("REST request to count KmsInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmsInfoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /kms-infos/:id : get the "id" kmsInfo.
     *
     * @param id the id of the kmsInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kmsInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kms-infos/{id}")
    public ResponseEntity<KmsInfoDTO> getKmsInfo(@PathVariable Long id) {
        log.debug("REST request to get KmsInfo : {}", id);
        Optional<KmsInfoDTO> kmsInfoDTO = kmsInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmsInfoDTO);
    }

    /**
     * DELETE  /kms-infos/:id : delete the "id" kmsInfo.
     *
     * @param id the id of the kmsInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kms-infos/{id}")
    public ResponseEntity<Void> deleteKmsInfo(@PathVariable Long id) {
        log.debug("REST request to delete KmsInfo : {}", id);
        kmsInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/kms-infos?query=:query : search for the kmsInfo corresponding
     * to the query.
     *
     * @param query the query of the kmsInfo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/kms-infos")
    public ResponseEntity<List<KmsInfoDTO>> searchKmsInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of KmsInfos for query {}", query);
        Page<KmsInfoDTO> page = kmsInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/kms-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
