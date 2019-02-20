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

import com.aerothinker.kms.domain.KmsInfo;
import com.aerothinker.kms.domain.*; // for static metamodels
import com.aerothinker.kms.repository.KmsInfoRepository;
import com.aerothinker.kms.repository.search.KmsInfoSearchRepository;
import com.aerothinker.kms.service.dto.KmsInfoCriteria;
import com.aerothinker.kms.service.dto.KmsInfoDTO;
import com.aerothinker.kms.service.mapper.KmsInfoMapper;

/**
 * Service for executing complex queries for KmsInfo entities in the database.
 * The main input is a {@link KmsInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmsInfoDTO} or a {@link Page} of {@link KmsInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmsInfoQueryService extends QueryService<KmsInfo> {

    private final Logger log = LoggerFactory.getLogger(KmsInfoQueryService.class);

    private final KmsInfoRepository kmsInfoRepository;

    private final KmsInfoMapper kmsInfoMapper;

    private final KmsInfoSearchRepository kmsInfoSearchRepository;

    public KmsInfoQueryService(KmsInfoRepository kmsInfoRepository, KmsInfoMapper kmsInfoMapper, KmsInfoSearchRepository kmsInfoSearchRepository) {
        this.kmsInfoRepository = kmsInfoRepository;
        this.kmsInfoMapper = kmsInfoMapper;
        this.kmsInfoSearchRepository = kmsInfoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link KmsInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmsInfoDTO> findByCriteria(KmsInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmsInfo> specification = createSpecification(criteria);
        return kmsInfoMapper.toDto(kmsInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link KmsInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmsInfoDTO> findByCriteria(KmsInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmsInfo> specification = createSpecification(criteria);
        return kmsInfoRepository.findAll(specification, page)
            .map(kmsInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmsInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmsInfo> specification = createSpecification(criteria);
        return kmsInfoRepository.count(specification);
    }

    /**
     * Function to convert KmsInfoCriteria to a {@link Specification}
     */
    private Specification<KmsInfo> createSpecification(KmsInfoCriteria criteria) {
        Specification<KmsInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), KmsInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), KmsInfo_.name));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), KmsInfo_.serialNumber));
            }
            if (criteria.getSortString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortString(), KmsInfo_.sortString));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), KmsInfo_.descString));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), KmsInfo_.answer));
            }
            if (criteria.getUsingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getUsingFlag(), KmsInfo_.usingFlag));
            }
            if (criteria.getVersionNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVersionNumber(), KmsInfo_.versionNumber));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), KmsInfo_.remarks));
            }
            if (criteria.getAttachmentPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachmentPath(), KmsInfo_.attachmentPath));
            }
            if (criteria.getAttachmentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachmentName(), KmsInfo_.attachmentName));
            }
            if (criteria.getImageBlobName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageBlobName(), KmsInfo_.imageBlobName));
            }
            if (criteria.getValidType() != null) {
                specification = specification.and(buildSpecification(criteria.getValidType(), KmsInfo_.validType));
            }
            if (criteria.getValidBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidBegin(), KmsInfo_.validBegin));
            }
            if (criteria.getValidEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidEnd(), KmsInfo_.validEnd));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), KmsInfo_.createTime));
            }
            if (criteria.getModifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyTime(), KmsInfo_.modifyTime));
            }
            if (criteria.getVerifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifyTime(), KmsInfo_.verifyTime));
            }
            if (criteria.getPublishedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishedTime(), KmsInfo_.publishedTime));
            }
            if (criteria.getVerifyNeed() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifyNeed(), KmsInfo_.verifyNeed));
            }
            if (criteria.getMarkAsVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkAsVerified(), KmsInfo_.markAsVerified));
            }
            if (criteria.getVerifyResult() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifyResult(), KmsInfo_.verifyResult));
            }
            if (criteria.getVerifyOpinion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerifyOpinion(), KmsInfo_.verifyOpinion));
            }
            if (criteria.getViewCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getViewCount(), KmsInfo_.viewCount));
            }
            if (criteria.getViewPermission() != null) {
                specification = specification.and(buildSpecification(criteria.getViewPermission(), KmsInfo_.viewPermission));
            }
            if (criteria.getViewPermPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getViewPermPerson(), KmsInfo_.viewPermPerson));
            }
            if (criteria.getParaSourceString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParaSourceString(), KmsInfo_.paraSourceString));
            }
            if (criteria.getVerifyRecId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifyRecId(),
                    root -> root.join(KmsInfo_.verifyRec, JoinType.LEFT).get(VerifyRec_.id)));
            }
            if (criteria.getParaTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaTypeId(),
                    root -> root.join(KmsInfo_.paraType, JoinType.LEFT).get(ParaType_.id)));
            }
            if (criteria.getParaClassId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaClassId(),
                    root -> root.join(KmsInfo_.paraClass, JoinType.LEFT).get(ParaClass_.id)));
            }
            if (criteria.getParaCatId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaCatId(),
                    root -> root.join(KmsInfo_.paraCat, JoinType.LEFT).get(ParaCat_.id)));
            }
            if (criteria.getParaStateId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaStateId(),
                    root -> root.join(KmsInfo_.paraState, JoinType.LEFT).get(ParaState_.id)));
            }
            if (criteria.getParaSourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaSourceId(),
                    root -> root.join(KmsInfo_.paraSource, JoinType.LEFT).get(ParaSource_.id)));
            }
            if (criteria.getParaAttrId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaAttrId(),
                    root -> root.join(KmsInfo_.paraAttr, JoinType.LEFT).get(ParaAttr_.id)));
            }
            if (criteria.getParaPropId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaPropId(),
                    root -> root.join(KmsInfo_.paraProp, JoinType.LEFT).get(ParaProp_.id)));
            }
            if (criteria.getCreatedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedUserId(),
                    root -> root.join(KmsInfo_.createdUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getCreatedDepById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedDepById(),
                    root -> root.join(KmsInfo_.createdDepBy, JoinType.LEFT).get(ParaDep_.id)));
            }
            if (criteria.getOwnerById() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerById(),
                    root -> root.join(KmsInfo_.ownerBy, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getOwnerDepById() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerDepById(),
                    root -> root.join(KmsInfo_.ownerDepBy, JoinType.LEFT).get(ParaDep_.id)));
            }
            if (criteria.getModifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserId(),
                    root -> root.join(KmsInfo_.modifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getModifiedUserDepId() != null) {
                specification = specification.and(buildSpecification(criteria.getModifiedUserDepId(),
                    root -> root.join(KmsInfo_.modifiedUserDep, JoinType.LEFT).get(ParaDep_.id)));
            }
            if (criteria.getVerifiedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedUserId(),
                    root -> root.join(KmsInfo_.verifiedUser, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getVerifiedDepById() != null) {
                specification = specification.and(buildSpecification(criteria.getVerifiedDepById(),
                    root -> root.join(KmsInfo_.verifiedDepBy, JoinType.LEFT).get(ParaDep_.id)));
            }
            if (criteria.getPublishedById() != null) {
                specification = specification.and(buildSpecification(criteria.getPublishedById(),
                    root -> root.join(KmsInfo_.publishedBy, JoinType.LEFT).get(ParaUser_.id)));
            }
            if (criteria.getPublishedDepById() != null) {
                specification = specification.and(buildSpecification(criteria.getPublishedDepById(),
                    root -> root.join(KmsInfo_.publishedDepBy, JoinType.LEFT).get(ParaDep_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(KmsInfo_.parent, JoinType.LEFT).get(KmsInfo_.id)));
            }
            if (criteria.getParaOtherId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaOtherId(),
                    root -> root.join(KmsInfo_.paraOthers, JoinType.LEFT).get(ParaOther_.id)));
            }
        }
        return specification;
    }
}
