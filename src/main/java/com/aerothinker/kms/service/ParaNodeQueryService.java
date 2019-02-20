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

import com.aerothinker.kms.domain.ParaNode;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaNodeRepository;
import com.aerothinker.kms.repository.search.ParaNodeSearchRepository;
import com.aerothinker.kms.service.dto.ParaNodeCriteria;
import com.aerothinker.kms.service.dto.ParaNodeDTO;
import com.aerothinker.kms.service.mapper.ParaNodeMapper;

/**
 * Service for executing complex queries for ParaNode entities in the database.
 * The main input is a {@link ParaNodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaNodeDTO} or a {@link Page} of {@link ParaNodeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaNodeQueryService extends QueryService<ParaNode> {

    private final Logger log = LoggerFactory.getLogger(ParaNodeQueryService.class);

    private final ParaNodeRepository paraNodeRepository;

    private final ParaNodeMapper paraNodeMapper;

    private final ParaNodeSearchRepository paraNodeSearchRepository;

    public ParaNodeQueryService(ParaNodeRepository paraNodeRepository, ParaNodeMapper paraNodeMapper, ParaNodeSearchRepository paraNodeSearchRepository) {
        this.paraNodeRepository = paraNodeRepository;
        this.paraNodeMapper = paraNodeMapper;
        this.paraNodeSearchRepository = paraNodeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaNodeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaNodeDTO> findByCriteria(ParaNodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaNode> specification = createSpecification(criteria);
        return paraNodeMapper.toDto(paraNodeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaNodeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaNodeDTO> findByCriteria(ParaNodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaNode> specification = createSpecification(criteria);
        return paraNodeRepository.findAll(specification, page)
            .map(paraNodeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaNodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaNode> specification = createSpecification(criteria);
        return paraNodeRepository.count(specification);
    }

    /**
     * Function to convert ParaNodeCriteria to a {@link Specification}
     */
    private Specification<ParaNode> createSpecification(ParaNodeCriteria criteria) {
        Specification<ParaNode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaNode_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaNode_.name));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), ParaNode_.link));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaNode_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), ParaNode_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaNode_.descString));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), ParaNode_.imageBlobName));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaNode_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaNode_.remarks));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaNode_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaNode_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaNode_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ParaNode_.parent, JoinType.LEFT).get(ParaNode_.id)));
            }
            if (criteria.getParaRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaRoleId(),
                    root -> root.join(ParaNode_.paraRoles, JoinType.LEFT).get(ParaRole_.id)));
            }
        }
        return specification;
    }
}
