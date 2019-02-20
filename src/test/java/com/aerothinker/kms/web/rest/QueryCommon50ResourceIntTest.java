package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.QueryCommon50;
import com.aerothinker.kms.repository.QueryCommon50Repository;
import com.aerothinker.kms.repository.search.QueryCommon50SearchRepository;
import com.aerothinker.kms.service.QueryCommon50Service;
import com.aerothinker.kms.service.dto.QueryCommon50DTO;
import com.aerothinker.kms.service.mapper.QueryCommon50Mapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.QueryCommon50Criteria;
import com.aerothinker.kms.service.QueryCommon50QueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.aerothinker.kms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QueryCommon50Resource REST controller.
 *
 * @see QueryCommon50Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class QueryCommon50ResourceIntTest {

    private static final String DEFAULT_QUERY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_QUERY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_QUERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QUERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_QUERY_USER = "AAAAAAAAAA";
    private static final String UPDATED_QUERY_USER = "BBBBBBBBBB";

    private static final String DEFAULT_COL_01 = "AAAAAAAAAA";
    private static final String UPDATED_COL_01 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_02 = "AAAAAAAAAA";
    private static final String UPDATED_COL_02 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_03 = "AAAAAAAAAA";
    private static final String UPDATED_COL_03 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_04 = "AAAAAAAAAA";
    private static final String UPDATED_COL_04 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_05 = "AAAAAAAAAA";
    private static final String UPDATED_COL_05 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_06 = "AAAAAAAAAA";
    private static final String UPDATED_COL_06 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_07 = "AAAAAAAAAA";
    private static final String UPDATED_COL_07 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_08 = "AAAAAAAAAA";
    private static final String UPDATED_COL_08 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_09 = "AAAAAAAAAA";
    private static final String UPDATED_COL_09 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_10 = "AAAAAAAAAA";
    private static final String UPDATED_COL_10 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_11 = "AAAAAAAAAA";
    private static final String UPDATED_COL_11 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_12 = "AAAAAAAAAA";
    private static final String UPDATED_COL_12 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_13 = "AAAAAAAAAA";
    private static final String UPDATED_COL_13 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_14 = "AAAAAAAAAA";
    private static final String UPDATED_COL_14 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_15 = "AAAAAAAAAA";
    private static final String UPDATED_COL_15 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_16 = "AAAAAAAAAA";
    private static final String UPDATED_COL_16 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_17 = "AAAAAAAAAA";
    private static final String UPDATED_COL_17 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_18 = "AAAAAAAAAA";
    private static final String UPDATED_COL_18 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_19 = "AAAAAAAAAA";
    private static final String UPDATED_COL_19 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_20 = "AAAAAAAAAA";
    private static final String UPDATED_COL_20 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_21 = "AAAAAAAAAA";
    private static final String UPDATED_COL_21 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_22 = "AAAAAAAAAA";
    private static final String UPDATED_COL_22 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_23 = "AAAAAAAAAA";
    private static final String UPDATED_COL_23 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_24 = "AAAAAAAAAA";
    private static final String UPDATED_COL_24 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_25 = "AAAAAAAAAA";
    private static final String UPDATED_COL_25 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_26 = "AAAAAAAAAA";
    private static final String UPDATED_COL_26 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_27 = "AAAAAAAAAA";
    private static final String UPDATED_COL_27 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_28 = "AAAAAAAAAA";
    private static final String UPDATED_COL_28 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_29 = "AAAAAAAAAA";
    private static final String UPDATED_COL_29 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_30 = "AAAAAAAAAA";
    private static final String UPDATED_COL_30 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_31 = "AAAAAAAAAA";
    private static final String UPDATED_COL_31 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_32 = "AAAAAAAAAA";
    private static final String UPDATED_COL_32 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_33 = "AAAAAAAAAA";
    private static final String UPDATED_COL_33 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_34 = "AAAAAAAAAA";
    private static final String UPDATED_COL_34 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_35 = "AAAAAAAAAA";
    private static final String UPDATED_COL_35 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_36 = "AAAAAAAAAA";
    private static final String UPDATED_COL_36 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_37 = "AAAAAAAAAA";
    private static final String UPDATED_COL_37 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_38 = "AAAAAAAAAA";
    private static final String UPDATED_COL_38 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_39 = "AAAAAAAAAA";
    private static final String UPDATED_COL_39 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_40 = "AAAAAAAAAA";
    private static final String UPDATED_COL_40 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_41 = "AAAAAAAAAA";
    private static final String UPDATED_COL_41 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_42 = "AAAAAAAAAA";
    private static final String UPDATED_COL_42 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_43 = "AAAAAAAAAA";
    private static final String UPDATED_COL_43 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_44 = "AAAAAAAAAA";
    private static final String UPDATED_COL_44 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_45 = "AAAAAAAAAA";
    private static final String UPDATED_COL_45 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_46 = "AAAAAAAAAA";
    private static final String UPDATED_COL_46 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_47 = "AAAAAAAAAA";
    private static final String UPDATED_COL_47 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_48 = "AAAAAAAAAA";
    private static final String UPDATED_COL_48 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_49 = "AAAAAAAAAA";
    private static final String UPDATED_COL_49 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_50 = "AAAAAAAAAA";
    private static final String UPDATED_COL_50 = "BBBBBBBBBB";

    @Autowired
    private QueryCommon50Repository queryCommon50Repository;

    @Autowired
    private QueryCommon50Mapper queryCommon50Mapper;

    @Autowired
    private QueryCommon50Service queryCommon50Service;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.QueryCommon50SearchRepositoryMockConfiguration
     */
    @Autowired
    private QueryCommon50SearchRepository mockQueryCommon50SearchRepository;

    @Autowired
    private QueryCommon50QueryService queryCommon50QueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restQueryCommon50MockMvc;

    private QueryCommon50 queryCommon50;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QueryCommon50Resource queryCommon50Resource = new QueryCommon50Resource(queryCommon50Service, queryCommon50QueryService);
        this.restQueryCommon50MockMvc = MockMvcBuilders.standaloneSetup(queryCommon50Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QueryCommon50 createEntity(EntityManager em) {
        QueryCommon50 queryCommon50 = new QueryCommon50()
            .queryName(DEFAULT_QUERY_NAME)
            .queryDate(DEFAULT_QUERY_DATE)
            .queryUser(DEFAULT_QUERY_USER)
            .col01(DEFAULT_COL_01)
            .col02(DEFAULT_COL_02)
            .col03(DEFAULT_COL_03)
            .col04(DEFAULT_COL_04)
            .col05(DEFAULT_COL_05)
            .col06(DEFAULT_COL_06)
            .col07(DEFAULT_COL_07)
            .col08(DEFAULT_COL_08)
            .col09(DEFAULT_COL_09)
            .col10(DEFAULT_COL_10)
            .col11(DEFAULT_COL_11)
            .col12(DEFAULT_COL_12)
            .col13(DEFAULT_COL_13)
            .col14(DEFAULT_COL_14)
            .col15(DEFAULT_COL_15)
            .col16(DEFAULT_COL_16)
            .col17(DEFAULT_COL_17)
            .col18(DEFAULT_COL_18)
            .col19(DEFAULT_COL_19)
            .col20(DEFAULT_COL_20)
            .col21(DEFAULT_COL_21)
            .col22(DEFAULT_COL_22)
            .col23(DEFAULT_COL_23)
            .col24(DEFAULT_COL_24)
            .col25(DEFAULT_COL_25)
            .col26(DEFAULT_COL_26)
            .col27(DEFAULT_COL_27)
            .col28(DEFAULT_COL_28)
            .col29(DEFAULT_COL_29)
            .col30(DEFAULT_COL_30)
            .col31(DEFAULT_COL_31)
            .col32(DEFAULT_COL_32)
            .col33(DEFAULT_COL_33)
            .col34(DEFAULT_COL_34)
            .col35(DEFAULT_COL_35)
            .col36(DEFAULT_COL_36)
            .col37(DEFAULT_COL_37)
            .col38(DEFAULT_COL_38)
            .col39(DEFAULT_COL_39)
            .col40(DEFAULT_COL_40)
            .col41(DEFAULT_COL_41)
            .col42(DEFAULT_COL_42)
            .col43(DEFAULT_COL_43)
            .col44(DEFAULT_COL_44)
            .col45(DEFAULT_COL_45)
            .col46(DEFAULT_COL_46)
            .col47(DEFAULT_COL_47)
            .col48(DEFAULT_COL_48)
            .col49(DEFAULT_COL_49)
            .col50(DEFAULT_COL_50);
        return queryCommon50;
    }

    @Before
    public void initTest() {
        queryCommon50 = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueryCommon50() throws Exception {
        int databaseSizeBeforeCreate = queryCommon50Repository.findAll().size();

        // Create the QueryCommon50
        QueryCommon50DTO queryCommon50DTO = queryCommon50Mapper.toDto(queryCommon50);
        restQueryCommon50MockMvc.perform(post("/api/query-common-50-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon50DTO)))
            .andExpect(status().isCreated());

        // Validate the QueryCommon50 in the database
        List<QueryCommon50> queryCommon50List = queryCommon50Repository.findAll();
        assertThat(queryCommon50List).hasSize(databaseSizeBeforeCreate + 1);
        QueryCommon50 testQueryCommon50 = queryCommon50List.get(queryCommon50List.size() - 1);
        assertThat(testQueryCommon50.getQueryName()).isEqualTo(DEFAULT_QUERY_NAME);
        assertThat(testQueryCommon50.getQueryDate()).isEqualTo(DEFAULT_QUERY_DATE);
        assertThat(testQueryCommon50.getQueryUser()).isEqualTo(DEFAULT_QUERY_USER);
        assertThat(testQueryCommon50.getCol01()).isEqualTo(DEFAULT_COL_01);
        assertThat(testQueryCommon50.getCol02()).isEqualTo(DEFAULT_COL_02);
        assertThat(testQueryCommon50.getCol03()).isEqualTo(DEFAULT_COL_03);
        assertThat(testQueryCommon50.getCol04()).isEqualTo(DEFAULT_COL_04);
        assertThat(testQueryCommon50.getCol05()).isEqualTo(DEFAULT_COL_05);
        assertThat(testQueryCommon50.getCol06()).isEqualTo(DEFAULT_COL_06);
        assertThat(testQueryCommon50.getCol07()).isEqualTo(DEFAULT_COL_07);
        assertThat(testQueryCommon50.getCol08()).isEqualTo(DEFAULT_COL_08);
        assertThat(testQueryCommon50.getCol09()).isEqualTo(DEFAULT_COL_09);
        assertThat(testQueryCommon50.getCol10()).isEqualTo(DEFAULT_COL_10);
        assertThat(testQueryCommon50.getCol11()).isEqualTo(DEFAULT_COL_11);
        assertThat(testQueryCommon50.getCol12()).isEqualTo(DEFAULT_COL_12);
        assertThat(testQueryCommon50.getCol13()).isEqualTo(DEFAULT_COL_13);
        assertThat(testQueryCommon50.getCol14()).isEqualTo(DEFAULT_COL_14);
        assertThat(testQueryCommon50.getCol15()).isEqualTo(DEFAULT_COL_15);
        assertThat(testQueryCommon50.getCol16()).isEqualTo(DEFAULT_COL_16);
        assertThat(testQueryCommon50.getCol17()).isEqualTo(DEFAULT_COL_17);
        assertThat(testQueryCommon50.getCol18()).isEqualTo(DEFAULT_COL_18);
        assertThat(testQueryCommon50.getCol19()).isEqualTo(DEFAULT_COL_19);
        assertThat(testQueryCommon50.getCol20()).isEqualTo(DEFAULT_COL_20);
        assertThat(testQueryCommon50.getCol21()).isEqualTo(DEFAULT_COL_21);
        assertThat(testQueryCommon50.getCol22()).isEqualTo(DEFAULT_COL_22);
        assertThat(testQueryCommon50.getCol23()).isEqualTo(DEFAULT_COL_23);
        assertThat(testQueryCommon50.getCol24()).isEqualTo(DEFAULT_COL_24);
        assertThat(testQueryCommon50.getCol25()).isEqualTo(DEFAULT_COL_25);
        assertThat(testQueryCommon50.getCol26()).isEqualTo(DEFAULT_COL_26);
        assertThat(testQueryCommon50.getCol27()).isEqualTo(DEFAULT_COL_27);
        assertThat(testQueryCommon50.getCol28()).isEqualTo(DEFAULT_COL_28);
        assertThat(testQueryCommon50.getCol29()).isEqualTo(DEFAULT_COL_29);
        assertThat(testQueryCommon50.getCol30()).isEqualTo(DEFAULT_COL_30);
        assertThat(testQueryCommon50.getCol31()).isEqualTo(DEFAULT_COL_31);
        assertThat(testQueryCommon50.getCol32()).isEqualTo(DEFAULT_COL_32);
        assertThat(testQueryCommon50.getCol33()).isEqualTo(DEFAULT_COL_33);
        assertThat(testQueryCommon50.getCol34()).isEqualTo(DEFAULT_COL_34);
        assertThat(testQueryCommon50.getCol35()).isEqualTo(DEFAULT_COL_35);
        assertThat(testQueryCommon50.getCol36()).isEqualTo(DEFAULT_COL_36);
        assertThat(testQueryCommon50.getCol37()).isEqualTo(DEFAULT_COL_37);
        assertThat(testQueryCommon50.getCol38()).isEqualTo(DEFAULT_COL_38);
        assertThat(testQueryCommon50.getCol39()).isEqualTo(DEFAULT_COL_39);
        assertThat(testQueryCommon50.getCol40()).isEqualTo(DEFAULT_COL_40);
        assertThat(testQueryCommon50.getCol41()).isEqualTo(DEFAULT_COL_41);
        assertThat(testQueryCommon50.getCol42()).isEqualTo(DEFAULT_COL_42);
        assertThat(testQueryCommon50.getCol43()).isEqualTo(DEFAULT_COL_43);
        assertThat(testQueryCommon50.getCol44()).isEqualTo(DEFAULT_COL_44);
        assertThat(testQueryCommon50.getCol45()).isEqualTo(DEFAULT_COL_45);
        assertThat(testQueryCommon50.getCol46()).isEqualTo(DEFAULT_COL_46);
        assertThat(testQueryCommon50.getCol47()).isEqualTo(DEFAULT_COL_47);
        assertThat(testQueryCommon50.getCol48()).isEqualTo(DEFAULT_COL_48);
        assertThat(testQueryCommon50.getCol49()).isEqualTo(DEFAULT_COL_49);
        assertThat(testQueryCommon50.getCol50()).isEqualTo(DEFAULT_COL_50);

        // Validate the QueryCommon50 in Elasticsearch
        verify(mockQueryCommon50SearchRepository, times(1)).save(testQueryCommon50);
    }

    @Test
    @Transactional
    public void createQueryCommon50WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queryCommon50Repository.findAll().size();

        // Create the QueryCommon50 with an existing ID
        queryCommon50.setId(1L);
        QueryCommon50DTO queryCommon50DTO = queryCommon50Mapper.toDto(queryCommon50);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueryCommon50MockMvc.perform(post("/api/query-common-50-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon50DTO)))
            .andExpect(status().isBadRequest());

        // Validate the QueryCommon50 in the database
        List<QueryCommon50> queryCommon50List = queryCommon50Repository.findAll();
        assertThat(queryCommon50List).hasSize(databaseSizeBeforeCreate);

        // Validate the QueryCommon50 in Elasticsearch
        verify(mockQueryCommon50SearchRepository, times(0)).save(queryCommon50);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50S() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryCommon50.getId().intValue())))
            .andExpect(jsonPath("$.[*].queryName").value(hasItem(DEFAULT_QUERY_NAME.toString())))
            .andExpect(jsonPath("$.[*].queryDate").value(hasItem(DEFAULT_QUERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].queryUser").value(hasItem(DEFAULT_QUERY_USER.toString())))
            .andExpect(jsonPath("$.[*].col01").value(hasItem(DEFAULT_COL_01.toString())))
            .andExpect(jsonPath("$.[*].col02").value(hasItem(DEFAULT_COL_02.toString())))
            .andExpect(jsonPath("$.[*].col03").value(hasItem(DEFAULT_COL_03.toString())))
            .andExpect(jsonPath("$.[*].col04").value(hasItem(DEFAULT_COL_04.toString())))
            .andExpect(jsonPath("$.[*].col05").value(hasItem(DEFAULT_COL_05.toString())))
            .andExpect(jsonPath("$.[*].col06").value(hasItem(DEFAULT_COL_06.toString())))
            .andExpect(jsonPath("$.[*].col07").value(hasItem(DEFAULT_COL_07.toString())))
            .andExpect(jsonPath("$.[*].col08").value(hasItem(DEFAULT_COL_08.toString())))
            .andExpect(jsonPath("$.[*].col09").value(hasItem(DEFAULT_COL_09.toString())))
            .andExpect(jsonPath("$.[*].col10").value(hasItem(DEFAULT_COL_10.toString())))
            .andExpect(jsonPath("$.[*].col11").value(hasItem(DEFAULT_COL_11.toString())))
            .andExpect(jsonPath("$.[*].col12").value(hasItem(DEFAULT_COL_12.toString())))
            .andExpect(jsonPath("$.[*].col13").value(hasItem(DEFAULT_COL_13.toString())))
            .andExpect(jsonPath("$.[*].col14").value(hasItem(DEFAULT_COL_14.toString())))
            .andExpect(jsonPath("$.[*].col15").value(hasItem(DEFAULT_COL_15.toString())))
            .andExpect(jsonPath("$.[*].col16").value(hasItem(DEFAULT_COL_16.toString())))
            .andExpect(jsonPath("$.[*].col17").value(hasItem(DEFAULT_COL_17.toString())))
            .andExpect(jsonPath("$.[*].col18").value(hasItem(DEFAULT_COL_18.toString())))
            .andExpect(jsonPath("$.[*].col19").value(hasItem(DEFAULT_COL_19.toString())))
            .andExpect(jsonPath("$.[*].col20").value(hasItem(DEFAULT_COL_20.toString())))
            .andExpect(jsonPath("$.[*].col21").value(hasItem(DEFAULT_COL_21.toString())))
            .andExpect(jsonPath("$.[*].col22").value(hasItem(DEFAULT_COL_22.toString())))
            .andExpect(jsonPath("$.[*].col23").value(hasItem(DEFAULT_COL_23.toString())))
            .andExpect(jsonPath("$.[*].col24").value(hasItem(DEFAULT_COL_24.toString())))
            .andExpect(jsonPath("$.[*].col25").value(hasItem(DEFAULT_COL_25.toString())))
            .andExpect(jsonPath("$.[*].col26").value(hasItem(DEFAULT_COL_26.toString())))
            .andExpect(jsonPath("$.[*].col27").value(hasItem(DEFAULT_COL_27.toString())))
            .andExpect(jsonPath("$.[*].col28").value(hasItem(DEFAULT_COL_28.toString())))
            .andExpect(jsonPath("$.[*].col29").value(hasItem(DEFAULT_COL_29.toString())))
            .andExpect(jsonPath("$.[*].col30").value(hasItem(DEFAULT_COL_30.toString())))
            .andExpect(jsonPath("$.[*].col31").value(hasItem(DEFAULT_COL_31.toString())))
            .andExpect(jsonPath("$.[*].col32").value(hasItem(DEFAULT_COL_32.toString())))
            .andExpect(jsonPath("$.[*].col33").value(hasItem(DEFAULT_COL_33.toString())))
            .andExpect(jsonPath("$.[*].col34").value(hasItem(DEFAULT_COL_34.toString())))
            .andExpect(jsonPath("$.[*].col35").value(hasItem(DEFAULT_COL_35.toString())))
            .andExpect(jsonPath("$.[*].col36").value(hasItem(DEFAULT_COL_36.toString())))
            .andExpect(jsonPath("$.[*].col37").value(hasItem(DEFAULT_COL_37.toString())))
            .andExpect(jsonPath("$.[*].col38").value(hasItem(DEFAULT_COL_38.toString())))
            .andExpect(jsonPath("$.[*].col39").value(hasItem(DEFAULT_COL_39.toString())))
            .andExpect(jsonPath("$.[*].col40").value(hasItem(DEFAULT_COL_40.toString())))
            .andExpect(jsonPath("$.[*].col41").value(hasItem(DEFAULT_COL_41.toString())))
            .andExpect(jsonPath("$.[*].col42").value(hasItem(DEFAULT_COL_42.toString())))
            .andExpect(jsonPath("$.[*].col43").value(hasItem(DEFAULT_COL_43.toString())))
            .andExpect(jsonPath("$.[*].col44").value(hasItem(DEFAULT_COL_44.toString())))
            .andExpect(jsonPath("$.[*].col45").value(hasItem(DEFAULT_COL_45.toString())))
            .andExpect(jsonPath("$.[*].col46").value(hasItem(DEFAULT_COL_46.toString())))
            .andExpect(jsonPath("$.[*].col47").value(hasItem(DEFAULT_COL_47.toString())))
            .andExpect(jsonPath("$.[*].col48").value(hasItem(DEFAULT_COL_48.toString())))
            .andExpect(jsonPath("$.[*].col49").value(hasItem(DEFAULT_COL_49.toString())))
            .andExpect(jsonPath("$.[*].col50").value(hasItem(DEFAULT_COL_50.toString())));
    }
    
    @Test
    @Transactional
    public void getQueryCommon50() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get the queryCommon50
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s/{id}", queryCommon50.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queryCommon50.getId().intValue()))
            .andExpect(jsonPath("$.queryName").value(DEFAULT_QUERY_NAME.toString()))
            .andExpect(jsonPath("$.queryDate").value(DEFAULT_QUERY_DATE.toString()))
            .andExpect(jsonPath("$.queryUser").value(DEFAULT_QUERY_USER.toString()))
            .andExpect(jsonPath("$.col01").value(DEFAULT_COL_01.toString()))
            .andExpect(jsonPath("$.col02").value(DEFAULT_COL_02.toString()))
            .andExpect(jsonPath("$.col03").value(DEFAULT_COL_03.toString()))
            .andExpect(jsonPath("$.col04").value(DEFAULT_COL_04.toString()))
            .andExpect(jsonPath("$.col05").value(DEFAULT_COL_05.toString()))
            .andExpect(jsonPath("$.col06").value(DEFAULT_COL_06.toString()))
            .andExpect(jsonPath("$.col07").value(DEFAULT_COL_07.toString()))
            .andExpect(jsonPath("$.col08").value(DEFAULT_COL_08.toString()))
            .andExpect(jsonPath("$.col09").value(DEFAULT_COL_09.toString()))
            .andExpect(jsonPath("$.col10").value(DEFAULT_COL_10.toString()))
            .andExpect(jsonPath("$.col11").value(DEFAULT_COL_11.toString()))
            .andExpect(jsonPath("$.col12").value(DEFAULT_COL_12.toString()))
            .andExpect(jsonPath("$.col13").value(DEFAULT_COL_13.toString()))
            .andExpect(jsonPath("$.col14").value(DEFAULT_COL_14.toString()))
            .andExpect(jsonPath("$.col15").value(DEFAULT_COL_15.toString()))
            .andExpect(jsonPath("$.col16").value(DEFAULT_COL_16.toString()))
            .andExpect(jsonPath("$.col17").value(DEFAULT_COL_17.toString()))
            .andExpect(jsonPath("$.col18").value(DEFAULT_COL_18.toString()))
            .andExpect(jsonPath("$.col19").value(DEFAULT_COL_19.toString()))
            .andExpect(jsonPath("$.col20").value(DEFAULT_COL_20.toString()))
            .andExpect(jsonPath("$.col21").value(DEFAULT_COL_21.toString()))
            .andExpect(jsonPath("$.col22").value(DEFAULT_COL_22.toString()))
            .andExpect(jsonPath("$.col23").value(DEFAULT_COL_23.toString()))
            .andExpect(jsonPath("$.col24").value(DEFAULT_COL_24.toString()))
            .andExpect(jsonPath("$.col25").value(DEFAULT_COL_25.toString()))
            .andExpect(jsonPath("$.col26").value(DEFAULT_COL_26.toString()))
            .andExpect(jsonPath("$.col27").value(DEFAULT_COL_27.toString()))
            .andExpect(jsonPath("$.col28").value(DEFAULT_COL_28.toString()))
            .andExpect(jsonPath("$.col29").value(DEFAULT_COL_29.toString()))
            .andExpect(jsonPath("$.col30").value(DEFAULT_COL_30.toString()))
            .andExpect(jsonPath("$.col31").value(DEFAULT_COL_31.toString()))
            .andExpect(jsonPath("$.col32").value(DEFAULT_COL_32.toString()))
            .andExpect(jsonPath("$.col33").value(DEFAULT_COL_33.toString()))
            .andExpect(jsonPath("$.col34").value(DEFAULT_COL_34.toString()))
            .andExpect(jsonPath("$.col35").value(DEFAULT_COL_35.toString()))
            .andExpect(jsonPath("$.col36").value(DEFAULT_COL_36.toString()))
            .andExpect(jsonPath("$.col37").value(DEFAULT_COL_37.toString()))
            .andExpect(jsonPath("$.col38").value(DEFAULT_COL_38.toString()))
            .andExpect(jsonPath("$.col39").value(DEFAULT_COL_39.toString()))
            .andExpect(jsonPath("$.col40").value(DEFAULT_COL_40.toString()))
            .andExpect(jsonPath("$.col41").value(DEFAULT_COL_41.toString()))
            .andExpect(jsonPath("$.col42").value(DEFAULT_COL_42.toString()))
            .andExpect(jsonPath("$.col43").value(DEFAULT_COL_43.toString()))
            .andExpect(jsonPath("$.col44").value(DEFAULT_COL_44.toString()))
            .andExpect(jsonPath("$.col45").value(DEFAULT_COL_45.toString()))
            .andExpect(jsonPath("$.col46").value(DEFAULT_COL_46.toString()))
            .andExpect(jsonPath("$.col47").value(DEFAULT_COL_47.toString()))
            .andExpect(jsonPath("$.col48").value(DEFAULT_COL_48.toString()))
            .andExpect(jsonPath("$.col49").value(DEFAULT_COL_49.toString()))
            .andExpect(jsonPath("$.col50").value(DEFAULT_COL_50.toString()));
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryName equals to DEFAULT_QUERY_NAME
        defaultQueryCommon50ShouldBeFound("queryName.equals=" + DEFAULT_QUERY_NAME);

        // Get all the queryCommon50List where queryName equals to UPDATED_QUERY_NAME
        defaultQueryCommon50ShouldNotBeFound("queryName.equals=" + UPDATED_QUERY_NAME);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryNameIsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryName in DEFAULT_QUERY_NAME or UPDATED_QUERY_NAME
        defaultQueryCommon50ShouldBeFound("queryName.in=" + DEFAULT_QUERY_NAME + "," + UPDATED_QUERY_NAME);

        // Get all the queryCommon50List where queryName equals to UPDATED_QUERY_NAME
        defaultQueryCommon50ShouldNotBeFound("queryName.in=" + UPDATED_QUERY_NAME);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryName is not null
        defaultQueryCommon50ShouldBeFound("queryName.specified=true");

        // Get all the queryCommon50List where queryName is null
        defaultQueryCommon50ShouldNotBeFound("queryName.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryDate equals to DEFAULT_QUERY_DATE
        defaultQueryCommon50ShouldBeFound("queryDate.equals=" + DEFAULT_QUERY_DATE);

        // Get all the queryCommon50List where queryDate equals to UPDATED_QUERY_DATE
        defaultQueryCommon50ShouldNotBeFound("queryDate.equals=" + UPDATED_QUERY_DATE);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryDateIsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryDate in DEFAULT_QUERY_DATE or UPDATED_QUERY_DATE
        defaultQueryCommon50ShouldBeFound("queryDate.in=" + DEFAULT_QUERY_DATE + "," + UPDATED_QUERY_DATE);

        // Get all the queryCommon50List where queryDate equals to UPDATED_QUERY_DATE
        defaultQueryCommon50ShouldNotBeFound("queryDate.in=" + UPDATED_QUERY_DATE);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryDate is not null
        defaultQueryCommon50ShouldBeFound("queryDate.specified=true");

        // Get all the queryCommon50List where queryDate is null
        defaultQueryCommon50ShouldNotBeFound("queryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryUserIsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryUser equals to DEFAULT_QUERY_USER
        defaultQueryCommon50ShouldBeFound("queryUser.equals=" + DEFAULT_QUERY_USER);

        // Get all the queryCommon50List where queryUser equals to UPDATED_QUERY_USER
        defaultQueryCommon50ShouldNotBeFound("queryUser.equals=" + UPDATED_QUERY_USER);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryUserIsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryUser in DEFAULT_QUERY_USER or UPDATED_QUERY_USER
        defaultQueryCommon50ShouldBeFound("queryUser.in=" + DEFAULT_QUERY_USER + "," + UPDATED_QUERY_USER);

        // Get all the queryCommon50List where queryUser equals to UPDATED_QUERY_USER
        defaultQueryCommon50ShouldNotBeFound("queryUser.in=" + UPDATED_QUERY_USER);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByQueryUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where queryUser is not null
        defaultQueryCommon50ShouldBeFound("queryUser.specified=true");

        // Get all the queryCommon50List where queryUser is null
        defaultQueryCommon50ShouldNotBeFound("queryUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol01IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col01 equals to DEFAULT_COL_01
        defaultQueryCommon50ShouldBeFound("col01.equals=" + DEFAULT_COL_01);

        // Get all the queryCommon50List where col01 equals to UPDATED_COL_01
        defaultQueryCommon50ShouldNotBeFound("col01.equals=" + UPDATED_COL_01);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol01IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col01 in DEFAULT_COL_01 or UPDATED_COL_01
        defaultQueryCommon50ShouldBeFound("col01.in=" + DEFAULT_COL_01 + "," + UPDATED_COL_01);

        // Get all the queryCommon50List where col01 equals to UPDATED_COL_01
        defaultQueryCommon50ShouldNotBeFound("col01.in=" + UPDATED_COL_01);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol01IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col01 is not null
        defaultQueryCommon50ShouldBeFound("col01.specified=true");

        // Get all the queryCommon50List where col01 is null
        defaultQueryCommon50ShouldNotBeFound("col01.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol02IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col02 equals to DEFAULT_COL_02
        defaultQueryCommon50ShouldBeFound("col02.equals=" + DEFAULT_COL_02);

        // Get all the queryCommon50List where col02 equals to UPDATED_COL_02
        defaultQueryCommon50ShouldNotBeFound("col02.equals=" + UPDATED_COL_02);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol02IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col02 in DEFAULT_COL_02 or UPDATED_COL_02
        defaultQueryCommon50ShouldBeFound("col02.in=" + DEFAULT_COL_02 + "," + UPDATED_COL_02);

        // Get all the queryCommon50List where col02 equals to UPDATED_COL_02
        defaultQueryCommon50ShouldNotBeFound("col02.in=" + UPDATED_COL_02);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol02IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col02 is not null
        defaultQueryCommon50ShouldBeFound("col02.specified=true");

        // Get all the queryCommon50List where col02 is null
        defaultQueryCommon50ShouldNotBeFound("col02.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol03IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col03 equals to DEFAULT_COL_03
        defaultQueryCommon50ShouldBeFound("col03.equals=" + DEFAULT_COL_03);

        // Get all the queryCommon50List where col03 equals to UPDATED_COL_03
        defaultQueryCommon50ShouldNotBeFound("col03.equals=" + UPDATED_COL_03);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol03IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col03 in DEFAULT_COL_03 or UPDATED_COL_03
        defaultQueryCommon50ShouldBeFound("col03.in=" + DEFAULT_COL_03 + "," + UPDATED_COL_03);

        // Get all the queryCommon50List where col03 equals to UPDATED_COL_03
        defaultQueryCommon50ShouldNotBeFound("col03.in=" + UPDATED_COL_03);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol03IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col03 is not null
        defaultQueryCommon50ShouldBeFound("col03.specified=true");

        // Get all the queryCommon50List where col03 is null
        defaultQueryCommon50ShouldNotBeFound("col03.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol04IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col04 equals to DEFAULT_COL_04
        defaultQueryCommon50ShouldBeFound("col04.equals=" + DEFAULT_COL_04);

        // Get all the queryCommon50List where col04 equals to UPDATED_COL_04
        defaultQueryCommon50ShouldNotBeFound("col04.equals=" + UPDATED_COL_04);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol04IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col04 in DEFAULT_COL_04 or UPDATED_COL_04
        defaultQueryCommon50ShouldBeFound("col04.in=" + DEFAULT_COL_04 + "," + UPDATED_COL_04);

        // Get all the queryCommon50List where col04 equals to UPDATED_COL_04
        defaultQueryCommon50ShouldNotBeFound("col04.in=" + UPDATED_COL_04);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol04IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col04 is not null
        defaultQueryCommon50ShouldBeFound("col04.specified=true");

        // Get all the queryCommon50List where col04 is null
        defaultQueryCommon50ShouldNotBeFound("col04.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol05IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col05 equals to DEFAULT_COL_05
        defaultQueryCommon50ShouldBeFound("col05.equals=" + DEFAULT_COL_05);

        // Get all the queryCommon50List where col05 equals to UPDATED_COL_05
        defaultQueryCommon50ShouldNotBeFound("col05.equals=" + UPDATED_COL_05);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol05IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col05 in DEFAULT_COL_05 or UPDATED_COL_05
        defaultQueryCommon50ShouldBeFound("col05.in=" + DEFAULT_COL_05 + "," + UPDATED_COL_05);

        // Get all the queryCommon50List where col05 equals to UPDATED_COL_05
        defaultQueryCommon50ShouldNotBeFound("col05.in=" + UPDATED_COL_05);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol05IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col05 is not null
        defaultQueryCommon50ShouldBeFound("col05.specified=true");

        // Get all the queryCommon50List where col05 is null
        defaultQueryCommon50ShouldNotBeFound("col05.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol06IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col06 equals to DEFAULT_COL_06
        defaultQueryCommon50ShouldBeFound("col06.equals=" + DEFAULT_COL_06);

        // Get all the queryCommon50List where col06 equals to UPDATED_COL_06
        defaultQueryCommon50ShouldNotBeFound("col06.equals=" + UPDATED_COL_06);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol06IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col06 in DEFAULT_COL_06 or UPDATED_COL_06
        defaultQueryCommon50ShouldBeFound("col06.in=" + DEFAULT_COL_06 + "," + UPDATED_COL_06);

        // Get all the queryCommon50List where col06 equals to UPDATED_COL_06
        defaultQueryCommon50ShouldNotBeFound("col06.in=" + UPDATED_COL_06);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol06IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col06 is not null
        defaultQueryCommon50ShouldBeFound("col06.specified=true");

        // Get all the queryCommon50List where col06 is null
        defaultQueryCommon50ShouldNotBeFound("col06.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol07IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col07 equals to DEFAULT_COL_07
        defaultQueryCommon50ShouldBeFound("col07.equals=" + DEFAULT_COL_07);

        // Get all the queryCommon50List where col07 equals to UPDATED_COL_07
        defaultQueryCommon50ShouldNotBeFound("col07.equals=" + UPDATED_COL_07);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol07IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col07 in DEFAULT_COL_07 or UPDATED_COL_07
        defaultQueryCommon50ShouldBeFound("col07.in=" + DEFAULT_COL_07 + "," + UPDATED_COL_07);

        // Get all the queryCommon50List where col07 equals to UPDATED_COL_07
        defaultQueryCommon50ShouldNotBeFound("col07.in=" + UPDATED_COL_07);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol07IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col07 is not null
        defaultQueryCommon50ShouldBeFound("col07.specified=true");

        // Get all the queryCommon50List where col07 is null
        defaultQueryCommon50ShouldNotBeFound("col07.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol08IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col08 equals to DEFAULT_COL_08
        defaultQueryCommon50ShouldBeFound("col08.equals=" + DEFAULT_COL_08);

        // Get all the queryCommon50List where col08 equals to UPDATED_COL_08
        defaultQueryCommon50ShouldNotBeFound("col08.equals=" + UPDATED_COL_08);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol08IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col08 in DEFAULT_COL_08 or UPDATED_COL_08
        defaultQueryCommon50ShouldBeFound("col08.in=" + DEFAULT_COL_08 + "," + UPDATED_COL_08);

        // Get all the queryCommon50List where col08 equals to UPDATED_COL_08
        defaultQueryCommon50ShouldNotBeFound("col08.in=" + UPDATED_COL_08);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol08IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col08 is not null
        defaultQueryCommon50ShouldBeFound("col08.specified=true");

        // Get all the queryCommon50List where col08 is null
        defaultQueryCommon50ShouldNotBeFound("col08.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol09IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col09 equals to DEFAULT_COL_09
        defaultQueryCommon50ShouldBeFound("col09.equals=" + DEFAULT_COL_09);

        // Get all the queryCommon50List where col09 equals to UPDATED_COL_09
        defaultQueryCommon50ShouldNotBeFound("col09.equals=" + UPDATED_COL_09);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol09IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col09 in DEFAULT_COL_09 or UPDATED_COL_09
        defaultQueryCommon50ShouldBeFound("col09.in=" + DEFAULT_COL_09 + "," + UPDATED_COL_09);

        // Get all the queryCommon50List where col09 equals to UPDATED_COL_09
        defaultQueryCommon50ShouldNotBeFound("col09.in=" + UPDATED_COL_09);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol09IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col09 is not null
        defaultQueryCommon50ShouldBeFound("col09.specified=true");

        // Get all the queryCommon50List where col09 is null
        defaultQueryCommon50ShouldNotBeFound("col09.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol10IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col10 equals to DEFAULT_COL_10
        defaultQueryCommon50ShouldBeFound("col10.equals=" + DEFAULT_COL_10);

        // Get all the queryCommon50List where col10 equals to UPDATED_COL_10
        defaultQueryCommon50ShouldNotBeFound("col10.equals=" + UPDATED_COL_10);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol10IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col10 in DEFAULT_COL_10 or UPDATED_COL_10
        defaultQueryCommon50ShouldBeFound("col10.in=" + DEFAULT_COL_10 + "," + UPDATED_COL_10);

        // Get all the queryCommon50List where col10 equals to UPDATED_COL_10
        defaultQueryCommon50ShouldNotBeFound("col10.in=" + UPDATED_COL_10);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol10IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col10 is not null
        defaultQueryCommon50ShouldBeFound("col10.specified=true");

        // Get all the queryCommon50List where col10 is null
        defaultQueryCommon50ShouldNotBeFound("col10.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol11IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col11 equals to DEFAULT_COL_11
        defaultQueryCommon50ShouldBeFound("col11.equals=" + DEFAULT_COL_11);

        // Get all the queryCommon50List where col11 equals to UPDATED_COL_11
        defaultQueryCommon50ShouldNotBeFound("col11.equals=" + UPDATED_COL_11);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol11IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col11 in DEFAULT_COL_11 or UPDATED_COL_11
        defaultQueryCommon50ShouldBeFound("col11.in=" + DEFAULT_COL_11 + "," + UPDATED_COL_11);

        // Get all the queryCommon50List where col11 equals to UPDATED_COL_11
        defaultQueryCommon50ShouldNotBeFound("col11.in=" + UPDATED_COL_11);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol11IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col11 is not null
        defaultQueryCommon50ShouldBeFound("col11.specified=true");

        // Get all the queryCommon50List where col11 is null
        defaultQueryCommon50ShouldNotBeFound("col11.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol12IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col12 equals to DEFAULT_COL_12
        defaultQueryCommon50ShouldBeFound("col12.equals=" + DEFAULT_COL_12);

        // Get all the queryCommon50List where col12 equals to UPDATED_COL_12
        defaultQueryCommon50ShouldNotBeFound("col12.equals=" + UPDATED_COL_12);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol12IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col12 in DEFAULT_COL_12 or UPDATED_COL_12
        defaultQueryCommon50ShouldBeFound("col12.in=" + DEFAULT_COL_12 + "," + UPDATED_COL_12);

        // Get all the queryCommon50List where col12 equals to UPDATED_COL_12
        defaultQueryCommon50ShouldNotBeFound("col12.in=" + UPDATED_COL_12);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol12IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col12 is not null
        defaultQueryCommon50ShouldBeFound("col12.specified=true");

        // Get all the queryCommon50List where col12 is null
        defaultQueryCommon50ShouldNotBeFound("col12.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol13IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col13 equals to DEFAULT_COL_13
        defaultQueryCommon50ShouldBeFound("col13.equals=" + DEFAULT_COL_13);

        // Get all the queryCommon50List where col13 equals to UPDATED_COL_13
        defaultQueryCommon50ShouldNotBeFound("col13.equals=" + UPDATED_COL_13);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol13IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col13 in DEFAULT_COL_13 or UPDATED_COL_13
        defaultQueryCommon50ShouldBeFound("col13.in=" + DEFAULT_COL_13 + "," + UPDATED_COL_13);

        // Get all the queryCommon50List where col13 equals to UPDATED_COL_13
        defaultQueryCommon50ShouldNotBeFound("col13.in=" + UPDATED_COL_13);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol13IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col13 is not null
        defaultQueryCommon50ShouldBeFound("col13.specified=true");

        // Get all the queryCommon50List where col13 is null
        defaultQueryCommon50ShouldNotBeFound("col13.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol14IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col14 equals to DEFAULT_COL_14
        defaultQueryCommon50ShouldBeFound("col14.equals=" + DEFAULT_COL_14);

        // Get all the queryCommon50List where col14 equals to UPDATED_COL_14
        defaultQueryCommon50ShouldNotBeFound("col14.equals=" + UPDATED_COL_14);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol14IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col14 in DEFAULT_COL_14 or UPDATED_COL_14
        defaultQueryCommon50ShouldBeFound("col14.in=" + DEFAULT_COL_14 + "," + UPDATED_COL_14);

        // Get all the queryCommon50List where col14 equals to UPDATED_COL_14
        defaultQueryCommon50ShouldNotBeFound("col14.in=" + UPDATED_COL_14);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol14IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col14 is not null
        defaultQueryCommon50ShouldBeFound("col14.specified=true");

        // Get all the queryCommon50List where col14 is null
        defaultQueryCommon50ShouldNotBeFound("col14.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol15IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col15 equals to DEFAULT_COL_15
        defaultQueryCommon50ShouldBeFound("col15.equals=" + DEFAULT_COL_15);

        // Get all the queryCommon50List where col15 equals to UPDATED_COL_15
        defaultQueryCommon50ShouldNotBeFound("col15.equals=" + UPDATED_COL_15);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol15IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col15 in DEFAULT_COL_15 or UPDATED_COL_15
        defaultQueryCommon50ShouldBeFound("col15.in=" + DEFAULT_COL_15 + "," + UPDATED_COL_15);

        // Get all the queryCommon50List where col15 equals to UPDATED_COL_15
        defaultQueryCommon50ShouldNotBeFound("col15.in=" + UPDATED_COL_15);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol15IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col15 is not null
        defaultQueryCommon50ShouldBeFound("col15.specified=true");

        // Get all the queryCommon50List where col15 is null
        defaultQueryCommon50ShouldNotBeFound("col15.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol16IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col16 equals to DEFAULT_COL_16
        defaultQueryCommon50ShouldBeFound("col16.equals=" + DEFAULT_COL_16);

        // Get all the queryCommon50List where col16 equals to UPDATED_COL_16
        defaultQueryCommon50ShouldNotBeFound("col16.equals=" + UPDATED_COL_16);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol16IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col16 in DEFAULT_COL_16 or UPDATED_COL_16
        defaultQueryCommon50ShouldBeFound("col16.in=" + DEFAULT_COL_16 + "," + UPDATED_COL_16);

        // Get all the queryCommon50List where col16 equals to UPDATED_COL_16
        defaultQueryCommon50ShouldNotBeFound("col16.in=" + UPDATED_COL_16);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol16IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col16 is not null
        defaultQueryCommon50ShouldBeFound("col16.specified=true");

        // Get all the queryCommon50List where col16 is null
        defaultQueryCommon50ShouldNotBeFound("col16.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol17IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col17 equals to DEFAULT_COL_17
        defaultQueryCommon50ShouldBeFound("col17.equals=" + DEFAULT_COL_17);

        // Get all the queryCommon50List where col17 equals to UPDATED_COL_17
        defaultQueryCommon50ShouldNotBeFound("col17.equals=" + UPDATED_COL_17);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol17IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col17 in DEFAULT_COL_17 or UPDATED_COL_17
        defaultQueryCommon50ShouldBeFound("col17.in=" + DEFAULT_COL_17 + "," + UPDATED_COL_17);

        // Get all the queryCommon50List where col17 equals to UPDATED_COL_17
        defaultQueryCommon50ShouldNotBeFound("col17.in=" + UPDATED_COL_17);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol17IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col17 is not null
        defaultQueryCommon50ShouldBeFound("col17.specified=true");

        // Get all the queryCommon50List where col17 is null
        defaultQueryCommon50ShouldNotBeFound("col17.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol18IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col18 equals to DEFAULT_COL_18
        defaultQueryCommon50ShouldBeFound("col18.equals=" + DEFAULT_COL_18);

        // Get all the queryCommon50List where col18 equals to UPDATED_COL_18
        defaultQueryCommon50ShouldNotBeFound("col18.equals=" + UPDATED_COL_18);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol18IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col18 in DEFAULT_COL_18 or UPDATED_COL_18
        defaultQueryCommon50ShouldBeFound("col18.in=" + DEFAULT_COL_18 + "," + UPDATED_COL_18);

        // Get all the queryCommon50List where col18 equals to UPDATED_COL_18
        defaultQueryCommon50ShouldNotBeFound("col18.in=" + UPDATED_COL_18);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol18IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col18 is not null
        defaultQueryCommon50ShouldBeFound("col18.specified=true");

        // Get all the queryCommon50List where col18 is null
        defaultQueryCommon50ShouldNotBeFound("col18.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol19IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col19 equals to DEFAULT_COL_19
        defaultQueryCommon50ShouldBeFound("col19.equals=" + DEFAULT_COL_19);

        // Get all the queryCommon50List where col19 equals to UPDATED_COL_19
        defaultQueryCommon50ShouldNotBeFound("col19.equals=" + UPDATED_COL_19);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol19IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col19 in DEFAULT_COL_19 or UPDATED_COL_19
        defaultQueryCommon50ShouldBeFound("col19.in=" + DEFAULT_COL_19 + "," + UPDATED_COL_19);

        // Get all the queryCommon50List where col19 equals to UPDATED_COL_19
        defaultQueryCommon50ShouldNotBeFound("col19.in=" + UPDATED_COL_19);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol19IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col19 is not null
        defaultQueryCommon50ShouldBeFound("col19.specified=true");

        // Get all the queryCommon50List where col19 is null
        defaultQueryCommon50ShouldNotBeFound("col19.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol20IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col20 equals to DEFAULT_COL_20
        defaultQueryCommon50ShouldBeFound("col20.equals=" + DEFAULT_COL_20);

        // Get all the queryCommon50List where col20 equals to UPDATED_COL_20
        defaultQueryCommon50ShouldNotBeFound("col20.equals=" + UPDATED_COL_20);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol20IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col20 in DEFAULT_COL_20 or UPDATED_COL_20
        defaultQueryCommon50ShouldBeFound("col20.in=" + DEFAULT_COL_20 + "," + UPDATED_COL_20);

        // Get all the queryCommon50List where col20 equals to UPDATED_COL_20
        defaultQueryCommon50ShouldNotBeFound("col20.in=" + UPDATED_COL_20);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol20IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col20 is not null
        defaultQueryCommon50ShouldBeFound("col20.specified=true");

        // Get all the queryCommon50List where col20 is null
        defaultQueryCommon50ShouldNotBeFound("col20.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol21IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col21 equals to DEFAULT_COL_21
        defaultQueryCommon50ShouldBeFound("col21.equals=" + DEFAULT_COL_21);

        // Get all the queryCommon50List where col21 equals to UPDATED_COL_21
        defaultQueryCommon50ShouldNotBeFound("col21.equals=" + UPDATED_COL_21);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol21IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col21 in DEFAULT_COL_21 or UPDATED_COL_21
        defaultQueryCommon50ShouldBeFound("col21.in=" + DEFAULT_COL_21 + "," + UPDATED_COL_21);

        // Get all the queryCommon50List where col21 equals to UPDATED_COL_21
        defaultQueryCommon50ShouldNotBeFound("col21.in=" + UPDATED_COL_21);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol21IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col21 is not null
        defaultQueryCommon50ShouldBeFound("col21.specified=true");

        // Get all the queryCommon50List where col21 is null
        defaultQueryCommon50ShouldNotBeFound("col21.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol22IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col22 equals to DEFAULT_COL_22
        defaultQueryCommon50ShouldBeFound("col22.equals=" + DEFAULT_COL_22);

        // Get all the queryCommon50List where col22 equals to UPDATED_COL_22
        defaultQueryCommon50ShouldNotBeFound("col22.equals=" + UPDATED_COL_22);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol22IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col22 in DEFAULT_COL_22 or UPDATED_COL_22
        defaultQueryCommon50ShouldBeFound("col22.in=" + DEFAULT_COL_22 + "," + UPDATED_COL_22);

        // Get all the queryCommon50List where col22 equals to UPDATED_COL_22
        defaultQueryCommon50ShouldNotBeFound("col22.in=" + UPDATED_COL_22);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol22IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col22 is not null
        defaultQueryCommon50ShouldBeFound("col22.specified=true");

        // Get all the queryCommon50List where col22 is null
        defaultQueryCommon50ShouldNotBeFound("col22.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol23IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col23 equals to DEFAULT_COL_23
        defaultQueryCommon50ShouldBeFound("col23.equals=" + DEFAULT_COL_23);

        // Get all the queryCommon50List where col23 equals to UPDATED_COL_23
        defaultQueryCommon50ShouldNotBeFound("col23.equals=" + UPDATED_COL_23);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol23IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col23 in DEFAULT_COL_23 or UPDATED_COL_23
        defaultQueryCommon50ShouldBeFound("col23.in=" + DEFAULT_COL_23 + "," + UPDATED_COL_23);

        // Get all the queryCommon50List where col23 equals to UPDATED_COL_23
        defaultQueryCommon50ShouldNotBeFound("col23.in=" + UPDATED_COL_23);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol23IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col23 is not null
        defaultQueryCommon50ShouldBeFound("col23.specified=true");

        // Get all the queryCommon50List where col23 is null
        defaultQueryCommon50ShouldNotBeFound("col23.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol24IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col24 equals to DEFAULT_COL_24
        defaultQueryCommon50ShouldBeFound("col24.equals=" + DEFAULT_COL_24);

        // Get all the queryCommon50List where col24 equals to UPDATED_COL_24
        defaultQueryCommon50ShouldNotBeFound("col24.equals=" + UPDATED_COL_24);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol24IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col24 in DEFAULT_COL_24 or UPDATED_COL_24
        defaultQueryCommon50ShouldBeFound("col24.in=" + DEFAULT_COL_24 + "," + UPDATED_COL_24);

        // Get all the queryCommon50List where col24 equals to UPDATED_COL_24
        defaultQueryCommon50ShouldNotBeFound("col24.in=" + UPDATED_COL_24);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol24IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col24 is not null
        defaultQueryCommon50ShouldBeFound("col24.specified=true");

        // Get all the queryCommon50List where col24 is null
        defaultQueryCommon50ShouldNotBeFound("col24.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol25IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col25 equals to DEFAULT_COL_25
        defaultQueryCommon50ShouldBeFound("col25.equals=" + DEFAULT_COL_25);

        // Get all the queryCommon50List where col25 equals to UPDATED_COL_25
        defaultQueryCommon50ShouldNotBeFound("col25.equals=" + UPDATED_COL_25);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol25IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col25 in DEFAULT_COL_25 or UPDATED_COL_25
        defaultQueryCommon50ShouldBeFound("col25.in=" + DEFAULT_COL_25 + "," + UPDATED_COL_25);

        // Get all the queryCommon50List where col25 equals to UPDATED_COL_25
        defaultQueryCommon50ShouldNotBeFound("col25.in=" + UPDATED_COL_25);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol25IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col25 is not null
        defaultQueryCommon50ShouldBeFound("col25.specified=true");

        // Get all the queryCommon50List where col25 is null
        defaultQueryCommon50ShouldNotBeFound("col25.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol26IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col26 equals to DEFAULT_COL_26
        defaultQueryCommon50ShouldBeFound("col26.equals=" + DEFAULT_COL_26);

        // Get all the queryCommon50List where col26 equals to UPDATED_COL_26
        defaultQueryCommon50ShouldNotBeFound("col26.equals=" + UPDATED_COL_26);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol26IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col26 in DEFAULT_COL_26 or UPDATED_COL_26
        defaultQueryCommon50ShouldBeFound("col26.in=" + DEFAULT_COL_26 + "," + UPDATED_COL_26);

        // Get all the queryCommon50List where col26 equals to UPDATED_COL_26
        defaultQueryCommon50ShouldNotBeFound("col26.in=" + UPDATED_COL_26);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol26IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col26 is not null
        defaultQueryCommon50ShouldBeFound("col26.specified=true");

        // Get all the queryCommon50List where col26 is null
        defaultQueryCommon50ShouldNotBeFound("col26.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol27IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col27 equals to DEFAULT_COL_27
        defaultQueryCommon50ShouldBeFound("col27.equals=" + DEFAULT_COL_27);

        // Get all the queryCommon50List where col27 equals to UPDATED_COL_27
        defaultQueryCommon50ShouldNotBeFound("col27.equals=" + UPDATED_COL_27);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol27IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col27 in DEFAULT_COL_27 or UPDATED_COL_27
        defaultQueryCommon50ShouldBeFound("col27.in=" + DEFAULT_COL_27 + "," + UPDATED_COL_27);

        // Get all the queryCommon50List where col27 equals to UPDATED_COL_27
        defaultQueryCommon50ShouldNotBeFound("col27.in=" + UPDATED_COL_27);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol27IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col27 is not null
        defaultQueryCommon50ShouldBeFound("col27.specified=true");

        // Get all the queryCommon50List where col27 is null
        defaultQueryCommon50ShouldNotBeFound("col27.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol28IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col28 equals to DEFAULT_COL_28
        defaultQueryCommon50ShouldBeFound("col28.equals=" + DEFAULT_COL_28);

        // Get all the queryCommon50List where col28 equals to UPDATED_COL_28
        defaultQueryCommon50ShouldNotBeFound("col28.equals=" + UPDATED_COL_28);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol28IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col28 in DEFAULT_COL_28 or UPDATED_COL_28
        defaultQueryCommon50ShouldBeFound("col28.in=" + DEFAULT_COL_28 + "," + UPDATED_COL_28);

        // Get all the queryCommon50List where col28 equals to UPDATED_COL_28
        defaultQueryCommon50ShouldNotBeFound("col28.in=" + UPDATED_COL_28);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol28IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col28 is not null
        defaultQueryCommon50ShouldBeFound("col28.specified=true");

        // Get all the queryCommon50List where col28 is null
        defaultQueryCommon50ShouldNotBeFound("col28.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol29IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col29 equals to DEFAULT_COL_29
        defaultQueryCommon50ShouldBeFound("col29.equals=" + DEFAULT_COL_29);

        // Get all the queryCommon50List where col29 equals to UPDATED_COL_29
        defaultQueryCommon50ShouldNotBeFound("col29.equals=" + UPDATED_COL_29);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol29IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col29 in DEFAULT_COL_29 or UPDATED_COL_29
        defaultQueryCommon50ShouldBeFound("col29.in=" + DEFAULT_COL_29 + "," + UPDATED_COL_29);

        // Get all the queryCommon50List where col29 equals to UPDATED_COL_29
        defaultQueryCommon50ShouldNotBeFound("col29.in=" + UPDATED_COL_29);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol29IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col29 is not null
        defaultQueryCommon50ShouldBeFound("col29.specified=true");

        // Get all the queryCommon50List where col29 is null
        defaultQueryCommon50ShouldNotBeFound("col29.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol30IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col30 equals to DEFAULT_COL_30
        defaultQueryCommon50ShouldBeFound("col30.equals=" + DEFAULT_COL_30);

        // Get all the queryCommon50List where col30 equals to UPDATED_COL_30
        defaultQueryCommon50ShouldNotBeFound("col30.equals=" + UPDATED_COL_30);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol30IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col30 in DEFAULT_COL_30 or UPDATED_COL_30
        defaultQueryCommon50ShouldBeFound("col30.in=" + DEFAULT_COL_30 + "," + UPDATED_COL_30);

        // Get all the queryCommon50List where col30 equals to UPDATED_COL_30
        defaultQueryCommon50ShouldNotBeFound("col30.in=" + UPDATED_COL_30);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol30IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col30 is not null
        defaultQueryCommon50ShouldBeFound("col30.specified=true");

        // Get all the queryCommon50List where col30 is null
        defaultQueryCommon50ShouldNotBeFound("col30.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol31IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col31 equals to DEFAULT_COL_31
        defaultQueryCommon50ShouldBeFound("col31.equals=" + DEFAULT_COL_31);

        // Get all the queryCommon50List where col31 equals to UPDATED_COL_31
        defaultQueryCommon50ShouldNotBeFound("col31.equals=" + UPDATED_COL_31);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol31IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col31 in DEFAULT_COL_31 or UPDATED_COL_31
        defaultQueryCommon50ShouldBeFound("col31.in=" + DEFAULT_COL_31 + "," + UPDATED_COL_31);

        // Get all the queryCommon50List where col31 equals to UPDATED_COL_31
        defaultQueryCommon50ShouldNotBeFound("col31.in=" + UPDATED_COL_31);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol31IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col31 is not null
        defaultQueryCommon50ShouldBeFound("col31.specified=true");

        // Get all the queryCommon50List where col31 is null
        defaultQueryCommon50ShouldNotBeFound("col31.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol32IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col32 equals to DEFAULT_COL_32
        defaultQueryCommon50ShouldBeFound("col32.equals=" + DEFAULT_COL_32);

        // Get all the queryCommon50List where col32 equals to UPDATED_COL_32
        defaultQueryCommon50ShouldNotBeFound("col32.equals=" + UPDATED_COL_32);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol32IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col32 in DEFAULT_COL_32 or UPDATED_COL_32
        defaultQueryCommon50ShouldBeFound("col32.in=" + DEFAULT_COL_32 + "," + UPDATED_COL_32);

        // Get all the queryCommon50List where col32 equals to UPDATED_COL_32
        defaultQueryCommon50ShouldNotBeFound("col32.in=" + UPDATED_COL_32);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol32IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col32 is not null
        defaultQueryCommon50ShouldBeFound("col32.specified=true");

        // Get all the queryCommon50List where col32 is null
        defaultQueryCommon50ShouldNotBeFound("col32.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol33IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col33 equals to DEFAULT_COL_33
        defaultQueryCommon50ShouldBeFound("col33.equals=" + DEFAULT_COL_33);

        // Get all the queryCommon50List where col33 equals to UPDATED_COL_33
        defaultQueryCommon50ShouldNotBeFound("col33.equals=" + UPDATED_COL_33);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol33IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col33 in DEFAULT_COL_33 or UPDATED_COL_33
        defaultQueryCommon50ShouldBeFound("col33.in=" + DEFAULT_COL_33 + "," + UPDATED_COL_33);

        // Get all the queryCommon50List where col33 equals to UPDATED_COL_33
        defaultQueryCommon50ShouldNotBeFound("col33.in=" + UPDATED_COL_33);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol33IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col33 is not null
        defaultQueryCommon50ShouldBeFound("col33.specified=true");

        // Get all the queryCommon50List where col33 is null
        defaultQueryCommon50ShouldNotBeFound("col33.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol34IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col34 equals to DEFAULT_COL_34
        defaultQueryCommon50ShouldBeFound("col34.equals=" + DEFAULT_COL_34);

        // Get all the queryCommon50List where col34 equals to UPDATED_COL_34
        defaultQueryCommon50ShouldNotBeFound("col34.equals=" + UPDATED_COL_34);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol34IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col34 in DEFAULT_COL_34 or UPDATED_COL_34
        defaultQueryCommon50ShouldBeFound("col34.in=" + DEFAULT_COL_34 + "," + UPDATED_COL_34);

        // Get all the queryCommon50List where col34 equals to UPDATED_COL_34
        defaultQueryCommon50ShouldNotBeFound("col34.in=" + UPDATED_COL_34);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol34IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col34 is not null
        defaultQueryCommon50ShouldBeFound("col34.specified=true");

        // Get all the queryCommon50List where col34 is null
        defaultQueryCommon50ShouldNotBeFound("col34.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol35IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col35 equals to DEFAULT_COL_35
        defaultQueryCommon50ShouldBeFound("col35.equals=" + DEFAULT_COL_35);

        // Get all the queryCommon50List where col35 equals to UPDATED_COL_35
        defaultQueryCommon50ShouldNotBeFound("col35.equals=" + UPDATED_COL_35);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol35IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col35 in DEFAULT_COL_35 or UPDATED_COL_35
        defaultQueryCommon50ShouldBeFound("col35.in=" + DEFAULT_COL_35 + "," + UPDATED_COL_35);

        // Get all the queryCommon50List where col35 equals to UPDATED_COL_35
        defaultQueryCommon50ShouldNotBeFound("col35.in=" + UPDATED_COL_35);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol35IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col35 is not null
        defaultQueryCommon50ShouldBeFound("col35.specified=true");

        // Get all the queryCommon50List where col35 is null
        defaultQueryCommon50ShouldNotBeFound("col35.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol36IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col36 equals to DEFAULT_COL_36
        defaultQueryCommon50ShouldBeFound("col36.equals=" + DEFAULT_COL_36);

        // Get all the queryCommon50List where col36 equals to UPDATED_COL_36
        defaultQueryCommon50ShouldNotBeFound("col36.equals=" + UPDATED_COL_36);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol36IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col36 in DEFAULT_COL_36 or UPDATED_COL_36
        defaultQueryCommon50ShouldBeFound("col36.in=" + DEFAULT_COL_36 + "," + UPDATED_COL_36);

        // Get all the queryCommon50List where col36 equals to UPDATED_COL_36
        defaultQueryCommon50ShouldNotBeFound("col36.in=" + UPDATED_COL_36);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol36IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col36 is not null
        defaultQueryCommon50ShouldBeFound("col36.specified=true");

        // Get all the queryCommon50List where col36 is null
        defaultQueryCommon50ShouldNotBeFound("col36.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol37IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col37 equals to DEFAULT_COL_37
        defaultQueryCommon50ShouldBeFound("col37.equals=" + DEFAULT_COL_37);

        // Get all the queryCommon50List where col37 equals to UPDATED_COL_37
        defaultQueryCommon50ShouldNotBeFound("col37.equals=" + UPDATED_COL_37);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol37IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col37 in DEFAULT_COL_37 or UPDATED_COL_37
        defaultQueryCommon50ShouldBeFound("col37.in=" + DEFAULT_COL_37 + "," + UPDATED_COL_37);

        // Get all the queryCommon50List where col37 equals to UPDATED_COL_37
        defaultQueryCommon50ShouldNotBeFound("col37.in=" + UPDATED_COL_37);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol37IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col37 is not null
        defaultQueryCommon50ShouldBeFound("col37.specified=true");

        // Get all the queryCommon50List where col37 is null
        defaultQueryCommon50ShouldNotBeFound("col37.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol38IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col38 equals to DEFAULT_COL_38
        defaultQueryCommon50ShouldBeFound("col38.equals=" + DEFAULT_COL_38);

        // Get all the queryCommon50List where col38 equals to UPDATED_COL_38
        defaultQueryCommon50ShouldNotBeFound("col38.equals=" + UPDATED_COL_38);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol38IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col38 in DEFAULT_COL_38 or UPDATED_COL_38
        defaultQueryCommon50ShouldBeFound("col38.in=" + DEFAULT_COL_38 + "," + UPDATED_COL_38);

        // Get all the queryCommon50List where col38 equals to UPDATED_COL_38
        defaultQueryCommon50ShouldNotBeFound("col38.in=" + UPDATED_COL_38);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol38IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col38 is not null
        defaultQueryCommon50ShouldBeFound("col38.specified=true");

        // Get all the queryCommon50List where col38 is null
        defaultQueryCommon50ShouldNotBeFound("col38.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol39IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col39 equals to DEFAULT_COL_39
        defaultQueryCommon50ShouldBeFound("col39.equals=" + DEFAULT_COL_39);

        // Get all the queryCommon50List where col39 equals to UPDATED_COL_39
        defaultQueryCommon50ShouldNotBeFound("col39.equals=" + UPDATED_COL_39);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol39IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col39 in DEFAULT_COL_39 or UPDATED_COL_39
        defaultQueryCommon50ShouldBeFound("col39.in=" + DEFAULT_COL_39 + "," + UPDATED_COL_39);

        // Get all the queryCommon50List where col39 equals to UPDATED_COL_39
        defaultQueryCommon50ShouldNotBeFound("col39.in=" + UPDATED_COL_39);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol39IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col39 is not null
        defaultQueryCommon50ShouldBeFound("col39.specified=true");

        // Get all the queryCommon50List where col39 is null
        defaultQueryCommon50ShouldNotBeFound("col39.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol40IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col40 equals to DEFAULT_COL_40
        defaultQueryCommon50ShouldBeFound("col40.equals=" + DEFAULT_COL_40);

        // Get all the queryCommon50List where col40 equals to UPDATED_COL_40
        defaultQueryCommon50ShouldNotBeFound("col40.equals=" + UPDATED_COL_40);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol40IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col40 in DEFAULT_COL_40 or UPDATED_COL_40
        defaultQueryCommon50ShouldBeFound("col40.in=" + DEFAULT_COL_40 + "," + UPDATED_COL_40);

        // Get all the queryCommon50List where col40 equals to UPDATED_COL_40
        defaultQueryCommon50ShouldNotBeFound("col40.in=" + UPDATED_COL_40);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol40IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col40 is not null
        defaultQueryCommon50ShouldBeFound("col40.specified=true");

        // Get all the queryCommon50List where col40 is null
        defaultQueryCommon50ShouldNotBeFound("col40.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol41IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col41 equals to DEFAULT_COL_41
        defaultQueryCommon50ShouldBeFound("col41.equals=" + DEFAULT_COL_41);

        // Get all the queryCommon50List where col41 equals to UPDATED_COL_41
        defaultQueryCommon50ShouldNotBeFound("col41.equals=" + UPDATED_COL_41);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol41IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col41 in DEFAULT_COL_41 or UPDATED_COL_41
        defaultQueryCommon50ShouldBeFound("col41.in=" + DEFAULT_COL_41 + "," + UPDATED_COL_41);

        // Get all the queryCommon50List where col41 equals to UPDATED_COL_41
        defaultQueryCommon50ShouldNotBeFound("col41.in=" + UPDATED_COL_41);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol41IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col41 is not null
        defaultQueryCommon50ShouldBeFound("col41.specified=true");

        // Get all the queryCommon50List where col41 is null
        defaultQueryCommon50ShouldNotBeFound("col41.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol42IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col42 equals to DEFAULT_COL_42
        defaultQueryCommon50ShouldBeFound("col42.equals=" + DEFAULT_COL_42);

        // Get all the queryCommon50List where col42 equals to UPDATED_COL_42
        defaultQueryCommon50ShouldNotBeFound("col42.equals=" + UPDATED_COL_42);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol42IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col42 in DEFAULT_COL_42 or UPDATED_COL_42
        defaultQueryCommon50ShouldBeFound("col42.in=" + DEFAULT_COL_42 + "," + UPDATED_COL_42);

        // Get all the queryCommon50List where col42 equals to UPDATED_COL_42
        defaultQueryCommon50ShouldNotBeFound("col42.in=" + UPDATED_COL_42);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol42IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col42 is not null
        defaultQueryCommon50ShouldBeFound("col42.specified=true");

        // Get all the queryCommon50List where col42 is null
        defaultQueryCommon50ShouldNotBeFound("col42.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol43IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col43 equals to DEFAULT_COL_43
        defaultQueryCommon50ShouldBeFound("col43.equals=" + DEFAULT_COL_43);

        // Get all the queryCommon50List where col43 equals to UPDATED_COL_43
        defaultQueryCommon50ShouldNotBeFound("col43.equals=" + UPDATED_COL_43);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol43IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col43 in DEFAULT_COL_43 or UPDATED_COL_43
        defaultQueryCommon50ShouldBeFound("col43.in=" + DEFAULT_COL_43 + "," + UPDATED_COL_43);

        // Get all the queryCommon50List where col43 equals to UPDATED_COL_43
        defaultQueryCommon50ShouldNotBeFound("col43.in=" + UPDATED_COL_43);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol43IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col43 is not null
        defaultQueryCommon50ShouldBeFound("col43.specified=true");

        // Get all the queryCommon50List where col43 is null
        defaultQueryCommon50ShouldNotBeFound("col43.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol44IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col44 equals to DEFAULT_COL_44
        defaultQueryCommon50ShouldBeFound("col44.equals=" + DEFAULT_COL_44);

        // Get all the queryCommon50List where col44 equals to UPDATED_COL_44
        defaultQueryCommon50ShouldNotBeFound("col44.equals=" + UPDATED_COL_44);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol44IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col44 in DEFAULT_COL_44 or UPDATED_COL_44
        defaultQueryCommon50ShouldBeFound("col44.in=" + DEFAULT_COL_44 + "," + UPDATED_COL_44);

        // Get all the queryCommon50List where col44 equals to UPDATED_COL_44
        defaultQueryCommon50ShouldNotBeFound("col44.in=" + UPDATED_COL_44);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol44IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col44 is not null
        defaultQueryCommon50ShouldBeFound("col44.specified=true");

        // Get all the queryCommon50List where col44 is null
        defaultQueryCommon50ShouldNotBeFound("col44.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol45IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col45 equals to DEFAULT_COL_45
        defaultQueryCommon50ShouldBeFound("col45.equals=" + DEFAULT_COL_45);

        // Get all the queryCommon50List where col45 equals to UPDATED_COL_45
        defaultQueryCommon50ShouldNotBeFound("col45.equals=" + UPDATED_COL_45);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol45IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col45 in DEFAULT_COL_45 or UPDATED_COL_45
        defaultQueryCommon50ShouldBeFound("col45.in=" + DEFAULT_COL_45 + "," + UPDATED_COL_45);

        // Get all the queryCommon50List where col45 equals to UPDATED_COL_45
        defaultQueryCommon50ShouldNotBeFound("col45.in=" + UPDATED_COL_45);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol45IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col45 is not null
        defaultQueryCommon50ShouldBeFound("col45.specified=true");

        // Get all the queryCommon50List where col45 is null
        defaultQueryCommon50ShouldNotBeFound("col45.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol46IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col46 equals to DEFAULT_COL_46
        defaultQueryCommon50ShouldBeFound("col46.equals=" + DEFAULT_COL_46);

        // Get all the queryCommon50List where col46 equals to UPDATED_COL_46
        defaultQueryCommon50ShouldNotBeFound("col46.equals=" + UPDATED_COL_46);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol46IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col46 in DEFAULT_COL_46 or UPDATED_COL_46
        defaultQueryCommon50ShouldBeFound("col46.in=" + DEFAULT_COL_46 + "," + UPDATED_COL_46);

        // Get all the queryCommon50List where col46 equals to UPDATED_COL_46
        defaultQueryCommon50ShouldNotBeFound("col46.in=" + UPDATED_COL_46);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol46IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col46 is not null
        defaultQueryCommon50ShouldBeFound("col46.specified=true");

        // Get all the queryCommon50List where col46 is null
        defaultQueryCommon50ShouldNotBeFound("col46.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol47IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col47 equals to DEFAULT_COL_47
        defaultQueryCommon50ShouldBeFound("col47.equals=" + DEFAULT_COL_47);

        // Get all the queryCommon50List where col47 equals to UPDATED_COL_47
        defaultQueryCommon50ShouldNotBeFound("col47.equals=" + UPDATED_COL_47);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol47IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col47 in DEFAULT_COL_47 or UPDATED_COL_47
        defaultQueryCommon50ShouldBeFound("col47.in=" + DEFAULT_COL_47 + "," + UPDATED_COL_47);

        // Get all the queryCommon50List where col47 equals to UPDATED_COL_47
        defaultQueryCommon50ShouldNotBeFound("col47.in=" + UPDATED_COL_47);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol47IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col47 is not null
        defaultQueryCommon50ShouldBeFound("col47.specified=true");

        // Get all the queryCommon50List where col47 is null
        defaultQueryCommon50ShouldNotBeFound("col47.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol48IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col48 equals to DEFAULT_COL_48
        defaultQueryCommon50ShouldBeFound("col48.equals=" + DEFAULT_COL_48);

        // Get all the queryCommon50List where col48 equals to UPDATED_COL_48
        defaultQueryCommon50ShouldNotBeFound("col48.equals=" + UPDATED_COL_48);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol48IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col48 in DEFAULT_COL_48 or UPDATED_COL_48
        defaultQueryCommon50ShouldBeFound("col48.in=" + DEFAULT_COL_48 + "," + UPDATED_COL_48);

        // Get all the queryCommon50List where col48 equals to UPDATED_COL_48
        defaultQueryCommon50ShouldNotBeFound("col48.in=" + UPDATED_COL_48);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol48IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col48 is not null
        defaultQueryCommon50ShouldBeFound("col48.specified=true");

        // Get all the queryCommon50List where col48 is null
        defaultQueryCommon50ShouldNotBeFound("col48.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol49IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col49 equals to DEFAULT_COL_49
        defaultQueryCommon50ShouldBeFound("col49.equals=" + DEFAULT_COL_49);

        // Get all the queryCommon50List where col49 equals to UPDATED_COL_49
        defaultQueryCommon50ShouldNotBeFound("col49.equals=" + UPDATED_COL_49);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol49IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col49 in DEFAULT_COL_49 or UPDATED_COL_49
        defaultQueryCommon50ShouldBeFound("col49.in=" + DEFAULT_COL_49 + "," + UPDATED_COL_49);

        // Get all the queryCommon50List where col49 equals to UPDATED_COL_49
        defaultQueryCommon50ShouldNotBeFound("col49.in=" + UPDATED_COL_49);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol49IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col49 is not null
        defaultQueryCommon50ShouldBeFound("col49.specified=true");

        // Get all the queryCommon50List where col49 is null
        defaultQueryCommon50ShouldNotBeFound("col49.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol50IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col50 equals to DEFAULT_COL_50
        defaultQueryCommon50ShouldBeFound("col50.equals=" + DEFAULT_COL_50);

        // Get all the queryCommon50List where col50 equals to UPDATED_COL_50
        defaultQueryCommon50ShouldNotBeFound("col50.equals=" + UPDATED_COL_50);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol50IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col50 in DEFAULT_COL_50 or UPDATED_COL_50
        defaultQueryCommon50ShouldBeFound("col50.in=" + DEFAULT_COL_50 + "," + UPDATED_COL_50);

        // Get all the queryCommon50List where col50 equals to UPDATED_COL_50
        defaultQueryCommon50ShouldNotBeFound("col50.in=" + UPDATED_COL_50);
    }

    @Test
    @Transactional
    public void getAllQueryCommon50SByCol50IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        // Get all the queryCommon50List where col50 is not null
        defaultQueryCommon50ShouldBeFound("col50.specified=true");

        // Get all the queryCommon50List where col50 is null
        defaultQueryCommon50ShouldNotBeFound("col50.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultQueryCommon50ShouldBeFound(String filter) throws Exception {
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryCommon50.getId().intValue())))
            .andExpect(jsonPath("$.[*].queryName").value(hasItem(DEFAULT_QUERY_NAME.toString())))
            .andExpect(jsonPath("$.[*].queryDate").value(hasItem(DEFAULT_QUERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].queryUser").value(hasItem(DEFAULT_QUERY_USER.toString())))
            .andExpect(jsonPath("$.[*].col01").value(hasItem(DEFAULT_COL_01.toString())))
            .andExpect(jsonPath("$.[*].col02").value(hasItem(DEFAULT_COL_02.toString())))
            .andExpect(jsonPath("$.[*].col03").value(hasItem(DEFAULT_COL_03.toString())))
            .andExpect(jsonPath("$.[*].col04").value(hasItem(DEFAULT_COL_04.toString())))
            .andExpect(jsonPath("$.[*].col05").value(hasItem(DEFAULT_COL_05.toString())))
            .andExpect(jsonPath("$.[*].col06").value(hasItem(DEFAULT_COL_06.toString())))
            .andExpect(jsonPath("$.[*].col07").value(hasItem(DEFAULT_COL_07.toString())))
            .andExpect(jsonPath("$.[*].col08").value(hasItem(DEFAULT_COL_08.toString())))
            .andExpect(jsonPath("$.[*].col09").value(hasItem(DEFAULT_COL_09.toString())))
            .andExpect(jsonPath("$.[*].col10").value(hasItem(DEFAULT_COL_10.toString())))
            .andExpect(jsonPath("$.[*].col11").value(hasItem(DEFAULT_COL_11.toString())))
            .andExpect(jsonPath("$.[*].col12").value(hasItem(DEFAULT_COL_12.toString())))
            .andExpect(jsonPath("$.[*].col13").value(hasItem(DEFAULT_COL_13.toString())))
            .andExpect(jsonPath("$.[*].col14").value(hasItem(DEFAULT_COL_14.toString())))
            .andExpect(jsonPath("$.[*].col15").value(hasItem(DEFAULT_COL_15.toString())))
            .andExpect(jsonPath("$.[*].col16").value(hasItem(DEFAULT_COL_16.toString())))
            .andExpect(jsonPath("$.[*].col17").value(hasItem(DEFAULT_COL_17.toString())))
            .andExpect(jsonPath("$.[*].col18").value(hasItem(DEFAULT_COL_18.toString())))
            .andExpect(jsonPath("$.[*].col19").value(hasItem(DEFAULT_COL_19.toString())))
            .andExpect(jsonPath("$.[*].col20").value(hasItem(DEFAULT_COL_20.toString())))
            .andExpect(jsonPath("$.[*].col21").value(hasItem(DEFAULT_COL_21.toString())))
            .andExpect(jsonPath("$.[*].col22").value(hasItem(DEFAULT_COL_22.toString())))
            .andExpect(jsonPath("$.[*].col23").value(hasItem(DEFAULT_COL_23.toString())))
            .andExpect(jsonPath("$.[*].col24").value(hasItem(DEFAULT_COL_24.toString())))
            .andExpect(jsonPath("$.[*].col25").value(hasItem(DEFAULT_COL_25.toString())))
            .andExpect(jsonPath("$.[*].col26").value(hasItem(DEFAULT_COL_26.toString())))
            .andExpect(jsonPath("$.[*].col27").value(hasItem(DEFAULT_COL_27.toString())))
            .andExpect(jsonPath("$.[*].col28").value(hasItem(DEFAULT_COL_28.toString())))
            .andExpect(jsonPath("$.[*].col29").value(hasItem(DEFAULT_COL_29.toString())))
            .andExpect(jsonPath("$.[*].col30").value(hasItem(DEFAULT_COL_30.toString())))
            .andExpect(jsonPath("$.[*].col31").value(hasItem(DEFAULT_COL_31.toString())))
            .andExpect(jsonPath("$.[*].col32").value(hasItem(DEFAULT_COL_32.toString())))
            .andExpect(jsonPath("$.[*].col33").value(hasItem(DEFAULT_COL_33.toString())))
            .andExpect(jsonPath("$.[*].col34").value(hasItem(DEFAULT_COL_34.toString())))
            .andExpect(jsonPath("$.[*].col35").value(hasItem(DEFAULT_COL_35.toString())))
            .andExpect(jsonPath("$.[*].col36").value(hasItem(DEFAULT_COL_36.toString())))
            .andExpect(jsonPath("$.[*].col37").value(hasItem(DEFAULT_COL_37.toString())))
            .andExpect(jsonPath("$.[*].col38").value(hasItem(DEFAULT_COL_38.toString())))
            .andExpect(jsonPath("$.[*].col39").value(hasItem(DEFAULT_COL_39.toString())))
            .andExpect(jsonPath("$.[*].col40").value(hasItem(DEFAULT_COL_40.toString())))
            .andExpect(jsonPath("$.[*].col41").value(hasItem(DEFAULT_COL_41.toString())))
            .andExpect(jsonPath("$.[*].col42").value(hasItem(DEFAULT_COL_42.toString())))
            .andExpect(jsonPath("$.[*].col43").value(hasItem(DEFAULT_COL_43.toString())))
            .andExpect(jsonPath("$.[*].col44").value(hasItem(DEFAULT_COL_44.toString())))
            .andExpect(jsonPath("$.[*].col45").value(hasItem(DEFAULT_COL_45.toString())))
            .andExpect(jsonPath("$.[*].col46").value(hasItem(DEFAULT_COL_46.toString())))
            .andExpect(jsonPath("$.[*].col47").value(hasItem(DEFAULT_COL_47.toString())))
            .andExpect(jsonPath("$.[*].col48").value(hasItem(DEFAULT_COL_48.toString())))
            .andExpect(jsonPath("$.[*].col49").value(hasItem(DEFAULT_COL_49.toString())))
            .andExpect(jsonPath("$.[*].col50").value(hasItem(DEFAULT_COL_50.toString())));

        // Check, that the count call also returns 1
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultQueryCommon50ShouldNotBeFound(String filter) throws Exception {
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQueryCommon50() throws Exception {
        // Get the queryCommon50
        restQueryCommon50MockMvc.perform(get("/api/query-common-50-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueryCommon50() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        int databaseSizeBeforeUpdate = queryCommon50Repository.findAll().size();

        // Update the queryCommon50
        QueryCommon50 updatedQueryCommon50 = queryCommon50Repository.findById(queryCommon50.getId()).get();
        // Disconnect from session so that the updates on updatedQueryCommon50 are not directly saved in db
        em.detach(updatedQueryCommon50);
        updatedQueryCommon50
            .queryName(UPDATED_QUERY_NAME)
            .queryDate(UPDATED_QUERY_DATE)
            .queryUser(UPDATED_QUERY_USER)
            .col01(UPDATED_COL_01)
            .col02(UPDATED_COL_02)
            .col03(UPDATED_COL_03)
            .col04(UPDATED_COL_04)
            .col05(UPDATED_COL_05)
            .col06(UPDATED_COL_06)
            .col07(UPDATED_COL_07)
            .col08(UPDATED_COL_08)
            .col09(UPDATED_COL_09)
            .col10(UPDATED_COL_10)
            .col11(UPDATED_COL_11)
            .col12(UPDATED_COL_12)
            .col13(UPDATED_COL_13)
            .col14(UPDATED_COL_14)
            .col15(UPDATED_COL_15)
            .col16(UPDATED_COL_16)
            .col17(UPDATED_COL_17)
            .col18(UPDATED_COL_18)
            .col19(UPDATED_COL_19)
            .col20(UPDATED_COL_20)
            .col21(UPDATED_COL_21)
            .col22(UPDATED_COL_22)
            .col23(UPDATED_COL_23)
            .col24(UPDATED_COL_24)
            .col25(UPDATED_COL_25)
            .col26(UPDATED_COL_26)
            .col27(UPDATED_COL_27)
            .col28(UPDATED_COL_28)
            .col29(UPDATED_COL_29)
            .col30(UPDATED_COL_30)
            .col31(UPDATED_COL_31)
            .col32(UPDATED_COL_32)
            .col33(UPDATED_COL_33)
            .col34(UPDATED_COL_34)
            .col35(UPDATED_COL_35)
            .col36(UPDATED_COL_36)
            .col37(UPDATED_COL_37)
            .col38(UPDATED_COL_38)
            .col39(UPDATED_COL_39)
            .col40(UPDATED_COL_40)
            .col41(UPDATED_COL_41)
            .col42(UPDATED_COL_42)
            .col43(UPDATED_COL_43)
            .col44(UPDATED_COL_44)
            .col45(UPDATED_COL_45)
            .col46(UPDATED_COL_46)
            .col47(UPDATED_COL_47)
            .col48(UPDATED_COL_48)
            .col49(UPDATED_COL_49)
            .col50(UPDATED_COL_50);
        QueryCommon50DTO queryCommon50DTO = queryCommon50Mapper.toDto(updatedQueryCommon50);

        restQueryCommon50MockMvc.perform(put("/api/query-common-50-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon50DTO)))
            .andExpect(status().isOk());

        // Validate the QueryCommon50 in the database
        List<QueryCommon50> queryCommon50List = queryCommon50Repository.findAll();
        assertThat(queryCommon50List).hasSize(databaseSizeBeforeUpdate);
        QueryCommon50 testQueryCommon50 = queryCommon50List.get(queryCommon50List.size() - 1);
        assertThat(testQueryCommon50.getQueryName()).isEqualTo(UPDATED_QUERY_NAME);
        assertThat(testQueryCommon50.getQueryDate()).isEqualTo(UPDATED_QUERY_DATE);
        assertThat(testQueryCommon50.getQueryUser()).isEqualTo(UPDATED_QUERY_USER);
        assertThat(testQueryCommon50.getCol01()).isEqualTo(UPDATED_COL_01);
        assertThat(testQueryCommon50.getCol02()).isEqualTo(UPDATED_COL_02);
        assertThat(testQueryCommon50.getCol03()).isEqualTo(UPDATED_COL_03);
        assertThat(testQueryCommon50.getCol04()).isEqualTo(UPDATED_COL_04);
        assertThat(testQueryCommon50.getCol05()).isEqualTo(UPDATED_COL_05);
        assertThat(testQueryCommon50.getCol06()).isEqualTo(UPDATED_COL_06);
        assertThat(testQueryCommon50.getCol07()).isEqualTo(UPDATED_COL_07);
        assertThat(testQueryCommon50.getCol08()).isEqualTo(UPDATED_COL_08);
        assertThat(testQueryCommon50.getCol09()).isEqualTo(UPDATED_COL_09);
        assertThat(testQueryCommon50.getCol10()).isEqualTo(UPDATED_COL_10);
        assertThat(testQueryCommon50.getCol11()).isEqualTo(UPDATED_COL_11);
        assertThat(testQueryCommon50.getCol12()).isEqualTo(UPDATED_COL_12);
        assertThat(testQueryCommon50.getCol13()).isEqualTo(UPDATED_COL_13);
        assertThat(testQueryCommon50.getCol14()).isEqualTo(UPDATED_COL_14);
        assertThat(testQueryCommon50.getCol15()).isEqualTo(UPDATED_COL_15);
        assertThat(testQueryCommon50.getCol16()).isEqualTo(UPDATED_COL_16);
        assertThat(testQueryCommon50.getCol17()).isEqualTo(UPDATED_COL_17);
        assertThat(testQueryCommon50.getCol18()).isEqualTo(UPDATED_COL_18);
        assertThat(testQueryCommon50.getCol19()).isEqualTo(UPDATED_COL_19);
        assertThat(testQueryCommon50.getCol20()).isEqualTo(UPDATED_COL_20);
        assertThat(testQueryCommon50.getCol21()).isEqualTo(UPDATED_COL_21);
        assertThat(testQueryCommon50.getCol22()).isEqualTo(UPDATED_COL_22);
        assertThat(testQueryCommon50.getCol23()).isEqualTo(UPDATED_COL_23);
        assertThat(testQueryCommon50.getCol24()).isEqualTo(UPDATED_COL_24);
        assertThat(testQueryCommon50.getCol25()).isEqualTo(UPDATED_COL_25);
        assertThat(testQueryCommon50.getCol26()).isEqualTo(UPDATED_COL_26);
        assertThat(testQueryCommon50.getCol27()).isEqualTo(UPDATED_COL_27);
        assertThat(testQueryCommon50.getCol28()).isEqualTo(UPDATED_COL_28);
        assertThat(testQueryCommon50.getCol29()).isEqualTo(UPDATED_COL_29);
        assertThat(testQueryCommon50.getCol30()).isEqualTo(UPDATED_COL_30);
        assertThat(testQueryCommon50.getCol31()).isEqualTo(UPDATED_COL_31);
        assertThat(testQueryCommon50.getCol32()).isEqualTo(UPDATED_COL_32);
        assertThat(testQueryCommon50.getCol33()).isEqualTo(UPDATED_COL_33);
        assertThat(testQueryCommon50.getCol34()).isEqualTo(UPDATED_COL_34);
        assertThat(testQueryCommon50.getCol35()).isEqualTo(UPDATED_COL_35);
        assertThat(testQueryCommon50.getCol36()).isEqualTo(UPDATED_COL_36);
        assertThat(testQueryCommon50.getCol37()).isEqualTo(UPDATED_COL_37);
        assertThat(testQueryCommon50.getCol38()).isEqualTo(UPDATED_COL_38);
        assertThat(testQueryCommon50.getCol39()).isEqualTo(UPDATED_COL_39);
        assertThat(testQueryCommon50.getCol40()).isEqualTo(UPDATED_COL_40);
        assertThat(testQueryCommon50.getCol41()).isEqualTo(UPDATED_COL_41);
        assertThat(testQueryCommon50.getCol42()).isEqualTo(UPDATED_COL_42);
        assertThat(testQueryCommon50.getCol43()).isEqualTo(UPDATED_COL_43);
        assertThat(testQueryCommon50.getCol44()).isEqualTo(UPDATED_COL_44);
        assertThat(testQueryCommon50.getCol45()).isEqualTo(UPDATED_COL_45);
        assertThat(testQueryCommon50.getCol46()).isEqualTo(UPDATED_COL_46);
        assertThat(testQueryCommon50.getCol47()).isEqualTo(UPDATED_COL_47);
        assertThat(testQueryCommon50.getCol48()).isEqualTo(UPDATED_COL_48);
        assertThat(testQueryCommon50.getCol49()).isEqualTo(UPDATED_COL_49);
        assertThat(testQueryCommon50.getCol50()).isEqualTo(UPDATED_COL_50);

        // Validate the QueryCommon50 in Elasticsearch
        verify(mockQueryCommon50SearchRepository, times(1)).save(testQueryCommon50);
    }

    @Test
    @Transactional
    public void updateNonExistingQueryCommon50() throws Exception {
        int databaseSizeBeforeUpdate = queryCommon50Repository.findAll().size();

        // Create the QueryCommon50
        QueryCommon50DTO queryCommon50DTO = queryCommon50Mapper.toDto(queryCommon50);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQueryCommon50MockMvc.perform(put("/api/query-common-50-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon50DTO)))
            .andExpect(status().isBadRequest());

        // Validate the QueryCommon50 in the database
        List<QueryCommon50> queryCommon50List = queryCommon50Repository.findAll();
        assertThat(queryCommon50List).hasSize(databaseSizeBeforeUpdate);

        // Validate the QueryCommon50 in Elasticsearch
        verify(mockQueryCommon50SearchRepository, times(0)).save(queryCommon50);
    }

    @Test
    @Transactional
    public void deleteQueryCommon50() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);

        int databaseSizeBeforeDelete = queryCommon50Repository.findAll().size();

        // Delete the queryCommon50
        restQueryCommon50MockMvc.perform(delete("/api/query-common-50-s/{id}", queryCommon50.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QueryCommon50> queryCommon50List = queryCommon50Repository.findAll();
        assertThat(queryCommon50List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the QueryCommon50 in Elasticsearch
        verify(mockQueryCommon50SearchRepository, times(1)).deleteById(queryCommon50.getId());
    }

    @Test
    @Transactional
    public void searchQueryCommon50() throws Exception {
        // Initialize the database
        queryCommon50Repository.saveAndFlush(queryCommon50);
        when(mockQueryCommon50SearchRepository.search(queryStringQuery("id:" + queryCommon50.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(queryCommon50), PageRequest.of(0, 1), 1));
        // Search the queryCommon50
        restQueryCommon50MockMvc.perform(get("/api/_search/query-common-50-s?query=id:" + queryCommon50.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryCommon50.getId().intValue())))
            .andExpect(jsonPath("$.[*].queryName").value(hasItem(DEFAULT_QUERY_NAME)))
            .andExpect(jsonPath("$.[*].queryDate").value(hasItem(DEFAULT_QUERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].queryUser").value(hasItem(DEFAULT_QUERY_USER)))
            .andExpect(jsonPath("$.[*].col01").value(hasItem(DEFAULT_COL_01)))
            .andExpect(jsonPath("$.[*].col02").value(hasItem(DEFAULT_COL_02)))
            .andExpect(jsonPath("$.[*].col03").value(hasItem(DEFAULT_COL_03)))
            .andExpect(jsonPath("$.[*].col04").value(hasItem(DEFAULT_COL_04)))
            .andExpect(jsonPath("$.[*].col05").value(hasItem(DEFAULT_COL_05)))
            .andExpect(jsonPath("$.[*].col06").value(hasItem(DEFAULT_COL_06)))
            .andExpect(jsonPath("$.[*].col07").value(hasItem(DEFAULT_COL_07)))
            .andExpect(jsonPath("$.[*].col08").value(hasItem(DEFAULT_COL_08)))
            .andExpect(jsonPath("$.[*].col09").value(hasItem(DEFAULT_COL_09)))
            .andExpect(jsonPath("$.[*].col10").value(hasItem(DEFAULT_COL_10)))
            .andExpect(jsonPath("$.[*].col11").value(hasItem(DEFAULT_COL_11)))
            .andExpect(jsonPath("$.[*].col12").value(hasItem(DEFAULT_COL_12)))
            .andExpect(jsonPath("$.[*].col13").value(hasItem(DEFAULT_COL_13)))
            .andExpect(jsonPath("$.[*].col14").value(hasItem(DEFAULT_COL_14)))
            .andExpect(jsonPath("$.[*].col15").value(hasItem(DEFAULT_COL_15)))
            .andExpect(jsonPath("$.[*].col16").value(hasItem(DEFAULT_COL_16)))
            .andExpect(jsonPath("$.[*].col17").value(hasItem(DEFAULT_COL_17)))
            .andExpect(jsonPath("$.[*].col18").value(hasItem(DEFAULT_COL_18)))
            .andExpect(jsonPath("$.[*].col19").value(hasItem(DEFAULT_COL_19)))
            .andExpect(jsonPath("$.[*].col20").value(hasItem(DEFAULT_COL_20)))
            .andExpect(jsonPath("$.[*].col21").value(hasItem(DEFAULT_COL_21)))
            .andExpect(jsonPath("$.[*].col22").value(hasItem(DEFAULT_COL_22)))
            .andExpect(jsonPath("$.[*].col23").value(hasItem(DEFAULT_COL_23)))
            .andExpect(jsonPath("$.[*].col24").value(hasItem(DEFAULT_COL_24)))
            .andExpect(jsonPath("$.[*].col25").value(hasItem(DEFAULT_COL_25)))
            .andExpect(jsonPath("$.[*].col26").value(hasItem(DEFAULT_COL_26)))
            .andExpect(jsonPath("$.[*].col27").value(hasItem(DEFAULT_COL_27)))
            .andExpect(jsonPath("$.[*].col28").value(hasItem(DEFAULT_COL_28)))
            .andExpect(jsonPath("$.[*].col29").value(hasItem(DEFAULT_COL_29)))
            .andExpect(jsonPath("$.[*].col30").value(hasItem(DEFAULT_COL_30)))
            .andExpect(jsonPath("$.[*].col31").value(hasItem(DEFAULT_COL_31)))
            .andExpect(jsonPath("$.[*].col32").value(hasItem(DEFAULT_COL_32)))
            .andExpect(jsonPath("$.[*].col33").value(hasItem(DEFAULT_COL_33)))
            .andExpect(jsonPath("$.[*].col34").value(hasItem(DEFAULT_COL_34)))
            .andExpect(jsonPath("$.[*].col35").value(hasItem(DEFAULT_COL_35)))
            .andExpect(jsonPath("$.[*].col36").value(hasItem(DEFAULT_COL_36)))
            .andExpect(jsonPath("$.[*].col37").value(hasItem(DEFAULT_COL_37)))
            .andExpect(jsonPath("$.[*].col38").value(hasItem(DEFAULT_COL_38)))
            .andExpect(jsonPath("$.[*].col39").value(hasItem(DEFAULT_COL_39)))
            .andExpect(jsonPath("$.[*].col40").value(hasItem(DEFAULT_COL_40)))
            .andExpect(jsonPath("$.[*].col41").value(hasItem(DEFAULT_COL_41)))
            .andExpect(jsonPath("$.[*].col42").value(hasItem(DEFAULT_COL_42)))
            .andExpect(jsonPath("$.[*].col43").value(hasItem(DEFAULT_COL_43)))
            .andExpect(jsonPath("$.[*].col44").value(hasItem(DEFAULT_COL_44)))
            .andExpect(jsonPath("$.[*].col45").value(hasItem(DEFAULT_COL_45)))
            .andExpect(jsonPath("$.[*].col46").value(hasItem(DEFAULT_COL_46)))
            .andExpect(jsonPath("$.[*].col47").value(hasItem(DEFAULT_COL_47)))
            .andExpect(jsonPath("$.[*].col48").value(hasItem(DEFAULT_COL_48)))
            .andExpect(jsonPath("$.[*].col49").value(hasItem(DEFAULT_COL_49)))
            .andExpect(jsonPath("$.[*].col50").value(hasItem(DEFAULT_COL_50)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QueryCommon50.class);
        QueryCommon50 queryCommon501 = new QueryCommon50();
        queryCommon501.setId(1L);
        QueryCommon50 queryCommon502 = new QueryCommon50();
        queryCommon502.setId(queryCommon501.getId());
        assertThat(queryCommon501).isEqualTo(queryCommon502);
        queryCommon502.setId(2L);
        assertThat(queryCommon501).isNotEqualTo(queryCommon502);
        queryCommon501.setId(null);
        assertThat(queryCommon501).isNotEqualTo(queryCommon502);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QueryCommon50DTO.class);
        QueryCommon50DTO queryCommon50DTO1 = new QueryCommon50DTO();
        queryCommon50DTO1.setId(1L);
        QueryCommon50DTO queryCommon50DTO2 = new QueryCommon50DTO();
        assertThat(queryCommon50DTO1).isNotEqualTo(queryCommon50DTO2);
        queryCommon50DTO2.setId(queryCommon50DTO1.getId());
        assertThat(queryCommon50DTO1).isEqualTo(queryCommon50DTO2);
        queryCommon50DTO2.setId(2L);
        assertThat(queryCommon50DTO1).isNotEqualTo(queryCommon50DTO2);
        queryCommon50DTO1.setId(null);
        assertThat(queryCommon50DTO1).isNotEqualTo(queryCommon50DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(queryCommon50Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(queryCommon50Mapper.fromId(null)).isNull();
    }
}
