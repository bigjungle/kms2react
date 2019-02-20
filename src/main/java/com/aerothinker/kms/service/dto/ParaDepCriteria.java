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
 * Criteria class for the ParaDep entity. This class is used in ParaDepResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /para-deps?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParaDepCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter serialNumber;

    private StringFilter descString;

    private StringFilter tel;

    private StringFilter address;

    private StringFilter remarks;

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

    public StringFilter getDescString() {
        return descString;
    }

    public void setDescString(StringFilter descString) {
        this.descString = descString;
    }

    public StringFilter getTel() {
        return tel;
    }

    public void setTel(StringFilter tel) {
        this.tel = tel;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParaDepCriteria that = (ParaDepCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(tel, that.tel) &&
            Objects.equals(address, that.address) &&
            Objects.equals(remarks, that.remarks) &&
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
        descString,
        tel,
        address,
        remarks,
        createdUserId,
        modifiedUserId,
        verifiedUserId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "ParaDepCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (tel != null ? "tel=" + tel + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdUserId != null ? "createdUserId=" + createdUserId + ", " : "") +
                (modifiedUserId != null ? "modifiedUserId=" + modifiedUserId + ", " : "") +
                (verifiedUserId != null ? "verifiedUserId=" + verifiedUserId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
