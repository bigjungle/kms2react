package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaUserService;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.repository.ParaUserRepository;
import com.aerothinker.kms.repository.search.ParaUserSearchRepository;
import com.aerothinker.kms.service.dto.ParaUserDTO;
import com.aerothinker.kms.service.mapper.ParaUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaUser.
 */
@Service
@Transactional
public class ParaUserServiceImpl implements ParaUserService {

    private final Logger log = LoggerFactory.getLogger(ParaUserServiceImpl.class);

    private final ParaUserRepository paraUserRepository;

    private final ParaUserMapper paraUserMapper;

    private final ParaUserSearchRepository paraUserSearchRepository;

    public ParaUserServiceImpl(ParaUserRepository paraUserRepository, ParaUserMapper paraUserMapper, ParaUserSearchRepository paraUserSearchRepository) {
        this.paraUserRepository = paraUserRepository;
        this.paraUserMapper = paraUserMapper;
        this.paraUserSearchRepository = paraUserSearchRepository;
    }

    /**
     * Save a paraUser.
     *
     * @param paraUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaUserDTO save(ParaUserDTO paraUserDTO) {
        log.debug("Request to save ParaUser : {}", paraUserDTO);
        ParaUser paraUser = paraUserMapper.toEntity(paraUserDTO);
        paraUser = paraUserRepository.save(paraUser);
        ParaUserDTO result = paraUserMapper.toDto(paraUser);
        paraUserSearchRepository.save(paraUser);
        return result;
    }

    /**
     * Get all the paraUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaUsers");
        return paraUserRepository.findAll(pageable)
            .map(paraUserMapper::toDto);
    }

    /**
     * Get all the ParaUser with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ParaUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return paraUserRepository.findAllWithEagerRelationships(pageable).map(paraUserMapper::toDto);
    }
    

    /**
     * Get one paraUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaUserDTO> findOne(Long id) {
        log.debug("Request to get ParaUser : {}", id);
        return paraUserRepository.findOneWithEagerRelationships(id)
            .map(paraUserMapper::toDto);
    }

    /**
     * Delete the paraUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaUser : {}", id);        paraUserRepository.deleteById(id);
        paraUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaUsers for query {}", query);
        return paraUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraUserMapper::toDto);
    }
}
