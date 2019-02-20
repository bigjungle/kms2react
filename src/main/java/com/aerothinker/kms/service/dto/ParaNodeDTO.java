package com.aerothinker.kms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ParaNode entity.
 */
public class ParaNodeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String name;

    @Size(max = 256)
    private String link;

    @Size(max = 256)
    private String serialNumber;

    @Size(max = 10)
    private String sortString;

    @Size(max = 256)
    private String descString;

    @Lob
    private byte[] imageBlob;
    private String imageBlobContentType;

    @Size(max = 512)
    private String imageBlobName;

    private Boolean usingFlag;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public String getDescString() {
        return descString;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getImageBlobName() {
        return imageBlobName;
    }

    public void setImageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long paraNodeId) {
        this.parentId = paraNodeId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String paraNodeName) {
        this.parentName = paraNodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParaNodeDTO paraNodeDTO = (ParaNodeDTO) o;
        if (paraNodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraNodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaNodeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", link='" + getLink() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", sortString='" + getSortString() + "'" +
            ", descString='" + getDescString() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobName='" + getImageBlobName() + "'" +
            ", usingFlag='" + isUsingFlag() + "'" +
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
