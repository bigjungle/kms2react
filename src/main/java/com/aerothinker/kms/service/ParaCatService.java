package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaCatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaCat.
 */
public interface ParaCatService {

    /**
     * Save a paraCat.
     *
     * @param paraCatDTO the entity to save
     * @return the persisted entity
     */
    ParaCatDTO save(ParaCatDTO paraCatDTO);

    /**
     * Get all the paraCats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaCatDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraCat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaCatDTO> findOne(Long id);

    /**
     * Delete the "id" paraCat.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraCat corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaCatDTO> search(String query, Pageable pageable);
}
