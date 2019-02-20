package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaStateService;
import com.aerothinker.kms.domain.ParaState;
import com.aerothinker.kms.repository.ParaStateRepository;
import com.aerothinker.kms.repository.search.ParaStateSearchRepository;
import com.aerothinker.kms.service.dto.ParaStateDTO;
import com.aerothinker.kms.service.mapper.ParaStateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaState.
 */
@Service
@Transactional
public class ParaStateServiceImpl implements ParaStateService {

    private final Logger log = LoggerFactory.getLogger(ParaStateServiceImpl.class);

    private final ParaStateRepository paraStateRepository;

    private final ParaStateMapper paraStateMapper;

    private final ParaStateSearchRepository paraStateSearchRepository;

    public ParaStateServiceImpl(ParaStateRepository paraStateRepository, ParaStateMapper paraStateMapper, ParaStateSearchRepository paraStateSearchRepository) {
        this.paraStateRepository = paraStateRepository;
        this.paraStateMapper = paraStateMapper;
        this.paraStateSearchRepository = paraStateSearchRepository;
    }

    /**
     * Save a paraState.
     *
     * @param paraStateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaStateDTO save(ParaStateDTO paraStateDTO) {
        log.debug("Request to save ParaState : {}", paraStateDTO);
        ParaState paraState = paraStateMapper.toEntity(paraStateDTO);
        paraState = paraStateRepository.save(paraState);
        ParaStateDTO result = paraStateMapper.toDto(paraState);
        paraStateSearchRepository.save(paraState);
        return result;
    }

    /**
     * Get all the paraStates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaStateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaStates");
        return paraStateRepository.findAll(pageable)
            .map(paraStateMapper::toDto);
    }


    /**
     * Get one paraState by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaStateDTO> findOne(Long id) {
        log.debug("Request to get ParaState : {}", id);
        return paraStateRepository.findById(id)
            .map(paraStateMapper::toDto);
    }

    /**
     * Delete the paraState by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaState : {}", id);        paraStateRepository.deleteById(id);
        paraStateSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraState corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaStateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaStates for query {}", query);
        return paraStateSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraStateMapper::toDto);
    }
}
