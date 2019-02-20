package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaOtherDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaOther.
 */
public interface ParaOtherService {

    /**
     * Save a paraOther.
     *
     * @param paraOtherDTO the entity to save
     * @return the persisted entity
     */
    ParaOtherDTO save(ParaOtherDTO paraOtherDTO);

    /**
     * Get all the paraOthers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaOtherDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraOther.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaOtherDTO> findOne(Long id);

    /**
     * Delete the "id" paraOther.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraOther corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaOtherDTO> search(String query, Pageable pageable);
}
