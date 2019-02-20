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
 * QueryCommon50 通用查询50
 * @author JungleYang.
 */
@ApiModel(description = "QueryCommon50 通用查询50 @author JungleYang.")
@Entity
@Table(name = "query_common_50")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "querycommon50")
public class QueryCommon50 implements Serializable {

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

    @Size(max = 4000)
    @Column(name = "col_11", length = 4000)
    private String col11;

    @Size(max = 4000)
    @Column(name = "col_12", length = 4000)
    private String col12;

    @Size(max = 4000)
    @Column(name = "col_13", length = 4000)
    private String col13;

    @Size(max = 4000)
    @Column(name = "col_14", length = 4000)
    private String col14;

    @Size(max = 4000)
    @Column(name = "col_15", length = 4000)
    private String col15;

    @Size(max = 4000)
    @Column(name = "col_16", length = 4000)
    private String col16;

    @Size(max = 4000)
    @Column(name = "col_17", length = 4000)
    private String col17;

    @Size(max = 4000)
    @Column(name = "col_18", length = 4000)
    private String col18;

    @Size(max = 4000)
    @Column(name = "col_19", length = 4000)
    private String col19;

    @Size(max = 4000)
    @Column(name = "col_20", length = 4000)
    private String col20;

    @Size(max = 4000)
    @Column(name = "col_21", length = 4000)
    private String col21;

    @Size(max = 4000)
    @Column(name = "col_22", length = 4000)
    private String col22;

    @Size(max = 4000)
    @Column(name = "col_23", length = 4000)
    private String col23;

    @Size(max = 4000)
    @Column(name = "col_24", length = 4000)
    private String col24;

    @Size(max = 4000)
    @Column(name = "col_25", length = 4000)
    private String col25;

    @Size(max = 4000)
    @Column(name = "col_26", length = 4000)
    private String col26;

    @Size(max = 4000)
    @Column(name = "col_27", length = 4000)
    private String col27;

    @Size(max = 4000)
    @Column(name = "col_28", length = 4000)
    private String col28;

    @Size(max = 4000)
    @Column(name = "col_29", length = 4000)
    private String col29;

    @Size(max = 4000)
    @Column(name = "col_30", length = 4000)
    private String col30;

    @Size(max = 4000)
    @Column(name = "col_31", length = 4000)
    private String col31;

    @Size(max = 4000)
    @Column(name = "col_32", length = 4000)
    private String col32;

    @Size(max = 4000)
    @Column(name = "col_33", length = 4000)
    private String col33;

    @Size(max = 4000)
    @Column(name = "col_34", length = 4000)
    private String col34;

    @Size(max = 4000)
    @Column(name = "col_35", length = 4000)
    private String col35;

    @Size(max = 4000)
    @Column(name = "col_36", length = 4000)
    private String col36;

    @Size(max = 4000)
    @Column(name = "col_37", length = 4000)
    private String col37;

    @Size(max = 4000)
    @Column(name = "col_38", length = 4000)
    private String col38;

    @Size(max = 4000)
    @Column(name = "col_39", length = 4000)
    private String col39;

    @Size(max = 4000)
    @Column(name = "col_40", length = 4000)
    private String col40;

    @Size(max = 4000)
    @Column(name = "col_41", length = 4000)
    private String col41;

    @Size(max = 4000)
    @Column(name = "col_42", length = 4000)
    private String col42;

    @Size(max = 4000)
    @Column(name = "col_43", length = 4000)
    private String col43;

    @Size(max = 4000)
    @Column(name = "col_44", length = 4000)
    private String col44;

    @Size(max = 4000)
    @Column(name = "col_45", length = 4000)
    private String col45;

    @Size(max = 4000)
    @Column(name = "col_46", length = 4000)
    private String col46;

    @Size(max = 4000)
    @Column(name = "col_47", length = 4000)
    private String col47;

    @Size(max = 4000)
    @Column(name = "col_48", length = 4000)
    private String col48;

    @Size(max = 4000)
    @Column(name = "col_49", length = 4000)
    private String col49;

    @Size(max = 4000)
    @Column(name = "col_50", length = 4000)
    private String col50;

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

    public QueryCommon50 queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Instant getQueryDate() {
        return queryDate;
    }

    public QueryCommon50 queryDate(Instant queryDate) {
        this.queryDate = queryDate;
        return this;
    }

    public void setQueryDate(Instant queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryUser() {
        return queryUser;
    }

    public QueryCommon50 queryUser(String queryUser) {
        this.queryUser = queryUser;
        return this;
    }

    public void setQueryUser(String queryUser) {
        this.queryUser = queryUser;
    }

    public String getCol01() {
        return col01;
    }

    public QueryCommon50 col01(String col01) {
        this.col01 = col01;
        return this;
    }

    public void setCol01(String col01) {
        this.col01 = col01;
    }

    public String getCol02() {
        return col02;
    }

    public QueryCommon50 col02(String col02) {
        this.col02 = col02;
        return this;
    }

    public void setCol02(String col02) {
        this.col02 = col02;
    }

    public String getCol03() {
        return col03;
    }

    public QueryCommon50 col03(String col03) {
        this.col03 = col03;
        return this;
    }

    public void setCol03(String col03) {
        this.col03 = col03;
    }

    public String getCol04() {
        return col04;
    }

    public QueryCommon50 col04(String col04) {
        this.col04 = col04;
        return this;
    }

    public void setCol04(String col04) {
        this.col04 = col04;
    }

    public String getCol05() {
        return col05;
    }

    public QueryCommon50 col05(String col05) {
        this.col05 = col05;
        return this;
    }

    public void setCol05(String col05) {
        this.col05 = col05;
    }

    public String getCol06() {
        return col06;
    }

    public QueryCommon50 col06(String col06) {
        this.col06 = col06;
        return this;
    }

    public void setCol06(String col06) {
        this.col06 = col06;
    }

    public String getCol07() {
        return col07;
    }

    public QueryCommon50 col07(String col07) {
        this.col07 = col07;
        return this;
    }

    public void setCol07(String col07) {
        this.col07 = col07;
    }

    public String getCol08() {
        return col08;
    }

    public QueryCommon50 col08(String col08) {
        this.col08 = col08;
        return this;
    }

    public void setCol08(String col08) {
        this.col08 = col08;
    }

    public String getCol09() {
        return col09;
    }

    public QueryCommon50 col09(String col09) {
        this.col09 = col09;
        return this;
    }

    public void setCol09(String col09) {
        this.col09 = col09;
    }

    public String getCol10() {
        return col10;
    }

    public QueryCommon50 col10(String col10) {
        this.col10 = col10;
        return this;
    }

    public void setCol10(String col10) {
        this.col10 = col10;
    }

    public String getCol11() {
        return col11;
    }

    public QueryCommon50 col11(String col11) {
        this.col11 = col11;
        return this;
    }

    public void setCol11(String col11) {
        this.col11 = col11;
    }

    public String getCol12() {
        return col12;
    }

    public QueryCommon50 col12(String col12) {
        this.col12 = col12;
        return this;
    }

    public void setCol12(String col12) {
        this.col12 = col12;
    }

    public String getCol13() {
        return col13;
    }

    public QueryCommon50 col13(String col13) {
        this.col13 = col13;
        return this;
    }

    public void setCol13(String col13) {
        this.col13 = col13;
    }

    public String getCol14() {
        return col14;
    }

    public QueryCommon50 col14(String col14) {
        this.col14 = col14;
        return this;
    }

    public void setCol14(String col14) {
        this.col14 = col14;
    }

    public String getCol15() {
        return col15;
    }

    public QueryCommon50 col15(String col15) {
        this.col15 = col15;
        return this;
    }

    public void setCol15(String col15) {
        this.col15 = col15;
    }

    public String getCol16() {
        return col16;
    }

    public QueryCommon50 col16(String col16) {
        this.col16 = col16;
        return this;
    }

    public void setCol16(String col16) {
        this.col16 = col16;
    }

    public String getCol17() {
        return col17;
    }

    public QueryCommon50 col17(String col17) {
        this.col17 = col17;
        return this;
    }

    public void setCol17(String col17) {
        this.col17 = col17;
    }

    public String getCol18() {
        return col18;
    }

    public QueryCommon50 col18(String col18) {
        this.col18 = col18;
        return this;
    }

    public void setCol18(String col18) {
        this.col18 = col18;
    }

    public String getCol19() {
        return col19;
    }

    public QueryCommon50 col19(String col19) {
        this.col19 = col19;
        return this;
    }

    public void setCol19(String col19) {
        this.col19 = col19;
    }

    public String getCol20() {
        return col20;
    }

    public QueryCommon50 col20(String col20) {
        this.col20 = col20;
        return this;
    }

    public void setCol20(String col20) {
        this.col20 = col20;
    }

    public String getCol21() {
        return col21;
    }

    public QueryCommon50 col21(String col21) {
        this.col21 = col21;
        return this;
    }

    public void setCol21(String col21) {
        this.col21 = col21;
    }

    public String getCol22() {
        return col22;
    }

    public QueryCommon50 col22(String col22) {
        this.col22 = col22;
        return this;
    }

    public void setCol22(String col22) {
        this.col22 = col22;
    }

    public String getCol23() {
        return col23;
    }

    public QueryCommon50 col23(String col23) {
        this.col23 = col23;
        return this;
    }

    public void setCol23(String col23) {
        this.col23 = col23;
    }

    public String getCol24() {
        return col24;
    }

    public QueryCommon50 col24(String col24) {
        this.col24 = col24;
        return this;
    }

    public void setCol24(String col24) {
        this.col24 = col24;
    }

    public String getCol25() {
        return col25;
    }

    public QueryCommon50 col25(String col25) {
        this.col25 = col25;
        return this;
    }

    public void setCol25(String col25) {
        this.col25 = col25;
    }

    public String getCol26() {
        return col26;
    }

    public QueryCommon50 col26(String col26) {
        this.col26 = col26;
        return this;
    }

    public void setCol26(String col26) {
        this.col26 = col26;
    }

    public String getCol27() {
        return col27;
    }

    public QueryCommon50 col27(String col27) {
        this.col27 = col27;
        return this;
    }

    public void setCol27(String col27) {
        this.col27 = col27;
    }

    public String getCol28() {
        return col28;
    }

    public QueryCommon50 col28(String col28) {
        this.col28 = col28;
        return this;
    }

    public void setCol28(String col28) {
        this.col28 = col28;
    }

    public String getCol29() {
        return col29;
    }

    public QueryCommon50 col29(String col29) {
        this.col29 = col29;
        return this;
    }

    public void setCol29(String col29) {
        this.col29 = col29;
    }

    public String getCol30() {
        return col30;
    }

    public QueryCommon50 col30(String col30) {
        this.col30 = col30;
        return this;
    }

    public void setCol30(String col30) {
        this.col30 = col30;
    }

    public String getCol31() {
        return col31;
    }

    public QueryCommon50 col31(String col31) {
        this.col31 = col31;
        return this;
    }

    public void setCol31(String col31) {
        this.col31 = col31;
    }

    public String getCol32() {
        return col32;
    }

    public QueryCommon50 col32(String col32) {
        this.col32 = col32;
        return this;
    }

    public void setCol32(String col32) {
        this.col32 = col32;
    }

    public String getCol33() {
        return col33;
    }

    public QueryCommon50 col33(String col33) {
        this.col33 = col33;
        return this;
    }

    public void setCol33(String col33) {
        this.col33 = col33;
    }

    public String getCol34() {
        return col34;
    }

    public QueryCommon50 col34(String col34) {
        this.col34 = col34;
        return this;
    }

    public void setCol34(String col34) {
        this.col34 = col34;
    }

    public String getCol35() {
        return col35;
    }

    public QueryCommon50 col35(String col35) {
        this.col35 = col35;
        return this;
    }

    public void setCol35(String col35) {
        this.col35 = col35;
    }

    public String getCol36() {
        return col36;
    }

    public QueryCommon50 col36(String col36) {
        this.col36 = col36;
        return this;
    }

    public void setCol36(String col36) {
        this.col36 = col36;
    }

    public String getCol37() {
        return col37;
    }

    public QueryCommon50 col37(String col37) {
        this.col37 = col37;
        return this;
    }

    public void setCol37(String col37) {
        this.col37 = col37;
    }

    public String getCol38() {
        return col38;
    }

    public QueryCommon50 col38(String col38) {
        this.col38 = col38;
        return this;
    }

    public void setCol38(String col38) {
        this.col38 = col38;
    }

    public String getCol39() {
        return col39;
    }

    public QueryCommon50 col39(String col39) {
        this.col39 = col39;
        return this;
    }

    public void setCol39(String col39) {
        this.col39 = col39;
    }

    public String getCol40() {
        return col40;
    }

    public QueryCommon50 col40(String col40) {
        this.col40 = col40;
        return this;
    }

    public void setCol40(String col40) {
        this.col40 = col40;
    }

    public String getCol41() {
        return col41;
    }

    public QueryCommon50 col41(String col41) {
        this.col41 = col41;
        return this;
    }

    public void setCol41(String col41) {
        this.col41 = col41;
    }

    public String getCol42() {
        return col42;
    }

    public QueryCommon50 col42(String col42) {
        this.col42 = col42;
        return this;
    }

    public void setCol42(String col42) {
        this.col42 = col42;
    }

    public String getCol43() {
        return col43;
    }

    public QueryCommon50 col43(String col43) {
        this.col43 = col43;
        return this;
    }

    public void setCol43(String col43) {
        this.col43 = col43;
    }

    public String getCol44() {
        return col44;
    }

    public QueryCommon50 col44(String col44) {
        this.col44 = col44;
        return this;
    }

    public void setCol44(String col44) {
        this.col44 = col44;
    }

    public String getCol45() {
        return col45;
    }

    public QueryCommon50 col45(String col45) {
        this.col45 = col45;
        return this;
    }

    public void setCol45(String col45) {
        this.col45 = col45;
    }

    public String getCol46() {
        return col46;
    }

    public QueryCommon50 col46(String col46) {
        this.col46 = col46;
        return this;
    }

    public void setCol46(String col46) {
        this.col46 = col46;
    }

    public String getCol47() {
        return col47;
    }

    public QueryCommon50 col47(String col47) {
        this.col47 = col47;
        return this;
    }

    public void setCol47(String col47) {
        this.col47 = col47;
    }

    public String getCol48() {
        return col48;
    }

    public QueryCommon50 col48(String col48) {
        this.col48 = col48;
        return this;
    }

    public void setCol48(String col48) {
        this.col48 = col48;
    }

    public String getCol49() {
        return col49;
    }

    public QueryCommon50 col49(String col49) {
        this.col49 = col49;
        return this;
    }

    public void setCol49(String col49) {
        this.col49 = col49;
    }

    public String getCol50() {
        return col50;
    }

    public QueryCommon50 col50(String col50) {
        this.col50 = col50;
        return this;
    }

    public void setCol50(String col50) {
        this.col50 = col50;
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
        QueryCommon50 queryCommon50 = (QueryCommon50) o;
        if (queryCommon50.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queryCommon50.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QueryCommon50{" +
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
            ", col11='" + getCol11() + "'" +
            ", col12='" + getCol12() + "'" +
            ", col13='" + getCol13() + "'" +
            ", col14='" + getCol14() + "'" +
            ", col15='" + getCol15() + "'" +
            ", col16='" + getCol16() + "'" +
            ", col17='" + getCol17() + "'" +
            ", col18='" + getCol18() + "'" +
            ", col19='" + getCol19() + "'" +
            ", col20='" + getCol20() + "'" +
            ", col21='" + getCol21() + "'" +
            ", col22='" + getCol22() + "'" +
            ", col23='" + getCol23() + "'" +
            ", col24='" + getCol24() + "'" +
            ", col25='" + getCol25() + "'" +
            ", col26='" + getCol26() + "'" +
            ", col27='" + getCol27() + "'" +
            ", col28='" + getCol28() + "'" +
            ", col29='" + getCol29() + "'" +
            ", col30='" + getCol30() + "'" +
            ", col31='" + getCol31() + "'" +
            ", col32='" + getCol32() + "'" +
            ", col33='" + getCol33() + "'" +
            ", col34='" + getCol34() + "'" +
            ", col35='" + getCol35() + "'" +
            ", col36='" + getCol36() + "'" +
            ", col37='" + getCol37() + "'" +
            ", col38='" + getCol38() + "'" +
            ", col39='" + getCol39() + "'" +
            ", col40='" + getCol40() + "'" +
            ", col41='" + getCol41() + "'" +
            ", col42='" + getCol42() + "'" +
            ", col43='" + getCol43() + "'" +
            ", col44='" + getCol44() + "'" +
            ", col45='" + getCol45() + "'" +
            ", col46='" + getCol46() + "'" +
            ", col47='" + getCol47() + "'" +
            ", col48='" + getCol48() + "'" +
            ", col49='" + getCol49() + "'" +
            ", col50='" + getCol50() + "'" +
            "}";
    }
}
