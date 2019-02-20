package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaDepDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaDep.
 */
public interface ParaDepService {

    /**
     * Save a paraDep.
     *
     * @param paraDepDTO the entity to save
     * @return the persisted entity
     */
    ParaDepDTO save(ParaDepDTO paraDepDTO);

    /**
     * Get all the paraDeps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaDepDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraDep.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaDepDTO> findOne(Long id);

    /**
     * Delete the "id" paraDep.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraDep corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaDepDTO> search(String query, Pageable pageable);
}
