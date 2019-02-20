package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaClassDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaClass.
 */
public interface ParaClassService {

    /**
     * Save a paraClass.
     *
     * @param paraClassDTO the entity to save
     * @return the persisted entity
     */
    ParaClassDTO save(ParaClassDTO paraClassDTO);

    /**
     * Get all the paraClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaClassDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraClass.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaClassDTO> findOne(Long id);

    /**
     * Delete the "id" paraClass.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraClass corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaClassDTO> search(String query, Pageable pageable);
}
