package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaSourceService;
import com.aerothinker.kms.domain.ParaSource;
import com.aerothinker.kms.repository.ParaSourceRepository;
import com.aerothinker.kms.repository.search.ParaSourceSearchRepository;
import com.aerothinker.kms.service.dto.ParaSourceDTO;
import com.aerothinker.kms.service.mapper.ParaSourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaSource.
 */
@Service
@Transactional
public class ParaSourceServiceImpl implements ParaSourceService {

    private final Logger log = LoggerFactory.getLogger(ParaSourceServiceImpl.class);

    private final ParaSourceRepository paraSourceRepository;

    private final ParaSourceMapper paraSourceMapper;

    private final ParaSourceSearchRepository paraSourceSearchRepository;

    public ParaSourceServiceImpl(ParaSourceRepository paraSourceRepository, ParaSourceMapper paraSourceMapper, ParaSourceSearchRepository paraSourceSearchRepository) {
        this.paraSourceRepository = paraSourceRepository;
        this.paraSourceMapper = paraSourceMapper;
        this.paraSourceSearchRepository = paraSourceSearchRepository;
    }

    /**
     * Save a paraSource.
     *
     * @param paraSourceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaSourceDTO save(ParaSourceDTO paraSourceDTO) {
        log.debug("Request to save ParaSource : {}", paraSourceDTO);
        ParaSource paraSource = paraSourceMapper.toEntity(paraSourceDTO);
        paraSource = paraSourceRepository.save(paraSource);
        ParaSourceDTO result = paraSourceMapper.toDto(paraSource);
        paraSourceSearchRepository.save(paraSource);
        return result;
    }

    /**
     * Get all the paraSources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaSourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaSources");
        return paraSourceRepository.findAll(pageable)
            .map(paraSourceMapper::toDto);
    }


    /**
     * Get one paraSource by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaSourceDTO> findOne(Long id) {
        log.debug("Request to get ParaSource : {}", id);
        return paraSourceRepository.findById(id)
            .map(paraSourceMapper::toDto);
    }

    /**
     * Delete the paraSource by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaSource : {}", id);        paraSourceRepository.deleteById(id);
        paraSourceSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraSource corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaSourceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaSources for query {}", query);
        return paraSourceSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraSourceMapper::toDto);
    }
}
