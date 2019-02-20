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

import com.aerothinker.kms.domain.ParaRole;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaRoleRepository;
import com.aerothinker.kms.repository.search.ParaRoleSearchRepository;
import com.aerothinker.kms.service.dto.ParaRoleCriteria;
import com.aerothinker.kms.service.dto.ParaRoleDTO;
import com.aerothinker.kms.service.mapper.ParaRoleMapper;

/**
 * Service for executing complex queries for ParaRole entities in the database.
 * The main input is a {@link ParaRoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaRoleDTO} or a {@link Page} of {@link ParaRoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaRoleQueryService extends QueryService<ParaRole> {

    private final Logger log = LoggerFactory.getLogger(ParaRoleQueryService.class);

    private final ParaRoleRepository paraRoleRepository;

    private final ParaRoleMapper paraRoleMapper;

    private final ParaRoleSearchRepository paraRoleSearchRepository;

    public ParaRoleQueryService(ParaRoleRepository paraRoleRepository, ParaRoleMapper paraRoleMapper, ParaRoleSearchRepository paraRoleSearchRepository) {
        this.paraRoleRepository = paraRoleRepository;
        this.paraRoleMapper = paraRoleMapper;
        this.paraRoleSearchRepository = paraRoleSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaRoleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaRoleDTO> findByCriteria(ParaRoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaRole> specification = createSpecification(criteria);
        return paraRoleMapper.toDto(paraRoleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaRoleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaRoleDTO> findByCriteria(ParaRoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaRole> specification = createSpecification(criteria);
        return paraRoleRepository.findAll(specification, page)
            .map(paraRoleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaRoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaRole> specification = createSpecification(criteria);
        return paraRoleRepository.count(specification);
    }

    /**
     * Function to convert ParaRoleCriteria to a {@link Specification}
     */
    private Specification<ParaRole> createSpecification(ParaRoleCriteria criteria) {
        Specification<ParaRole> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaRole_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaRole_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaRole_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), ParaRole_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaRole_.descString));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), ParaRole_.imageBlobName));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaRole_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaRole_.remarks));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaRole_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaRole_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaRole_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ParaRole_.parent, JoinType.LEFT).get(ParaRole_.id)));
            }
            if (criteria.getParaNodeId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaNodeId(),
                    root -> root.join(ParaRole_.paraNodes, JoinType.LEFT).get(ParaNode_.id)));
            }
            if (criteria.getParaUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaUserId(),
                    root -> root.join(ParaRole_.paraUsers, JoinType.LEFT).get(ParaUser_.id)));
            }
        }
        return specification;
    }
}
