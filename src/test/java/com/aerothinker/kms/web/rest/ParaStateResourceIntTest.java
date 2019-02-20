package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaState;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaState;
import com.aerothinker.kms.repository.ParaStateRepository;
import com.aerothinker.kms.repository.search.ParaStateSearchRepository;
import com.aerothinker.kms.service.ParaStateService;
import com.aerothinker.kms.service.dto.ParaStateDTO;
import com.aerothinker.kms.service.mapper.ParaStateMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaStateCriteria;
import com.aerothinker.kms.service.ParaStateQueryService;

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
import org.springframework.util.Base64Utils;
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

import com.aerothinker.kms.domain.enumeration.ValidType;
/**
 * Test class for the ParaStateResource REST controller.
 *
 * @see ParaStateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaStateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SORT_STRING = "AAAAAAAAAA";
    private static final String UPDATED_SORT_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMAGE_BLOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_BLOB_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USING_FLAG = false;
    private static final Boolean UPDATED_USING_FLAG = true;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final ValidType DEFAULT_VALID_TYPE = ValidType.LONG;
    private static final ValidType UPDATED_VALID_TYPE = ValidType.SCOPE;

    private static final Instant DEFAULT_VALID_BEGIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_BEGIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VERIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VERIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ParaStateRepository paraStateRepository;

    @Autowired
    private ParaStateMapper paraStateMapper;

    @Autowired
    private ParaStateService paraStateService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaStateSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaStateSearchRepository mockParaStateSearchRepository;

    @Autowired
    private ParaStateQueryService paraStateQueryService;

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

    private MockMvc restParaStateMockMvc;

    private ParaState paraState;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaStateResource paraStateResource = new ParaStateResource(paraStateService, paraStateQueryService);
        this.restParaStateMockMvc = MockMvcBuilders.standaloneSetup(paraStateResource)
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
    public static ParaState createEntity(EntityManager em) {
        ParaState paraState = new ParaState()
            .name(DEFAULT_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .sortString(DEFAULT_SORT_STRING)
            .descString(DEFAULT_DESC_STRING)
            .imageBlob(DEFAULT_IMAGE_BLOB)
            .imageBlobContentType(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(DEFAULT_IMAGE_BLOB_NAME)
            .usingFlag(DEFAULT_USING_FLAG)
            .remarks(DEFAULT_REMARKS)
            .validType(DEFAULT_VALID_TYPE)
            .validBegin(DEFAULT_VALID_BEGIN)
            .validEnd(DEFAULT_VALID_END)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME)
            .verifyTime(DEFAULT_VERIFY_TIME);
        return paraState;
    }

    @Before
    public void initTest() {
        paraState = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaState() throws Exception {
        int databaseSizeBeforeCreate = paraStateRepository.findAll().size();

        // Create the ParaState
        ParaStateDTO paraStateDTO = paraStateMapper.toDto(paraState);
        restParaStateMockMvc.perform(post("/api/para-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraStateDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaState in the database
        List<ParaState> paraStateList = paraStateRepository.findAll();
        assertThat(paraStateList).hasSize(databaseSizeBeforeCreate + 1);
        ParaState testParaState = paraStateList.get(paraStateList.size() - 1);
        assertThat(testParaState.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaState.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaState.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaState.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaState.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaState.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaState.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaState.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaState.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaState.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaState.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaState.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaState.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaState.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaState.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaState in Elasticsearch
        verify(mockParaStateSearchRepository, times(1)).save(testParaState);
    }

    @Test
    @Transactional
    public void createParaStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraStateRepository.findAll().size();

        // Create the ParaState with an existing ID
        paraState.setId(1L);
        ParaStateDTO paraStateDTO = paraStateMapper.toDto(paraState);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaStateMockMvc.perform(post("/api/para-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaState in the database
        List<ParaState> paraStateList = paraStateRepository.findAll();
        assertThat(paraStateList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaState in Elasticsearch
        verify(mockParaStateSearchRepository, times(0)).save(paraState);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraStateRepository.findAll().size();
        // set the field null
        paraState.setName(null);

        // Create the ParaState, which fails.
        ParaStateDTO paraStateDTO = paraStateMapper.toDto(paraState);

        restParaStateMockMvc.perform(post("/api/para-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraStateDTO)))
            .andExpect(status().isBadRequest());

        List<ParaState> paraStateList = paraStateRepository.findAll();
        assertThat(paraStateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaStates() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList
        restParaStateMockMvc.perform(get("/api/para-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraState.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validBegin").value(hasItem(DEFAULT_VALID_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].validEnd").value(hasItem(DEFAULT_VALID_END.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyTime").value(hasItem(DEFAULT_VERIFY_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getParaState() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get the paraState
        restParaStateMockMvc.perform(get("/api/para-states/{id}", paraState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraState.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.sortString").value(DEFAULT_SORT_STRING.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.imageBlobContentType").value(DEFAULT_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB)))
            .andExpect(jsonPath("$.imageBlobName").value(DEFAULT_IMAGE_BLOB_NAME.toString()))
            .andExpect(jsonPath("$.usingFlag").value(DEFAULT_USING_FLAG.booleanValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.validType").value(DEFAULT_VALID_TYPE.toString()))
            .andExpect(jsonPath("$.validBegin").value(DEFAULT_VALID_BEGIN.toString()))
            .andExpect(jsonPath("$.validEnd").value(DEFAULT_VALID_END.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()))
            .andExpect(jsonPath("$.verifyTime").value(DEFAULT_VERIFY_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllParaStatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where name equals to DEFAULT_NAME
        defaultParaStateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraStateList where name equals to UPDATED_NAME
        defaultParaStateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaStateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraStateList where name equals to UPDATED_NAME
        defaultParaStateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where name is not null
        defaultParaStateShouldBeFound("name.specified=true");

        // Get all the paraStateList where name is null
        defaultParaStateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaStateShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraStateList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaStateShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaStatesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaStateShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraStateList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaStateShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaStatesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where serialNumber is not null
        defaultParaStateShouldBeFound("serialNumber.specified=true");

        // Get all the paraStateList where serialNumber is null
        defaultParaStateShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where sortString equals to DEFAULT_SORT_STRING
        defaultParaStateShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraStateList where sortString equals to UPDATED_SORT_STRING
        defaultParaStateShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaStatesBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaStateShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraStateList where sortString equals to UPDATED_SORT_STRING
        defaultParaStateShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaStatesBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where sortString is not null
        defaultParaStateShouldBeFound("sortString.specified=true");

        // Get all the paraStateList where sortString is null
        defaultParaStateShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where descString equals to DEFAULT_DESC_STRING
        defaultParaStateShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraStateList where descString equals to UPDATED_DESC_STRING
        defaultParaStateShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaStatesByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaStateShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraStateList where descString equals to UPDATED_DESC_STRING
        defaultParaStateShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaStatesByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where descString is not null
        defaultParaStateShouldBeFound("descString.specified=true");

        // Get all the paraStateList where descString is null
        defaultParaStateShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaStateShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraStateList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaStateShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaStateShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraStateList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaStateShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where imageBlobName is not null
        defaultParaStateShouldBeFound("imageBlobName.specified=true");

        // Get all the paraStateList where imageBlobName is null
        defaultParaStateShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaStateShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraStateList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaStateShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaStatesByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaStateShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraStateList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaStateShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaStatesByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where usingFlag is not null
        defaultParaStateShouldBeFound("usingFlag.specified=true");

        // Get all the paraStateList where usingFlag is null
        defaultParaStateShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where remarks equals to DEFAULT_REMARKS
        defaultParaStateShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraStateList where remarks equals to UPDATED_REMARKS
        defaultParaStateShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaStatesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaStateShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraStateList where remarks equals to UPDATED_REMARKS
        defaultParaStateShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaStatesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where remarks is not null
        defaultParaStateShouldBeFound("remarks.specified=true");

        // Get all the paraStateList where remarks is null
        defaultParaStateShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validType equals to DEFAULT_VALID_TYPE
        defaultParaStateShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraStateList where validType equals to UPDATED_VALID_TYPE
        defaultParaStateShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaStateShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraStateList where validType equals to UPDATED_VALID_TYPE
        defaultParaStateShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validType is not null
        defaultParaStateShouldBeFound("validType.specified=true");

        // Get all the paraStateList where validType is null
        defaultParaStateShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaStateShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraStateList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaStateShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaStateShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraStateList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaStateShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validBegin is not null
        defaultParaStateShouldBeFound("validBegin.specified=true");

        // Get all the paraStateList where validBegin is null
        defaultParaStateShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validEnd equals to DEFAULT_VALID_END
        defaultParaStateShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraStateList where validEnd equals to UPDATED_VALID_END
        defaultParaStateShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaStateShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraStateList where validEnd equals to UPDATED_VALID_END
        defaultParaStateShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaStatesByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where validEnd is not null
        defaultParaStateShouldBeFound("validEnd.specified=true");

        // Get all the paraStateList where validEnd is null
        defaultParaStateShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaStateShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraStateList where createTime equals to UPDATED_CREATE_TIME
        defaultParaStateShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaStateShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraStateList where createTime equals to UPDATED_CREATE_TIME
        defaultParaStateShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where createTime is not null
        defaultParaStateShouldBeFound("createTime.specified=true");

        // Get all the paraStateList where createTime is null
        defaultParaStateShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaStateShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraStateList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaStateShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaStateShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraStateList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaStateShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where modifyTime is not null
        defaultParaStateShouldBeFound("modifyTime.specified=true");

        // Get all the paraStateList where modifyTime is null
        defaultParaStateShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaStateShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraStateList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaStateShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaStateShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraStateList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaStateShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaStatesByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        // Get all the paraStateList where verifyTime is not null
        defaultParaStateShouldBeFound("verifyTime.specified=true");

        // Get all the paraStateList where verifyTime is null
        defaultParaStateShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaStatesByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraState.setCreatedUser(createdUser);
        paraStateRepository.saveAndFlush(paraState);
        Long createdUserId = createdUser.getId();

        // Get all the paraStateList where createdUser equals to createdUserId
        defaultParaStateShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraStateList where createdUser equals to createdUserId + 1
        defaultParaStateShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaStatesByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraState.setModifiedUser(modifiedUser);
        paraStateRepository.saveAndFlush(paraState);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraStateList where modifiedUser equals to modifiedUserId
        defaultParaStateShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraStateList where modifiedUser equals to modifiedUserId + 1
        defaultParaStateShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaStatesByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraState.setVerifiedUser(verifiedUser);
        paraStateRepository.saveAndFlush(paraState);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraStateList where verifiedUser equals to verifiedUserId
        defaultParaStateShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraStateList where verifiedUser equals to verifiedUserId + 1
        defaultParaStateShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaStatesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaState parent = ParaStateResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraState.setParent(parent);
        paraStateRepository.saveAndFlush(paraState);
        Long parentId = parent.getId();

        // Get all the paraStateList where parent equals to parentId
        defaultParaStateShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraStateList where parent equals to parentId + 1
        defaultParaStateShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaStateShouldBeFound(String filter) throws Exception {
        restParaStateMockMvc.perform(get("/api/para-states?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraState.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validBegin").value(hasItem(DEFAULT_VALID_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].validEnd").value(hasItem(DEFAULT_VALID_END.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyTime").value(hasItem(DEFAULT_VERIFY_TIME.toString())));

        // Check, that the count call also returns 1
        restParaStateMockMvc.perform(get("/api/para-states/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaStateShouldNotBeFound(String filter) throws Exception {
        restParaStateMockMvc.perform(get("/api/para-states?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaStateMockMvc.perform(get("/api/para-states/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaState() throws Exception {
        // Get the paraState
        restParaStateMockMvc.perform(get("/api/para-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaState() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        int databaseSizeBeforeUpdate = paraStateRepository.findAll().size();

        // Update the paraState
        ParaState updatedParaState = paraStateRepository.findById(paraState.getId()).get();
        // Disconnect from session so that the updates on updatedParaState are not directly saved in db
        em.detach(updatedParaState);
        updatedParaState
            .name(UPDATED_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .sortString(UPDATED_SORT_STRING)
            .descString(UPDATED_DESC_STRING)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(UPDATED_IMAGE_BLOB_NAME)
            .usingFlag(UPDATED_USING_FLAG)
            .remarks(UPDATED_REMARKS)
            .validType(UPDATED_VALID_TYPE)
            .validBegin(UPDATED_VALID_BEGIN)
            .validEnd(UPDATED_VALID_END)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME)
            .verifyTime(UPDATED_VERIFY_TIME);
        ParaStateDTO paraStateDTO = paraStateMapper.toDto(updatedParaState);

        restParaStateMockMvc.perform(put("/api/para-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraStateDTO)))
            .andExpect(status().isOk());

        // Validate the ParaState in the database
        List<ParaState> paraStateList = paraStateRepository.findAll();
        assertThat(paraStateList).hasSize(databaseSizeBeforeUpdate);
        ParaState testParaState = paraStateList.get(paraStateList.size() - 1);
        assertThat(testParaState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaState.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaState.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaState.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaState.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaState.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaState.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaState.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaState.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaState.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaState.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaState.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaState.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaState.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaState.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaState in Elasticsearch
        verify(mockParaStateSearchRepository, times(1)).save(testParaState);
    }

    @Test
    @Transactional
    public void updateNonExistingParaState() throws Exception {
        int databaseSizeBeforeUpdate = paraStateRepository.findAll().size();

        // Create the ParaState
        ParaStateDTO paraStateDTO = paraStateMapper.toDto(paraState);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaStateMockMvc.perform(put("/api/para-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaState in the database
        List<ParaState> paraStateList = paraStateRepository.findAll();
        assertThat(paraStateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaState in Elasticsearch
        verify(mockParaStateSearchRepository, times(0)).save(paraState);
    }

    @Test
    @Transactional
    public void deleteParaState() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);

        int databaseSizeBeforeDelete = paraStateRepository.findAll().size();

        // Delete the paraState
        restParaStateMockMvc.perform(delete("/api/para-states/{id}", paraState.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaState> paraStateList = paraStateRepository.findAll();
        assertThat(paraStateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaState in Elasticsearch
        verify(mockParaStateSearchRepository, times(1)).deleteById(paraState.getId());
    }

    @Test
    @Transactional
    public void searchParaState() throws Exception {
        // Initialize the database
        paraStateRepository.saveAndFlush(paraState);
        when(mockParaStateSearchRepository.search(queryStringQuery("id:" + paraState.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraState), PageRequest.of(0, 1), 1));
        // Search the paraState
        restParaStateMockMvc.perform(get("/api/_search/para-states?query=id:" + paraState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraState.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME)))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validBegin").value(hasItem(DEFAULT_VALID_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].validEnd").value(hasItem(DEFAULT_VALID_END.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyTime").value(hasItem(DEFAULT_VERIFY_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaState.class);
        ParaState paraState1 = new ParaState();
        paraState1.setId(1L);
        ParaState paraState2 = new ParaState();
        paraState2.setId(paraState1.getId());
        assertThat(paraState1).isEqualTo(paraState2);
        paraState2.setId(2L);
        assertThat(paraState1).isNotEqualTo(paraState2);
        paraState1.setId(null);
        assertThat(paraState1).isNotEqualTo(paraState2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaStateDTO.class);
        ParaStateDTO paraStateDTO1 = new ParaStateDTO();
        paraStateDTO1.setId(1L);
        ParaStateDTO paraStateDTO2 = new ParaStateDTO();
        assertThat(paraStateDTO1).isNotEqualTo(paraStateDTO2);
        paraStateDTO2.setId(paraStateDTO1.getId());
        assertThat(paraStateDTO1).isEqualTo(paraStateDTO2);
        paraStateDTO2.setId(2L);
        assertThat(paraStateDTO1).isNotEqualTo(paraStateDTO2);
        paraStateDTO1.setId(null);
        assertThat(paraStateDTO1).isNotEqualTo(paraStateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraStateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraStateMapper.fromId(null)).isNull();
    }
}
