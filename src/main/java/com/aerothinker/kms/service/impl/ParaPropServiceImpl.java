package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaPropService;
import com.aerothinker.kms.domain.ParaProp;
import com.aerothinker.kms.repository.ParaPropRepository;
import com.aerothinker.kms.repository.search.ParaPropSearchRepository;
import com.aerothinker.kms.service.dto.ParaPropDTO;
import com.aerothinker.kms.service.mapper.ParaPropMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaProp.
 */
@Service
@Transactional
public class ParaPropServiceImpl implements ParaPropService {

    private final Logger log = LoggerFactory.getLogger(ParaPropServiceImpl.class);

    private final ParaPropRepository paraPropRepository;

    private final ParaPropMapper paraPropMapper;

    private final ParaPropSearchRepository paraPropSearchRepository;

    public ParaPropServiceImpl(ParaPropRepository paraPropRepository, ParaPropMapper paraPropMapper, ParaPropSearchRepository paraPropSearchRepository) {
        this.paraPropRepository = paraPropRepository;
        this.paraPropMapper = paraPropMapper;
        this.paraPropSearchRepository = paraPropSearchRepository;
    }

    /**
     * Save a paraProp.
     *
     * @param paraPropDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaPropDTO save(ParaPropDTO paraPropDTO) {
        log.debug("Request to save ParaProp : {}", paraPropDTO);
        ParaProp paraProp = paraPropMapper.toEntity(paraPropDTO);
        paraProp = paraPropRepository.save(paraProp);
        ParaPropDTO result = paraPropMapper.toDto(paraProp);
        paraPropSearchRepository.save(paraProp);
        return result;
    }

    /**
     * Get all the paraProps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaPropDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaProps");
        return paraPropRepository.findAll(pageable)
            .map(paraPropMapper::toDto);
    }


    /**
     * Get one paraProp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaPropDTO> findOne(Long id) {
        log.debug("Request to get ParaProp : {}", id);
        return paraPropRepository.findById(id)
            .map(paraPropMapper::toDto);
    }

    /**
     * Delete the paraProp by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaProp : {}", id);        paraPropRepository.deleteById(id);
        paraPropSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraProp corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaPropDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaProps for query {}", query);
        return paraPropSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraPropMapper::toDto);
    }
}
