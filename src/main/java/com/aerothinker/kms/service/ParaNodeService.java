package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaNodeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaNode.
 */
public interface ParaNodeService {

    /**
     * Save a paraNode.
     *
     * @param paraNodeDTO the entity to save
     * @return the persisted entity
     */
    ParaNodeDTO save(ParaNodeDTO paraNodeDTO);

    /**
     * Get all the paraNodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaNodeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraNode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaNodeDTO> findOne(Long id);

    /**
     * Delete the "id" paraNode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraNode corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaNodeDTO> search(String query, Pageable pageable);
}
