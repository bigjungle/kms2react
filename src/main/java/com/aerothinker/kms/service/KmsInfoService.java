package com.aerothinker.kms.service;

import com.aerothinker.kms.service.dto.KmsInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing KmsInfo.
 */
public interface KmsInfoService {

    /**
     * Save a kmsInfo.
     *
     * @param kmsInfoDTO the entity to save
     * @return the persisted entity
     */
    KmsInfoDTO save(KmsInfoDTO kmsInfoDTO);

    /**
     * Get all the kmsInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KmsInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the KmsInfo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<KmsInfoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" kmsInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<KmsInfoDTO> findOne(Long id);

    /**
     * Delete the "id" kmsInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the kmsInfo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KmsInfoDTO> search(String query, Pageable pageable);
}
