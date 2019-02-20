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

/**
 * Verify Record 审核记录表
 * @author.
 */
@ApiModel(description = "Verify Record 审核记录表 @author.")
@Entity
@Table(name = "verify_rec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "verifyrec")
public class VerifyRec implements Serializable {

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
     * 描述
     */
    @Size(max = 256)
    @ApiModelProperty(value = "描述")
    @Column(name = "desc_string", length = 256)
    private String descString;

    /**
     * 审核结果
     */
    @ApiModelProperty(value = "审核结果")
    @Column(name = "verify_result")
    private Boolean verifyResult;

    /**
     * 审核得分
     */
    @ApiModelProperty(value = "审核得分")
    @Column(name = "verify_score")
    private Integer verifyScore;

    /**
     * 备注
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "备注")
    @Column(name = "remarks", length = 1000)
    private String remarks;

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

    @ManyToOne
    @JsonIgnoreProperties("verifyRecs")
    private ParaUser createdUser;

    @ManyToOne
    @JsonIgnoreProperties("verifyRecs")
    private ParaUser modifiedUser;

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

    public VerifyRec name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescString() {
        return descString;
    }

    public VerifyRec descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public Boolean isVerifyResult() {
        return verifyResult;
    }

    public VerifyRec verifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
        return this;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getVerifyScore() {
        return verifyScore;
    }

    public VerifyRec verifyScore(Integer verifyScore) {
        this.verifyScore = verifyScore;
        return this;
    }

    public void setVerifyScore(Integer verifyScore) {
        this.verifyScore = verifyScore;
    }

    public String getRemarks() {
        return remarks;
    }

    public VerifyRec remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public VerifyRec createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public VerifyRec modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public ParaUser getCreatedUser() {
        return createdUser;
    }

    public VerifyRec createdUser(ParaUser paraUser) {
        this.createdUser = paraUser;
        return this;
    }

    public void setCreatedUser(ParaUser paraUser) {
        this.createdUser = paraUser;
    }

    public ParaUser getModifiedUser() {
        return modifiedUser;
    }

    public VerifyRec modifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
        return this;
    }

    public void setModifiedUser(ParaUser paraUser) {
        this.modifiedUser = paraUser;
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
        VerifyRec verifyRec = (VerifyRec) o;
        if (verifyRec.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), verifyRec.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VerifyRec{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descString='" + getDescString() + "'" +
            ", verifyResult='" + isVerifyResult() + "'" +
            ", verifyScore=" + getVerifyScore() +
            ", remarks='" + getRemarks() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
