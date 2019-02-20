package com.aerothinker.kms.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VerifyRec entity.
 */
public class VerifyRecDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String name;

    @Size(max = 256)
    private String descString;

    private Boolean verifyResult;

    private Integer verifyScore;

    @Size(max = 1000)
    private String remarks;

    private Instant createTime;

    private Instant modifyTime;

    private Long createdUserId;

    private String createdUserName;

    private Long modifiedUserId;

    private String modifiedUserName;

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

    public String getDescString() {
        return descString;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public Boolean isVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getVerifyScore() {
        return verifyScore;
    }

    public void setVerifyScore(Integer verifyScore) {
        this.verifyScore = verifyScore;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VerifyRecDTO verifyRecDTO = (VerifyRecDTO) o;
        if (verifyRecDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), verifyRecDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VerifyRecDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descString='" + getDescString() + "'" +
            ", verifyResult='" + isVerifyResult() + "'" +
            ", verifyScore=" + getVerifyScore() +
            ", remarks='" + getRemarks() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            ", createdUser=" + getCreatedUserId() +
            ", createdUser='" + getCreatedUserName() + "'" +
            ", modifiedUser=" + getModifiedUserId() +
            ", modifiedUser='" + getModifiedUserName() + "'" +
            "}";
    }
}
