package com.aerothinker.kms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.aerothinker.kms.domain.enumeration.ParaOtherValueType;
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
 * Criteria class for the ParaOther entity. This class is used in ParaOtherResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /para-others?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParaOtherCriteria implements Serializable {
    /**
     * Class for filtering ParaOtherValueType
     */
    public static class ParaOtherValueTypeFilter extends Filter<ParaOtherValueType> {
    }
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

    private ParaOtherValueTypeFilter paraOtherValueType;

    private StringFilter paraValueSt;

    private InstantFilter paraValueIn;

    private BooleanFilter paraValueBo;

    private StringFilter paraValueJs;

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

    private LongFilter kmsInfoId;

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

    public ParaOtherValueTypeFilter getParaOtherValueType() {
        return paraOtherValueType;
    }

    public void setParaOtherValueType(ParaOtherValueTypeFilter paraOtherValueType) {
        this.paraOtherValueType = paraOtherValueType;
    }

    public StringFilter getParaValueSt() {
        return paraValueSt;
    }

    public void setParaValueSt(StringFilter paraValueSt) {
        this.paraValueSt = paraValueSt;
    }

    public InstantFilter getParaValueIn() {
        return paraValueIn;
    }

    public void setParaValueIn(InstantFilter paraValueIn) {
        this.paraValueIn = paraValueIn;
    }

    public BooleanFilter getParaValueBo() {
        return paraValueBo;
    }

    public void setParaValueBo(BooleanFilter paraValueBo) {
        this.paraValueBo = paraValueBo;
    }

    public StringFilter getParaValueJs() {
        return paraValueJs;
    }

    public void setParaValueJs(StringFilter paraValueJs) {
        this.paraValueJs = paraValueJs;
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

    public LongFilter getKmsInfoId() {
        return kmsInfoId;
    }

    public void setKmsInfoId(LongFilter kmsInfoId) {
        this.kmsInfoId = kmsInfoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParaOtherCriteria that = (ParaOtherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(sortString, that.sortString) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(paraOtherValueType, that.paraOtherValueType) &&
            Objects.equals(paraValueSt, that.paraValueSt) &&
            Objects.equals(paraValueIn, that.paraValueIn) &&
            Objects.equals(paraValueBo, that.paraValueBo) &&
            Objects.equals(paraValueJs, that.paraValueJs) &&
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
            Objects.equals(kmsInfoId, that.kmsInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        serialNumber,
        sortString,
        descString,
        paraOtherValueType,
        paraValueSt,
        paraValueIn,
        paraValueBo,
        paraValueJs,
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
        kmsInfoId
        );
    }

    @Override
    public String toString() {
        return "ParaOtherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (sortString != null ? "sortString=" + sortString + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (paraOtherValueType != null ? "paraOtherValueType=" + paraOtherValueType + ", " : "") +
                (paraValueSt != null ? "paraValueSt=" + paraValueSt + ", " : "") +
                (paraValueIn != null ? "paraValueIn=" + paraValueIn + ", " : "") +
                (paraValueBo != null ? "paraValueBo=" + paraValueBo + ", " : "") +
                (paraValueJs != null ? "paraValueJs=" + paraValueJs + ", " : "") +
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
                (kmsInfoId != null ? "kmsInfoId=" + kmsInfoId + ", " : "") +
            "}";
    }

}
