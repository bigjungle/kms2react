package com.aerothinker.kms.domain;


import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * QueryCommon10 通用查询10
 * @author JungleYang.
 */
@ApiModel(description = "QueryCommon10 通用查询10 @author JungleYang.")
@Entity
@Table(name = "query_common_10")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "querycommon10")
public class QueryCommon10 implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 256)
    @Column(name = "query_name", length = 256)
    private String queryName;

    @Column(name = "query_date")
    private Instant queryDate;

    @Size(max = 256)
    @Column(name = "query_user", length = 256)
    private String queryUser;

    @Size(max = 4000)
    @Column(name = "col_01", length = 4000)
    private String col01;

    @Size(max = 4000)
    @Column(name = "col_02", length = 4000)
    private String col02;

    @Size(max = 4000)
    @Column(name = "col_03", length = 4000)
    private String col03;

    @Size(max = 4000)
    @Column(name = "col_04", length = 4000)
    private String col04;

    @Size(max = 4000)
    @Column(name = "col_05", length = 4000)
    private String col05;

    @Size(max = 4000)
    @Column(name = "col_06", length = 4000)
    private String col06;

    @Size(max = 4000)
    @Column(name = "col_07", length = 4000)
    private String col07;

    @Size(max = 4000)
    @Column(name = "col_08", length = 4000)
    private String col08;

    @Size(max = 4000)
    @Column(name = "col_09", length = 4000)
    private String col09;

    @Size(max = 4000)
    @Column(name = "col_10", length = 4000)
    private String col10;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueryName() {
        return queryName;
    }

    public QueryCommon10 queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Instant getQueryDate() {
        return queryDate;
    }

    public QueryCommon10 queryDate(Instant queryDate) {
        this.queryDate = queryDate;
        return this;
    }

    public void setQueryDate(Instant queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryUser() {
        return queryUser;
    }

    public QueryCommon10 queryUser(String queryUser) {
        this.queryUser = queryUser;
        return this;
    }

    public void setQueryUser(String queryUser) {
        this.queryUser = queryUser;
    }

    public String getCol01() {
        return col01;
    }

    public QueryCommon10 col01(String col01) {
        this.col01 = col01;
        return this;
    }

    public void setCol01(String col01) {
        this.col01 = col01;
    }

    public String getCol02() {
        return col02;
    }

    public QueryCommon10 col02(String col02) {
        this.col02 = col02;
        return this;
    }

    public void setCol02(String col02) {
        this.col02 = col02;
    }

    public String getCol03() {
        return col03;
    }

    public QueryCommon10 col03(String col03) {
        this.col03 = col03;
        return this;
    }

    public void setCol03(String col03) {
        this.col03 = col03;
    }

    public String getCol04() {
        return col04;
    }

    public QueryCommon10 col04(String col04) {
        this.col04 = col04;
        return this;
    }

    public void setCol04(String col04) {
        this.col04 = col04;
    }

    public String getCol05() {
        return col05;
    }

    public QueryCommon10 col05(String col05) {
        this.col05 = col05;
        return this;
    }

    public void setCol05(String col05) {
        this.col05 = col05;
    }

    public String getCol06() {
        return col06;
    }

    public QueryCommon10 col06(String col06) {
        this.col06 = col06;
        return this;
    }

    public void setCol06(String col06) {
        this.col06 = col06;
    }

    public String getCol07() {
        return col07;
    }

    public QueryCommon10 col07(String col07) {
        this.col07 = col07;
        return this;
    }

    public void setCol07(String col07) {
        this.col07 = col07;
    }

    public String getCol08() {
        return col08;
    }

    public QueryCommon10 col08(String col08) {
        this.col08 = col08;
        return this;
    }

    public void setCol08(String col08) {
        this.col08 = col08;
    }

    public String getCol09() {
        return col09;
    }

    public QueryCommon10 col09(String col09) {
        this.col09 = col09;
        return this;
    }

    public void setCol09(String col09) {
        this.col09 = col09;
    }

    public String getCol10() {
        return col10;
    }

    public QueryCommon10 col10(String col10) {
        this.col10 = col10;
        return this;
    }

    public void setCol10(String col10) {
        this.col10 = col10;
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
        QueryCommon10 queryCommon10 = (QueryCommon10) o;
        if (queryCommon10.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queryCommon10.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QueryCommon10{" +
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
