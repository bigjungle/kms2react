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

import com.aerothinker.kms.domain.QueryCommon50;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.QueryCommon50Repository;
import com.aerothinker.kms.repository.search.QueryCommon50SearchRepository;
import com.aerothinker.kms.service.dto.QueryCommon50Criteria;
import com.aerothinker.kms.service.dto.QueryCommon50DTO;
import com.aerothinker.kms.service.mapper.QueryCommon50Mapper;

/**
 * Service for executing complex queries for QueryCommon50 entities in the database.
 * The main input is a {@link QueryCommon50Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QueryCommon50DTO} or a {@link Page} of {@link QueryCommon50DTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QueryCommon50QueryService extends QueryService<QueryCommon50> {

    private final Logger log = LoggerFactory.getLogger(QueryCommon50QueryService.class);

    private final QueryCommon50Repository queryCommon50Repository;

    private final QueryCommon50Mapper queryCommon50Mapper;

    private final QueryCommon50SearchRepository queryCommon50SearchRepository;

    public QueryCommon50QueryService(QueryCommon50Repository queryCommon50Repository, QueryCommon50Mapper queryCommon50Mapper, QueryCommon50SearchRepository queryCommon50SearchRepository) {
        this.queryCommon50Repository = queryCommon50Repository;
        this.queryCommon50Mapper = queryCommon50Mapper;
        this.queryCommon50SearchRepository = queryCommon50SearchRepository;
    }

    /**
     * Return a {@link List} of {@link QueryCommon50DTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QueryCommon50DTO> findByCriteria(QueryCommon50Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QueryCommon50> specification = createSpecification(criteria);
        return queryCommon50Mapper.toDto(queryCommon50Repository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QueryCommon50DTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QueryCommon50DTO> findByCriteria(QueryCommon50Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QueryCommon50> specification = createSpecification(criteria);
        return queryCommon50Repository.findAll(specification, page)
            .map(queryCommon50Mapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QueryCommon50Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QueryCommon50> specification = createSpecification(criteria);
        return queryCommon50Repository.count(specification);
    }

    /**
     * Function to convert QueryCommon50Criteria to a {@link Specification}
     */
    private Specification<QueryCommon50> createSpecification(QueryCommon50Criteria criteria) {
        Specification<QueryCommon50> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QueryCommon50_.id));
            }
            if (criteria.getQueryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQueryName(), QueryCommon50_.queryName));
            }
            if (criteria.getQueryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQueryDate(), QueryCommon50_.queryDate));
            }
            if (criteria.getQueryUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQueryUser(), QueryCommon50_.queryUser));
            }
            if (criteria.getCol01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol01(), QueryCommon50_.col01));
            }
            if (criteria.getCol02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol02(), QueryCommon50_.col02));
            }
            if (criteria.getCol03() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol03(), QueryCommon50_.col03));
            }
            if (criteria.getCol04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol04(), QueryCommon50_.col04));
            }
            if (criteria.getCol05() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol05(), QueryCommon50_.col05));
            }
            if (criteria.getCol06() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol06(), QueryCommon50_.col06));
            }
            if (criteria.getCol07() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol07(), QueryCommon50_.col07));
            }
            if (criteria.getCol08() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol08(), QueryCommon50_.col08));
            }
            if (criteria.getCol09() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol09(), QueryCommon50_.col09));
            }
            if (criteria.getCol10() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol10(), QueryCommon50_.col10));
            }
            if (criteria.getCol11() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol11(), QueryCommon50_.col11));
            }
            if (criteria.getCol12() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol12(), QueryCommon50_.col12));
            }
            if (criteria.getCol13() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol13(), QueryCommon50_.col13));
            }
            if (criteria.getCol14() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol14(), QueryCommon50_.col14));
            }
            if (criteria.getCol15() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol15(), QueryCommon50_.col15));
            }
            if (criteria.getCol16() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol16(), QueryCommon50_.col16));
            }
            if (criteria.getCol17() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol17(), QueryCommon50_.col17));
            }
            if (criteria.getCol18() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol18(), QueryCommon50_.col18));
            }
            if (criteria.getCol19() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol19(), QueryCommon50_.col19));
            }
            if (criteria.getCol20() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol20(), QueryCommon50_.col20));
            }
            if (criteria.getCol21() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol21(), QueryCommon50_.col21));
            }
            if (criteria.getCol22() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol22(), QueryCommon50_.col22));
            }
            if (criteria.getCol23() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol23(), QueryCommon50_.col23));
            }
            if (criteria.getCol24() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol24(), QueryCommon50_.col24));
            }
            if (criteria.getCol25() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol25(), QueryCommon50_.col25));
            }
            if (criteria.getCol26() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol26(), QueryCommon50_.col26));
            }
            if (criteria.getCol27() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol27(), QueryCommon50_.col27));
            }
            if (criteria.getCol28() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol28(), QueryCommon50_.col28));
            }
            if (criteria.getCol29() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol29(), QueryCommon50_.col29));
            }
            if (criteria.getCol30() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol30(), QueryCommon50_.col30));
            }
            if (criteria.getCol31() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol31(), QueryCommon50_.col31));
            }
            if (criteria.getCol32() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol32(), QueryCommon50_.col32));
            }
            if (criteria.getCol33() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol33(), QueryCommon50_.col33));
            }
            if (criteria.getCol34() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol34(), QueryCommon50_.col34));
            }
            if (criteria.getCol35() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol35(), QueryCommon50_.col35));
            }
            if (criteria.getCol36() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol36(), QueryCommon50_.col36));
            }
            if (criteria.getCol37() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol37(), QueryCommon50_.col37));
            }
            if (criteria.getCol38() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol38(), QueryCommon50_.col38));
            }
            if (criteria.getCol39() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol39(), QueryCommon50_.col39));
            }
            if (criteria.getCol40() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol40(), QueryCommon50_.col40));
            }
            if (criteria.getCol41() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol41(), QueryCommon50_.col41));
            }
            if (criteria.getCol42() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol42(), QueryCommon50_.col42));
            }
            if (criteria.getCol43() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol43(), QueryCommon50_.col43));
            }
            if (criteria.getCol44() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol44(), QueryCommon50_.col44));
            }
            if (criteria.getCol45() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol45(), QueryCommon50_.col45));
            }
            if (criteria.getCol46() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol46(), QueryCommon50_.col46));
            }
            if (criteria.getCol47() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol47(), QueryCommon50_.col47));
            }
            if (criteria.getCol48() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol48(), QueryCommon50_.col48));
            }
            if (criteria.getCol49() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol49(), QueryCommon50_.col49));
            }
            if (criteria.getCol50() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCol50(), QueryCommon50_.col50));
            }
        }
        return specification;
    }
}
