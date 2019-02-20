package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaProp;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaProp;
import com.aerothinker.kms.repository.ParaPropRepository;
import com.aerothinker.kms.repository.search.ParaPropSearchRepository;
import com.aerothinker.kms.service.ParaPropService;
import com.aerothinker.kms.service.dto.ParaPropDTO;
import com.aerothinker.kms.service.mapper.ParaPropMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaPropCriteria;
import com.aerothinker.kms.service.ParaPropQueryService;

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
 * Test class for the ParaPropResource REST controller.
 *
 * @see ParaPropResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaPropResourceIntTest {

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
    private ParaPropRepository paraPropRepository;

    @Autowired
    private ParaPropMapper paraPropMapper;

    @Autowired
    private ParaPropService paraPropService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaPropSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaPropSearchRepository mockParaPropSearchRepository;

    @Autowired
    private ParaPropQueryService paraPropQueryService;

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

    private MockMvc restParaPropMockMvc;

    private ParaProp paraProp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaPropResource paraPropResource = new ParaPropResource(paraPropService, paraPropQueryService);
        this.restParaPropMockMvc = MockMvcBuilders.standaloneSetup(paraPropResource)
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
    public static ParaProp createEntity(EntityManager em) {
        ParaProp paraProp = new ParaProp()
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
        return paraProp;
    }

    @Before
    public void initTest() {
        paraProp = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaProp() throws Exception {
        int databaseSizeBeforeCreate = paraPropRepository.findAll().size();

        // Create the ParaProp
        ParaPropDTO paraPropDTO = paraPropMapper.toDto(paraProp);
        restParaPropMockMvc.perform(post("/api/para-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraPropDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaProp in the database
        List<ParaProp> paraPropList = paraPropRepository.findAll();
        assertThat(paraPropList).hasSize(databaseSizeBeforeCreate + 1);
        ParaProp testParaProp = paraPropList.get(paraPropList.size() - 1);
        assertThat(testParaProp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaProp.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaProp.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaProp.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaProp.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaProp.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaProp.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaProp.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaProp.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaProp.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaProp.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaProp.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaProp.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaProp.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaProp.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaProp in Elasticsearch
        verify(mockParaPropSearchRepository, times(1)).save(testParaProp);
    }

    @Test
    @Transactional
    public void createParaPropWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraPropRepository.findAll().size();

        // Create the ParaProp with an existing ID
        paraProp.setId(1L);
        ParaPropDTO paraPropDTO = paraPropMapper.toDto(paraProp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaPropMockMvc.perform(post("/api/para-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraPropDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaProp in the database
        List<ParaProp> paraPropList = paraPropRepository.findAll();
        assertThat(paraPropList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaProp in Elasticsearch
        verify(mockParaPropSearchRepository, times(0)).save(paraProp);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraPropRepository.findAll().size();
        // set the field null
        paraProp.setName(null);

        // Create the ParaProp, which fails.
        ParaPropDTO paraPropDTO = paraPropMapper.toDto(paraProp);

        restParaPropMockMvc.perform(post("/api/para-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraPropDTO)))
            .andExpect(status().isBadRequest());

        List<ParaProp> paraPropList = paraPropRepository.findAll();
        assertThat(paraPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaProps() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList
        restParaPropMockMvc.perform(get("/api/para-props?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraProp.getId().intValue())))
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
    public void getParaProp() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get the paraProp
        restParaPropMockMvc.perform(get("/api/para-props/{id}", paraProp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraProp.getId().intValue()))
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
    public void getAllParaPropsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where name equals to DEFAULT_NAME
        defaultParaPropShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraPropList where name equals to UPDATED_NAME
        defaultParaPropShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaPropShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraPropList where name equals to UPDATED_NAME
        defaultParaPropShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where name is not null
        defaultParaPropShouldBeFound("name.specified=true");

        // Get all the paraPropList where name is null
        defaultParaPropShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaPropShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraPropList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaPropShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaPropsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaPropShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraPropList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaPropShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaPropsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where serialNumber is not null
        defaultParaPropShouldBeFound("serialNumber.specified=true");

        // Get all the paraPropList where serialNumber is null
        defaultParaPropShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where sortString equals to DEFAULT_SORT_STRING
        defaultParaPropShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraPropList where sortString equals to UPDATED_SORT_STRING
        defaultParaPropShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaPropsBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaPropShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraPropList where sortString equals to UPDATED_SORT_STRING
        defaultParaPropShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaPropsBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where sortString is not null
        defaultParaPropShouldBeFound("sortString.specified=true");

        // Get all the paraPropList where sortString is null
        defaultParaPropShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where descString equals to DEFAULT_DESC_STRING
        defaultParaPropShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraPropList where descString equals to UPDATED_DESC_STRING
        defaultParaPropShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaPropsByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaPropShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraPropList where descString equals to UPDATED_DESC_STRING
        defaultParaPropShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaPropsByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where descString is not null
        defaultParaPropShouldBeFound("descString.specified=true");

        // Get all the paraPropList where descString is null
        defaultParaPropShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaPropShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraPropList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaPropShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaPropShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraPropList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaPropShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where imageBlobName is not null
        defaultParaPropShouldBeFound("imageBlobName.specified=true");

        // Get all the paraPropList where imageBlobName is null
        defaultParaPropShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaPropShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraPropList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaPropShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaPropsByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaPropShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraPropList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaPropShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaPropsByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where usingFlag is not null
        defaultParaPropShouldBeFound("usingFlag.specified=true");

        // Get all the paraPropList where usingFlag is null
        defaultParaPropShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where remarks equals to DEFAULT_REMARKS
        defaultParaPropShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraPropList where remarks equals to UPDATED_REMARKS
        defaultParaPropShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaPropsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaPropShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraPropList where remarks equals to UPDATED_REMARKS
        defaultParaPropShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaPropsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where remarks is not null
        defaultParaPropShouldBeFound("remarks.specified=true");

        // Get all the paraPropList where remarks is null
        defaultParaPropShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validType equals to DEFAULT_VALID_TYPE
        defaultParaPropShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraPropList where validType equals to UPDATED_VALID_TYPE
        defaultParaPropShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaPropShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraPropList where validType equals to UPDATED_VALID_TYPE
        defaultParaPropShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validType is not null
        defaultParaPropShouldBeFound("validType.specified=true");

        // Get all the paraPropList where validType is null
        defaultParaPropShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaPropShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraPropList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaPropShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaPropShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraPropList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaPropShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validBegin is not null
        defaultParaPropShouldBeFound("validBegin.specified=true");

        // Get all the paraPropList where validBegin is null
        defaultParaPropShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validEnd equals to DEFAULT_VALID_END
        defaultParaPropShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraPropList where validEnd equals to UPDATED_VALID_END
        defaultParaPropShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaPropShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraPropList where validEnd equals to UPDATED_VALID_END
        defaultParaPropShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaPropsByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where validEnd is not null
        defaultParaPropShouldBeFound("validEnd.specified=true");

        // Get all the paraPropList where validEnd is null
        defaultParaPropShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaPropShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraPropList where createTime equals to UPDATED_CREATE_TIME
        defaultParaPropShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaPropShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraPropList where createTime equals to UPDATED_CREATE_TIME
        defaultParaPropShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where createTime is not null
        defaultParaPropShouldBeFound("createTime.specified=true");

        // Get all the paraPropList where createTime is null
        defaultParaPropShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaPropShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraPropList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaPropShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaPropShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraPropList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaPropShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where modifyTime is not null
        defaultParaPropShouldBeFound("modifyTime.specified=true");

        // Get all the paraPropList where modifyTime is null
        defaultParaPropShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaPropShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraPropList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaPropShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaPropShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraPropList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaPropShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaPropsByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        // Get all the paraPropList where verifyTime is not null
        defaultParaPropShouldBeFound("verifyTime.specified=true");

        // Get all the paraPropList where verifyTime is null
        defaultParaPropShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaPropsByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraProp.setCreatedUser(createdUser);
        paraPropRepository.saveAndFlush(paraProp);
        Long createdUserId = createdUser.getId();

        // Get all the paraPropList where createdUser equals to createdUserId
        defaultParaPropShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraPropList where createdUser equals to createdUserId + 1
        defaultParaPropShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaPropsByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraProp.setModifiedUser(modifiedUser);
        paraPropRepository.saveAndFlush(paraProp);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraPropList where modifiedUser equals to modifiedUserId
        defaultParaPropShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraPropList where modifiedUser equals to modifiedUserId + 1
        defaultParaPropShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaPropsByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraProp.setVerifiedUser(verifiedUser);
        paraPropRepository.saveAndFlush(paraProp);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraPropList where verifiedUser equals to verifiedUserId
        defaultParaPropShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraPropList where verifiedUser equals to verifiedUserId + 1
        defaultParaPropShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaPropsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaProp parent = ParaPropResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraProp.setParent(parent);
        paraPropRepository.saveAndFlush(paraProp);
        Long parentId = parent.getId();

        // Get all the paraPropList where parent equals to parentId
        defaultParaPropShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraPropList where parent equals to parentId + 1
        defaultParaPropShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaPropShouldBeFound(String filter) throws Exception {
        restParaPropMockMvc.perform(get("/api/para-props?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraProp.getId().intValue())))
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
        restParaPropMockMvc.perform(get("/api/para-props/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaPropShouldNotBeFound(String filter) throws Exception {
        restParaPropMockMvc.perform(get("/api/para-props?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaPropMockMvc.perform(get("/api/para-props/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaProp() throws Exception {
        // Get the paraProp
        restParaPropMockMvc.perform(get("/api/para-props/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaProp() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        int databaseSizeBeforeUpdate = paraPropRepository.findAll().size();

        // Update the paraProp
        ParaProp updatedParaProp = paraPropRepository.findById(paraProp.getId()).get();
        // Disconnect from session so that the updates on updatedParaProp are not directly saved in db
        em.detach(updatedParaProp);
        updatedParaProp
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
        ParaPropDTO paraPropDTO = paraPropMapper.toDto(updatedParaProp);

        restParaPropMockMvc.perform(put("/api/para-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraPropDTO)))
            .andExpect(status().isOk());

        // Validate the ParaProp in the database
        List<ParaProp> paraPropList = paraPropRepository.findAll();
        assertThat(paraPropList).hasSize(databaseSizeBeforeUpdate);
        ParaProp testParaProp = paraPropList.get(paraPropList.size() - 1);
        assertThat(testParaProp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaProp.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaProp.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaProp.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaProp.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaProp.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaProp.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaProp.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaProp.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaProp.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaProp.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaProp.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaProp.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaProp.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaProp.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaProp in Elasticsearch
        verify(mockParaPropSearchRepository, times(1)).save(testParaProp);
    }

    @Test
    @Transactional
    public void updateNonExistingParaProp() throws Exception {
        int databaseSizeBeforeUpdate = paraPropRepository.findAll().size();

        // Create the ParaProp
        ParaPropDTO paraPropDTO = paraPropMapper.toDto(paraProp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaPropMockMvc.perform(put("/api/para-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraPropDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaProp in the database
        List<ParaProp> paraPropList = paraPropRepository.findAll();
        assertThat(paraPropList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaProp in Elasticsearch
        verify(mockParaPropSearchRepository, times(0)).save(paraProp);
    }

    @Test
    @Transactional
    public void deleteParaProp() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);

        int databaseSizeBeforeDelete = paraPropRepository.findAll().size();

        // Delete the paraProp
        restParaPropMockMvc.perform(delete("/api/para-props/{id}", paraProp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaProp> paraPropList = paraPropRepository.findAll();
        assertThat(paraPropList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaProp in Elasticsearch
        verify(mockParaPropSearchRepository, times(1)).deleteById(paraProp.getId());
    }

    @Test
    @Transactional
    public void searchParaProp() throws Exception {
        // Initialize the database
        paraPropRepository.saveAndFlush(paraProp);
        when(mockParaPropSearchRepository.search(queryStringQuery("id:" + paraProp.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraProp), PageRequest.of(0, 1), 1));
        // Search the paraProp
        restParaPropMockMvc.perform(get("/api/_search/para-props?query=id:" + paraProp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraProp.getId().intValue())))
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
        TestUtil.equalsVerifier(ParaProp.class);
        ParaProp paraProp1 = new ParaProp();
        paraProp1.setId(1L);
        ParaProp paraProp2 = new ParaProp();
        paraProp2.setId(paraProp1.getId());
        assertThat(paraProp1).isEqualTo(paraProp2);
        paraProp2.setId(2L);
        assertThat(paraProp1).isNotEqualTo(paraProp2);
        paraProp1.setId(null);
        assertThat(paraProp1).isNotEqualTo(paraProp2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaPropDTO.class);
        ParaPropDTO paraPropDTO1 = new ParaPropDTO();
        paraPropDTO1.setId(1L);
        ParaPropDTO paraPropDTO2 = new ParaPropDTO();
        assertThat(paraPropDTO1).isNotEqualTo(paraPropDTO2);
        paraPropDTO2.setId(paraPropDTO1.getId());
        assertThat(paraPropDTO1).isEqualTo(paraPropDTO2);
        paraPropDTO2.setId(2L);
        assertThat(paraPropDTO1).isNotEqualTo(paraPropDTO2);
        paraPropDTO1.setId(null);
        assertThat(paraPropDTO1).isNotEqualTo(paraPropDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraPropMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraPropMapper.fromId(null)).isNull();
    }
}
