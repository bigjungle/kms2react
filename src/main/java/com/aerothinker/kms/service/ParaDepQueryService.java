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

import com.aerothinker.kms.domain.ParaDep;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaDepRepository;
import com.aerothinker.kms.repository.search.ParaDepSearchRepository;
import com.aerothinker.kms.service.dto.ParaDepCriteria;
import com.aerothinker.kms.service.dto.ParaDepDTO;
import com.aerothinker.kms.service.mapper.ParaDepMapper;

/**
 * Service for executing complex queries for ParaDep entities in the database.
 * The main input is a {@link ParaDepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaDepDTO} or a {@link Page} of {@link ParaDepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaDepQueryService extends QueryService<ParaDep> {

    private final Logger log = LoggerFactory.getLogger(ParaDepQueryService.class);

    private final ParaDepRepository paraDepRepository;

    private final ParaDepMapper paraDepMapper;

    private final ParaDepSearchRepository paraDepSearchRepository;

    public ParaDepQueryService(ParaDepRepository paraDepRepository, ParaDepMapper paraDepMapper, ParaDepSearchRepository paraDepSearchRepository) {
        this.paraDepRepository = paraDepRepository;
        this.paraDepMapper = paraDepMapper;
        this.paraDepSearchRepository = paraDepSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaDepDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaDepDTO> findByCriteria(ParaDepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaDep> specification = createSpecification(criteria);
        return paraDepMapper.toDto(paraDepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaDepDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaDepDTO> findByCriteria(ParaDepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaDep> specification = createSpecification(criteria);
        return paraDepRepository.findAll(specification, page)
            .map(paraDepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaDepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaDep> specification = createSpecification(criteria);
        return paraDepRepository.count(specification);
    }

    /**
     * Function to convert ParaDepCriteria to a {@link Specification}
     */
    private Specification<ParaDep> createSpecification(ParaDepCriteria criteria) {
        Specification<ParaDep> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaDep_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaDep_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaDep_.serialNumber));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaDep_.descString));
            }
            if (criteria.getTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTel(), ParaDep_.tel));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ParaDep_.address));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaDep_.remarks));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaDep_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaDep_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaDep_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ParaDep_.parent, JoinType.LEFT).get(ParaDep_.id)));
            }
        }
        return specification;
    }
}
