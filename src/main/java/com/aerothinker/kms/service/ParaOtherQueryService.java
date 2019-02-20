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

import com.aerothinker.kms.domain.ParaOther;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.ParaOtherRepository;
import com.aerothinker.kms.repository.search.ParaOtherSearchRepository;
import com.aerothinker.kms.service.dto.ParaOtherCriteria;
import com.aerothinker.kms.service.dto.ParaOtherDTO;
import com.aerothinker.kms.service.mapper.ParaOtherMapper;

/**
 * Service for executing complex queries for ParaOther entities in the database.
 * The main input is a {@link ParaOtherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParaOtherDTO} or a {@link Page} of {@link ParaOtherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParaOtherQueryService extends QueryService<ParaOther> {

    private final Logger log = LoggerFactory.getLogger(ParaOtherQueryService.class);

    private final ParaOtherRepository paraOtherRepository;

    private final ParaOtherMapper paraOtherMapper;

    private final ParaOtherSearchRepository paraOtherSearchRepository;

    public ParaOtherQueryService(ParaOtherRepository paraOtherRepository, ParaOtherMapper paraOtherMapper, ParaOtherSearchRepository paraOtherSearchRepository) {
        this.paraOtherRepository = paraOtherRepository;
        this.paraOtherMapper = paraOtherMapper;
        this.paraOtherSearchRepository = paraOtherSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParaOtherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParaOtherDTO> findByCriteria(ParaOtherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParaOther> specification = createSpecification(criteria);
        return paraOtherMapper.toDto(paraOtherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParaOtherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParaOtherDTO> findByCriteria(ParaOtherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParaOther> specification = createSpecification(criteria);
        return paraOtherRepository.findAll(specification, page)
            .map(paraOtherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParaOtherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParaOther> specification = createSpecification(criteria);
        return paraOtherRepository.count(specification);
    }

    /**
     * Function to convert ParaOtherCriteria to a {@link Specification}
     */
    private Specification<ParaOther> createSpecification(ParaOtherCriteria criteria) {
        Specification<ParaOther> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParaOther_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ParaOther_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), ParaOther_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), ParaOther_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), ParaOther_.descString));
            }
            if (criteria.getParaOtherValueType() != null) {
                specification = specification.and(buildSpecification(criteria.getParaOtherValueType(), ParaOther_.paraOtherValueType));
            }
            if (criteria.getParaValueSt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParaValueSt(), ParaOther_.paraValueSt));
            }
            if (criteria.getParaValueIn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParaValueIn(), ParaOther_.paraValueIn));
            }
            if (criteria.getParaValueBo() != null) {
                specification = specification.and(buildSpecification(criteria.getParaValueBo(), ParaOther_.paraValueBo));
            }
            if (criteria.getParaValueJs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParaValueJs(), ParaOther_.paraValueJs));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), ParaOther_.imageBlobName));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), ParaOther_.usingFlag));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ParaOther_.remarks));
            }
            if (criteria.getValidType() != null) {
                specification = specification.and(buildSpecification(criteria.getValidType(), ParaOther_.validType));
            }
            if (criteria.getValidBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidBegin(), ParaOther_.validBegin));
            }
            if (criteria.getValidEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidEnd(), ParaOther_.validEnd));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), ParaOther_.createTime));
            }
            if (criteria.getModifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyTime(), ParaOther_.modifyTime));
            }
            if (criteria.getVerifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifyTime(), ParaOther_.verifyTime));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(ParaOther_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(ParaOther_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(ParaOther_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getKmsInfoId() != null) {
                specification = specification.and(buildSpecification(criteria.getKmsInfoId(),
                    root -> root.join(ParaOther_.kmsInfos, JoinType.LEFT).get(KmsInfo_.id)));
            }
        }
        return specification;
    }
}
