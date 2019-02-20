package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.QueryCommon10DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing QueryCommon10.
 */
public interface QueryCommon10Service {

    /**
     * Save a queryCommon10.
     *
     * @param queryCommon10DTO the entity to save
     * @return the persisted entity
     */
    QueryCommon10DTO save(QueryCommon10DTO queryCommon10DTO);

    /**
     * Get all the queryCommon10S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QueryCommon10DTO> findAll(Pageable pageable);


    /**
     * Get the "id" queryCommon10.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QueryCommon10DTO> findOne(Long id);

    /**
     * Delete the "id" queryCommon10.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the queryCommon10 corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QueryCommon10DTO> search(String query, Pageable pageable);
}
