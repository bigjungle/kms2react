package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaType.
 */
public interface ParaTypeService {

    /**
     * Save a paraType.
     *
     * @param paraTypeDTO the entity to save
     * @return the persisted entity
     */
    ParaTypeDTO save(ParaTypeDTO paraTypeDTO);

    /**
     * Get all the paraTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaTypeDTO> findOne(Long id);

    /**
     * Delete the "id" paraType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaTypeDTO> search(String query, Pageable pageable);
}
