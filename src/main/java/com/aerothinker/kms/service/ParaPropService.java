package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaPropDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaProp.
 */
public interface ParaPropService {

    /**
     * Save a paraProp.
     *
     * @param paraPropDTO the entity to save
     * @return the persisted entity
     */
    ParaPropDTO save(ParaPropDTO paraPropDTO);

    /**
     * Get all the paraProps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaPropDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraProp.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaPropDTO> findOne(Long id);

    /**
     * Delete the "id" paraProp.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraProp corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaPropDTO> search(String query, Pageable pageable);
}
