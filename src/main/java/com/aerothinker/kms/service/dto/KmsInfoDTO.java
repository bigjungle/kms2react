package com.aerothinker.kms.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.aerothinker.kms.domain.enumeration.ValidType;
import com.aerothinker.kms.domain.enumeration.ViewPermission;

/**
 * A DTO for the KmsInfo entity.
 */
public class KmsInfoDTO implements Serializable {

    private Long id;

    @Size(max = 4000)
    private String name;

    @Size(max = 256)
    private String serialNumber;

    @Size(max = 10)
    private String sortString;

    @Size(max = 4000)
    private String descString;

    @Size(max = 4000)
    private String answer;

    private Boolean usingFlag;

    @Size(max = 256)
    private String versionNumber;

    @Size(max = 4000)
    private String remarks;

    @Size(max = 4000)
    private String attachmentPath;

    @Lob
    private byte[] attachmentBlob;
    private String attachmentBlobContentType;

    @Size(max = 4000)
    private String attachmentName;

    @Lob
    private String textBlob;

    @Lob
    private byte[] imageBlob;
    private String imageBlobContentType;

    @Size(max = 4000)
    private String imageBlobName;

    private ValidType validType;

    private Instant validBegin;

    private Instant validEnd;

    private Instant createTime;

    private Instant modifyTime;

    private Instant verifyTime;

    private Instant publishedTime;

    private Boolean verifyNeed;

    private Boolean markAsVerified;

    private Boolean verifyResult;

    @Size(max = 4000)
    private String verifyOpinion;

    private Integer viewCount;

    private ViewPermission viewPermission;

    @Size(max = 4000)
    private String viewPermPerson;

    @Size(max = 4000)
    private String paraSourceString;

    private Long verifyRecId;

    private String verifyRecName;

    private Long paraTypeId;

    private String paraTypeName;

    private Long paraClassId;

    private String paraClassName;

    private Long paraCatId;

    private String paraCatName;

    private Long paraStateId;

    private String paraStateName;

    private Long paraSourceId;

    private String paraSourceName;

    private Long paraAttrId;

    private String paraAttrName;

    private Long paraPropId;

    private String paraPropName;

    private Long createdUserId;

    private String createdUserName;

    private Long createdDepById;

    private String createdDepByName;

    private Long ownerById;

    private String ownerByName;

    private Long ownerDepById;

    private String ownerDepByName;

    private Long modifiedUserId;

    private String modifiedUserName;

    private Long modifiedUserDepId;

    private String modifiedUserDepName;

    private Long verifiedUserId;

    private String verifiedUserName;

    private Long verifiedDepById;

    private String verifiedDepByName;

    private Long publishedById;

    private String publishedByName;

    private Long publishedDepById;

    private String publishedDepByName;

    private Long parentId;

    private String parentName;

    private Set<ParaOtherDTO> paraOthers = new HashSet<>();

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isUsingFlag() {
        return usingFlag;
    }

    public void setUsingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public byte[] getAttachmentBlob() {
        return attachmentBlob;
    }

    public void setAttachmentBlob(byte[] attachmentBlob) {
        this.attachmentBlob = attachmentBlob;
    }

    public String getAttachmentBlobContentType() {
        return attachmentBlobContentType;
    }

    public void setAttachmentBlobContentType(String attachmentBlobContentType) {
        this.attachmentBlobContentType = attachmentBlobContentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getTextBlob() {
        return textBlob;
    }

    public void setTextBlob(String textBlob) {
        this.textBlob = textBlob;
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

    public ValidType getValidType() {
        return validType;
    }

    public void setValidType(ValidType validType) {
        this.validType = validType;
    }

    public Instant getValidBegin() {
        return validBegin;
    }

    public void setValidBegin(Instant validBegin) {
        this.validBegin = validBegin;
    }

    public Instant getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Instant validEnd) {
        this.validEnd = validEnd;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Instant getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Instant verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Instant getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Instant publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Boolean isVerifyNeed() {
        return verifyNeed;
    }

    public void setVerifyNeed(Boolean verifyNeed) {
        this.verifyNeed = verifyNeed;
    }

    public Boolean isMarkAsVerified() {
        return markAsVerified;
    }

    public void setMarkAsVerified(Boolean markAsVerified) {
        this.markAsVerified = markAsVerified;
    }

    public Boolean isVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getVerifyOpinion() {
        return verifyOpinion;
    }

    public void setVerifyOpinion(String verifyOpinion) {
        this.verifyOpinion = verifyOpinion;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public ViewPermission getViewPermission() {
        return viewPermission;
    }

    public void setViewPermission(ViewPermission viewPermission) {
        this.viewPermission = viewPermission;
    }

    public String getViewPermPerson() {
        return viewPermPerson;
    }

    public void setViewPermPerson(String viewPermPerson) {
        this.viewPermPerson = viewPermPerson;
    }

    public String getParaSourceString() {
        return paraSourceString;
    }

    public void setParaSourceString(String paraSourceString) {
        this.paraSourceString = paraSourceString;
    }

    public Long getVerifyRecId() {
        return verifyRecId;
    }

    public void setVerifyRecId(Long verifyRecId) {
        this.verifyRecId = verifyRecId;
    }

    public String getVerifyRecName() {
        return verifyRecName;
    }

    public void setVerifyRecName(String verifyRecName) {
        this.verifyRecName = verifyRecName;
    }

    public Long getParaTypeId() {
        return paraTypeId;
    }

    public void setParaTypeId(Long paraTypeId) {
        this.paraTypeId = paraTypeId;
    }

    public String getParaTypeName() {
        return paraTypeName;
    }

    public void setParaTypeName(String paraTypeName) {
        this.paraTypeName = paraTypeName;
    }

    public Long getParaClassId() {
        return paraClassId;
    }

    public void setParaClassId(Long paraClassId) {
        this.paraClassId = paraClassId;
    }

    public String getParaClassName() {
        return paraClassName;
    }

    public void setParaClassName(String paraClassName) {
        this.paraClassName = paraClassName;
    }

    public Long getParaCatId() {
        return paraCatId;
    }

    public void setParaCatId(Long paraCatId) {
        this.paraCatId = paraCatId;
    }

    public String getParaCatName() {
        return paraCatName;
    }

    public void setParaCatName(String paraCatName) {
        this.paraCatName = paraCatName;
    }

    public Long getParaStateId() {
        return paraStateId;
    }

    public void setParaStateId(Long paraStateId) {
        this.paraStateId = paraStateId;
    }

    public String getParaStateName() {
        return paraStateName;
    }

    public void setParaStateName(String paraStateName) {
        this.paraStateName = paraStateName;
    }

    public Long getParaSourceId() {
        return paraSourceId;
    }

    public void setParaSourceId(Long paraSourceId) {
        this.paraSourceId = paraSourceId;
    }

    public String getParaSourceName() {
        return paraSourceName;
    }

    public void setParaSourceName(String paraSourceName) {
        this.paraSourceName = paraSourceName;
    }

    public Long getParaAttrId() {
        return paraAttrId;
    }

    public void setParaAttrId(Long paraAttrId) {
        this.paraAttrId = paraAttrId;
    }

    public String getParaAttrName() {
        return paraAttrName;
    }

    public void setParaAttrName(String paraAttrName) {
        this.paraAttrName = paraAttrName;
    }

    public Long getParaPropId() {
        return paraPropId;
    }

    public void setParaPropId(Long paraPropId) {
        this.paraPropId = paraPropId;
    }

    public String getParaPropName() {
        return paraPropName;
    }

    public void setParaPropName(String paraPropName) {
        this.paraPropName = paraPropName;
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

    public Long getCreatedDepById() {
        return createdDepById;
    }

    public void setCreatedDepById(Long paraDepId) {
        this.createdDepById = paraDepId;
    }

    public String getCreatedDepByName() {
        return createdDepByName;
    }

    public void setCreatedDepByName(String paraDepName) {
        this.createdDepByName = paraDepName;
    }

    public Long getOwnerById() {
        return ownerById;
    }

    public void setOwnerById(Long paraUserId) {
        this.ownerById = paraUserId;
    }

    public String getOwnerByName() {
        return ownerByName;
    }

    public void setOwnerByName(String paraUserName) {
        this.ownerByName = paraUserName;
    }

    public Long getOwnerDepById() {
        return ownerDepById;
    }

    public void setOwnerDepById(Long paraDepId) {
        this.ownerDepById = paraDepId;
    }

    public String getOwnerDepByName() {
        return ownerDepByName;
    }

    public void setOwnerDepByName(String paraDepName) {
        this.ownerDepByName = paraDepName;
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

    public Long getModifiedUserDepId() {
        return modifiedUserDepId;
    }

    public void setModifiedUserDepId(Long paraDepId) {
        this.modifiedUserDepId = paraDepId;
    }

    public String getModifiedUserDepName() {
        return modifiedUserDepName;
    }

    public void setModifiedUserDepName(String paraDepName) {
        this.modifiedUserDepName = paraDepName;
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

    public Long getVerifiedDepById() {
        return verifiedDepById;
    }

    public void setVerifiedDepById(Long paraDepId) {
        this.verifiedDepById = paraDepId;
    }

    public String getVerifiedDepByName() {
        return verifiedDepByName;
    }

    public void setVerifiedDepByName(String paraDepName) {
        this.verifiedDepByName = paraDepName;
    }

    public Long getPublishedById() {
        return publishedById;
    }

    public void setPublishedById(Long paraUserId) {
        this.publishedById = paraUserId;
    }

    public String getPublishedByName() {
        return publishedByName;
    }

    public void setPublishedByName(String paraUserName) {
        this.publishedByName = paraUserName;
    }

    public Long getPublishedDepById() {
        return publishedDepById;
    }

    public void setPublishedDepById(Long paraDepId) {
        this.publishedDepById = paraDepId;
    }

    public String getPublishedDepByName() {
        return publishedDepByName;
    }

    public void setPublishedDepByName(String paraDepName) {
        this.publishedDepByName = paraDepName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long kmsInfoId) {
        this.parentId = kmsInfoId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String kmsInfoName) {
        this.parentName = kmsInfoName;
    }

    public Set<ParaOtherDTO> getParaOthers() {
        return paraOthers;
    }

    public void setParaOthers(Set<ParaOtherDTO> paraOthers) {
        this.paraOthers = paraOthers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KmsInfoDTO kmsInfoDTO = (KmsInfoDTO) o;
        if (kmsInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kmsInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KmsInfoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", sortString='" + getSortString() + "'" +
            ", descString='" + getDescString() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", usingFlag='" + isUsingFlag() + "'" +
            ", versionNumber='" + getVersionNumber() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", attachmentPath='" + getAttachmentPath() + "'" +
            ", attachmentBlob='" + getAttachmentBlob() + "'" +
            ", attachmentName='" + getAttachmentName() + "'" +
            ", textBlob='" + getTextBlob() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobName='" + getImageBlobName() + "'" +
            ", validType='" + getValidType() + "'" +
            ", validBegin='" + getValidBegin() + "'" +
            ", validEnd='" + getValidEnd() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            ", verifyTime='" + getVerifyTime() + "'" +
            ", publishedTime='" + getPublishedTime() + "'" +
            ", verifyNeed='" + isVerifyNeed() + "'" +
            ", markAsVerified='" + isMarkAsVerified() + "'" +
            ", verifyResult='" + isVerifyResult() + "'" +
            ", verifyOpinion='" + getVerifyOpinion() + "'" +
            ", viewCount=" + getViewCount() +
            ", viewPermission='" + getViewPermission() + "'" +
            ", viewPermPerson='" + getViewPermPerson() + "'" +
            ", paraSourceString='" + getParaSourceString() + "'" +
            ", verifyRec=" + getVerifyRecId() +
            ", verifyRec='" + getVerifyRecName() + "'" +
            ", paraType=" + getParaTypeId() +
            ", paraType='" + getParaTypeName() + "'" +
            ", paraClass=" + getParaClassId() +
            ", paraClass='" + getParaClassName() + "'" +
            ", paraCat=" + getParaCatId() +
            ", paraCat='" + getParaCatName() + "'" +
            ", paraState=" + getParaStateId() +
            ", paraState='" + getParaStateName() + "'" +
            ", paraSource=" + getParaSourceId() +
            ", paraSource='" + getParaSourceName() + "'" +
            ", paraAttr=" + getParaAttrId() +
            ", paraAttr='" + getParaAttrName() + "'" +
            ", paraProp=" + getParaPropId() +
            ", paraProp='" + getParaPropName() + "'" +
            ", createdUser=" + getCreatedUserId() +
            ", createdUser='" + getCreatedUserName() + "'" +
            ", createdDepBy=" + getCreatedDepById() +
            ", createdDepBy='" + getCreatedDepByName() + "'" +
            ", ownerBy=" + getOwnerById() +
            ", ownerBy='" + getOwnerByName() + "'" +
            ", ownerDepBy=" + getOwnerDepById() +
            ", ownerDepBy='" + getOwnerDepByName() + "'" +
            ", modifiedUser=" + getModifiedUserId() +
            ", modifiedUser='" + getModifiedUserName() + "'" +
            ", modifiedUserDep=" + getModifiedUserDepId() +
            ", modifiedUserDep='" + getModifiedUserDepName() + "'" +
            ", verifiedUser=" + getVerifiedUserId() +
            ", verifiedUser='" + getVerifiedUserName() + "'" +
            ", verifiedDepBy=" + getVerifiedDepById() +
            ", verifiedDepBy='" + getVerifiedDepByName() + "'" +
            ", publishedBy=" + getPublishedById() +
            ", publishedBy='" + getPublishedByName() + "'" +
            ", publishedDepBy=" + getPublishedDepById() +
            ", publishedDepBy='" + getPublishedDepByName() + "'" +
            ", parent=" + getParentId() +
            ", parent='" + getParentName() + "'" +
            "}";
    }
}
