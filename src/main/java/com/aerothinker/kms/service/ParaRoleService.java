package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaRole.
 */
public interface ParaRoleService {

    /**
     * Save a paraRole.
     *
     * @param paraRoleDTO the entity to save
     * @return the persisted entity
     */
    ParaRoleDTO save(ParaRoleDTO paraRoleDTO);

    /**
     * Get all the paraRoles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaRoleDTO> findAll(Pageable pageable);

    /**
     * Get all the ParaRole with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ParaRoleDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" paraRole.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaRoleDTO> findOne(Long id);

    /**
     * Delete the "id" paraRole.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraRole corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaRoleDTO> search(String query, Pageable pageable);
}
