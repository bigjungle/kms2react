package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaNodeService;
import com.aerothinker.kms.domain.ParaNode;
import com.aerothinker.kms.repository.ParaNodeRepository;
import com.aerothinker.kms.repository.search.ParaNodeSearchRepository;
import com.aerothinker.kms.service.dto.ParaNodeDTO;
import com.aerothinker.kms.service.mapper.ParaNodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaNode.
 */
@Service
@Transactional
public class ParaNodeServiceImpl implements ParaNodeService {

    private final Logger log = LoggerFactory.getLogger(ParaNodeServiceImpl.class);

    private final ParaNodeRepository paraNodeRepository;

    private final ParaNodeMapper paraNodeMapper;

    private final ParaNodeSearchRepository paraNodeSearchRepository;

    public ParaNodeServiceImpl(ParaNodeRepository paraNodeRepository, ParaNodeMapper paraNodeMapper, ParaNodeSearchRepository paraNodeSearchRepository) {
        this.paraNodeRepository = paraNodeRepository;
        this.paraNodeMapper = paraNodeMapper;
        this.paraNodeSearchRepository = paraNodeSearchRepository;
    }

    /**
     * Save a paraNode.
     *
     * @param paraNodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaNodeDTO save(ParaNodeDTO paraNodeDTO) {
        log.debug("Request to save ParaNode : {}", paraNodeDTO);
        ParaNode paraNode = paraNodeMapper.toEntity(paraNodeDTO);
        paraNode = paraNodeRepository.save(paraNode);
        ParaNodeDTO result = paraNodeMapper.toDto(paraNode);
        paraNodeSearchRepository.save(paraNode);
        return result;
    }

    /**
     * Get all the paraNodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaNodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaNodes");
        return paraNodeRepository.findAll(pageable)
            .map(paraNodeMapper::toDto);
    }


    /**
     * Get one paraNode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaNodeDTO> findOne(Long id) {
        log.debug("Request to get ParaNode : {}", id);
        return paraNodeRepository.findById(id)
            .map(paraNodeMapper::toDto);
    }

    /**
     * Delete the paraNode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaNode : {}", id);        paraNodeRepository.deleteById(id);
        paraNodeSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraNode corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaNodeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaNodes for query {}", query);
        return paraNodeSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraNodeMapper::toDto);
    }
}
