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

import com.aerothinker.kms.domain.ParaSource;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaSourceRepository;
import com.aerothinker.kms.repository.search.ParaSourceSearchRepository;
import com.aerothinker.kms.service.dto.ParaSourceCriteria;
import com.aerothinker.kms.service.dto.ParaSourceDTO;
import com.aerothinker.kms.service.mapper.ParaSourceMapper;

/**
 * Service for executing complex queries for ParaSource entities in the database.
 * The main input is a {@link ParaSourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaSourceDTO} or a {@link Page} of {@link ParaSourceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaSourceQueryService extends QueryService<ParaSource> {

    private final Logger log = LoggerFactory.getLogger(ParaSourceQueryService.class);

    private final ParaSourceRepository paraSourceRepository;

    private final ParaSourceMapper paraSourceMapper;

    private final ParaSourceSearchRepository paraSourceSearchRepository;

    public ParaSourceQueryService(ParaSourceRepository paraSourceRepository, ParaSourceMapper paraSourceMapper, ParaSourceSearchRepository paraSourceSearchRepository) {
        this.paraSourceRepository = paraSourceRepository;
        this.paraSourceMapper = paraSourceMapper;
        this.paraSourceSearchRepository = paraSourceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaSourceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaSourceDTO> findByCriteria(ParaSourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaSource> specification = createSpecification(criteria);
        return paraSourceMapper.toDto(paraSourceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaSourceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaSourceDTO> findByCriteria(ParaSourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaSource> specification = createSpecification(criteria);
        return paraSourceRepository.findAll(specification, page)
            .map(paraSourceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaSourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaSource> specification = createSpecification(criteria);
        return paraSourceRepository.count(specification);
    }

    /**
     * Function to convert ParaSourceCriteria to a {@link Specification}
     */
    private Specification<ParaSource> createSpecification(ParaSourceCriteria criteria) {
        Specification<ParaSource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaSource_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaSource_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaSource_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), ParaSource_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaSource_.descString));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), ParaSource_.imageBlobName));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaSource_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaSource_.remarks));
            }
            if (criteria.getValidType() != null) {
                specification = specification.and(buildSpecification(criteria.getValidType(), ParaSource_.validType));
            }
            if (criteria.getValidBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidBegin(), ParaSource_.validBegin));
            }
            if (criteria.getValidEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidEnd(), ParaSource_.validEnd));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), ParaSource_.createTime));
            }
            if (criteria.getModifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyTime(), ParaSource_.modifyTime));
            }
            if (criteria.getVerifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifyTime(), ParaSource_.verifyTime));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaSource_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaSource_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaSource_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ParaSource_.parent, JoinType.LEFT).get(ParaSource_.id)));
            }
        }
        return specification;
    }
}
