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

import com.aerothinker.kms.domain.ParaState;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaStateRepository;
import com.aerothinker.kms.repository.search.ParaStateSearchRepository;
import com.aerothinker.kms.service.dto.ParaStateCriteria;
import com.aerothinker.kms.service.dto.ParaStateDTO;
import com.aerothinker.kms.service.mapper.ParaStateMapper;

/**
 * Service for executing complex queries for ParaState entities in the database.
 * The main input is a {@link ParaStateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaStateDTO} or a {@link Page} of {@link ParaStateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaStateQueryService extends QueryService<ParaState> {

    private final Logger log = LoggerFactory.getLogger(ParaStateQueryService.class);

    private final ParaStateRepository paraStateRepository;

    private final ParaStateMapper paraStateMapper;

    private final ParaStateSearchRepository paraStateSearchRepository;

    public ParaStateQueryService(ParaStateRepository paraStateRepository, ParaStateMapper paraStateMapper, ParaStateSearchRepository paraStateSearchRepository) {
        this.paraStateRepository = paraStateRepository;
        this.paraStateMapper = paraStateMapper;
        this.paraStateSearchRepository = paraStateSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaStateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaStateDTO> findByCriteria(ParaStateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaState> specification = createSpecification(criteria);
        return paraStateMapper.toDto(paraStateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaStateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaStateDTO> findByCriteria(ParaStateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaState> specification = createSpecification(criteria);
        return paraStateRepository.findAll(specification, page)
            .map(paraStateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaStateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaState> specification = createSpecification(criteria);
        return paraStateRepository.count(specification);
    }

    /**
     * Function to convert ParaStateCriteria to a {@link Specification}
     */
    private Specification<ParaState> createSpecification(ParaStateCriteria criteria) {
        Specification<ParaState> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaState_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaState_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaState_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), ParaState_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaState_.descString));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), ParaState_.imageBlobName));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaState_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaState_.remarks));
            }
            if (criteria.getValidType() != null) {
                specification = specification.and(buildSpecification(criteria.getValidType(), ParaState_.validType));
            }
            if (criteria.getValidBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidBegin(), ParaState_.validBegin));
            }
            if (criteria.getValidEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidEnd(), ParaState_.validEnd));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), ParaState_.createTime));
            }
            if (criteria.getModifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyTime(), ParaState_.modifyTime));
            }
            if (criteria.getVerifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifyTime(), ParaState_.verifyTime));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaState_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaState_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaState_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ParaState_.parent, JoinType.LEFT).get(ParaState_.id)));
            }
        }
        return specification;
    }
}
