package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.QueryCommon10;
import com.aerothinker.kms.repository.QueryCommon10Repository;
import com.aerothinker.kms.repository.search.QueryCommon10SearchRepository;
import com.aerothinker.kms.service.QueryCommon10Service;
import com.aerothinker.kms.service.dto.QueryCommon10DTO;
import com.aerothinker.kms.service.mapper.QueryCommon10Mapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.QueryCommon10Criteria;
import com.aerothinker.kms.service.QueryCommon10QueryService;

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
 * Test class for the QueryCommon10Resource REST controller.
 *
 * @see QueryCommon10Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class QueryCommon10ResourceIntTest {

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

    @Autowired
    private QueryCommon10Repository queryCommon10Repository;

    @Autowired
    private QueryCommon10Mapper queryCommon10Mapper;

    @Autowired
    private QueryCommon10Service queryCommon10Service;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.QueryCommon10SearchRepositoryMockConfiguration
     */
    @Autowired
    private QueryCommon10SearchRepository mockQueryCommon10SearchRepository;

    @Autowired
    private QueryCommon10QueryService queryCommon10QueryService;

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

    private MockMvc restQueryCommon10MockMvc;

    private QueryCommon10 queryCommon10;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QueryCommon10Resource queryCommon10Resource = new QueryCommon10Resource(queryCommon10Service, queryCommon10QueryService);
        this.restQueryCommon10MockMvc = MockMvcBuilders.standaloneSetup(queryCommon10Resource)
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
    public static QueryCommon10 createEntity(EntityManager em) {
        QueryCommon10 queryCommon10 = new QueryCommon10()
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
            .col10(DEFAULT_COL_10);
        return queryCommon10;
    }

    @Before
    public void initTest() {
        queryCommon10 = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueryCommon10() throws Exception {
        int databaseSizeBeforeCreate = queryCommon10Repository.findAll().size();

        // Create the QueryCommon10
        QueryCommon10DTO queryCommon10DTO = queryCommon10Mapper.toDto(queryCommon10);
        restQueryCommon10MockMvc.perform(post("/api/query-common-10-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon10DTO)))
            .andExpect(status().isCreated());

        // Validate the QueryCommon10 in the database
        List<QueryCommon10> queryCommon10List = queryCommon10Repository.findAll();
        assertThat(queryCommon10List).hasSize(databaseSizeBeforeCreate + 1);
        QueryCommon10 testQueryCommon10 = queryCommon10List.get(queryCommon10List.size() - 1);
        assertThat(testQueryCommon10.getQueryName()).isEqualTo(DEFAULT_QUERY_NAME);
        assertThat(testQueryCommon10.getQueryDate()).isEqualTo(DEFAULT_QUERY_DATE);
        assertThat(testQueryCommon10.getQueryUser()).isEqualTo(DEFAULT_QUERY_USER);
        assertThat(testQueryCommon10.getCol01()).isEqualTo(DEFAULT_COL_01);
        assertThat(testQueryCommon10.getCol02()).isEqualTo(DEFAULT_COL_02);
        assertThat(testQueryCommon10.getCol03()).isEqualTo(DEFAULT_COL_03);
        assertThat(testQueryCommon10.getCol04()).isEqualTo(DEFAULT_COL_04);
        assertThat(testQueryCommon10.getCol05()).isEqualTo(DEFAULT_COL_05);
        assertThat(testQueryCommon10.getCol06()).isEqualTo(DEFAULT_COL_06);
        assertThat(testQueryCommon10.getCol07()).isEqualTo(DEFAULT_COL_07);
        assertThat(testQueryCommon10.getCol08()).isEqualTo(DEFAULT_COL_08);
        assertThat(testQueryCommon10.getCol09()).isEqualTo(DEFAULT_COL_09);
        assertThat(testQueryCommon10.getCol10()).isEqualTo(DEFAULT_COL_10);

        // Validate the QueryCommon10 in Elasticsearch
        verify(mockQueryCommon10SearchRepository, times(1)).save(testQueryCommon10);
    }

    @Test
    @Transactional
    public void createQueryCommon10WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queryCommon10Repository.findAll().size();

        // Create the QueryCommon10 with an existing ID
        queryCommon10.setId(1L);
        QueryCommon10DTO queryCommon10DTO = queryCommon10Mapper.toDto(queryCommon10);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueryCommon10MockMvc.perform(post("/api/query-common-10-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon10DTO)))
            .andExpect(status().isBadRequest());

        // Validate the QueryCommon10 in the database
        List<QueryCommon10> queryCommon10List = queryCommon10Repository.findAll();
        assertThat(queryCommon10List).hasSize(databaseSizeBeforeCreate);

        // Validate the QueryCommon10 in Elasticsearch
        verify(mockQueryCommon10SearchRepository, times(0)).save(queryCommon10);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10S() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryCommon10.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].col10").value(hasItem(DEFAULT_COL_10.toString())));
    }
    
    @Test
    @Transactional
    public void getQueryCommon10() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get the queryCommon10
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s/{id}", queryCommon10.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queryCommon10.getId().intValue()))
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
            .andExpect(jsonPath("$.col10").value(DEFAULT_COL_10.toString()));
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryName equals to DEFAULT_QUERY_NAME
        defaultQueryCommon10ShouldBeFound("queryName.equals=" + DEFAULT_QUERY_NAME);

        // Get all the queryCommon10List where queryName equals to UPDATED_QUERY_NAME
        defaultQueryCommon10ShouldNotBeFound("queryName.equals=" + UPDATED_QUERY_NAME);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryNameIsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryName in DEFAULT_QUERY_NAME or UPDATED_QUERY_NAME
        defaultQueryCommon10ShouldBeFound("queryName.in=" + DEFAULT_QUERY_NAME + "," + UPDATED_QUERY_NAME);

        // Get all the queryCommon10List where queryName equals to UPDATED_QUERY_NAME
        defaultQueryCommon10ShouldNotBeFound("queryName.in=" + UPDATED_QUERY_NAME);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryName is not null
        defaultQueryCommon10ShouldBeFound("queryName.specified=true");

        // Get all the queryCommon10List where queryName is null
        defaultQueryCommon10ShouldNotBeFound("queryName.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryDate equals to DEFAULT_QUERY_DATE
        defaultQueryCommon10ShouldBeFound("queryDate.equals=" + DEFAULT_QUERY_DATE);

        // Get all the queryCommon10List where queryDate equals to UPDATED_QUERY_DATE
        defaultQueryCommon10ShouldNotBeFound("queryDate.equals=" + UPDATED_QUERY_DATE);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryDateIsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryDate in DEFAULT_QUERY_DATE or UPDATED_QUERY_DATE
        defaultQueryCommon10ShouldBeFound("queryDate.in=" + DEFAULT_QUERY_DATE + "," + UPDATED_QUERY_DATE);

        // Get all the queryCommon10List where queryDate equals to UPDATED_QUERY_DATE
        defaultQueryCommon10ShouldNotBeFound("queryDate.in=" + UPDATED_QUERY_DATE);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryDate is not null
        defaultQueryCommon10ShouldBeFound("queryDate.specified=true");

        // Get all the queryCommon10List where queryDate is null
        defaultQueryCommon10ShouldNotBeFound("queryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryUserIsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryUser equals to DEFAULT_QUERY_USER
        defaultQueryCommon10ShouldBeFound("queryUser.equals=" + DEFAULT_QUERY_USER);

        // Get all the queryCommon10List where queryUser equals to UPDATED_QUERY_USER
        defaultQueryCommon10ShouldNotBeFound("queryUser.equals=" + UPDATED_QUERY_USER);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryUserIsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryUser in DEFAULT_QUERY_USER or UPDATED_QUERY_USER
        defaultQueryCommon10ShouldBeFound("queryUser.in=" + DEFAULT_QUERY_USER + "," + UPDATED_QUERY_USER);

        // Get all the queryCommon10List where queryUser equals to UPDATED_QUERY_USER
        defaultQueryCommon10ShouldNotBeFound("queryUser.in=" + UPDATED_QUERY_USER);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByQueryUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where queryUser is not null
        defaultQueryCommon10ShouldBeFound("queryUser.specified=true");

        // Get all the queryCommon10List where queryUser is null
        defaultQueryCommon10ShouldNotBeFound("queryUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol01IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col01 equals to DEFAULT_COL_01
        defaultQueryCommon10ShouldBeFound("col01.equals=" + DEFAULT_COL_01);

        // Get all the queryCommon10List where col01 equals to UPDATED_COL_01
        defaultQueryCommon10ShouldNotBeFound("col01.equals=" + UPDATED_COL_01);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol01IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col01 in DEFAULT_COL_01 or UPDATED_COL_01
        defaultQueryCommon10ShouldBeFound("col01.in=" + DEFAULT_COL_01 + "," + UPDATED_COL_01);

        // Get all the queryCommon10List where col01 equals to UPDATED_COL_01
        defaultQueryCommon10ShouldNotBeFound("col01.in=" + UPDATED_COL_01);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol01IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col01 is not null
        defaultQueryCommon10ShouldBeFound("col01.specified=true");

        // Get all the queryCommon10List where col01 is null
        defaultQueryCommon10ShouldNotBeFound("col01.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol02IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col02 equals to DEFAULT_COL_02
        defaultQueryCommon10ShouldBeFound("col02.equals=" + DEFAULT_COL_02);

        // Get all the queryCommon10List where col02 equals to UPDATED_COL_02
        defaultQueryCommon10ShouldNotBeFound("col02.equals=" + UPDATED_COL_02);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol02IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col02 in DEFAULT_COL_02 or UPDATED_COL_02
        defaultQueryCommon10ShouldBeFound("col02.in=" + DEFAULT_COL_02 + "," + UPDATED_COL_02);

        // Get all the queryCommon10List where col02 equals to UPDATED_COL_02
        defaultQueryCommon10ShouldNotBeFound("col02.in=" + UPDATED_COL_02);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol02IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col02 is not null
        defaultQueryCommon10ShouldBeFound("col02.specified=true");

        // Get all the queryCommon10List where col02 is null
        defaultQueryCommon10ShouldNotBeFound("col02.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol03IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col03 equals to DEFAULT_COL_03
        defaultQueryCommon10ShouldBeFound("col03.equals=" + DEFAULT_COL_03);

        // Get all the queryCommon10List where col03 equals to UPDATED_COL_03
        defaultQueryCommon10ShouldNotBeFound("col03.equals=" + UPDATED_COL_03);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol03IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col03 in DEFAULT_COL_03 or UPDATED_COL_03
        defaultQueryCommon10ShouldBeFound("col03.in=" + DEFAULT_COL_03 + "," + UPDATED_COL_03);

        // Get all the queryCommon10List where col03 equals to UPDATED_COL_03
        defaultQueryCommon10ShouldNotBeFound("col03.in=" + UPDATED_COL_03);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol03IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col03 is not null
        defaultQueryCommon10ShouldBeFound("col03.specified=true");

        // Get all the queryCommon10List where col03 is null
        defaultQueryCommon10ShouldNotBeFound("col03.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol04IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col04 equals to DEFAULT_COL_04
        defaultQueryCommon10ShouldBeFound("col04.equals=" + DEFAULT_COL_04);

        // Get all the queryCommon10List where col04 equals to UPDATED_COL_04
        defaultQueryCommon10ShouldNotBeFound("col04.equals=" + UPDATED_COL_04);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol04IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col04 in DEFAULT_COL_04 or UPDATED_COL_04
        defaultQueryCommon10ShouldBeFound("col04.in=" + DEFAULT_COL_04 + "," + UPDATED_COL_04);

        // Get all the queryCommon10List where col04 equals to UPDATED_COL_04
        defaultQueryCommon10ShouldNotBeFound("col04.in=" + UPDATED_COL_04);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol04IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col04 is not null
        defaultQueryCommon10ShouldBeFound("col04.specified=true");

        // Get all the queryCommon10List where col04 is null
        defaultQueryCommon10ShouldNotBeFound("col04.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol05IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col05 equals to DEFAULT_COL_05
        defaultQueryCommon10ShouldBeFound("col05.equals=" + DEFAULT_COL_05);

        // Get all the queryCommon10List where col05 equals to UPDATED_COL_05
        defaultQueryCommon10ShouldNotBeFound("col05.equals=" + UPDATED_COL_05);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol05IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col05 in DEFAULT_COL_05 or UPDATED_COL_05
        defaultQueryCommon10ShouldBeFound("col05.in=" + DEFAULT_COL_05 + "," + UPDATED_COL_05);

        // Get all the queryCommon10List where col05 equals to UPDATED_COL_05
        defaultQueryCommon10ShouldNotBeFound("col05.in=" + UPDATED_COL_05);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol05IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col05 is not null
        defaultQueryCommon10ShouldBeFound("col05.specified=true");

        // Get all the queryCommon10List where col05 is null
        defaultQueryCommon10ShouldNotBeFound("col05.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol06IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col06 equals to DEFAULT_COL_06
        defaultQueryCommon10ShouldBeFound("col06.equals=" + DEFAULT_COL_06);

        // Get all the queryCommon10List where col06 equals to UPDATED_COL_06
        defaultQueryCommon10ShouldNotBeFound("col06.equals=" + UPDATED_COL_06);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol06IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col06 in DEFAULT_COL_06 or UPDATED_COL_06
        defaultQueryCommon10ShouldBeFound("col06.in=" + DEFAULT_COL_06 + "," + UPDATED_COL_06);

        // Get all the queryCommon10List where col06 equals to UPDATED_COL_06
        defaultQueryCommon10ShouldNotBeFound("col06.in=" + UPDATED_COL_06);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol06IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col06 is not null
        defaultQueryCommon10ShouldBeFound("col06.specified=true");

        // Get all the queryCommon10List where col06 is null
        defaultQueryCommon10ShouldNotBeFound("col06.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol07IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col07 equals to DEFAULT_COL_07
        defaultQueryCommon10ShouldBeFound("col07.equals=" + DEFAULT_COL_07);

        // Get all the queryCommon10List where col07 equals to UPDATED_COL_07
        defaultQueryCommon10ShouldNotBeFound("col07.equals=" + UPDATED_COL_07);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol07IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col07 in DEFAULT_COL_07 or UPDATED_COL_07
        defaultQueryCommon10ShouldBeFound("col07.in=" + DEFAULT_COL_07 + "," + UPDATED_COL_07);

        // Get all the queryCommon10List where col07 equals to UPDATED_COL_07
        defaultQueryCommon10ShouldNotBeFound("col07.in=" + UPDATED_COL_07);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol07IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col07 is not null
        defaultQueryCommon10ShouldBeFound("col07.specified=true");

        // Get all the queryCommon10List where col07 is null
        defaultQueryCommon10ShouldNotBeFound("col07.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol08IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col08 equals to DEFAULT_COL_08
        defaultQueryCommon10ShouldBeFound("col08.equals=" + DEFAULT_COL_08);

        // Get all the queryCommon10List where col08 equals to UPDATED_COL_08
        defaultQueryCommon10ShouldNotBeFound("col08.equals=" + UPDATED_COL_08);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol08IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col08 in DEFAULT_COL_08 or UPDATED_COL_08
        defaultQueryCommon10ShouldBeFound("col08.in=" + DEFAULT_COL_08 + "," + UPDATED_COL_08);

        // Get all the queryCommon10List where col08 equals to UPDATED_COL_08
        defaultQueryCommon10ShouldNotBeFound("col08.in=" + UPDATED_COL_08);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol08IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col08 is not null
        defaultQueryCommon10ShouldBeFound("col08.specified=true");

        // Get all the queryCommon10List where col08 is null
        defaultQueryCommon10ShouldNotBeFound("col08.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol09IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col09 equals to DEFAULT_COL_09
        defaultQueryCommon10ShouldBeFound("col09.equals=" + DEFAULT_COL_09);

        // Get all the queryCommon10List where col09 equals to UPDATED_COL_09
        defaultQueryCommon10ShouldNotBeFound("col09.equals=" + UPDATED_COL_09);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol09IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col09 in DEFAULT_COL_09 or UPDATED_COL_09
        defaultQueryCommon10ShouldBeFound("col09.in=" + DEFAULT_COL_09 + "," + UPDATED_COL_09);

        // Get all the queryCommon10List where col09 equals to UPDATED_COL_09
        defaultQueryCommon10ShouldNotBeFound("col09.in=" + UPDATED_COL_09);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol09IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col09 is not null
        defaultQueryCommon10ShouldBeFound("col09.specified=true");

        // Get all the queryCommon10List where col09 is null
        defaultQueryCommon10ShouldNotBeFound("col09.specified=false");
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol10IsEqualToSomething() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col10 equals to DEFAULT_COL_10
        defaultQueryCommon10ShouldBeFound("col10.equals=" + DEFAULT_COL_10);

        // Get all the queryCommon10List where col10 equals to UPDATED_COL_10
        defaultQueryCommon10ShouldNotBeFound("col10.equals=" + UPDATED_COL_10);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol10IsInShouldWork() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col10 in DEFAULT_COL_10 or UPDATED_COL_10
        defaultQueryCommon10ShouldBeFound("col10.in=" + DEFAULT_COL_10 + "," + UPDATED_COL_10);

        // Get all the queryCommon10List where col10 equals to UPDATED_COL_10
        defaultQueryCommon10ShouldNotBeFound("col10.in=" + UPDATED_COL_10);
    }

    @Test
    @Transactional
    public void getAllQueryCommon10SByCol10IsNullOrNotNull() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        // Get all the queryCommon10List where col10 is not null
        defaultQueryCommon10ShouldBeFound("col10.specified=true");

        // Get all the queryCommon10List where col10 is null
        defaultQueryCommon10ShouldNotBeFound("col10.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultQueryCommon10ShouldBeFound(String filter) throws Exception {
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryCommon10.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].col10").value(hasItem(DEFAULT_COL_10.toString())));

        // Check, that the count call also returns 1
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultQueryCommon10ShouldNotBeFound(String filter) throws Exception {
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQueryCommon10() throws Exception {
        // Get the queryCommon10
        restQueryCommon10MockMvc.perform(get("/api/query-common-10-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueryCommon10() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        int databaseSizeBeforeUpdate = queryCommon10Repository.findAll().size();

        // Update the queryCommon10
        QueryCommon10 updatedQueryCommon10 = queryCommon10Repository.findById(queryCommon10.getId()).get();
        // Disconnect from session so that the updates on updatedQueryCommon10 are not directly saved in db
        em.detach(updatedQueryCommon10);
        updatedQueryCommon10
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
            .col10(UPDATED_COL_10);
        QueryCommon10DTO queryCommon10DTO = queryCommon10Mapper.toDto(updatedQueryCommon10);

        restQueryCommon10MockMvc.perform(put("/api/query-common-10-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon10DTO)))
            .andExpect(status().isOk());

        // Validate the QueryCommon10 in the database
        List<QueryCommon10> queryCommon10List = queryCommon10Repository.findAll();
        assertThat(queryCommon10List).hasSize(databaseSizeBeforeUpdate);
        QueryCommon10 testQueryCommon10 = queryCommon10List.get(queryCommon10List.size() - 1);
        assertThat(testQueryCommon10.getQueryName()).isEqualTo(UPDATED_QUERY_NAME);
        assertThat(testQueryCommon10.getQueryDate()).isEqualTo(UPDATED_QUERY_DATE);
        assertThat(testQueryCommon10.getQueryUser()).isEqualTo(UPDATED_QUERY_USER);
        assertThat(testQueryCommon10.getCol01()).isEqualTo(UPDATED_COL_01);
        assertThat(testQueryCommon10.getCol02()).isEqualTo(UPDATED_COL_02);
        assertThat(testQueryCommon10.getCol03()).isEqualTo(UPDATED_COL_03);
        assertThat(testQueryCommon10.getCol04()).isEqualTo(UPDATED_COL_04);
        assertThat(testQueryCommon10.getCol05()).isEqualTo(UPDATED_COL_05);
        assertThat(testQueryCommon10.getCol06()).isEqualTo(UPDATED_COL_06);
        assertThat(testQueryCommon10.getCol07()).isEqualTo(UPDATED_COL_07);
        assertThat(testQueryCommon10.getCol08()).isEqualTo(UPDATED_COL_08);
        assertThat(testQueryCommon10.getCol09()).isEqualTo(UPDATED_COL_09);
        assertThat(testQueryCommon10.getCol10()).isEqualTo(UPDATED_COL_10);

        // Validate the QueryCommon10 in Elasticsearch
        verify(mockQueryCommon10SearchRepository, times(1)).save(testQueryCommon10);
    }

    @Test
    @Transactional
    public void updateNonExistingQueryCommon10() throws Exception {
        int databaseSizeBeforeUpdate = queryCommon10Repository.findAll().size();

        // Create the QueryCommon10
        QueryCommon10DTO queryCommon10DTO = queryCommon10Mapper.toDto(queryCommon10);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQueryCommon10MockMvc.perform(put("/api/query-common-10-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queryCommon10DTO)))
            .andExpect(status().isBadRequest());

        // Validate the QueryCommon10 in the database
        List<QueryCommon10> queryCommon10List = queryCommon10Repository.findAll();
        assertThat(queryCommon10List).hasSize(databaseSizeBeforeUpdate);

        // Validate the QueryCommon10 in Elasticsearch
        verify(mockQueryCommon10SearchRepository, times(0)).save(queryCommon10);
    }

    @Test
    @Transactional
    public void deleteQueryCommon10() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);

        int databaseSizeBeforeDelete = queryCommon10Repository.findAll().size();

        // Delete the queryCommon10
        restQueryCommon10MockMvc.perform(delete("/api/query-common-10-s/{id}", queryCommon10.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QueryCommon10> queryCommon10List = queryCommon10Repository.findAll();
        assertThat(queryCommon10List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the QueryCommon10 in Elasticsearch
        verify(mockQueryCommon10SearchRepository, times(1)).deleteById(queryCommon10.getId());
    }

    @Test
    @Transactional
    public void searchQueryCommon10() throws Exception {
        // Initialize the database
        queryCommon10Repository.saveAndFlush(queryCommon10);
        when(mockQueryCommon10SearchRepository.search(queryStringQuery("id:" + queryCommon10.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(queryCommon10), PageRequest.of(0, 1), 1));
        // Search the queryCommon10
        restQueryCommon10MockMvc.perform(get("/api/_search/query-common-10-s?query=id:" + queryCommon10.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryCommon10.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].col10").value(hasItem(DEFAULT_COL_10)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QueryCommon10.class);
        QueryCommon10 queryCommon101 = new QueryCommon10();
        queryCommon101.setId(1L);
        QueryCommon10 queryCommon102 = new QueryCommon10();
        queryCommon102.setId(queryCommon101.getId());
        assertThat(queryCommon101).isEqualTo(queryCommon102);
        queryCommon102.setId(2L);
        assertThat(queryCommon101).isNotEqualTo(queryCommon102);
        queryCommon101.setId(null);
        assertThat(queryCommon101).isNotEqualTo(queryCommon102);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QueryCommon10DTO.class);
        QueryCommon10DTO queryCommon10DTO1 = new QueryCommon10DTO();
        queryCommon10DTO1.setId(1L);
        QueryCommon10DTO queryCommon10DTO2 = new QueryCommon10DTO();
        assertThat(queryCommon10DTO1).isNotEqualTo(queryCommon10DTO2);
        queryCommon10DTO2.setId(queryCommon10DTO1.getId());
        assertThat(queryCommon10DTO1).isEqualTo(queryCommon10DTO2);
        queryCommon10DTO2.setId(2L);
        assertThat(queryCommon10DTO1).isNotEqualTo(queryCommon10DTO2);
        queryCommon10DTO1.setId(null);
        assertThat(queryCommon10DTO1).isNotEqualTo(queryCommon10DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(queryCommon10Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(queryCommon10Mapper.fromId(null)).isNull();
    }
}
