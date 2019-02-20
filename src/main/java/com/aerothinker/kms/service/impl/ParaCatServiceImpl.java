package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaCatService;
import com.aerothinker.kms.domain.ParaCat;
import com.aerothinker.kms.repository.ParaCatRepository;
import com.aerothinker.kms.repository.search.ParaCatSearchRepository;
import com.aerothinker.kms.service.dto.ParaCatDTO;
import com.aerothinker.kms.service.mapper.ParaCatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaCat.
 */
@Service
@Transactional
public class ParaCatServiceImpl implements ParaCatService {

    private final Logger log = LoggerFactory.getLogger(ParaCatServiceImpl.class);

    private final ParaCatRepository paraCatRepository;

    private final ParaCatMapper paraCatMapper;

    private final ParaCatSearchRepository paraCatSearchRepository;

    public ParaCatServiceImpl(ParaCatRepository paraCatRepository, ParaCatMapper paraCatMapper, ParaCatSearchRepository paraCatSearchRepository) {
        this.paraCatRepository = paraCatRepository;
        this.paraCatMapper = paraCatMapper;
        this.paraCatSearchRepository = paraCatSearchRepository;
    }

    /**
     * Save a paraCat.
     *
     * @param paraCatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaCatDTO save(ParaCatDTO paraCatDTO) {
        log.debug("Request to save ParaCat : {}", paraCatDTO);
        ParaCat paraCat = paraCatMapper.toEntity(paraCatDTO);
        paraCat = paraCatRepository.save(paraCat);
        ParaCatDTO result = paraCatMapper.toDto(paraCat);
        paraCatSearchRepository.save(paraCat);
        return result;
    }

    /**
     * Get all the paraCats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaCatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaCats");
        return paraCatRepository.findAll(pageable)
            .map(paraCatMapper::toDto);
    }


    /**
     * Get one paraCat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaCatDTO> findOne(Long id) {
        log.debug("Request to get ParaCat : {}", id);
        return paraCatRepository.findById(id)
            .map(paraCatMapper::toDto);
    }

    /**
     * Delete the paraCat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaCat : {}", id);        paraCatRepository.deleteById(id);
        paraCatSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraCat corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaCatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaCats for query {}", query);
        return paraCatSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraCatMapper::toDto);
    }
}
