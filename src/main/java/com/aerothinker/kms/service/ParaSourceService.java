package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaSourceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaSource.
 */
public interface ParaSourceService {

    /**
     * Save a paraSource.
     *
     * @param paraSourceDTO the entity to save
     * @return the persisted entity
     */
    ParaSourceDTO save(ParaSourceDTO paraSourceDTO);

    /**
     * Get all the paraSources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaSourceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraSource.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaSourceDTO> findOne(Long id);

    /**
     * Delete the "id" paraSource.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraSource corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaSourceDTO> search(String query, Pageable pageable);
}
