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

import com.aerothinker.kms.domain.ParaClass;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaClassRepository;
import com.aerothinker.kms.repository.search.ParaClassSearchRepository;
import com.aerothinker.kms.service.dto.ParaClassCriteria;
import com.aerothinker.kms.service.dto.ParaClassDTO;
import com.aerothinker.kms.service.mapper.ParaClassMapper;

/**
 * Service for executing complex queries for ParaClass entities in the database.
 * The main input is a {@link ParaClassCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaClassDTO} or a {@link Page} of {@link ParaClassDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaClassQueryService extends QueryService<ParaClass> {

    private final Logger log = LoggerFactory.getLogger(ParaClassQueryService.class);

    private final ParaClassRepository paraClassRepository;

    private final ParaClassMapper paraClassMapper;

    private final ParaClassSearchRepository paraClassSearchRepository;

    public ParaClassQueryService(ParaClassRepository paraClassRepository, ParaClassMapper paraClassMapper, ParaClassSearchRepository paraClassSearchRepository) {
        this.paraClassRepository = paraClassRepository;
        this.paraClassMapper = paraClassMapper;
        this.paraClassSearchRepository = paraClassSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaClassDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaClassDTO> findByCriteria(ParaClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaClass> specification = createSpecification(criteria);
        return paraClassMapper.toDto(paraClassRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaClassDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaClassDTO> findByCriteria(ParaClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaClass> specification = createSpecification(criteria);
        return paraClassRepository.findAll(specification, page)
            .map(paraClassMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaClassCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaClass> specification = createSpecification(criteria);
        return paraClassRepository.count(specification);
    }

    /**
     * Function to convert ParaClassCriteria to a {@link Specification}
     */
    private Specification<ParaClass> createSpecification(ParaClassCriteria criteria) {
        Specification<ParaClass> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaClass_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaClass_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaClass_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), ParaClass_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaClass_.descString));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), ParaClass_.imageBlobName));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaClass_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaClass_.remarks));
            }
            if (criteria.getValidType() != null) {
                specification = specification.and(buildSpecification(criteria.getValidType(), ParaClass_.validType));
            }
            if (criteria.getValidBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidBegin(), ParaClass_.validBegin));
            }
            if (criteria.getValidEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidEnd(), ParaClass_.validEnd));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), ParaClass_.createTime));
            }
            if (criteria.getModifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyTime(), ParaClass_.modifyTime));
            }
            if (criteria.getVerifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifyTime(), ParaClass_.verifyTime));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaClass_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaClass_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaClass_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ParaClass_.parent, JoinType.LEFT).get(ParaClass_.id)));
            }
        }
        return specification;
    }
}
