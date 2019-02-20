package com.aerothinker.kms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the ParaRole entity. This class is used in ParaRoleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /para-roles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParaRoleCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter serialNumber;

    private StringFilter sortString;

    private StringFilter descString;

    private StringFilter imageBlobName;

    private BooleanFilter usingFlag;

    private StringFilter remarks;

    private LongFilter createdUserId;

    private LongFilter modifiedUserId;

    private LongFilter verifiedUserId;

    private LongFilter parentId;

    private LongFilter paraNodeId;

    private LongFilter paraUserId;

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

    public LongFilter getParaNodeId() {
        return paraNodeId;
    }

    public void setParaNodeId(LongFilter paraNodeId) {
        this.paraNodeId = paraNodeId;
    }

    public LongFilter getParaUserId() {
        return paraUserId;
    }

    public void setParaUserId(LongFilter paraUserId) {
        this.paraUserId = paraUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParaRoleCriteria that = (ParaRoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(sortString, that.sortString) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(imageBlobName, that.imageBlobName) &&
            Objects.equals(usingFlag, that.usingFlag) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdUserId, that.createdUserId) &&
            Objects.equals(modifiedUserId, that.modifiedUserId) &&
            Objects.equals(verifiedUserId, that.verifiedUserId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(paraNodeId, that.paraNodeId) &&
            Objects.equals(paraUserId, that.paraUserId);
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
        createdUserId,
        modifiedUserId,
        verifiedUserId,
        parentId,
        paraNodeId,
        paraUserId
        );
    }

    @Override
    public String toString() {
        return "ParaRoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (sortString != null ? "sortString=" + sortString + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (imageBlobName != null ? "imageBlobName=" + imageBlobName + ", " : "") +
                (usingFlag != null ? "usingFlag=" + usingFlag + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdUserId != null ? "createdUserId=" + createdUserId + ", " : "") +
                (modifiedUserId != null ? "modifiedUserId=" + modifiedUserId + ", " : "") +
                (verifiedUserId != null ? "verifiedUserId=" + verifiedUserId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (paraNodeId != null ? "paraNodeId=" + paraNodeId + ", " : "") +
                (paraUserId != null ? "paraUserId=" + paraUserId + ", " : "") +
            "}";
    }

}
