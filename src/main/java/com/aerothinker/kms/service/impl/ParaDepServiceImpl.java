package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaDepService;
import com.aerothinker.kms.domain.ParaDep;
import com.aerothinker.kms.repository.ParaDepRepository;
import com.aerothinker.kms.repository.search.ParaDepSearchRepository;
import com.aerothinker.kms.service.dto.ParaDepDTO;
import com.aerothinker.kms.service.mapper.ParaDepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaDep.
 */
@Service
@Transactional
public class ParaDepServiceImpl implements ParaDepService {

    private final Logger log = LoggerFactory.getLogger(ParaDepServiceImpl.class);

    private final ParaDepRepository paraDepRepository;

    private final ParaDepMapper paraDepMapper;

    private final ParaDepSearchRepository paraDepSearchRepository;

    public ParaDepServiceImpl(ParaDepRepository paraDepRepository, ParaDepMapper paraDepMapper, ParaDepSearchRepository paraDepSearchRepository) {
        this.paraDepRepository = paraDepRepository;
        this.paraDepMapper = paraDepMapper;
        this.paraDepSearchRepository = paraDepSearchRepository;
    }

    /**
     * Save a paraDep.
     *
     * @param paraDepDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaDepDTO save(ParaDepDTO paraDepDTO) {
        log.debug("Request to save ParaDep : {}", paraDepDTO);
        ParaDep paraDep = paraDepMapper.toEntity(paraDepDTO);
        paraDep = paraDepRepository.save(paraDep);
        ParaDepDTO result = paraDepMapper.toDto(paraDep);
        paraDepSearchRepository.save(paraDep);
        return result;
    }

    /**
     * Get all the paraDeps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaDepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaDeps");
        return paraDepRepository.findAll(pageable)
            .map(paraDepMapper::toDto);
    }


    /**
     * Get one paraDep by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaDepDTO> findOne(Long id) {
        log.debug("Request to get ParaDep : {}", id);
        return paraDepRepository.findById(id)
            .map(paraDepMapper::toDto);
    }

    /**
     * Delete the paraDep by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaDep : {}", id);        paraDepRepository.deleteById(id);
        paraDepSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraDep corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaDepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaDeps for query {}", query);
        return paraDepSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraDepMapper::toDto);
    }
}
