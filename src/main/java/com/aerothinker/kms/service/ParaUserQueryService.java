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

import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaUserRepository;
import com.aerothinker.kms.repository.search.ParaUserSearchRepository;
import com.aerothinker.kms.service.dto.ParaUserCriteria;
import com.aerothinker.kms.service.dto.ParaUserDTO;
import com.aerothinker.kms.service.mapper.ParaUserMapper;

/**
 * Service for executing complex queries for ParaUser entities in the database.
 * The main input is a {@link ParaUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaUserDTO} or a {@link Page} of {@link ParaUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaUserQueryService extends QueryService<ParaUser> {

    private final Logger log = LoggerFactory.getLogger(ParaUserQueryService.class);

    private final ParaUserRepository paraUserRepository;

    private final ParaUserMapper paraUserMapper;

    private final ParaUserSearchRepository paraUserSearchRepository;

    public ParaUserQueryService(ParaUserRepository paraUserRepository, ParaUserMapper paraUserMapper, ParaUserSearchRepository paraUserSearchRepository) {
        this.paraUserRepository = paraUserRepository;
        this.paraUserMapper = paraUserMapper;
        this.paraUserSearchRepository = paraUserSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaUserDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaUserDTO> findByCriteria(ParaUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaUser> specification = createSpecification(criteria);
        return paraUserMapper.toDto(paraUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaUserDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaUserDTO> findByCriteria(ParaUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaUser> specification = createSpecification(criteria);
        return paraUserRepository.findAll(specification, page)
            .map(paraUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaUser> specification = createSpecification(criteria);
        return paraUserRepository.count(specification);
    }

    /**
     * Function to convert ParaUserCriteria to a {@link Specification}
     */
    private Specification<ParaUser> createSpecification(ParaUserCriteria criteria) {
        Specification<ParaUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaUser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), ParaUser_.userId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaUser_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaUser_.serialNumber));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaUser_.descString));
            }
            if (criteria.getJobId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobId(), ParaUser_.jobId));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ParaUser_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ParaUser_.lastName));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), ParaUser_.mobile));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), ParaUser_.mail));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaUser_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaUser_.remarks));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaUser_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaUser_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaUser_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getParaRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaRoleId(),
                    root -> root.join(ParaUser_.paraRoles, JoinType.LEFT).get(ParaRole_.id)));
            }
        }
        return specification;
    }
}
