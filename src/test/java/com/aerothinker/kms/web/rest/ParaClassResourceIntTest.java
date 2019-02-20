package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaClass;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaClass;
import com.aerothinker.kms.repository.ParaClassRepository;
import com.aerothinker.kms.repository.search.ParaClassSearchRepository;
import com.aerothinker.kms.service.ParaClassService;
import com.aerothinker.kms.service.dto.ParaClassDTO;
import com.aerothinker.kms.service.mapper.ParaClassMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaClassCriteria;
import com.aerothinker.kms.service.ParaClassQueryService;

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
 * Test class for the ParaClassResource REST controller.
 *
 * @see ParaClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaClassResourceIntTest {

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
    private ParaClassRepository paraClassRepository;

    @Autowired
    private ParaClassMapper paraClassMapper;

    @Autowired
    private ParaClassService paraClassService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaClassSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaClassSearchRepository mockParaClassSearchRepository;

    @Autowired
    private ParaClassQueryService paraClassQueryService;

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

    private MockMvc restParaClassMockMvc;

    private ParaClass paraClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaClassResource paraClassResource = new ParaClassResource(paraClassService, paraClassQueryService);
        this.restParaClassMockMvc = MockMvcBuilders.standaloneSetup(paraClassResource)
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
    public static ParaClass createEntity(EntityManager em) {
        ParaClass paraClass = new ParaClass()
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
        return paraClass;
    }

    @Before
    public void initTest() {
        paraClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaClass() throws Exception {
        int databaseSizeBeforeCreate = paraClassRepository.findAll().size();

        // Create the ParaClass
        ParaClassDTO paraClassDTO = paraClassMapper.toDto(paraClass);
        restParaClassMockMvc.perform(post("/api/para-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraClassDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaClass in the database
        List<ParaClass> paraClassList = paraClassRepository.findAll();
        assertThat(paraClassList).hasSize(databaseSizeBeforeCreate + 1);
        ParaClass testParaClass = paraClassList.get(paraClassList.size() - 1);
        assertThat(testParaClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaClass.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaClass.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaClass.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaClass.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaClass.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaClass.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaClass.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaClass.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaClass.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaClass.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaClass.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaClass.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaClass.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaClass.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaClass in Elasticsearch
        verify(mockParaClassSearchRepository, times(1)).save(testParaClass);
    }

    @Test
    @Transactional
    public void createParaClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraClassRepository.findAll().size();

        // Create the ParaClass with an existing ID
        paraClass.setId(1L);
        ParaClassDTO paraClassDTO = paraClassMapper.toDto(paraClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaClassMockMvc.perform(post("/api/para-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaClass in the database
        List<ParaClass> paraClassList = paraClassRepository.findAll();
        assertThat(paraClassList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaClass in Elasticsearch
        verify(mockParaClassSearchRepository, times(0)).save(paraClass);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraClassRepository.findAll().size();
        // set the field null
        paraClass.setName(null);

        // Create the ParaClass, which fails.
        ParaClassDTO paraClassDTO = paraClassMapper.toDto(paraClass);

        restParaClassMockMvc.perform(post("/api/para-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraClassDTO)))
            .andExpect(status().isBadRequest());

        List<ParaClass> paraClassList = paraClassRepository.findAll();
        assertThat(paraClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaClasses() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList
        restParaClassMockMvc.perform(get("/api/para-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraClass.getId().intValue())))
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
    public void getParaClass() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get the paraClass
        restParaClassMockMvc.perform(get("/api/para-classes/{id}", paraClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraClass.getId().intValue()))
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
    public void getAllParaClassesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where name equals to DEFAULT_NAME
        defaultParaClassShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraClassList where name equals to UPDATED_NAME
        defaultParaClassShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaClassShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraClassList where name equals to UPDATED_NAME
        defaultParaClassShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where name is not null
        defaultParaClassShouldBeFound("name.specified=true");

        // Get all the paraClassList where name is null
        defaultParaClassShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaClassShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraClassList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaClassShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaClassesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaClassShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraClassList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaClassShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaClassesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where serialNumber is not null
        defaultParaClassShouldBeFound("serialNumber.specified=true");

        // Get all the paraClassList where serialNumber is null
        defaultParaClassShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where sortString equals to DEFAULT_SORT_STRING
        defaultParaClassShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraClassList where sortString equals to UPDATED_SORT_STRING
        defaultParaClassShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaClassesBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaClassShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraClassList where sortString equals to UPDATED_SORT_STRING
        defaultParaClassShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaClassesBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where sortString is not null
        defaultParaClassShouldBeFound("sortString.specified=true");

        // Get all the paraClassList where sortString is null
        defaultParaClassShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where descString equals to DEFAULT_DESC_STRING
        defaultParaClassShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraClassList where descString equals to UPDATED_DESC_STRING
        defaultParaClassShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaClassesByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaClassShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraClassList where descString equals to UPDATED_DESC_STRING
        defaultParaClassShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaClassesByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where descString is not null
        defaultParaClassShouldBeFound("descString.specified=true");

        // Get all the paraClassList where descString is null
        defaultParaClassShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaClassShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraClassList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaClassShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaClassShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraClassList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaClassShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where imageBlobName is not null
        defaultParaClassShouldBeFound("imageBlobName.specified=true");

        // Get all the paraClassList where imageBlobName is null
        defaultParaClassShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaClassShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraClassList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaClassShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaClassesByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaClassShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraClassList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaClassShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaClassesByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where usingFlag is not null
        defaultParaClassShouldBeFound("usingFlag.specified=true");

        // Get all the paraClassList where usingFlag is null
        defaultParaClassShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where remarks equals to DEFAULT_REMARKS
        defaultParaClassShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraClassList where remarks equals to UPDATED_REMARKS
        defaultParaClassShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaClassesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaClassShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraClassList where remarks equals to UPDATED_REMARKS
        defaultParaClassShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaClassesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where remarks is not null
        defaultParaClassShouldBeFound("remarks.specified=true");

        // Get all the paraClassList where remarks is null
        defaultParaClassShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validType equals to DEFAULT_VALID_TYPE
        defaultParaClassShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraClassList where validType equals to UPDATED_VALID_TYPE
        defaultParaClassShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaClassShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraClassList where validType equals to UPDATED_VALID_TYPE
        defaultParaClassShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validType is not null
        defaultParaClassShouldBeFound("validType.specified=true");

        // Get all the paraClassList where validType is null
        defaultParaClassShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaClassShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraClassList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaClassShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaClassShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraClassList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaClassShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validBegin is not null
        defaultParaClassShouldBeFound("validBegin.specified=true");

        // Get all the paraClassList where validBegin is null
        defaultParaClassShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validEnd equals to DEFAULT_VALID_END
        defaultParaClassShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraClassList where validEnd equals to UPDATED_VALID_END
        defaultParaClassShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaClassShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraClassList where validEnd equals to UPDATED_VALID_END
        defaultParaClassShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaClassesByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where validEnd is not null
        defaultParaClassShouldBeFound("validEnd.specified=true");

        // Get all the paraClassList where validEnd is null
        defaultParaClassShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaClassShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraClassList where createTime equals to UPDATED_CREATE_TIME
        defaultParaClassShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaClassShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraClassList where createTime equals to UPDATED_CREATE_TIME
        defaultParaClassShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where createTime is not null
        defaultParaClassShouldBeFound("createTime.specified=true");

        // Get all the paraClassList where createTime is null
        defaultParaClassShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaClassShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraClassList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaClassShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaClassShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraClassList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaClassShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where modifyTime is not null
        defaultParaClassShouldBeFound("modifyTime.specified=true");

        // Get all the paraClassList where modifyTime is null
        defaultParaClassShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaClassShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraClassList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaClassShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaClassShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraClassList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaClassShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaClassesByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        // Get all the paraClassList where verifyTime is not null
        defaultParaClassShouldBeFound("verifyTime.specified=true");

        // Get all the paraClassList where verifyTime is null
        defaultParaClassShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaClassesByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraClass.setCreatedUser(createdUser);
        paraClassRepository.saveAndFlush(paraClass);
        Long createdUserId = createdUser.getId();

        // Get all the paraClassList where createdUser equals to createdUserId
        defaultParaClassShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraClassList where createdUser equals to createdUserId + 1
        defaultParaClassShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaClassesByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraClass.setModifiedUser(modifiedUser);
        paraClassRepository.saveAndFlush(paraClass);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraClassList where modifiedUser equals to modifiedUserId
        defaultParaClassShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraClassList where modifiedUser equals to modifiedUserId + 1
        defaultParaClassShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaClassesByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraClass.setVerifiedUser(verifiedUser);
        paraClassRepository.saveAndFlush(paraClass);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraClassList where verifiedUser equals to verifiedUserId
        defaultParaClassShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraClassList where verifiedUser equals to verifiedUserId + 1
        defaultParaClassShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaClassesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaClass parent = ParaClassResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraClass.setParent(parent);
        paraClassRepository.saveAndFlush(paraClass);
        Long parentId = parent.getId();

        // Get all the paraClassList where parent equals to parentId
        defaultParaClassShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraClassList where parent equals to parentId + 1
        defaultParaClassShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaClassShouldBeFound(String filter) throws Exception {
        restParaClassMockMvc.perform(get("/api/para-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraClass.getId().intValue())))
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
        restParaClassMockMvc.perform(get("/api/para-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaClassShouldNotBeFound(String filter) throws Exception {
        restParaClassMockMvc.perform(get("/api/para-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaClassMockMvc.perform(get("/api/para-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaClass() throws Exception {
        // Get the paraClass
        restParaClassMockMvc.perform(get("/api/para-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaClass() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        int databaseSizeBeforeUpdate = paraClassRepository.findAll().size();

        // Update the paraClass
        ParaClass updatedParaClass = paraClassRepository.findById(paraClass.getId()).get();
        // Disconnect from session so that the updates on updatedParaClass are not directly saved in db
        em.detach(updatedParaClass);
        updatedParaClass
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
        ParaClassDTO paraClassDTO = paraClassMapper.toDto(updatedParaClass);

        restParaClassMockMvc.perform(put("/api/para-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraClassDTO)))
            .andExpect(status().isOk());

        // Validate the ParaClass in the database
        List<ParaClass> paraClassList = paraClassRepository.findAll();
        assertThat(paraClassList).hasSize(databaseSizeBeforeUpdate);
        ParaClass testParaClass = paraClassList.get(paraClassList.size() - 1);
        assertThat(testParaClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaClass.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaClass.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaClass.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaClass.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaClass.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaClass.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaClass.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaClass.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaClass.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaClass.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaClass.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaClass.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaClass.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaClass.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaClass in Elasticsearch
        verify(mockParaClassSearchRepository, times(1)).save(testParaClass);
    }

    @Test
    @Transactional
    public void updateNonExistingParaClass() throws Exception {
        int databaseSizeBeforeUpdate = paraClassRepository.findAll().size();

        // Create the ParaClass
        ParaClassDTO paraClassDTO = paraClassMapper.toDto(paraClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaClassMockMvc.perform(put("/api/para-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaClass in the database
        List<ParaClass> paraClassList = paraClassRepository.findAll();
        assertThat(paraClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaClass in Elasticsearch
        verify(mockParaClassSearchRepository, times(0)).save(paraClass);
    }

    @Test
    @Transactional
    public void deleteParaClass() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);

        int databaseSizeBeforeDelete = paraClassRepository.findAll().size();

        // Delete the paraClass
        restParaClassMockMvc.perform(delete("/api/para-classes/{id}", paraClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaClass> paraClassList = paraClassRepository.findAll();
        assertThat(paraClassList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaClass in Elasticsearch
        verify(mockParaClassSearchRepository, times(1)).deleteById(paraClass.getId());
    }

    @Test
    @Transactional
    public void searchParaClass() throws Exception {
        // Initialize the database
        paraClassRepository.saveAndFlush(paraClass);
        when(mockParaClassSearchRepository.search(queryStringQuery("id:" + paraClass.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraClass), PageRequest.of(0, 1), 1));
        // Search the paraClass
        restParaClassMockMvc.perform(get("/api/_search/para-classes?query=id:" + paraClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraClass.getId().intValue())))
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
        TestUtil.equalsVerifier(ParaClass.class);
        ParaClass paraClass1 = new ParaClass();
        paraClass1.setId(1L);
        ParaClass paraClass2 = new ParaClass();
        paraClass2.setId(paraClass1.getId());
        assertThat(paraClass1).isEqualTo(paraClass2);
        paraClass2.setId(2L);
        assertThat(paraClass1).isNotEqualTo(paraClass2);
        paraClass1.setId(null);
        assertThat(paraClass1).isNotEqualTo(paraClass2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaClassDTO.class);
        ParaClassDTO paraClassDTO1 = new ParaClassDTO();
        paraClassDTO1.setId(1L);
        ParaClassDTO paraClassDTO2 = new ParaClassDTO();
        assertThat(paraClassDTO1).isNotEqualTo(paraClassDTO2);
        paraClassDTO2.setId(paraClassDTO1.getId());
        assertThat(paraClassDTO1).isEqualTo(paraClassDTO2);
        paraClassDTO2.setId(2L);
        assertThat(paraClassDTO1).isNotEqualTo(paraClassDTO2);
        paraClassDTO1.setId(null);
        assertThat(paraClassDTO1).isNotEqualTo(paraClassDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraClassMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraClassMapper.fromId(null)).isNull();
    }
}
