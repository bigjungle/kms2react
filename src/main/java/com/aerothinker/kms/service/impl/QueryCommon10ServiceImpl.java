package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.QueryCommon10Service;
import com.aerothinker.kms.domain.QueryCommon10;
import com.aerothinker.kms.repository.QueryCommon10Repository;
import com.aerothinker.kms.repository.search.QueryCommon10SearchRepository;
import com.aerothinker.kms.service.dto.QueryCommon10DTO;
import com.aerothinker.kms.service.mapper.QueryCommon10Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QueryCommon10.
 */
@Service
@Transactional
public class QueryCommon10ServiceImpl implements QueryCommon10Service {

    private final Logger log = LoggerFactory.getLogger(QueryCommon10ServiceImpl.class);

    private final QueryCommon10Repository queryCommon10Repository;

    private final QueryCommon10Mapper queryCommon10Mapper;

    private final QueryCommon10SearchRepository queryCommon10SearchRepository;

    public QueryCommon10ServiceImpl(QueryCommon10Repository queryCommon10Repository, QueryCommon10Mapper queryCommon10Mapper, QueryCommon10SearchRepository queryCommon10SearchRepository) {
        this.queryCommon10Repository = queryCommon10Repository;
        this.queryCommon10Mapper = queryCommon10Mapper;
        this.queryCommon10SearchRepository = queryCommon10SearchRepository;
    }

    /**
     * Save a queryCommon10.
     *
     * @param queryCommon10DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QueryCommon10DTO save(QueryCommon10DTO queryCommon10DTO) {
        log.debug("Request to save QueryCommon10 : {}", queryCommon10DTO);
        QueryCommon10 queryCommon10 = queryCommon10Mapper.toEntity(queryCommon10DTO);
        queryCommon10 = queryCommon10Repository.save(queryCommon10);
        QueryCommon10DTO result = queryCommon10Mapper.toDto(queryCommon10);
        queryCommon10SearchRepository.save(queryCommon10);
        return result;
    }

    /**
     * Get all the queryCommon10S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QueryCommon10DTO> findAll(Pageable pageable) {
        log.debug("Request to get all QueryCommon10S");
        return queryCommon10Repository.findAll(pageable)
            .map(queryCommon10Mapper::toDto);
    }


    /**
     * Get one queryCommon10 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QueryCommon10DTO> findOne(Long id) {
        log.debug("Request to get QueryCommon10 : {}", id);
        return queryCommon10Repository.findById(id)
            .map(queryCommon10Mapper::toDto);
    }

    /**
     * Delete the queryCommon10 by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QueryCommon10 : {}", id);        queryCommon10Repository.deleteById(id);
        queryCommon10SearchRepository.deleteById(id);
    }

    /**
     * Search for the queryCommon10 corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QueryCommon10DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QueryCommon10S for query {}", query);
        return queryCommon10SearchRepository.search(queryStringQuery(query), pageable)
            .map(queryCommon10Mapper::toDto);
    }
}
