package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.QueryCommon50DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing QueryCommon50.
 */
public interface QueryCommon50Service {

    /**
     * Save a queryCommon50.
     *
     * @param queryCommon50DTO the entity to save
     * @return the persisted entity
     */
    QueryCommon50DTO save(QueryCommon50DTO queryCommon50DTO);

    /**
     * Get all the queryCommon50S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QueryCommon50DTO> findAll(Pageable pageable);


    /**
     * Get the "id" queryCommon50.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QueryCommon50DTO> findOne(Long id);

    /**
     * Delete the "id" queryCommon50.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the queryCommon50 corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QueryCommon50DTO> search(String query, Pageable pageable);
}
