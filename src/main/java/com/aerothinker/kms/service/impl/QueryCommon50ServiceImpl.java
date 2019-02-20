package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.QueryCommon50Service;
import com.aerothinker.kms.domain.QueryCommon50;
import com.aerothinker.kms.repository.QueryCommon50Repository;
import com.aerothinker.kms.repository.search.QueryCommon50SearchRepository;
import com.aerothinker.kms.service.dto.QueryCommon50DTO;
import com.aerothinker.kms.service.mapper.QueryCommon50Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QueryCommon50.
 */
@Service
@Transactional
public class QueryCommon50ServiceImpl implements QueryCommon50Service {

    private final Logger log = LoggerFactory.getLogger(QueryCommon50ServiceImpl.class);

    private final QueryCommon50Repository queryCommon50Repository;

    private final QueryCommon50Mapper queryCommon50Mapper;

    private final QueryCommon50SearchRepository queryCommon50SearchRepository;

    public QueryCommon50ServiceImpl(QueryCommon50Repository queryCommon50Repository, QueryCommon50Mapper queryCommon50Mapper, QueryCommon50SearchRepository queryCommon50SearchRepository) {
        this.queryCommon50Repository = queryCommon50Repository;
        this.queryCommon50Mapper = queryCommon50Mapper;
        this.queryCommon50SearchRepository = queryCommon50SearchRepository;
    }

    /**
     * Save a queryCommon50.
     *
     * @param queryCommon50DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QueryCommon50DTO save(QueryCommon50DTO queryCommon50DTO) {
        log.debug("Request to save QueryCommon50 : {}", queryCommon50DTO);
        QueryCommon50 queryCommon50 = queryCommon50Mapper.toEntity(queryCommon50DTO);
        queryCommon50 = queryCommon50Repository.save(queryCommon50);
        QueryCommon50DTO result = queryCommon50Mapper.toDto(queryCommon50);
        queryCommon50SearchRepository.save(queryCommon50);
        return result;
    }

    /**
     * Get all the queryCommon50S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QueryCommon50DTO> findAll(Pageable pageable) {
        log.debug("Request to get all QueryCommon50S");
        return queryCommon50Repository.findAll(pageable)
            .map(queryCommon50Mapper::toDto);
    }


    /**
     * Get one queryCommon50 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QueryCommon50DTO> findOne(Long id) {
        log.debug("Request to get QueryCommon50 : {}", id);
        return queryCommon50Repository.findById(id)
            .map(queryCommon50Mapper::toDto);
    }

    /**
     * Delete the queryCommon50 by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QueryCommon50 : {}", id);        queryCommon50Repository.deleteById(id);
        queryCommon50SearchRepository.deleteById(id);
    }

    /**
     * Search for the queryCommon50 corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QueryCommon50DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QueryCommon50S for query {}", query);
        return queryCommon50SearchRepository.search(queryStringQuery(query), pageable)
            .map(queryCommon50Mapper::toDto);
    }
}
