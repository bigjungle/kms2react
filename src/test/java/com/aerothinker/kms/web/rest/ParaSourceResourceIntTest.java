package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaSource;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaSource;
import com.aerothinker.kms.repository.ParaSourceRepository;
import com.aerothinker.kms.repository.search.ParaSourceSearchRepository;
import com.aerothinker.kms.service.ParaSourceService;
import com.aerothinker.kms.service.dto.ParaSourceDTO;
import com.aerothinker.kms.service.mapper.ParaSourceMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaSourceCriteria;
import com.aerothinker.kms.service.ParaSourceQueryService;

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
 * Test class for the ParaSourceResource REST controller.
 *
 * @see ParaSourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaSourceResourceIntTest {

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
    private ParaSourceRepository paraSourceRepository;

    @Autowired
    private ParaSourceMapper paraSourceMapper;

    @Autowired
    private ParaSourceService paraSourceService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaSourceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaSourceSearchRepository mockParaSourceSearchRepository;

    @Autowired
    private ParaSourceQueryService paraSourceQueryService;

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

    private MockMvc restParaSourceMockMvc;

    private ParaSource paraSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaSourceResource paraSourceResource = new ParaSourceResource(paraSourceService, paraSourceQueryService);
        this.restParaSourceMockMvc = MockMvcBuilders.standaloneSetup(paraSourceResource)
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
    public static ParaSource createEntity(EntityManager em) {
        ParaSource paraSource = new ParaSource()
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
        return paraSource;
    }

    @Before
    public void initTest() {
        paraSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaSource() throws Exception {
        int databaseSizeBeforeCreate = paraSourceRepository.findAll().size();

        // Create the ParaSource
        ParaSourceDTO paraSourceDTO = paraSourceMapper.toDto(paraSource);
        restParaSourceMockMvc.perform(post("/api/para-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaSource in the database
        List<ParaSource> paraSourceList = paraSourceRepository.findAll();
        assertThat(paraSourceList).hasSize(databaseSizeBeforeCreate + 1);
        ParaSource testParaSource = paraSourceList.get(paraSourceList.size() - 1);
        assertThat(testParaSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaSource.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaSource.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaSource.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaSource.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaSource.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaSource.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaSource.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaSource.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaSource.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaSource.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaSource.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaSource.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaSource.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaSource.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaSource in Elasticsearch
        verify(mockParaSourceSearchRepository, times(1)).save(testParaSource);
    }

    @Test
    @Transactional
    public void createParaSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraSourceRepository.findAll().size();

        // Create the ParaSource with an existing ID
        paraSource.setId(1L);
        ParaSourceDTO paraSourceDTO = paraSourceMapper.toDto(paraSource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaSourceMockMvc.perform(post("/api/para-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaSource in the database
        List<ParaSource> paraSourceList = paraSourceRepository.findAll();
        assertThat(paraSourceList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaSource in Elasticsearch
        verify(mockParaSourceSearchRepository, times(0)).save(paraSource);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraSourceRepository.findAll().size();
        // set the field null
        paraSource.setName(null);

        // Create the ParaSource, which fails.
        ParaSourceDTO paraSourceDTO = paraSourceMapper.toDto(paraSource);

        restParaSourceMockMvc.perform(post("/api/para-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraSourceDTO)))
            .andExpect(status().isBadRequest());

        List<ParaSource> paraSourceList = paraSourceRepository.findAll();
        assertThat(paraSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaSources() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList
        restParaSourceMockMvc.perform(get("/api/para-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraSource.getId().intValue())))
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
    public void getParaSource() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get the paraSource
        restParaSourceMockMvc.perform(get("/api/para-sources/{id}", paraSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraSource.getId().intValue()))
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
    public void getAllParaSourcesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where name equals to DEFAULT_NAME
        defaultParaSourceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraSourceList where name equals to UPDATED_NAME
        defaultParaSourceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaSourceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraSourceList where name equals to UPDATED_NAME
        defaultParaSourceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where name is not null
        defaultParaSourceShouldBeFound("name.specified=true");

        // Get all the paraSourceList where name is null
        defaultParaSourceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaSourceShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraSourceList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaSourceShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaSourcesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaSourceShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraSourceList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaSourceShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaSourcesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where serialNumber is not null
        defaultParaSourceShouldBeFound("serialNumber.specified=true");

        // Get all the paraSourceList where serialNumber is null
        defaultParaSourceShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where sortString equals to DEFAULT_SORT_STRING
        defaultParaSourceShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraSourceList where sortString equals to UPDATED_SORT_STRING
        defaultParaSourceShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaSourcesBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaSourceShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraSourceList where sortString equals to UPDATED_SORT_STRING
        defaultParaSourceShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaSourcesBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where sortString is not null
        defaultParaSourceShouldBeFound("sortString.specified=true");

        // Get all the paraSourceList where sortString is null
        defaultParaSourceShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where descString equals to DEFAULT_DESC_STRING
        defaultParaSourceShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraSourceList where descString equals to UPDATED_DESC_STRING
        defaultParaSourceShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaSourceShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraSourceList where descString equals to UPDATED_DESC_STRING
        defaultParaSourceShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where descString is not null
        defaultParaSourceShouldBeFound("descString.specified=true");

        // Get all the paraSourceList where descString is null
        defaultParaSourceShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaSourceShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraSourceList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaSourceShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaSourceShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraSourceList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaSourceShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where imageBlobName is not null
        defaultParaSourceShouldBeFound("imageBlobName.specified=true");

        // Get all the paraSourceList where imageBlobName is null
        defaultParaSourceShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaSourceShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraSourceList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaSourceShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaSourceShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraSourceList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaSourceShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where usingFlag is not null
        defaultParaSourceShouldBeFound("usingFlag.specified=true");

        // Get all the paraSourceList where usingFlag is null
        defaultParaSourceShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where remarks equals to DEFAULT_REMARKS
        defaultParaSourceShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraSourceList where remarks equals to UPDATED_REMARKS
        defaultParaSourceShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaSourceShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraSourceList where remarks equals to UPDATED_REMARKS
        defaultParaSourceShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where remarks is not null
        defaultParaSourceShouldBeFound("remarks.specified=true");

        // Get all the paraSourceList where remarks is null
        defaultParaSourceShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validType equals to DEFAULT_VALID_TYPE
        defaultParaSourceShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraSourceList where validType equals to UPDATED_VALID_TYPE
        defaultParaSourceShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaSourceShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraSourceList where validType equals to UPDATED_VALID_TYPE
        defaultParaSourceShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validType is not null
        defaultParaSourceShouldBeFound("validType.specified=true");

        // Get all the paraSourceList where validType is null
        defaultParaSourceShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaSourceShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraSourceList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaSourceShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaSourceShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraSourceList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaSourceShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validBegin is not null
        defaultParaSourceShouldBeFound("validBegin.specified=true");

        // Get all the paraSourceList where validBegin is null
        defaultParaSourceShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validEnd equals to DEFAULT_VALID_END
        defaultParaSourceShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraSourceList where validEnd equals to UPDATED_VALID_END
        defaultParaSourceShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaSourceShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraSourceList where validEnd equals to UPDATED_VALID_END
        defaultParaSourceShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where validEnd is not null
        defaultParaSourceShouldBeFound("validEnd.specified=true");

        // Get all the paraSourceList where validEnd is null
        defaultParaSourceShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaSourceShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraSourceList where createTime equals to UPDATED_CREATE_TIME
        defaultParaSourceShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaSourceShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraSourceList where createTime equals to UPDATED_CREATE_TIME
        defaultParaSourceShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where createTime is not null
        defaultParaSourceShouldBeFound("createTime.specified=true");

        // Get all the paraSourceList where createTime is null
        defaultParaSourceShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaSourceShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraSourceList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaSourceShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaSourceShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraSourceList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaSourceShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where modifyTime is not null
        defaultParaSourceShouldBeFound("modifyTime.specified=true");

        // Get all the paraSourceList where modifyTime is null
        defaultParaSourceShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaSourceShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraSourceList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaSourceShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaSourceShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraSourceList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaSourceShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaSourcesByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        // Get all the paraSourceList where verifyTime is not null
        defaultParaSourceShouldBeFound("verifyTime.specified=true");

        // Get all the paraSourceList where verifyTime is null
        defaultParaSourceShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaSourcesByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraSource.setCreatedUser(createdUser);
        paraSourceRepository.saveAndFlush(paraSource);
        Long createdUserId = createdUser.getId();

        // Get all the paraSourceList where createdUser equals to createdUserId
        defaultParaSourceShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraSourceList where createdUser equals to createdUserId + 1
        defaultParaSourceShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaSourcesByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraSource.setModifiedUser(modifiedUser);
        paraSourceRepository.saveAndFlush(paraSource);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraSourceList where modifiedUser equals to modifiedUserId
        defaultParaSourceShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraSourceList where modifiedUser equals to modifiedUserId + 1
        defaultParaSourceShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaSourcesByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraSource.setVerifiedUser(verifiedUser);
        paraSourceRepository.saveAndFlush(paraSource);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraSourceList where verifiedUser equals to verifiedUserId
        defaultParaSourceShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraSourceList where verifiedUser equals to verifiedUserId + 1
        defaultParaSourceShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaSourcesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaSource parent = ParaSourceResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraSource.setParent(parent);
        paraSourceRepository.saveAndFlush(paraSource);
        Long parentId = parent.getId();

        // Get all the paraSourceList where parent equals to parentId
        defaultParaSourceShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraSourceList where parent equals to parentId + 1
        defaultParaSourceShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaSourceShouldBeFound(String filter) throws Exception {
        restParaSourceMockMvc.perform(get("/api/para-sources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraSource.getId().intValue())))
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
        restParaSourceMockMvc.perform(get("/api/para-sources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaSourceShouldNotBeFound(String filter) throws Exception {
        restParaSourceMockMvc.perform(get("/api/para-sources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaSourceMockMvc.perform(get("/api/para-sources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaSource() throws Exception {
        // Get the paraSource
        restParaSourceMockMvc.perform(get("/api/para-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaSource() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        int databaseSizeBeforeUpdate = paraSourceRepository.findAll().size();

        // Update the paraSource
        ParaSource updatedParaSource = paraSourceRepository.findById(paraSource.getId()).get();
        // Disconnect from session so that the updates on updatedParaSource are not directly saved in db
        em.detach(updatedParaSource);
        updatedParaSource
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
        ParaSourceDTO paraSourceDTO = paraSourceMapper.toDto(updatedParaSource);

        restParaSourceMockMvc.perform(put("/api/para-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraSourceDTO)))
            .andExpect(status().isOk());

        // Validate the ParaSource in the database
        List<ParaSource> paraSourceList = paraSourceRepository.findAll();
        assertThat(paraSourceList).hasSize(databaseSizeBeforeUpdate);
        ParaSource testParaSource = paraSourceList.get(paraSourceList.size() - 1);
        assertThat(testParaSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaSource.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaSource.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaSource.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaSource.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaSource.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaSource.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaSource.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaSource.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaSource.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaSource.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaSource.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaSource.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaSource.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaSource.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaSource in Elasticsearch
        verify(mockParaSourceSearchRepository, times(1)).save(testParaSource);
    }

    @Test
    @Transactional
    public void updateNonExistingParaSource() throws Exception {
        int databaseSizeBeforeUpdate = paraSourceRepository.findAll().size();

        // Create the ParaSource
        ParaSourceDTO paraSourceDTO = paraSourceMapper.toDto(paraSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaSourceMockMvc.perform(put("/api/para-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaSource in the database
        List<ParaSource> paraSourceList = paraSourceRepository.findAll();
        assertThat(paraSourceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaSource in Elasticsearch
        verify(mockParaSourceSearchRepository, times(0)).save(paraSource);
    }

    @Test
    @Transactional
    public void deleteParaSource() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);

        int databaseSizeBeforeDelete = paraSourceRepository.findAll().size();

        // Delete the paraSource
        restParaSourceMockMvc.perform(delete("/api/para-sources/{id}", paraSource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaSource> paraSourceList = paraSourceRepository.findAll();
        assertThat(paraSourceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaSource in Elasticsearch
        verify(mockParaSourceSearchRepository, times(1)).deleteById(paraSource.getId());
    }

    @Test
    @Transactional
    public void searchParaSource() throws Exception {
        // Initialize the database
        paraSourceRepository.saveAndFlush(paraSource);
        when(mockParaSourceSearchRepository.search(queryStringQuery("id:" + paraSource.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraSource), PageRequest.of(0, 1), 1));
        // Search the paraSource
        restParaSourceMockMvc.perform(get("/api/_search/para-sources?query=id:" + paraSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraSource.getId().intValue())))
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
        TestUtil.equalsVerifier(ParaSource.class);
        ParaSource paraSource1 = new ParaSource();
        paraSource1.setId(1L);
        ParaSource paraSource2 = new ParaSource();
        paraSource2.setId(paraSource1.getId());
        assertThat(paraSource1).isEqualTo(paraSource2);
        paraSource2.setId(2L);
        assertThat(paraSource1).isNotEqualTo(paraSource2);
        paraSource1.setId(null);
        assertThat(paraSource1).isNotEqualTo(paraSource2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaSourceDTO.class);
        ParaSourceDTO paraSourceDTO1 = new ParaSourceDTO();
        paraSourceDTO1.setId(1L);
        ParaSourceDTO paraSourceDTO2 = new ParaSourceDTO();
        assertThat(paraSourceDTO1).isNotEqualTo(paraSourceDTO2);
        paraSourceDTO2.setId(paraSourceDTO1.getId());
        assertThat(paraSourceDTO1).isEqualTo(paraSourceDTO2);
        paraSourceDTO2.setId(2L);
        assertThat(paraSourceDTO1).isNotEqualTo(paraSourceDTO2);
        paraSourceDTO1.setId(null);
        assertThat(paraSourceDTO1).isNotEqualTo(paraSourceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraSourceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraSourceMapper.fromId(null)).isNull();
    }
}
