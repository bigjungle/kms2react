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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * ParaUser 用户信息
 * @author JungleYang.
 */
@ApiModel(description = "ParaUser 用户信息 @author JungleYang.")
@Entity
@Table(name = "para_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "parauser")
public class ParaUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 用户ID
     */
    @Size(max = 256)
    @ApiModelProperty(value = "用户ID")
    @Column(name = "user_id", length = 256)
    private String userId;

    /**
     * 姓名
     */
    @Size(max = 256)
    @ApiModelProperty(value = "姓名")
    @Column(name = "name", length = 256)
    private String name;

    /**
     * 编号
     */
    @Size(max = 256)
    @ApiModelProperty(value = "编号")
    @Column(name = "serial_number", length = 256)
    private String serialNumber;

    /**
     * 描述
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "描述")
    @Column(name = "desc_string", length = 4000)
    private String descString;

    /**
     * 工作代码
     */
    @Size(max = 256)
    @ApiModelProperty(value = "工作代码")
    @Column(name = "job_id", length = 256)
    private String jobId;

    /**
     * 姓
     */
    @Size(max = 256)
    @ApiModelProperty(value = "姓")
    @Column(name = "first_name", length = 256)
    private String firstName;

    /**
     * 名
     */
    @Size(max = 256)
    @ApiModelProperty(value = "名")
    @Column(name = "last_name", length = 256)
    private String lastName;

    /**
     * 移动电话
     */
    @Size(max = 256)
    @ApiModelProperty(value = "移动电话")
    @Column(name = "mobile", length = 256)
    private String mobile;

    /**
     * 邮箱
     */
    @Size(max = 256)
    @ApiModelProperty(value = "邮箱")
    @Column(name = "mail", length = 256)
    private String mail;

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
    @JsonIgnoreProperties("paraUsers")
    private ParaUser createdUser;

    @ManyToOne
    @JsonIgnoreProperties("paraUsers")
    private ParaUser modifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraUsers")
    private ParaUser verifiedUser;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "para_user_para_role",
               joinColumns = @JoinColumn(name = "para_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "para_role_id", referencedColumnName = "id"))
    private Set<ParaRole> paraRoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public ParaUser userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public ParaUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ParaUser serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescString() {
        return descString;
    }

    public ParaUser descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getJobId() {
        return jobId;
    }

    public ParaUser jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public ParaUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ParaUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public ParaUser mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public ParaUser mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean isUsingFlag() {
        return usingFlag;
    }

    public ParaUser usingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
        return this;
    }

    public void setUsingFlag(Boolean usingFlag) {
        this.usingFlag = usingFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public ParaUser remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ParaUser getCreatedUser() {
        return createdUser;
    }

    public ParaUser createdUser(ParaUser paraUser) {
        this.createdUser = paraUser;
        return this;
    }

    public void setCreatedUser(ParaUser paraUser) {
        this.createdUser = paraUser;
    }

    public ParaUser getModifiedUser() {
        return modifiedUser;
    }

    public ParaUser modifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
        return this;
    }

    public void setModifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
    }

    public ParaUser getVerifiedUser() {
        return verifiedUser;
    }

    public ParaUser verifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
        return this;
    }

    public void setVerifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
    }

    public Set<ParaRole> getParaRoles() {
        return paraRoles;
    }

    public ParaUser paraRoles(Set<ParaRole> paraRoles) {
        this.paraRoles = paraRoles;
        return this;
    }

    public ParaUser addParaRole(ParaRole paraRole) {
        this.paraRoles.add(paraRole);
        paraRole.getParaUsers().add(this);
        return this;
    }

    public ParaUser removeParaRole(ParaRole paraRole) {
        this.paraRoles.remove(paraRole);
        paraRole.getParaUsers().remove(this);
        return this;
    }

    public void setParaRoles(Set<ParaRole> paraRoles) {
        this.paraRoles = paraRoles;
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
        ParaUser paraUser = (ParaUser) o;
        if (paraUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaUser{" +
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
            "}";
    }
}
