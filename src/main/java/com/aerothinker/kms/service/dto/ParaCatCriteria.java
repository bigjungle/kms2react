package com.aerothinker.kms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.aerothinker.kms.domain.enumeration.ValidType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the ParaCat entity. This class is used in ParaCatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /para-cats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParaCatCriteria implements Serializable {
    /**
     * Class for filtering ValidType
     */
    public static class ValidTypeFilter extends Filter<ValidType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter serialNumber;

    private StringFilter sortString;

    private StringFilter descString;

    private StringFilter imageBlobName;

    private BooleanFilter usingFlag;

    private StringFilter remarks;

    private ValidTypeFilter validType;

    private InstantFilter validBegin;

    private InstantFilter validEnd;

    private InstantFilter createTime;

    private InstantFilter modifyTime;

    private InstantFilter verifyTime;

    private LongFilter createdUserId;

    private LongFilter modifiedUserId;

    private LongFilter verifiedUserId;

    private LongFilter parentId;

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

    public StringFilter getImageBlobName() {
        return imageBlobName;
    }

    public void setImageBlobName(StringFilter imageBlobName) {
        this.imageBlobName = imageBlobName;
    }

    public BooleanFilter getUsingFlag() {
        return usingFlag;
    }

    public void setUsingFlag(BooleanFilter usingFlag) {
        this.usingFlag = usingFlag;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
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

    public LongFilter getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(LongFilter createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LongFilter getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(LongFilter modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public LongFilter getVerifiedUserId() {
        return verifiedUserId;
    }

    public void setVerifiedUserId(LongFilter verifiedUserId) {
        this.verifiedUserId = verifiedUserId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParaCatCriteria that = (ParaCatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(sortString, that.sortString) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(imageBlobName, that.imageBlobName) &&
            Objects.equals(usingFlag, that.usingFlag) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(validType, that.validType) &&
            Objects.equals(validBegin, that.validBegin) &&
            Objects.equals(validEnd, that.validEnd) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(modifyTime, that.modifyTime) &&
            Objects.equals(verifyTime, that.verifyTime) &&
            Objects.equals(createdUserId, that.createdUserId) &&
            Objects.equals(modifiedUserId, that.modifiedUserId) &&
            Objects.equals(verifiedUserId, that.verifiedUserId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        serialNumber,
        sortString,
        descString,
        imageBlobName,
        usingFlag,
        remarks,
        validType,
        validBegin,
        validEnd,
        createTime,
        modifyTime,
        verifyTime,
        createdUserId,
        modifiedUserId,
        verifiedUserId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "ParaCatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (sortString != null ? "sortString=" + sortString + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (imageBlobName != null ? "imageBlobName=" + imageBlobName + ", " : "") +
                (usingFlag != null ? "usingFlag=" + usingFlag + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (validType != null ? "validType=" + validType + ", " : "") +
                (validBegin != null ? "validBegin=" + validBegin + ", " : "") +
                (validEnd != null ? "validEnd=" + validEnd + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (modifyTime != null ? "modifyTime=" + modifyTime + ", " : "") +
                (verifyTime != null ? "verifyTime=" + verifyTime + ", " : "") +
                (createdUserId != null ? "createdUserId=" + createdUserId + ", " : "") +
                (modifiedUserId != null ? "modifiedUserId=" + modifiedUserId + ", " : "") +
                (verifiedUserId != null ? "verifiedUserId=" + verifiedUserId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
