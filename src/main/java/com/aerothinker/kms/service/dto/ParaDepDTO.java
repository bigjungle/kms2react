package com.aerothinker.kms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ParaDep entity.
 */
public class ParaDepDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String name;

    @Size(max = 256)
    private String serialNumber;

    @Size(max = 4000)
    private String descString;

    @Size(max = 256)
    private String tel;

    @Size(max = 256)
    private String address;

    @Size(max = 1000)
    private String remarks;

    private Long createdUserId;

    private String createdUserName;

    private Long modifiedUserId;

    private String modifiedUserName;

    private Long verifiedUserId;

    private String verifiedUserName;

    private Long parentId;

    private String parentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long paraDepId) {
        this.parentId = paraDepId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String paraDepName) {
        this.parentName = paraDepName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParaDepDTO paraDepDTO = (ParaDepDTO) o;
        if (paraDepDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraDepDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaDepDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", descString='" + getDescString() + "'" +
            ", tel='" + getTel() + "'" +
            ", address='" + getAddress() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdUser=" + getCreatedUserId() +
            ", createdUser='" + getCreatedUserName() + "'" +
            ", modifiedUser=" + getModifiedUserId() +
            ", modifiedUser='" + getModifiedUserName() + "'" +
            ", verifiedUser=" + getVerifiedUserId() +
            ", verifiedUser='" + getVerifiedUserName() + "'" +
            ", parent=" + getParentId() +
            ", parent='" + getParentName() + "'" +
            "}";
    }
}
