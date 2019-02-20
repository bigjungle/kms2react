package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.VerifyRecDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing VerifyRec.
 */
public interface VerifyRecService {

    /**
     * Save a verifyRec.
     *
     * @param verifyRecDTO the entity to save
     * @return the persisted entity
     */
    VerifyRecDTO save(VerifyRecDTO verifyRecDTO);

    /**
     * Get all the verifyRecs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VerifyRecDTO> findAll(Pageable pageable);


    /**
     * Get the "id" verifyRec.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VerifyRecDTO> findOne(Long id);

    /**
     * Delete the "id" verifyRec.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the verifyRec corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VerifyRecDTO> search(String query, Pageable pageable);
}
