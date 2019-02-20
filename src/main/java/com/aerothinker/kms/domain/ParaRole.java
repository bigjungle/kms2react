package com.aerothinker.kms.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * ParaRole 角色信息
 * @author JungleYang.
 */
@ApiModel(description = "ParaRole 角色信息 @author JungleYang.")
@Entity
@Table(name = "para_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pararole")
public class ParaRole implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("paraRoles")
    private ParaUser createdUser;

    @ManyToOne
    @JsonIgnoreProperties("paraRoles")
    private ParaUser modifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraRoles")
    private ParaUser verifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraRoles")
    private ParaRole parent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "para_role_para_node",
               joinColumns = @JoinColumn(name = "para_role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "para_node_id", referencedColumnName = "id"))
    private Set<ParaNode> paraNodes = new HashSet<>();

    @ManyToMany(mappedBy = "paraRoles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ParaUser> paraUsers = new HashSet<>();

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

    public ParaRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ParaRole serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSortString() {
        return sortString;
    }

    public ParaRole sortString(String sortString) {
        this.sortString = sortString;
        return this;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public String getDescString() {
        return descString;
    }

    public ParaRole descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public ParaRole imageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
        return this;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getImageBlobContentType() {
        return imageBlobContentType;
    }

    public ParaRole imageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
        return this;
    }

    public void setImageBlobContentType(String imageBlobContentType) {
        this.imageBlobContentType = imageBlobContentType;
    }

    public String getImageBlobName() {
        return imageBlobName;
    }

    public ParaRole imageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
        return this;
    }

    public void setImageBlobName(String imageBlobName) {
        this.imageBlobName = imageBlobName;
    }

    public Boolean isUsingFlag() {
        return usingFlag;
    }

    public ParaRole usingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
        return this;
    }

    public void setUsingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public ParaRole remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ParaUser getCreatedUser() {
        return createdUser;
    }

    public ParaRole createdUser(ParaUser paraUser) {
        this.createdUser = paraUser;
        return this;
    }

    public void setCreatedUser(ParaUser paraUser) {
        this.createdUser = paraUser;
    }

    public ParaUser getModifiedUser() {
        return modifiedUser;
    }

    public ParaRole modifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
        return this;
    }

    public void setModifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
    }

    public ParaUser getVerifiedUser() {
        return verifiedUser;
    }

    public ParaRole verifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
        return this;
    }

    public void setVerifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
    }

    public ParaRole getParent() {
        return parent;
    }

    public ParaRole parent(ParaRole paraRole) {
        this.parent = paraRole;
        return this;
    }

    public void setParent(ParaRole paraRole) {
        this.parent = paraRole;
    }

    public Set<ParaNode> getParaNodes() {
        return paraNodes;
    }

    public ParaRole paraNodes(Set<ParaNode> paraNodes) {
        this.paraNodes = paraNodes;
        return this;
    }

    public ParaRole addParaNode(ParaNode paraNode) {
        this.paraNodes.add(paraNode);
        paraNode.getParaRoles().add(this);
        return this;
    }

    public ParaRole removeParaNode(ParaNode paraNode) {
        this.paraNodes.remove(paraNode);
        paraNode.getParaRoles().remove(this);
        return this;
    }

    public void setParaNodes(Set<ParaNode> paraNodes) {
        this.paraNodes = paraNodes;
    }

    public Set<ParaUser> getParaUsers() {
        return paraUsers;
    }

    public ParaRole paraUsers(Set<ParaUser> paraUsers) {
        this.paraUsers = paraUsers;
        return this;
    }

    public ParaRole addParaUser(ParaUser paraUser) {
        this.paraUsers.add(paraUser);
        paraUser.getParaRoles().add(this);
        return this;
    }

    public ParaRole removeParaUser(ParaUser paraUser) {
        this.paraUsers.remove(paraUser);
        paraUser.getParaRoles().remove(this);
        return this;
    }

    public void setParaUsers(Set<ParaUser> paraUsers) {
        this.paraUsers = paraUsers;
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
        ParaRole paraRole = (ParaRole) o;
        if (paraRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaRole{" +
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
            "}";
    }
}
