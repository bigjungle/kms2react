package com.aerothinker.kms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.aerothinker.kms.domain.enumeration.ValidType;
import com.aerothinker.kms.domain.enumeration.ViewPermission;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the KmsInfo entity. This class is used in KmsInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /kms-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KmsInfoCriteria implements Serializable {
    /**
     * Class for filtering ValidType
     */
    public static class ValidTypeFilter extends Filter<ValidType> {
    }
    /**
     * Class for filtering ViewPermission
     */
    public static class ViewPermissionFilter extends Filter<ViewPermission> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter serialNumber;

    private StringFilter sortString;

    private StringFilter descString;

    private StringFilter answer;

    private BooleanFilter usingFlag;

    private StringFilter versionNumber;

    private StringFilter remarks;

    private StringFilter attachmentPath;

    private StringFilter attachmentName;

    private StringFilter imageBlobName;

    private ValidTypeFilter validType;

    private InstantFilter validBegin;

    private InstantFilter validEnd;

    private InstantFilter createTime;

    private InstantFilter modifyTime;

    private InstantFilter verifyTime;

    private InstantFilter publishedTime;

    private BooleanFilter verifyNeed;

    private BooleanFilter markAsVerified;

    private BooleanFilter verifyResult;

    private StringFilter verifyOpinion;

    private IntegerFilter viewCount;

    private ViewPermissionFilter viewPermission;

    private StringFilter viewPermPerson;

    private StringFilter paraSourceString;

    private LongFilter verifyRecId;

    private LongFilter paraTypeId;

    private LongFilter paraClassId;

    private LongFilter paraCatId;

    private LongFilter paraStateId;

    private LongFilter paraSourceId;

    private LongFilter paraAttrId;

    private LongFilter paraPropId;

    private LongFilter createdUserId;

    private LongFilter createdDepById;

    private LongFilter ownerById;

    private LongFilter ownerDepById;

    private LongFilter modifiedUserId;

    private LongFilter modifiedUserDepId;

    private LongFilter verifiedUserId;

    private LongFilter verifiedDepById;

    private LongFilter publishedById;

    private LongFilter publishedDepById;

    private LongFilter parentId;

    private LongFilter paraOtherId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public StringFilter getSortString() {
        return sortString;
    }

    public void setSortString(StringFilter sortString) {
        this.sortString = sortString;
    }

    public StringFilter getDescString() {
        return descString;
    }

    public void setDescString(StringFilter descString) {
        this.descString = descString;
    }

    public StringFilter getAnswer() {
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
    }

    public BooleanFilter getUsingFlag() {
        return usingFlag;
    }

    public void setUsingFlag(BooleanFilter usingFlag) {
        this.usingFlag = usingFlag;
    }

    public StringFilter getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(StringFilter versionNumber) {
        this.versionNumber = versionNumber;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public StringFilter getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(StringFilter attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public StringFilter getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(StringFilter attachmentName) {
        this.attachmentName = attachmentName;
    }

    public StringFilter getImageBlobName() {
        return imageBlobName;
    }

    public void setImageBlobName(StringFilter imageBlobName) {
        this.imageBlobName = imageBlobName;
    }

    public ValidTypeFilter getValidType() {
        return validType;
    }

    public void setValidType(ValidTypeFilter validType) {
        this.validType = validType;
    }

    public InstantFilter getValidBegin() {
        return validBegin;
    }

    public void setValidBegin(InstantFilter validBegin) {
        this.validBegin = validBegin;
    }

    public InstantFilter getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(InstantFilter validEnd) {
        this.validEnd = validEnd;
    }

    public InstantFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(InstantFilter createTime) {
        this.createTime = createTime;
    }

    public InstantFilter getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(InstantFilter modifyTime) {
        this.modifyTime = modifyTime;
    }

    public InstantFilter getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(InstantFilter verifyTime) {
        this.verifyTime = verifyTime;
    }

    public InstantFilter getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(InstantFilter publishedTime) {
        this.publishedTime = publishedTime;
    }

    public BooleanFilter getVerifyNeed() {
        return verifyNeed;
    }

    public void setVerifyNeed(BooleanFilter verifyNeed) {
        this.verifyNeed = verifyNeed;
    }

    public BooleanFilter getMarkAsVerified() {
        return markAsVerified;
    }

    public void setMarkAsVerified(BooleanFilter markAsVerified) {
        this.markAsVerified = markAsVerified;
    }

    public BooleanFilter getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(BooleanFilter verifyResult) {
        this.verifyResult = verifyResult;
    }

    public StringFilter getVerifyOpinion() {
        return verifyOpinion;
    }

    public void setVerifyOpinion(StringFilter verifyOpinion) {
        this.verifyOpinion = verifyOpinion;
    }

    public IntegerFilter getViewCount() {
        return viewCount;
    }

    public void setViewCount(IntegerFilter viewCount) {
        this.viewCount = viewCount;
    }

    public ViewPermissionFilter getViewPermission() {
        return viewPermission;
    }

    public void setViewPermission(ViewPermissionFilter viewPermission) {
        this.viewPermission = viewPermission;
    }

    public StringFilter getViewPermPerson() {
        return viewPermPerson;
    }

    public void setViewPermPerson(StringFilter viewPermPerson) {
        this.viewPermPerson = viewPermPerson;
    }

    public StringFilter getParaSourceString() {
        return paraSourceString;
    }

    public void setParaSourceString(StringFilter paraSourceString) {
        this.paraSourceString = paraSourceString;
    }

    public LongFilter getVerifyRecId() {
        return verifyRecId;
    }

    public void setVerifyRecId(LongFilter verifyRecId) {
        this.verifyRecId = verifyRecId;
    }

    public LongFilter getParaTypeId() {
        return paraTypeId;
    }

    public void setParaTypeId(LongFilter paraTypeId) {
        this.paraTypeId = paraTypeId;
    }

    public LongFilter getParaClassId() {
        return paraClassId;
    }

    public void setParaClassId(LongFilter paraClassId) {
        this.paraClassId = paraClassId;
    }

    public LongFilter getParaCatId() {
        return paraCatId;
    }

    public void setParaCatId(LongFilter paraCatId) {
        this.paraCatId = paraCatId;
    }

    public LongFilter getParaStateId() {
        return paraStateId;
    }

    public void setParaStateId(LongFilter paraStateId) {
        this.paraStateId = paraStateId;
    }

    public LongFilter getParaSourceId() {
        return paraSourceId;
    }

    public void setParaSourceId(LongFilter paraSourceId) {
        this.paraSourceId = paraSourceId;
    }

    public LongFilter getParaAttrId() {
        return paraAttrId;
    }

    public void setParaAttrId(LongFilter paraAttrId) {
        this.paraAttrId = paraAttrId;
    }

    public LongFilter getParaPropId() {
        return paraPropId;
    }

    public void setParaPropId(LongFilter paraPropId) {
        this.paraPropId = paraPropId;
    }

    public LongFilter getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(LongFilter createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LongFilter getCreatedDepById() {
        return createdDepById;
    }

    public void setCreatedDepById(LongFilter createdDepById) {
        this.createdDepById = createdDepById;
    }

    public LongFilter getOwnerById() {
        return ownerById;
    }

    public void setOwnerById(LongFilter ownerById) {
        this.ownerById = ownerById;
    }

    public LongFilter getOwnerDepById() {
        return ownerDepById;
    }

    public void setOwnerDepById(LongFilter ownerDepById) {
        this.ownerDepById = ownerDepById;
    }

    public LongFilter getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(LongFilter modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public LongFilter getModifiedUserDepId() {
        return modifiedUserDepId;
    }

    public void setModifiedUserDepId(LongFilter modifiedUserDepId) {
        this.modifiedUserDepId = modifiedUserDepId;
    }

    public LongFilter getVerifiedUserId() {
        return verifiedUserId;
    }

    public void setVerifiedUserId(LongFilter verifiedUserId) {
        this.verifiedUserId = verifiedUserId;
    }

    public LongFilter getVerifiedDepById() {
        return verifiedDepById;
    }

    public void setVerifiedDepById(LongFilter verifiedDepById) {
        this.verifiedDepById = verifiedDepById;
    }

    public LongFilter getPublishedById() {
        return publishedById;
    }

    public void setPublishedById(LongFilter publishedById) {
        this.publishedById = publishedById;
    }

    public LongFilter getPublishedDepById() {
        return publishedDepById;
    }

    public void setPublishedDepById(LongFilter publishedDepById) {
        this.publishedDepById = publishedDepById;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getParaOtherId() {
        return paraOtherId;
    }

    public void setParaOtherId(LongFilter paraOtherId) {
        this.paraOtherId = paraOtherId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KmsInfoCriteria that = (KmsInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(sortString, that.sortString) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(usingFlag, that.usingFlag) &&
            Objects.equals(versionNumber, that.versionNumber) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(attachmentPath, that.attachmentPath) &&
            Objects.equals(attachmentName, that.attachmentName) &&
            Objects.equals(imageBlobName, that.imageBlobName) &&
            Objects.equals(validType, that.validType) &&
            Objects.equals(validBegin, that.validBegin) &&
            Objects.equals(validEnd, that.validEnd) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(modifyTime, that.modifyTime) &&
            Objects.equals(verifyTime, that.verifyTime) &&
            Objects.equals(publishedTime, that.publishedTime) &&
            Objects.equals(verifyNeed, that.verifyNeed) &&
            Objects.equals(markAsVerified, that.markAsVerified) &&
            Objects.equals(verifyResult, that.verifyResult) &&
            Objects.equals(verifyOpinion, that.verifyOpinion) &&
            Objects.equals(viewCount, that.viewCount) &&
            Objects.equals(viewPermission, that.viewPermission) &&
            Objects.equals(viewPermPerson, that.viewPermPerson) &&
            Objects.equals(paraSourceString, that.paraSourceString) &&
            Objects.equals(verifyRecId, that.verifyRecId) &&
            Objects.equals(paraTypeId, that.paraTypeId) &&
            Objects.equals(paraClassId, that.paraClassId) &&
            Objects.equals(paraCatId, that.paraCatId) &&
            Objects.equals(paraStateId, that.paraStateId) &&
            Objects.equals(paraSourceId, that.paraSourceId) &&
            Objects.equals(paraAttrId, that.paraAttrId) &&
            Objects.equals(paraPropId, that.paraPropId) &&
            Objects.equals(createdUserId, that.createdUserId) &&
            Objects.equals(createdDepById, that.createdDepById) &&
            Objects.equals(ownerById, that.ownerById) &&
            Objects.equals(ownerDepById, that.ownerDepById) &&
            Objects.equals(modifiedUserId, that.modifiedUserId) &&
            Objects.equals(modifiedUserDepId, that.modifiedUserDepId) &&
            Objects.equals(verifiedUserId, that.verifiedUserId) &&
            Objects.equals(verifiedDepById, that.verifiedDepById) &&
            Objects.equals(publishedById, that.publishedById) &&
            Objects.equals(publishedDepById, that.publishedDepById) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(paraOtherId, that.paraOtherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        serialNumber,
        sortString,
        descString,
        answer,
        usingFlag,
        versionNumber,
        remarks,
        attachmentPath,
        attachmentName,
        imageBlobName,
        validType,
        validBegin,
        validEnd,
        createTime,
        modifyTime,
        verifyTime,
        publishedTime,
        verifyNeed,
        markAsVerified,
        verifyResult,
        verifyOpinion,
        viewCount,
        viewPermission,
        viewPermPerson,
        paraSourceString,
        verifyRecId,
        paraTypeId,
        paraClassId,
        paraCatId,
        paraStateId,
        paraSourceId,
        paraAttrId,
        paraPropId,
        createdUserId,
        createdDepById,
        ownerById,
        ownerDepById,
        modifiedUserId,
        modifiedUserDepId,
        verifiedUserId,
        verifiedDepById,
        publishedById,
        publishedDepById,
        parentId,
        paraOtherId
        );
    }

    @Override
    public String toString() {
        return "KmsInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (sortString != null ? "sortString=" + sortString + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (usingFlag != null ? "usingFlag=" + usingFlag + ", " : "") +
                (versionNumber != null ? "versionNumber=" + versionNumber + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (attachmentPath != null ? "attachmentPath=" + attachmentPath + ", " : "") +
                (attachmentName != null ? "attachmentName=" + attachmentName + ", " : "") +
                (imageBlobName != null ? "imageBlobName=" + imageBlobName + ", " : "") +
                (validType != null ? "validType=" + validType + ", " : "") +
                (validBegin != null ? "validBegin=" + validBegin + ", " : "") +
                (validEnd != null ? "validEnd=" + validEnd + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (modifyTime != null ? "modifyTime=" + modifyTime + ", " : "") +
                (verifyTime != null ? "verifyTime=" + verifyTime + ", " : "") +
                (publishedTime != null ? "publishedTime=" + publishedTime + ", " : "") +
                (verifyNeed != null ? "verifyNeed=" + verifyNeed + ", " : "") +
                (markAsVerified != null ? "markAsVerified=" + markAsVerified + ", " : "") +
                (verifyResult != null ? "verifyResult=" + verifyResult + ", " : "") +
                (verifyOpinion != null ? "verifyOpinion=" + verifyOpinion + ", " : "") +
                (viewCount != null ? "viewCount=" + viewCount + ", " : "") +
                (viewPermission != null ? "viewPermission=" + viewPermission + ", " : "") +
                (viewPermPerson != null ? "viewPermPerson=" + viewPermPerson + ", " : "") +
                (paraSourceString != null ? "paraSourceString=" + paraSourceString + ", " : "") +
                (verifyRecId != null ? "verifyRecId=" + verifyRecId + ", " : "") +
                (paraTypeId != null ? "paraTypeId=" + paraTypeId + ", " : "") +
                (paraClassId != null ? "paraClassId=" + paraClassId + ", " : "") +
                (paraCatId != null ? "paraCatId=" + paraCatId + ", " : "") +
                (paraStateId != null ? "paraStateId=" + paraStateId + ", " : "") +
                (paraSourceId != null ? "paraSourceId=" + paraSourceId + ", " : "") +
                (paraAttrId != null ? "paraAttrId=" + paraAttrId + ", " : "") +
                (paraPropId != null ? "paraPropId=" + paraPropId + ", " : "") +
                (createdUserId != null ? "createdUserId=" + createdUserId + ", " : "") +
                (createdDepById != null ? "createdDepById=" + createdDepById + ", " : "") +
                (ownerById != null ? "ownerById=" + ownerById + ", " : "") +
                (ownerDepById != null ? "ownerDepById=" + ownerDepById + ", " : "") +
                (modifiedUserId != null ? "modifiedUserId=" + modifiedUserId + ", " : "") +
                (modifiedUserDepId != null ? "modifiedUserDepId=" + modifiedUserDepId + ", " : "") +
                (verifiedUserId != null ? "verifiedUserId=" + verifiedUserId + ", " : "") +
                (verifiedDepById != null ? "verifiedDepById=" + verifiedDepById + ", " : "") +
                (publishedById != null ? "publishedById=" + publishedById + ", " : "") +
                (publishedDepById != null ? "publishedDepById=" + publishedDepById + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (paraOtherId != null ? "paraOtherId=" + paraOtherId + ", " : "") +
            "}";
    }

}
