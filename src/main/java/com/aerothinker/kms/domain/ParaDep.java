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
import java.util.Objects;

/**
 * ParaDep 单位信息
 * @author JungleYang.
 */
@ApiModel(description = "ParaDep 单位信息 @author JungleYang.")
@Entity
@Table(name = "para_dep")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paradep")
public class ParaDep implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 名称
     */
    @Size(max = 256)
    @ApiModelProperty(value = "名称")
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
     * 电话
     */
    @Size(max = 256)
    @ApiModelProperty(value = "电话")
    @Column(name = "tel", length = 256)
    private String tel;

    /**
     * 地址
     */
    @Size(max = 256)
    @ApiModelProperty(value = "地址")
    @Column(name = "address", length = 256)
    private String address;

    /**
     * 备注
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "备注")
    @Column(name = "remarks", length = 1000)
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("paraDeps")
    private ParaUser createdUser;

    @ManyToOne
    @JsonIgnoreProperties("paraDeps")
    private ParaUser modifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraDeps")
    private ParaUser verifiedUser;

    @ManyToOne
    @JsonIgnoreProperties("paraDeps")
    private ParaDep parent;

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

    public ParaDep name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ParaDep serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescString() {
        return descString;
    }

    public ParaDep descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getTel() {
        return tel;
    }

    public ParaDep tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public ParaDep address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public ParaDep remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ParaUser getCreatedUser() {
        return createdUser;
    }

    public ParaDep createdUser(ParaUser paraUser) {
        this.createdUser = paraUser;
        return this;
    }

    public void setCreatedUser(ParaUser paraUser) {
        this.createdUser = paraUser;
    }

    public ParaUser getModifiedUser() {
        return modifiedUser;
    }

    public ParaDep modifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
        return this;
    }

    public void setModifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
    }

    public ParaUser getVerifiedUser() {
        return verifiedUser;
    }

    public ParaDep verifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
        return this;
    }

    public void setVerifiedUser(ParaUser paraUser) {
        this.verifiedUser = paraUser;
    }

    public ParaDep getParent() {
        return parent;
    }

    public ParaDep parent(ParaDep paraDep) {
        this.parent = paraDep;
        return this;
    }

    public void setParent(ParaDep paraDep) {
        this.parent = paraDep;
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
        ParaDep paraDep = (ParaDep) o;
        if (paraDep.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paraDep.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParaDep{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", descString='" + getDescString() + "'" +
            ", tel='" + getTel() + "'" +
            ", address='" + getAddress() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
