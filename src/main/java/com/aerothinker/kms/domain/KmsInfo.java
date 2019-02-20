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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.aerothinker.kms.domain.enumeration.ValidType;

import com.aerothinker.kms.domain.enumeration.ViewPermission;

/**
 * kmsInfo 知识库信息表
 * @author JungleYang.
 */
@ApiModel(description = "kmsInfo 知识库信息表 @author JungleYang.")
@Entity
@Table(name = "kms_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "kmsinfo")
public class KmsInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 名称
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "名称")
    @Column(name = "name", length = 4000)
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
    @Size(max = 4000)
    @ApiModelProperty(value = "描述")
    @Column(name = "desc_string", length = 4000)
    private String descString;

    /**
     * 答案
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "答案")
    @Column(name = "answer", length = 4000)
    private String answer;

    /**
     * 是否使用
     */
    @ApiModelProperty(value = "是否使用")
    @Column(name = "using_flag")
    private Boolean usingFlag;

    /**
     * 版本号
     */
    @Size(max = 256)
    @ApiModelProperty(value = "版本号")
    @Column(name = "version_number", length = 256)
    private String versionNumber;

    /**
     * 备注
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "备注")
    @Column(name = "remarks", length = 4000)
    private String remarks;

    /**
     * 附件路径
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "附件路径")
    @Column(name = "attachment_path", length = 4000)
    private String attachmentPath;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    @Lob
    @Column(name = "attachment_blob")
    private byte[] attachmentBlob;

    @Column(name = "attachment_blob_content_type")
    private String attachmentBlobContentType;

    /**
     * 附件名称
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "附件名称")
    @Column(name = "attachment_name", length = 4000)
    private String attachmentName;

    /**
     * 文本附件
     */
    @ApiModelProperty(value = "文本附件")
    @Lob
    @Column(name = "text_blob")
    private String textBlob;

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
     * 附件名称
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "附件名称")
    @Column(name = "image_blob_name", length = 4000)
    private String imageBlobName;

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

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @Column(name = "published_time")
    private Instant publishedTime;

    /**
     * 是否需要审核
     */
    @ApiModelProperty(value = "是否需要审核")
    @Column(name = "verify_need")
    private Boolean verifyNeed;

    /**
     * 审核完成标识
     */
    @ApiModelProperty(value = "审核完成标识")
    @Column(name = "mark_as_verified")
    private Boolean markAsVerified;

    /**
     * 审核结果
     */
    @ApiModelProperty(value = "审核结果")
    @Column(name = "verify_result")
    private Boolean verifyResult;

    /**
     * 审核意见
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "审核意见")
    @Column(name = "verify_opinion", length = 4000)
    private String verifyOpinion;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 查看权限类别
     */
    @ApiModelProperty(value = "查看权限类别")
    @Enumerated(EnumType.STRING)
    @Column(name = "view_permission")
    private ViewPermission viewPermission;

    /**
     * 查看权限人员Json
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "查看权限人员Json")
    @Column(name = "view_perm_person", length = 4000)
    private String viewPermPerson;

    /**
     * 来源相关信息
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "来源相关信息")
    @Column(name = "para_source_string", length = 4000)
    private String paraSourceString;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private VerifyRec verifyRec;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaType paraType;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaClass paraClass;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaCat paraCat;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaState paraState;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaSource paraSource;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaAttr paraAttr;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaProp paraProp;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaUser createdUser;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaDep createdDepBy;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaUser ownerBy;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaDep ownerDepBy;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaUser modifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaDep modifiedUserDep;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaUser verifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaDep verifiedDepBy;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaUser publishedBy;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private ParaDep publishedDepBy;

    @ManyToOne
    @JsonIgnoreProperties("kmsInfos")
    private KmsInfo parent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "kms_info_para_other",
               joinColumns = @JoinColumn(name = "kms_info_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "para_other_id", referencedColumnName = "id"))
    private Set<ParaOther> paraOthers = new HashSet<>();

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

    public KmsInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public KmsInfo serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSortString() {
        return sortString;
    }

    public KmsInfo sortString(String sortString) {
        this.sortString = sortString;
        return this;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public String getDescString() {
        return descString;
    }

    public KmsInfo descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getAnswer() {
        return answer;
    }

    public KmsInfo answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isUsingFlag() {
        return usingFlag;
    }

    public KmsInfo usingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
        return this;
    }

    public void setUsingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public KmsInfo versionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public KmsInfo remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public KmsInfo attachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
        return this;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public byte[] getAttachmentBlob() {
        return attachmentBlob;
    }

    public KmsInfo attachmentBlob(byte[] attachmentBlob) {
        this.attachmentBlob = attachmentBlob;
        return this;
    }

    public void setAttachmentBlob(byte[] attachmentBlob) {
        this.attachmentBlob = attachmentBlob;
    }

    public String getAttachmentBlobContentType() {
        return attachmentBlobContentType;
    }

    public KmsInfo attachmentBlobContentType(String attachmentBlobContentType) {
        this.attachmentBlobContentType = attachmentBlobContentType;
        return this;
    }

    public void setAttachmentBlobContentType(String attachmentBlobContentType) {
        this.attachmentBlobContentType = attachmentBlobContentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public KmsInfo attachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getTextBlob() {
        return textBlob;
    }

    public KmsInfo textBlob(String textBlob) {
        this.textBlob = textBlob;
        return this;
    }

    public void setTextBlob(String textBlob) {
        this.textBlob = textBlob;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public KmsInfo imageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
        return this;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public KmsInfo imageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
        return this;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getImageBlobName() {
        return imageBlobName;
    }

    public KmsInfo imageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
        return this;
    }

    public void setImageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
    }

    public ValidType getValidType() {
        return validType;
    }

    public KmsInfo validType(ValidType validType) {
        this.validType = validType;
        return this;
    }

    public void setValidType(ValidType validType) {
        this.validType = validType;
    }

    public Instant getValidBegin() {
        return validBegin;
    }

    public KmsInfo validBegin(Instant validBegin) {
        this.validBegin = validBegin;
        return this;
    }

    public void setValidBegin(Instant validBegin) {
        this.validBegin = validBegin;
    }

    public Instant getValidEnd() {
        return validEnd;
    }

    public KmsInfo validEnd(Instant validEnd) {
        this.validEnd = validEnd;
        return this;
    }

    public void setValidEnd(Instant validEnd) {
        this.validEnd = validEnd;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public KmsInfo createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public KmsInfo modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Instant getVerifyTime() {
        return verifyTime;
    }

    public KmsInfo verifyTime(Instant verifyTime) {
        this.verifyTime = verifyTime;
        return this;
    }

    public void setVerifyTime(Instant verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Instant getPublishedTime() {
        return publishedTime;
    }

    public KmsInfo publishedTime(Instant publishedTime) {
        this.publishedTime = publishedTime;
        return this;
    }

    public void setPublishedTime(Instant publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Boolean isVerifyNeed() {
        return verifyNeed;
    }

    public KmsInfo verifyNeed(Boolean verifyNeed) {
        this.verifyNeed = verifyNeed;
        return this;
    }

    public void setVerifyNeed(Boolean verifyNeed) {
        this.verifyNeed = verifyNeed;
    }

    public Boolean isMarkAsVerified() {
        return markAsVerified;
    }

    public KmsInfo markAsVerified(Boolean markAsVerified) {
        this.markAsVerified = markAsVerified;
        return this;
    }

    public void setMarkAsVerified(Boolean markAsVerified) {
        this.markAsVerified = markAsVerified;
    }

    public Boolean isVerifyResult() {
        return verifyResult;
    }

    public KmsInfo verifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
        return this;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getVerifyOpinion() {
        return verifyOpinion;
    }

    public KmsInfo verifyOpinion(String verifyOpinion) {
        this.verifyOpinion = verifyOpinion;
        return this;
    }

    public void setVerifyOpinion(String verifyOpinion) {
        this.verifyOpinion = verifyOpinion;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public KmsInfo viewCount(Integer viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public ViewPermission getViewPermission() {
        return viewPermission;
    }

    public KmsInfo viewPermission(ViewPermission viewPermission) {
        this.viewPermission = viewPermission;
        return this;
    }

    public void setViewPermission(ViewPermission viewPermission) {
        this.viewPermission = viewPermission;
    }

    public String getViewPermPerson() {
        return viewPermPerson;
    }

    public KmsInfo viewPermPerson(String viewPermPerson) {
        this.viewPermPerson = viewPermPerson;
        return this;
    }

    public void setViewPermPerson(String viewPermPerson) {
        this.viewPermPerson = viewPermPerson;
    }

    public String getParaSourceString() {
        return paraSourceString;
    }

    public KmsInfo paraSourceString(String paraSourceString) {
        this.paraSourceString = paraSourceString;
        return this;
    }

    public void setParaSourceString(String paraSourceString) {
        this.paraSourceString = paraSourceString;
    }

    public VerifyRec getVerifyRec() {
        return verifyRec;
    }

    public KmsInfo verifyRec(VerifyRec verifyRec) {
        this.verifyRec = verifyRec;
        return this;
    }

    public void setVerifyRec(VerifyRec verifyRec) {
        this.verifyRec = verifyRec;
    }

    public ParaType getParaType() {
        return paraType;
    }

    public KmsInfo paraType(ParaType paraType) {
        this.paraType = paraType;
        return this;
    }

    public void setParaType(ParaType paraType) {
        this.paraType = paraType;
    }

    public ParaClass getParaClass() {
        return paraClass;
    }

    public KmsInfo paraClass(ParaClass paraClass) {
        this.paraClass = paraClass;
        return this;
    }

    public void setParaClass(ParaClass paraClass) {
        this.paraClass = paraClass;
    }

    public ParaCat getParaCat() {
        return paraCat;
    }

    public KmsInfo paraCat(ParaCat paraCat) {
        this.paraCat = paraCat;
        return this;
    }

    public void setParaCat(ParaCat paraCat) {
        this.paraCat = paraCat;
    }

    public ParaState getParaState() {
        return paraState;
    }

    public KmsInfo paraState(ParaState paraState) {
        this.paraState = paraState;
        return this;
    }

    public void setParaState(ParaState paraState) {
        this.paraState = paraState;
    }

    public ParaSource getParaSource() {
        return paraSource;
    }

    public KmsInfo paraSource(ParaSource paraSource) {
        this.paraSource = paraSource;
        return this;
    }

    public void setParaSource(ParaSource paraSource) {
        this.paraSource = paraSource;
    }

    public ParaAttr getParaAttr() {
        return paraAttr;
    }

    public KmsInfo paraAttr(ParaAttr paraAttr) {
        this.paraAttr = paraAttr;
        return this;
    }

    public void setParaAttr(ParaAttr paraAttr) {
        this.paraAttr = paraAttr;
    }

    public ParaProp getParaProp() {
        return paraProp;
    }

    public KmsInfo paraProp(ParaProp paraProp) {
        this.paraProp = paraProp;
        return this;
    }

    public void setParaProp(ParaProp paraProp) {
        this.paraProp = paraProp;
    }

    public ParaUser getCreatedUser() {
        return createdUser;
    }

    public KmsInfo createdUser(ParaUser paraUser) {
        this.createdUser = paraUser;
        return this;
    }

    public void setCreatedUser(ParaUser paraUser) {
        this.createdUser = paraUser;
    }

    public ParaDep getCreatedDepBy() {
        return createdDepBy;
    }

    public KmsInfo createdDepBy(ParaDep paraDep) {
        this.createdDepBy = paraDep;
        return this;
    }

    public void setCreatedDepBy(ParaDep paraDep) {
        this.createdDepBy = paraDep;
    }

    public ParaUser getOwnerBy() {
        return ownerBy;
    }

    public KmsInfo ownerBy(ParaUser paraUser) {
        this.ownerBy = paraUser;
        return this;
    }

    public void setOwnerBy(ParaUser paraUser) {
        this.ownerBy = paraUser;
    }

    public ParaDep getOwnerDepBy() {
        return ownerDepBy;
    }

    public KmsInfo ownerDepBy(ParaDep paraDep) {
        this.ownerDepBy = paraDep;
        return this;
    }

    public void setOwnerDepBy(ParaDep paraDep) {
        this.ownerDepBy = paraDep;
    }

    public ParaUser getModifiedUser() {
        return modifiedUser;
    }

    public KmsInfo modifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
        return this;
    }

    public void setModifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
    }

    public ParaDep getModifiedUserDep() {
        return modifiedUserDep;
    }

    public KmsInfo modifiedUserDep(ParaDep paraDep) {
        this.modifiedUserDep = paraDep;
        return this;
    }

    public void setModifiedUserDep(ParaDep paraDep) {
        this.modifiedUserDep = paraDep;
    }

    public ParaUser getVerifiedUser() {
        return verifiedUser;
    }

    public KmsInfo verifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
        return this;
    }

    public void setVerifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
    }

    public ParaDep getVerifiedDepBy() {
        return verifiedDepBy;
    }

    public KmsInfo verifiedDepBy(ParaDep paraDep) {
        this.verifiedDepBy = paraDep;
        return this;
    }

    public void setVerifiedDepBy(ParaDep paraDep) {
        this.verifiedDepBy = paraDep;
    }

    public ParaUser getPublishedBy() {
        return publishedBy;
    }

    public KmsInfo publishedBy(ParaUser paraUser) {
        this.publishedBy = paraUser;
        return this;
    }

    public void setPublishedBy(ParaUser paraUser) {
        this.publishedBy = paraUser;
    }

    public ParaDep getPublishedDepBy() {
        return publishedDepBy;
    }

    public KmsInfo publishedDepBy(ParaDep paraDep) {
        this.publishedDepBy = paraDep;
        return this;
    }

    public void setPublishedDepBy(ParaDep paraDep) {
        this.publishedDepBy = paraDep;
    }

    public KmsInfo getParent() {
        return parent;
    }

    public KmsInfo parent(KmsInfo kmsInfo) {
        this.parent = kmsInfo;
        return this;
    }

    public void setParent(KmsInfo kmsInfo) {
        this.parent = kmsInfo;
    }

    public Set<ParaOther> getParaOthers() {
        return paraOthers;
    }

    public KmsInfo paraOthers(Set<ParaOther> paraOthers) {
        this.paraOthers = paraOthers;
        return this;
    }

    public KmsInfo addParaOther(ParaOther paraOther) {
        this.paraOthers.add(paraOther);
        paraOther.getKmsInfos().add(this);
        return this;
    }

    public KmsInfo removeParaOther(ParaOther paraOther) {
        this.paraOthers.remove(paraOther);
        paraOther.getKmsInfos().remove(this);
        return this;
    }

    public void setParaOthers(Set<ParaOther> paraOthers) {
        this.paraOthers = paraOthers;
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
        KmsInfo kmsInfo = (KmsInfo) o;
        if (kmsInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kmsInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KmsInfo{" +
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
            ", attachmentBlobContentType='" + getAttachmentBlobContentType() + "'" +
            ", attachmentName='" + getAttachmentName() + "'" +
            ", textBlob='" + getTextBlob() + "'" +
            ", imageBlob='" + getImageBlob() + "'" +
            ", imageBlobContentType='" + getImageBlobContentType() + "'" +
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
            "}";
    }
}
