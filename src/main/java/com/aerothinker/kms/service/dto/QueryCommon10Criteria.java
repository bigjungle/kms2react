package com.aerothinker.kms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the QueryCommon10 entity. This class is used in QueryCommon10Resource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /query-common-10-s?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QueryCommon10Criteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter queryName;

    private InstantFilter queryDate;

    private StringFilter queryUser;

    private StringFilter col01;

    private StringFilter col02;

    private StringFilter col03;

    private StringFilter col04;

    private StringFilter col05;

    private StringFilter col06;

    private StringFilter col07;

    private StringFilter col08;

    private StringFilter col09;

    private StringFilter col10;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQueryName() {
        return queryName;
    }

    public void setQueryName(StringFilter queryName) {
        this.queryName = queryName;
    }

    public InstantFilter getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(InstantFilter queryDate) {
        this.queryDate = queryDate;
    }

    public StringFilter getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(StringFilter queryUser) {
        this.queryUser = queryUser;
    }

    public StringFilter getCol01() {
        return col01;
    }

    public void setCol01(StringFilter col01) {
        this.col01 = col01;
    }

    public StringFilter getCol02() {
        return col02;
    }

    public void setCol02(StringFilter col02) {
        this.col02 = col02;
    }

    public StringFilter getCol03() {
        return col03;
    }

    public void setCol03(StringFilter col03) {
        this.col03 = col03;
    }

    public StringFilter getCol04() {
        return col04;
    }

    public void setCol04(StringFilter col04) {
        this.col04 = col04;
    }

    public StringFilter getCol05() {
        return col05;
    }

    public void setCol05(StringFilter col05) {
        this.col05 = col05;
    }

    public StringFilter getCol06() {
        return col06;
    }

    public void setCol06(StringFilter col06) {
        this.col06 = col06;
    }

    public StringFilter getCol07() {
        return col07;
    }

    public void setCol07(StringFilter col07) {
        this.col07 = col07;
    }

    public StringFilter getCol08() {
        return col08;
    }

    public void setCol08(StringFilter col08) {
        this.col08 = col08;
    }

    public StringFilter getCol09() {
        return col09;
    }

    public void setCol09(StringFilter col09) {
        this.col09 = col09;
    }

    public StringFilter getCol10() {
        return col10;
    }

    public void setCol10(StringFilter col10) {
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
        final QueryCommon10Criteria that = (QueryCommon10Criteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(queryName, that.queryName) &&
            Objects.equals(queryDate, that.queryDate) &&
            Objects.equals(queryUser, that.queryUser) &&
            Objects.equals(col01, that.col01) &&
            Objects.equals(col02, that.col02) &&
            Objects.equals(col03, that.col03) &&
            Objects.equals(col04, that.col04) &&
            Objects.equals(col05, that.col05) &&
            Objects.equals(col06, that.col06) &&
            Objects.equals(col07, that.col07) &&
            Objects.equals(col08, that.col08) &&
            Objects.equals(col09, that.col09) &&
            Objects.equals(col10, that.col10);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        queryName,
        queryDate,
        queryUser,
        col01,
        col02,
        col03,
        col04,
        col05,
        col06,
        col07,
        col08,
        col09,
        col10
        );
    }

    @Override
    public String toString() {
        return "QueryCommon10Criteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (queryName != null ? "queryName=" + queryName + ", " : "") +
                (queryDate != null ? "queryDate=" + queryDate + ", " : "") +
                (queryUser != null ? "queryUser=" + queryUser + ", " : "") +
                (col01 != null ? "col01=" + col01 + ", " : "") +
                (col02 != null ? "col02=" + col02 + ", " : "") +
                (col03 != null ? "col03=" + col03 + ", " : "") +
                (col04 != null ? "col04=" + col04 + ", " : "") +
                (col05 != null ? "col05=" + col05 + ", " : "") +
                (col06 != null ? "col06=" + col06 + ", " : "") +
                (col07 != null ? "col07=" + col07 + ", " : "") +
                (col08 != null ? "col08=" + col08 + ", " : "") +
                (col09 != null ? "col09=" + col09 + ", " : "") +
                (col10 != null ? "col10=" + col10 + ", " : "") +
            "}";
    }

}
