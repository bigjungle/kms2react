package com.aerothinker.kms.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.aerothinker.kms.domain.enumeration.ParaOtherValueType;
import com.aerothinker.kms.domain.enumeration.ValidType;

/**
 * A DTO for the ParaOther entity.
 */
public class ParaOtherDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String name;

    @Size(max = 256)
    private String serialNumber;

    @Size(max = 10)
    private String sortString;

    @Size(max = 256)
    private String descString;

    private ParaOtherValueType paraOtherValueType;

    @Size(max = 4000)
    private String paraValueSt;

    private Instant paraValueIn;

    private Boolean paraValueBo;

    private String paraValueJs;

    @Lob
    private byte[] paraValueBl;
    private String paraValueBlContentType;

    @Lob
    private byte[] imageBlob;
    private String imageBlobContentType;

    @Size(max = 512)
    private String imageBlobName;

    private Boolean usingFlag;

    @Size(max = 1000)
    private String remarks;

    private ValidType validType;

    private Instant validBegin;

    private Instant validEnd;

    private Instant createTime;

    private Instant modifyTime;

    private Instant verifyTime;

    private Long createdUserId;

    private String createdUserName;

    private Long modifiedUserId;

    private String modifiedUserName;

    private Long verifiedUserId;

    private String verifiedUserName;

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

    public ParaOtherValueType getParaOtherValueType() {
        return paraOtherValueType;
    }

    public void setParaOtherValueType(ParaOtherValueType paraOtherValueType) {
        this.paraOtherValueType = paraOtherValueType;
    }

    public String getParaValueSt() {
        return paraValueSt;
    }

    public void setParaValueSt(String paraValueSt) {
        this.paraValueSt = paraValueSt;
    }

    public Instant getParaValueIn() {
        return paraValueIn;
    }

    public void setParaValueIn(Instant paraValueIn) {
        this.paraValueIn = paraValueIn;
    }

    public Boolean isParaValueBo() {
        return paraValueBo;
    }

    public void setParaValueBo(Boolean paraValueBo) {
        this.paraValueBo = paraValueBo;
    }

    public String getParaValueJs() {
        return paraValueJs;
    }

    public void setParaValueJs(String paraValueJs) {
        this.paraValueJs = paraValueJs;
    }

    public byte[] getParaValueBl() {
        return paraValueBl;
    }

    public void setParaValueBl(byte[] paraValueBl) {
        this.paraValueBl = paraValueBl;
    }

    public String getParaValueBlContentType() {
        return paraValueBlContentType;
    }

    public void setParaValueBlContentType(String paraValueBlContentType) {
        this.paraValueBlContentType = paraValueBlContentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParaOtherDTO paraOtherDTO = (ParaOtherDTO) o;
        if (paraOtherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraOtherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaOtherDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", sortString='" + getSortString() + "'" +
            ", descString='" + getDescString() + "'" +
            ", paraOtherValueType='" + getParaOtherValueType() + "'" +
            ", paraValueSt='" + getParaValueSt() + "'" +
            ", paraValueIn='" + getParaValueIn() + "'" +
            ", paraValueBo='" + isParaValueBo() + "'" +
            ", paraValueJs='" + getParaValueJs() + "'" +
            ", paraValueBl='" + getParaValueBl() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobName='" + getImageBlobName() + "'" +
            ", usingFlag='" + isUsingFlag() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", validType='" + getValidType() + "'" +
            ", validBegin='" + getValidBegin() + "'" +
            ", validEnd='" + getValidEnd() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            ", verifyTime='" + getVerifyTime() + "'" +
            ", createdUser=" + getCreatedUserId() +
            ", createdUser='" + getCreatedUserName() + "'" +
            ", modifiedUser=" + getModifiedUserId() +
            ", modifiedUser='" + getModifiedUserName() + "'" +
            ", verifiedUser=" + getVerifiedUserId() +
            ", verifiedUser='" + getVerifiedUserName() + "'" +
            "}";
    }
}
