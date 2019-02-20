package com.aerothinker.kms.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.aerothinker.kms.domain.QueryCommon10;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.QueryCommon10Repository;
import com.aerothinker.kms.repository.search.QueryCommon10SearchRepository;
import com.aerothinker.kms.service.dto.QueryCommon10Criteria;
import com.aerothinker.kms.service.dto.QueryCommon10DTO;
import com.aerothinker.kms.service.mapper.QueryCommon10Mapper;

/**
 * Service for executing complex queries for QueryCommon10 entities in the database.
 * The main input is a {@link QueryCommon10Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QueryCommon10DTO} or a {@link Page} of {@link QueryCommon10DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QueryCommon10QueryService extends QueryService<QueryCommon10> {

    private final Logger log = LoggerFactory.getLogger(QueryCommon10QueryService.class);

    private final QueryCommon10Repository queryCommon10Repository;

    private final QueryCommon10Mapper queryCommon10Mapper;

    private final QueryCommon10SearchRepository queryCommon10SearchRepository;

    public QueryCommon10QueryService(QueryCommon10Repository queryCommon10Repository, QueryCommon10Mapper queryCommon10Mapper, QueryCommon10SearchRepository queryCommon10SearchRepository) {
        this.queryCommon10Repository = queryCommon10Repository;
        this.queryCommon10Mapper = queryCommon10Mapper;
        this.queryCommon10SearchRepository = queryCommon10SearchRepository;
    }

    /**
     * Return a {@link List} of {@link QueryCommon10DTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QueryCommon10DTO> findByCriteria(QueryCommon10Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QueryCommon10> specification = createSpecification(criteria);
        return queryCommon10Mapper.toDto(queryCommon10Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QueryCommon10DTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QueryCommon10DTO> findByCriteria(QueryCommon10Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QueryCommon10> specification = createSpecification(criteria);
        return queryCommon10Repository.findAll(specification, page)
            .map(queryCommon10Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QueryCommon10Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QueryCommon10> specification = createSpecification(criteria);
        return queryCommon10Repository.count(specification);
    }

    /**
     * Function to convert QueryCommon10Criteria to a {@link Specification}
     */
    private Specification<QueryCommon10> createSpecification(QueryCommon10Criteria criteria) {
        Specification<QueryCommon10> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QueryCommon10_.id));
            }
            if (criteria.getQueryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQueryName(), QueryCommon10_.queryName));
            }
            if (criteria.getQueryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQueryDate(), QueryCommon10_.queryDate));
            }
            if (criteria.getQueryUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQueryUser(), QueryCommon10_.queryUser));
            }
            if (criteria.getCol01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol01(), QueryCommon10_.col01));
            }
            if (criteria.getCol02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol02(), QueryCommon10_.col02));
            }
            if (criteria.getCol03() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol03(), QueryCommon10_.col03));
            }
            if (criteria.getCol04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol04(), QueryCommon10_.col04));
            }
            if (criteria.getCol05() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol05(), QueryCommon10_.col05));
            }
            if (criteria.getCol06() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol06(), QueryCommon10_.col06));
            }
            if (criteria.getCol07() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol07(), QueryCommon10_.col07));
            }
            if (criteria.getCol08() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol08(), QueryCommon10_.col08));
            }
            if (criteria.getCol09() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol09(), QueryCommon10_.col09));
            }
            if (criteria.getCol10() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol10(), QueryCommon10_.col10));
            }
        }
        return specification;
    }
}
