package com.aerothinker.kms.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.aerothinker.kms.domain.enumeration.ValidType;

/**
 * Para State 状态参数表
 * @author.
 */
@ApiModel(description = "Para State 状态参数表 @author.")
@Entity
@Table(name = "para_state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "parastate")
public class ParaState implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 256)
    @ApiModelProperty(value = "名称", required = true)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 编号
     */
    @Size(max = 256)
    @ApiModelProperty(value = "编号")
    @Column(name = "serial_number", length = 256)
    private String serialNumber;

    /**
     * 序号
     */
    @Size(max = 10)
    @ApiModelProperty(value = "序号")
    @Column(name = "sort_string", length = 10)
    private String sortString;

    /**
     * 描述
     */
    @Size(max = 256)
    @ApiModelProperty(value = "描述")
    @Column(name = "desc_string", length = 256)
    private String descString;

    /**
     * 图片附件
     */
    @ApiModelProperty(value = "图片附件")
    @Lob
    @Column(name = "image_blob")
    private byte[] imageBlob;

    @Column(name = "image_blob_content_type")
    private String imageBlobContentType;

    /**
     * 图片附件名称
     */
    @Size(max = 512)
    @ApiModelProperty(value = "图片附件名称")
    @Column(name = "image_blob_name", length = 512)
    private String imageBlobName;

    /**
     * 是否使用
     */
    @ApiModelProperty(value = "是否使用")
    @Column(name = "using_flag")
    private Boolean usingFlag;

    /**
     * 备注
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "备注")
    @Column(name = "remarks", length = 1000)
    private String remarks;

    /**
     * 有效类型
     */
    @ApiModelProperty(value = "有效类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "valid_type")
    private ValidType validType;

    /**
     * 生效开始时间
     */
    @ApiModelProperty(value = "生效开始时间")
    @Column(name = "valid_begin")
    private Instant validBegin;

    /**
     * 有效截止时间
     */
    @ApiModelProperty(value = "有效截止时间")
    @Column(name = "valid_end")
    private Instant validEnd;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间")
    @Column(name = "modify_time")
    private Instant modifyTime;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @Column(name = "verify_time")
    private Instant verifyTime;

    @ManyToOne
    @JsonIgnoreProperties("paraStates")
    private ParaUser createdUser;

    @ManyToOne
    @JsonIgnoreProperties("paraStates")
    private ParaUser modifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraStates")
    private ParaUser verifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraStates")
    private ParaState parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ParaState name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ParaState serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSortString() {
        return sortString;
    }

    public ParaState sortString(String sortString) {
        this.sortString = sortString;
        return this;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public String getDescString() {
        return descString;
    }

    public ParaState descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public ParaState imageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
        return this;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public ParaState imageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
        return this;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getImageBlobName() {
        return imageBlobName;
    }

    public ParaState imageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
        return this;
    }

    public void setImageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
    }

    public Boolean isUsingFlag() {
        return usingFlag;
    }

    public ParaState usingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
        return this;
    }

    public void setUsingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public ParaState remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ValidType getValidType() {
        return validType;
    }

    public ParaState validType(ValidType validType) {
        this.validType = validType;
        return this;
    }

    public void setValidType(ValidType validType) {
        this.validType = validType;
    }

    public Instant getValidBegin() {
        return validBegin;
    }

    public ParaState validBegin(Instant validBegin) {
        this.validBegin = validBegin;
        return this;
    }

    public void setValidBegin(Instant validBegin) {
        this.validBegin = validBegin;
    }

    public Instant getValidEnd() {
        return validEnd;
    }

    public ParaState validEnd(Instant validEnd) {
        this.validEnd = validEnd;
        return this;
    }

    public void setValidEnd(Instant validEnd) {
        this.validEnd = validEnd;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public ParaState createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public ParaState modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Instant getVerifyTime() {
        return verifyTime;
    }

    public ParaState verifyTime(Instant verifyTime) {
        this.verifyTime = verifyTime;
        return this;
    }

    public void setVerifyTime(Instant verifyTime) {
        this.verifyTime = verifyTime;
    }

    public ParaUser getCreatedUser() {
        return createdUser;
    }

    public ParaState createdUser(ParaUser paraUser) {
        this.createdUser = paraUser;
        return this;
    }

    public void setCreatedUser(ParaUser paraUser) {
        this.createdUser = paraUser;
    }

    public ParaUser getModifiedUser() {
        return modifiedUser;
    }

    public ParaState modifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
        return this;
    }

    public void setModifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
    }

    public ParaUser getVerifiedUser() {
        return verifiedUser;
    }

    public ParaState verifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
        return this;
    }

    public void setVerifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
    }

    public ParaState getParent() {
        return parent;
    }

    public ParaState parent(ParaState paraState) {
        this.parent = paraState;
        return this;
    }

    public void setParent(ParaState paraState) {
        this.parent = paraState;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParaState paraState = (ParaState) o;
        if (paraState.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraState.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaState{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", sortString='" + getSortString() + "'" +
            ", descString='" + getDescString() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobContentType='" + getImageBlobContentType() + "'" +
            ", imageBlobName='" + getImageBlobName() + "'" +
            ", usingFlag='" + isUsingFlag() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", validType='" + getValidType() + "'" +
            ", validBegin='" + getValidBegin() + "'" +
            ", validEnd='" + getValidEnd() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            ", verifyTime='" + getVerifyTime() + "'" +
            "}";
    }
}
