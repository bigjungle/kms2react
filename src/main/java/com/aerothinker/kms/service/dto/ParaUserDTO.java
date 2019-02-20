package com.aerothinker.kms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ParaUser entity.
 */
public class ParaUserDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String userId;

    @Size(max = 256)
    private String name;

    @Size(max = 256)
    private String serialNumber;

    @Size(max = 4000)
    private String descString;

    @Size(max = 256)
    private String jobId;

    @Size(max = 256)
    private String firstName;

    @Size(max = 256)
    private String lastName;

    @Size(max = 256)
    private String mobile;

    @Size(max = 256)
    private String mail;

    private Boolean usingFlag;

    @Size(max = 1000)
    private String remarks;

    private Long createdUserId;

    private String createdUserName;

    private Long modifiedUserId;

    private String modifiedUserName;

    private Long verifiedUserId;

    private String verifiedUserName;

    private Set<ParaRoleDTO> paraRoles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescString() {
        return descString;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean isUsingFlag() {
        return usingFlag;
    }

    public void setUsingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long paraUserId) {
        this.createdUserId = paraUserId;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String paraUserName) {
        this.createdUserName = paraUserName;
    }

    public Long getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Long paraUserId) {
        this.modifiedUserId = paraUserId;
    }

    public String getModifiedUserName() {
        return modifiedUserName;
    }

    public void setModifiedUserName(String paraUserName) {
        this.modifiedUserName = paraUserName;
    }

    public Long getVerifiedUserId() {
        return verifiedUserId;
    }

    public void setVerifiedUserId(Long paraUserId) {
        this.verifiedUserId = paraUserId;
    }

    public String getVerifiedUserName() {
        return verifiedUserName;
    }

    public void setVerifiedUserName(String paraUserName) {
        this.verifiedUserName = paraUserName;
    }

    public Set<ParaRoleDTO> getParaRoles() {
        return paraRoles;
    }

    public void setParaRoles(Set<ParaRoleDTO> paraRoles) {
        this.paraRoles = paraRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParaUserDTO paraUserDTO = (ParaUserDTO) o;
        if (paraUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaUserDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", descString='" + getDescString() + "'" +
            ", jobId='" + getJobId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", mail='" + getMail() + "'" +
            ", usingFlag='" + isUsingFlag() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdUser=" + getCreatedUserId() +
            ", createdUser='" + getCreatedUserName() + "'" +
            ", modifiedUser=" + getModifiedUserId() +
            ", modifiedUser='" + getModifiedUserName() + "'" +
            ", verifiedUser=" + getVerifiedUserId() +
            ", verifiedUser='" + getVerifiedUserName() + "'" +
            "}";
    }
}
