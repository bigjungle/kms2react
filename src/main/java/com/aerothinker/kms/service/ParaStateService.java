package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaStateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaState.
 */
public interface ParaStateService {

    /**
     * Save a paraState.
     *
     * @param paraStateDTO the entity to save
     * @return the persisted entity
     */
    ParaStateDTO save(ParaStateDTO paraStateDTO);

    /**
     * Get all the paraStates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaStateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraState.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaStateDTO> findOne(Long id);

    /**
     * Delete the "id" paraState.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraState corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaStateDTO> search(String query, Pageable pageable);
}
