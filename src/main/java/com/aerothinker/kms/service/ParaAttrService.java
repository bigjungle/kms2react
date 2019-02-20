package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.ParaAttrDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParaAttr.
 */
public interface ParaAttrService {

    /**
     * Save a paraAttr.
     *
     * @param paraAttrDTO the entity to save
     * @return the persisted entity
     */
    ParaAttrDTO save(ParaAttrDTO paraAttrDTO);

    /**
     * Get all the paraAttrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaAttrDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paraAttr.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParaAttrDTO> findOne(Long id);

    /**
     * Delete the "id" paraAttr.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paraAttr corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParaAttrDTO> search(String query, Pageable pageable);
}
