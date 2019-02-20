package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaRoleService;
import com.aerothinker.kms.domain.ParaRole;
import com.aerothinker.kms.repository.ParaRoleRepository;
import com.aerothinker.kms.repository.search.ParaRoleSearchRepository;
import com.aerothinker.kms.service.dto.ParaRoleDTO;
import com.aerothinker.kms.service.mapper.ParaRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaRole.
 */
@Service
@Transactional
public class ParaRoleServiceImpl implements ParaRoleService {

    private final Logger log = LoggerFactory.getLogger(ParaRoleServiceImpl.class);

    private final ParaRoleRepository paraRoleRepository;

    private final ParaRoleMapper paraRoleMapper;

    private final ParaRoleSearchRepository paraRoleSearchRepository;

    public ParaRoleServiceImpl(ParaRoleRepository paraRoleRepository, ParaRoleMapper paraRoleMapper, ParaRoleSearchRepository paraRoleSearchRepository) {
        this.paraRoleRepository = paraRoleRepository;
        this.paraRoleMapper = paraRoleMapper;
        this.paraRoleSearchRepository = paraRoleSearchRepository;
    }

    /**
     * Save a paraRole.
     *
     * @param paraRoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaRoleDTO save(ParaRoleDTO paraRoleDTO) {
        log.debug("Request to save ParaRole : {}", paraRoleDTO);
        ParaRole paraRole = paraRoleMapper.toEntity(paraRoleDTO);
        paraRole = paraRoleRepository.save(paraRole);
        ParaRoleDTO result = paraRoleMapper.toDto(paraRole);
        paraRoleSearchRepository.save(paraRole);
        return result;
    }

    /**
     * Get all the paraRoles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaRoles");
        return paraRoleRepository.findAll(pageable)
            .map(paraRoleMapper::toDto);
    }

    /**
     * Get all the ParaRole with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ParaRoleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return paraRoleRepository.findAllWithEagerRelationships(pageable).map(paraRoleMapper::toDto);
    }
    

    /**
     * Get one paraRole by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaRoleDTO> findOne(Long id) {
        log.debug("Request to get ParaRole : {}", id);
        return paraRoleRepository.findOneWithEagerRelationships(id)
            .map(paraRoleMapper::toDto);
    }

    /**
     * Delete the paraRole by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaRole : {}", id);        paraRoleRepository.deleteById(id);
        paraRoleSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraRole corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaRoleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaRoles for query {}", query);
        return paraRoleSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraRoleMapper::toDto);
    }
}
