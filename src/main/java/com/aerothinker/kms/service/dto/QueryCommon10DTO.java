package com.aerothinker.kms.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the QueryCommon10 entity.
 */
public class QueryCommon10DTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String queryName;

    private Instant queryDate;

    @Size(max = 256)
    private String queryUser;

    @Size(max = 4000)
    private String col01;

    @Size(max = 4000)
    private String col02;

    @Size(max = 4000)
    private String col03;

    @Size(max = 4000)
    private String col04;

    @Size(max = 4000)
    private String col05;

    @Size(max = 4000)
    private String col06;

    @Size(max = 4000)
    private String col07;

    @Size(max = 4000)
    private String col08;

    @Size(max = 4000)
    private String col09;

    @Size(max = 4000)
    private String col10;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Instant getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Instant queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(String queryUser) {
        this.queryUser = queryUser;
    }

    public String getCol01() {
        return col01;
    }

    public void setCol01(String col01) {
        this.col01 = col01;
    }

    public String getCol02() {
        return col02;
    }

    public void setCol02(String col02) {
        this.col02 = col02;
    }

    public String getCol03() {
        return col03;
    }

    public void setCol03(String col03) {
        this.col03 = col03;
    }

    public String getCol04() {
        return col04;
    }

    public void setCol04(String col04) {
        this.col04 = col04;
    }

    public String getCol05() {
        return col05;
    }

    public void setCol05(String col05) {
        this.col05 = col05;
    }

    public String getCol06() {
        return col06;
    }

    public void setCol06(String col06) {
        this.col06 = col06;
    }

    public String getCol07() {
        return col07;
    }

    public void setCol07(String col07) {
        this.col07 = col07;
    }

    public String getCol08() {
        return col08;
    }

    public void setCol08(String col08) {
        this.col08 = col08;
    }

    public String getCol09() {
        return col09;
    }

    public void setCol09(String col09) {
        this.col09 = col09;
    }

    public String getCol10() {
        return col10;
    }

    public void setCol10(String col10) {
        this.col10 = col10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QueryCommon10DTO queryCommon10DTO = (QueryCommon10DTO) o;
        if (queryCommon10DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queryCommon10DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QueryCommon10DTO{" +
            "id=" + getId() +
            ", queryName='" + getQueryName() + "'" +
            ", queryDate='" + getQueryDate() + "'" +
            ", queryUser='" + getQueryUser() + "'" +
            ", col01='" + getCol01() + "'" +
            ", col02='" + getCol02() + "'" +
            ", col03='" + getCol03() + "'" +
            ", col04='" + getCol04() + "'" +
            ", col05='" + getCol05() + "'" +
            ", col06='" + getCol06() + "'" +
            ", col07='" + getCol07() + "'" +
            ", col08='" + getCol08() + "'" +
            ", col09='" + getCol09() + "'" +
            ", col10='" + getCol10() + "'" +
            "}";
    }
}
