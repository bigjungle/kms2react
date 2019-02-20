package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaUser.
 */
public interface ParaUserService {

    /**
     * Save a paraUser.
     *
     * @param paraUserDTO the entity to save
     * @return the persisted entity
     */
    ParaUserDTO save(ParaUserDTO paraUserDTO);

    /**
     * Get all the paraUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaUserDTO> findAll(Pageable pageable);

    /**
     * Get all the ParaUser with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ParaUserDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" paraUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaUserDTO> findOne(Long id);

    /**
     * Delete the "id" paraUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaUserDTO> search(String query, Pageable pageable);
}
