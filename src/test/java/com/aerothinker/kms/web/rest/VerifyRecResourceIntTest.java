package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.VerifyRec;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.repository.VerifyRecRepository;
import com.aerothinker.kms.repository.search.VerifyRecSearchRepository;
import com.aerothinker.kms.service.VerifyRecService;
import com.aerothinker.kms.service.dto.VerifyRecDTO;
import com.aerothinker.kms.service.mapper.VerifyRecMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.VerifyRecCriteria;
import com.aerothinker.kms.service.VerifyRecQueryService;

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
 * Test class for the VerifyRecResource REST controller.
 *
 * @see VerifyRecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class VerifyRecResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERIFY_RESULT = false;
    private static final Boolean UPDATED_VERIFY_RESULT = true;

    private static final Integer DEFAULT_VERIFY_SCORE = 1;
    private static final Integer UPDATED_VERIFY_SCORE = 2;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VerifyRecRepository verifyRecRepository;

    @Autowired
    private VerifyRecMapper verifyRecMapper;

    @Autowired
    private VerifyRecService verifyRecService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.VerifyRecSearchRepositoryMockConfiguration
     */
    @Autowired
    private VerifyRecSearchRepository mockVerifyRecSearchRepository;

    @Autowired
    private VerifyRecQueryService verifyRecQueryService;

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

    private MockMvc restVerifyRecMockMvc;

    private VerifyRec verifyRec;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VerifyRecResource verifyRecResource = new VerifyRecResource(verifyRecService, verifyRecQueryService);
        this.restVerifyRecMockMvc = MockMvcBuilders.standaloneSetup(verifyRecResource)
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
    public static VerifyRec createEntity(EntityManager em) {
        VerifyRec verifyRec = new VerifyRec()
            .name(DEFAULT_NAME)
            .descString(DEFAULT_DESC_STRING)
            .verifyResult(DEFAULT_VERIFY_RESULT)
            .verifyScore(DEFAULT_VERIFY_SCORE)
            .remarks(DEFAULT_REMARKS)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return verifyRec;
    }

    @Before
    public void initTest() {
        verifyRec = createEntity(em);
    }

    @Test
    @Transactional
    public void createVerifyRec() throws Exception {
        int databaseSizeBeforeCreate = verifyRecRepository.findAll().size();

        // Create the VerifyRec
        VerifyRecDTO verifyRecDTO = verifyRecMapper.toDto(verifyRec);
        restVerifyRecMockMvc.perform(post("/api/verify-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyRecDTO)))
            .andExpect(status().isCreated());

        // Validate the VerifyRec in the database
        List<VerifyRec> verifyRecList = verifyRecRepository.findAll();
        assertThat(verifyRecList).hasSize(databaseSizeBeforeCreate + 1);
        VerifyRec testVerifyRec = verifyRecList.get(verifyRecList.size() - 1);
        assertThat(testVerifyRec.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVerifyRec.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testVerifyRec.isVerifyResult()).isEqualTo(DEFAULT_VERIFY_RESULT);
        assertThat(testVerifyRec.getVerifyScore()).isEqualTo(DEFAULT_VERIFY_SCORE);
        assertThat(testVerifyRec.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testVerifyRec.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testVerifyRec.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);

        // Validate the VerifyRec in Elasticsearch
        verify(mockVerifyRecSearchRepository, times(1)).save(testVerifyRec);
    }

    @Test
    @Transactional
    public void createVerifyRecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = verifyRecRepository.findAll().size();

        // Create the VerifyRec with an existing ID
        verifyRec.setId(1L);
        VerifyRecDTO verifyRecDTO = verifyRecMapper.toDto(verifyRec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerifyRecMockMvc.perform(post("/api/verify-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyRecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VerifyRec in the database
        List<VerifyRec> verifyRecList = verifyRecRepository.findAll();
        assertThat(verifyRecList).hasSize(databaseSizeBeforeCreate);

        // Validate the VerifyRec in Elasticsearch
        verify(mockVerifyRecSearchRepository, times(0)).save(verifyRec);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = verifyRecRepository.findAll().size();
        // set the field null
        verifyRec.setName(null);

        // Create the VerifyRec, which fails.
        VerifyRecDTO verifyRecDTO = verifyRecMapper.toDto(verifyRec);

        restVerifyRecMockMvc.perform(post("/api/verify-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyRecDTO)))
            .andExpect(status().isBadRequest());

        List<VerifyRec> verifyRecList = verifyRecRepository.findAll();
        assertThat(verifyRecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVerifyRecs() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList
        restVerifyRecMockMvc.perform(get("/api/verify-recs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verifyRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].verifyResult").value(hasItem(DEFAULT_VERIFY_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyScore").value(hasItem(DEFAULT_VERIFY_SCORE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getVerifyRec() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get the verifyRec
        restVerifyRecMockMvc.perform(get("/api/verify-recs/{id}", verifyRec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(verifyRec.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.verifyResult").value(DEFAULT_VERIFY_RESULT.booleanValue()))
            .andExpect(jsonPath("$.verifyScore").value(DEFAULT_VERIFY_SCORE))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where name equals to DEFAULT_NAME
        defaultVerifyRecShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the verifyRecList where name equals to UPDATED_NAME
        defaultVerifyRecShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVerifyRecShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the verifyRecList where name equals to UPDATED_NAME
        defaultVerifyRecShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where name is not null
        defaultVerifyRecShouldBeFound("name.specified=true");

        // Get all the verifyRecList where name is null
        defaultVerifyRecShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where descString equals to DEFAULT_DESC_STRING
        defaultVerifyRecShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the verifyRecList where descString equals to UPDATED_DESC_STRING
        defaultVerifyRecShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultVerifyRecShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the verifyRecList where descString equals to UPDATED_DESC_STRING
        defaultVerifyRecShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where descString is not null
        defaultVerifyRecShouldBeFound("descString.specified=true");

        // Get all the verifyRecList where descString is null
        defaultVerifyRecShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyResultIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyResult equals to DEFAULT_VERIFY_RESULT
        defaultVerifyRecShouldBeFound("verifyResult.equals=" + DEFAULT_VERIFY_RESULT);

        // Get all the verifyRecList where verifyResult equals to UPDATED_VERIFY_RESULT
        defaultVerifyRecShouldNotBeFound("verifyResult.equals=" + UPDATED_VERIFY_RESULT);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyResultIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyResult in DEFAULT_VERIFY_RESULT or UPDATED_VERIFY_RESULT
        defaultVerifyRecShouldBeFound("verifyResult.in=" + DEFAULT_VERIFY_RESULT + "," + UPDATED_VERIFY_RESULT);

        // Get all the verifyRecList where verifyResult equals to UPDATED_VERIFY_RESULT
        defaultVerifyRecShouldNotBeFound("verifyResult.in=" + UPDATED_VERIFY_RESULT);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyResult is not null
        defaultVerifyRecShouldBeFound("verifyResult.specified=true");

        // Get all the verifyRecList where verifyResult is null
        defaultVerifyRecShouldNotBeFound("verifyResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyScore equals to DEFAULT_VERIFY_SCORE
        defaultVerifyRecShouldBeFound("verifyScore.equals=" + DEFAULT_VERIFY_SCORE);

        // Get all the verifyRecList where verifyScore equals to UPDATED_VERIFY_SCORE
        defaultVerifyRecShouldNotBeFound("verifyScore.equals=" + UPDATED_VERIFY_SCORE);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyScoreIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyScore in DEFAULT_VERIFY_SCORE or UPDATED_VERIFY_SCORE
        defaultVerifyRecShouldBeFound("verifyScore.in=" + DEFAULT_VERIFY_SCORE + "," + UPDATED_VERIFY_SCORE);

        // Get all the verifyRecList where verifyScore equals to UPDATED_VERIFY_SCORE
        defaultVerifyRecShouldNotBeFound("verifyScore.in=" + UPDATED_VERIFY_SCORE);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyScore is not null
        defaultVerifyRecShouldBeFound("verifyScore.specified=true");

        // Get all the verifyRecList where verifyScore is null
        defaultVerifyRecShouldNotBeFound("verifyScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyScore greater than or equals to DEFAULT_VERIFY_SCORE
        defaultVerifyRecShouldBeFound("verifyScore.greaterOrEqualThan=" + DEFAULT_VERIFY_SCORE);

        // Get all the verifyRecList where verifyScore greater than or equals to UPDATED_VERIFY_SCORE
        defaultVerifyRecShouldNotBeFound("verifyScore.greaterOrEqualThan=" + UPDATED_VERIFY_SCORE);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByVerifyScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where verifyScore less than or equals to DEFAULT_VERIFY_SCORE
        defaultVerifyRecShouldNotBeFound("verifyScore.lessThan=" + DEFAULT_VERIFY_SCORE);

        // Get all the verifyRecList where verifyScore less than or equals to UPDATED_VERIFY_SCORE
        defaultVerifyRecShouldBeFound("verifyScore.lessThan=" + UPDATED_VERIFY_SCORE);
    }


    @Test
    @Transactional
    public void getAllVerifyRecsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where remarks equals to DEFAULT_REMARKS
        defaultVerifyRecShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the verifyRecList where remarks equals to UPDATED_REMARKS
        defaultVerifyRecShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultVerifyRecShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the verifyRecList where remarks equals to UPDATED_REMARKS
        defaultVerifyRecShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where remarks is not null
        defaultVerifyRecShouldBeFound("remarks.specified=true");

        // Get all the verifyRecList where remarks is null
        defaultVerifyRecShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where createTime equals to DEFAULT_CREATE_TIME
        defaultVerifyRecShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the verifyRecList where createTime equals to UPDATED_CREATE_TIME
        defaultVerifyRecShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultVerifyRecShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the verifyRecList where createTime equals to UPDATED_CREATE_TIME
        defaultVerifyRecShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where createTime is not null
        defaultVerifyRecShouldBeFound("createTime.specified=true");

        // Get all the verifyRecList where createTime is null
        defaultVerifyRecShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultVerifyRecShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the verifyRecList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultVerifyRecShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultVerifyRecShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the verifyRecList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultVerifyRecShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        // Get all the verifyRecList where modifyTime is not null
        defaultVerifyRecShouldBeFound("modifyTime.specified=true");

        // Get all the verifyRecList where modifyTime is null
        defaultVerifyRecShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllVerifyRecsByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        verifyRec.setCreatedUser(createdUser);
        verifyRecRepository.saveAndFlush(verifyRec);
        Long createdUserId = createdUser.getId();

        // Get all the verifyRecList where createdUser equals to createdUserId
        defaultVerifyRecShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the verifyRecList where createdUser equals to createdUserId + 1
        defaultVerifyRecShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllVerifyRecsByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        verifyRec.setModifiedUser(modifiedUser);
        verifyRecRepository.saveAndFlush(verifyRec);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the verifyRecList where modifiedUser equals to modifiedUserId
        defaultVerifyRecShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the verifyRecList where modifiedUser equals to modifiedUserId + 1
        defaultVerifyRecShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVerifyRecShouldBeFound(String filter) throws Exception {
        restVerifyRecMockMvc.perform(get("/api/verify-recs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verifyRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].verifyResult").value(hasItem(DEFAULT_VERIFY_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyScore").value(hasItem(DEFAULT_VERIFY_SCORE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));

        // Check, that the count call also returns 1
        restVerifyRecMockMvc.perform(get("/api/verify-recs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVerifyRecShouldNotBeFound(String filter) throws Exception {
        restVerifyRecMockMvc.perform(get("/api/verify-recs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVerifyRecMockMvc.perform(get("/api/verify-recs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVerifyRec() throws Exception {
        // Get the verifyRec
        restVerifyRecMockMvc.perform(get("/api/verify-recs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVerifyRec() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        int databaseSizeBeforeUpdate = verifyRecRepository.findAll().size();

        // Update the verifyRec
        VerifyRec updatedVerifyRec = verifyRecRepository.findById(verifyRec.getId()).get();
        // Disconnect from session so that the updates on updatedVerifyRec are not directly saved in db
        em.detach(updatedVerifyRec);
        updatedVerifyRec
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .verifyResult(UPDATED_VERIFY_RESULT)
            .verifyScore(UPDATED_VERIFY_SCORE)
            .remarks(UPDATED_REMARKS)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        VerifyRecDTO verifyRecDTO = verifyRecMapper.toDto(updatedVerifyRec);

        restVerifyRecMockMvc.perform(put("/api/verify-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyRecDTO)))
            .andExpect(status().isOk());

        // Validate the VerifyRec in the database
        List<VerifyRec> verifyRecList = verifyRecRepository.findAll();
        assertThat(verifyRecList).hasSize(databaseSizeBeforeUpdate);
        VerifyRec testVerifyRec = verifyRecList.get(verifyRecList.size() - 1);
        assertThat(testVerifyRec.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVerifyRec.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testVerifyRec.isVerifyResult()).isEqualTo(UPDATED_VERIFY_RESULT);
        assertThat(testVerifyRec.getVerifyScore()).isEqualTo(UPDATED_VERIFY_SCORE);
        assertThat(testVerifyRec.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testVerifyRec.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testVerifyRec.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);

        // Validate the VerifyRec in Elasticsearch
        verify(mockVerifyRecSearchRepository, times(1)).save(testVerifyRec);
    }

    @Test
    @Transactional
    public void updateNonExistingVerifyRec() throws Exception {
        int databaseSizeBeforeUpdate = verifyRecRepository.findAll().size();

        // Create the VerifyRec
        VerifyRecDTO verifyRecDTO = verifyRecMapper.toDto(verifyRec);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerifyRecMockMvc.perform(put("/api/verify-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyRecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VerifyRec in the database
        List<VerifyRec> verifyRecList = verifyRecRepository.findAll();
        assertThat(verifyRecList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VerifyRec in Elasticsearch
        verify(mockVerifyRecSearchRepository, times(0)).save(verifyRec);
    }

    @Test
    @Transactional
    public void deleteVerifyRec() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);

        int databaseSizeBeforeDelete = verifyRecRepository.findAll().size();

        // Delete the verifyRec
        restVerifyRecMockMvc.perform(delete("/api/verify-recs/{id}", verifyRec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VerifyRec> verifyRecList = verifyRecRepository.findAll();
        assertThat(verifyRecList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VerifyRec in Elasticsearch
        verify(mockVerifyRecSearchRepository, times(1)).deleteById(verifyRec.getId());
    }

    @Test
    @Transactional
    public void searchVerifyRec() throws Exception {
        // Initialize the database
        verifyRecRepository.saveAndFlush(verifyRec);
        when(mockVerifyRecSearchRepository.search(queryStringQuery("id:" + verifyRec.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(verifyRec), PageRequest.of(0, 1), 1));
        // Search the verifyRec
        restVerifyRecMockMvc.perform(get("/api/_search/verify-recs?query=id:" + verifyRec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verifyRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].verifyResult").value(hasItem(DEFAULT_VERIFY_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyScore").value(hasItem(DEFAULT_VERIFY_SCORE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerifyRec.class);
        VerifyRec verifyRec1 = new VerifyRec();
        verifyRec1.setId(1L);
        VerifyRec verifyRec2 = new VerifyRec();
        verifyRec2.setId(verifyRec1.getId());
        assertThat(verifyRec1).isEqualTo(verifyRec2);
        verifyRec2.setId(2L);
        assertThat(verifyRec1).isNotEqualTo(verifyRec2);
        verifyRec1.setId(null);
        assertThat(verifyRec1).isNotEqualTo(verifyRec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerifyRecDTO.class);
        VerifyRecDTO verifyRecDTO1 = new VerifyRecDTO();
        verifyRecDTO1.setId(1L);
        VerifyRecDTO verifyRecDTO2 = new VerifyRecDTO();
        assertThat(verifyRecDTO1).isNotEqualTo(verifyRecDTO2);
        verifyRecDTO2.setId(verifyRecDTO1.getId());
        assertThat(verifyRecDTO1).isEqualTo(verifyRecDTO2);
        verifyRecDTO2.setId(2L);
        assertThat(verifyRecDTO1).isNotEqualTo(verifyRecDTO2);
        verifyRecDTO1.setId(null);
        assertThat(verifyRecDTO1).isNotEqualTo(verifyRecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(verifyRecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(verifyRecMapper.fromId(null)).isNull();
    }
}
