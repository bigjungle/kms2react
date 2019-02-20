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
 * Criteria class for the ParaUser entity. This class is used in ParaUserResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /para-users?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParaUserCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userId;

    private StringFilter name;

    private StringFilter serialNumber;

    private StringFilter descString;

    private StringFilter jobId;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter mobile;

    private StringFilter mail;

    private BooleanFilter usingFlag;

    private StringFilter remarks;

    private LongFilter createdUserId;

    private LongFilter modifiedUserId;

    private LongFilter verifiedUserId;

    private LongFilter paraRoleId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
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

    public StringFilter getJobId() {
        return jobId;
    }

    public void setJobId(StringFilter jobId) {
        this.jobId = jobId;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
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

    public LongFilter getParaRoleId() {
        return paraRoleId;
    }

    public void setParaRoleId(LongFilter paraRoleId) {
        this.paraRoleId = paraRoleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParaUserCriteria that = (ParaUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(usingFlag, that.usingFlag) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdUserId, that.createdUserId) &&
            Objects.equals(modifiedUserId, that.modifiedUserId) &&
            Objects.equals(verifiedUserId, that.verifiedUserId) &&
            Objects.equals(paraRoleId, that.paraRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        name,
        serialNumber,
        descString,
        jobId,
        firstName,
        lastName,
        mobile,
        mail,
        usingFlag,
        remarks,
        createdUserId,
        modifiedUserId,
        verifiedUserId,
        paraRoleId
        );
    }

    @Override
    public String toString() {
        return "ParaUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (usingFlag != null ? "usingFlag=" + usingFlag + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdUserId != null ? "createdUserId=" + createdUserId + ", " : "") +
                (modifiedUserId != null ? "modifiedUserId=" + modifiedUserId + ", " : "") +
                (verifiedUserId != null ? "verifiedUserId=" + verifiedUserId + ", " : "") +
                (paraRoleId != null ? "paraRoleId=" + paraRoleId + ", " : "") +
            "}";
    }

}
