package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaCat;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaCat;
import com.aerothinker.kms.repository.ParaCatRepository;
import com.aerothinker.kms.repository.search.ParaCatSearchRepository;
import com.aerothinker.kms.service.ParaCatService;
import com.aerothinker.kms.service.dto.ParaCatDTO;
import com.aerothinker.kms.service.mapper.ParaCatMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaCatCriteria;
import com.aerothinker.kms.service.ParaCatQueryService;

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
 * Test class for the ParaCatResource REST controller.
 *
 * @see ParaCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaCatResourceIntTest {

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
    private ParaCatRepository paraCatRepository;

    @Autowired
    private ParaCatMapper paraCatMapper;

    @Autowired
    private ParaCatService paraCatService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaCatSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaCatSearchRepository mockParaCatSearchRepository;

    @Autowired
    private ParaCatQueryService paraCatQueryService;

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

    private MockMvc restParaCatMockMvc;

    private ParaCat paraCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaCatResource paraCatResource = new ParaCatResource(paraCatService, paraCatQueryService);
        this.restParaCatMockMvc = MockMvcBuilders.standaloneSetup(paraCatResource)
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
    public static ParaCat createEntity(EntityManager em) {
        ParaCat paraCat = new ParaCat()
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
        return paraCat;
    }

    @Before
    public void initTest() {
        paraCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaCat() throws Exception {
        int databaseSizeBeforeCreate = paraCatRepository.findAll().size();

        // Create the ParaCat
        ParaCatDTO paraCatDTO = paraCatMapper.toDto(paraCat);
        restParaCatMockMvc.perform(post("/api/para-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraCatDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaCat in the database
        List<ParaCat> paraCatList = paraCatRepository.findAll();
        assertThat(paraCatList).hasSize(databaseSizeBeforeCreate + 1);
        ParaCat testParaCat = paraCatList.get(paraCatList.size() - 1);
        assertThat(testParaCat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaCat.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaCat.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaCat.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaCat.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaCat.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaCat.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaCat.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaCat.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaCat.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaCat.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaCat.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaCat.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaCat.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaCat.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaCat in Elasticsearch
        verify(mockParaCatSearchRepository, times(1)).save(testParaCat);
    }

    @Test
    @Transactional
    public void createParaCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraCatRepository.findAll().size();

        // Create the ParaCat with an existing ID
        paraCat.setId(1L);
        ParaCatDTO paraCatDTO = paraCatMapper.toDto(paraCat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaCatMockMvc.perform(post("/api/para-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaCat in the database
        List<ParaCat> paraCatList = paraCatRepository.findAll();
        assertThat(paraCatList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaCat in Elasticsearch
        verify(mockParaCatSearchRepository, times(0)).save(paraCat);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraCatRepository.findAll().size();
        // set the field null
        paraCat.setName(null);

        // Create the ParaCat, which fails.
        ParaCatDTO paraCatDTO = paraCatMapper.toDto(paraCat);

        restParaCatMockMvc.perform(post("/api/para-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraCatDTO)))
            .andExpect(status().isBadRequest());

        List<ParaCat> paraCatList = paraCatRepository.findAll();
        assertThat(paraCatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaCats() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList
        restParaCatMockMvc.perform(get("/api/para-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraCat.getId().intValue())))
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
    public void getParaCat() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get the paraCat
        restParaCatMockMvc.perform(get("/api/para-cats/{id}", paraCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraCat.getId().intValue()))
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
    public void getAllParaCatsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where name equals to DEFAULT_NAME
        defaultParaCatShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraCatList where name equals to UPDATED_NAME
        defaultParaCatShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaCatShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraCatList where name equals to UPDATED_NAME
        defaultParaCatShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where name is not null
        defaultParaCatShouldBeFound("name.specified=true");

        // Get all the paraCatList where name is null
        defaultParaCatShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaCatShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraCatList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaCatShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaCatsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaCatShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraCatList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaCatShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaCatsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where serialNumber is not null
        defaultParaCatShouldBeFound("serialNumber.specified=true");

        // Get all the paraCatList where serialNumber is null
        defaultParaCatShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where sortString equals to DEFAULT_SORT_STRING
        defaultParaCatShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraCatList where sortString equals to UPDATED_SORT_STRING
        defaultParaCatShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaCatsBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaCatShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraCatList where sortString equals to UPDATED_SORT_STRING
        defaultParaCatShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaCatsBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where sortString is not null
        defaultParaCatShouldBeFound("sortString.specified=true");

        // Get all the paraCatList where sortString is null
        defaultParaCatShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where descString equals to DEFAULT_DESC_STRING
        defaultParaCatShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraCatList where descString equals to UPDATED_DESC_STRING
        defaultParaCatShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaCatsByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaCatShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraCatList where descString equals to UPDATED_DESC_STRING
        defaultParaCatShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaCatsByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where descString is not null
        defaultParaCatShouldBeFound("descString.specified=true");

        // Get all the paraCatList where descString is null
        defaultParaCatShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaCatShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraCatList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaCatShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaCatShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraCatList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaCatShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where imageBlobName is not null
        defaultParaCatShouldBeFound("imageBlobName.specified=true");

        // Get all the paraCatList where imageBlobName is null
        defaultParaCatShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaCatShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraCatList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaCatShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaCatsByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaCatShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraCatList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaCatShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaCatsByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where usingFlag is not null
        defaultParaCatShouldBeFound("usingFlag.specified=true");

        // Get all the paraCatList where usingFlag is null
        defaultParaCatShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where remarks equals to DEFAULT_REMARKS
        defaultParaCatShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraCatList where remarks equals to UPDATED_REMARKS
        defaultParaCatShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaCatsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaCatShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraCatList where remarks equals to UPDATED_REMARKS
        defaultParaCatShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaCatsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where remarks is not null
        defaultParaCatShouldBeFound("remarks.specified=true");

        // Get all the paraCatList where remarks is null
        defaultParaCatShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validType equals to DEFAULT_VALID_TYPE
        defaultParaCatShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraCatList where validType equals to UPDATED_VALID_TYPE
        defaultParaCatShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaCatShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraCatList where validType equals to UPDATED_VALID_TYPE
        defaultParaCatShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validType is not null
        defaultParaCatShouldBeFound("validType.specified=true");

        // Get all the paraCatList where validType is null
        defaultParaCatShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaCatShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraCatList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaCatShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaCatShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraCatList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaCatShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validBegin is not null
        defaultParaCatShouldBeFound("validBegin.specified=true");

        // Get all the paraCatList where validBegin is null
        defaultParaCatShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validEnd equals to DEFAULT_VALID_END
        defaultParaCatShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraCatList where validEnd equals to UPDATED_VALID_END
        defaultParaCatShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaCatShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraCatList where validEnd equals to UPDATED_VALID_END
        defaultParaCatShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaCatsByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where validEnd is not null
        defaultParaCatShouldBeFound("validEnd.specified=true");

        // Get all the paraCatList where validEnd is null
        defaultParaCatShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaCatShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraCatList where createTime equals to UPDATED_CREATE_TIME
        defaultParaCatShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaCatShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraCatList where createTime equals to UPDATED_CREATE_TIME
        defaultParaCatShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where createTime is not null
        defaultParaCatShouldBeFound("createTime.specified=true");

        // Get all the paraCatList where createTime is null
        defaultParaCatShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaCatShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraCatList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaCatShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaCatShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraCatList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaCatShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where modifyTime is not null
        defaultParaCatShouldBeFound("modifyTime.specified=true");

        // Get all the paraCatList where modifyTime is null
        defaultParaCatShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaCatShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraCatList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaCatShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaCatShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraCatList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaCatShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaCatsByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        // Get all the paraCatList where verifyTime is not null
        defaultParaCatShouldBeFound("verifyTime.specified=true");

        // Get all the paraCatList where verifyTime is null
        defaultParaCatShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaCatsByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraCat.setCreatedUser(createdUser);
        paraCatRepository.saveAndFlush(paraCat);
        Long createdUserId = createdUser.getId();

        // Get all the paraCatList where createdUser equals to createdUserId
        defaultParaCatShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraCatList where createdUser equals to createdUserId + 1
        defaultParaCatShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaCatsByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraCat.setModifiedUser(modifiedUser);
        paraCatRepository.saveAndFlush(paraCat);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraCatList where modifiedUser equals to modifiedUserId
        defaultParaCatShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraCatList where modifiedUser equals to modifiedUserId + 1
        defaultParaCatShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaCatsByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraCat.setVerifiedUser(verifiedUser);
        paraCatRepository.saveAndFlush(paraCat);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraCatList where verifiedUser equals to verifiedUserId
        defaultParaCatShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraCatList where verifiedUser equals to verifiedUserId + 1
        defaultParaCatShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaCatsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaCat parent = ParaCatResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraCat.setParent(parent);
        paraCatRepository.saveAndFlush(paraCat);
        Long parentId = parent.getId();

        // Get all the paraCatList where parent equals to parentId
        defaultParaCatShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraCatList where parent equals to parentId + 1
        defaultParaCatShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaCatShouldBeFound(String filter) throws Exception {
        restParaCatMockMvc.perform(get("/api/para-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraCat.getId().intValue())))
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
        restParaCatMockMvc.perform(get("/api/para-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaCatShouldNotBeFound(String filter) throws Exception {
        restParaCatMockMvc.perform(get("/api/para-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaCatMockMvc.perform(get("/api/para-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaCat() throws Exception {
        // Get the paraCat
        restParaCatMockMvc.perform(get("/api/para-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaCat() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        int databaseSizeBeforeUpdate = paraCatRepository.findAll().size();

        // Update the paraCat
        ParaCat updatedParaCat = paraCatRepository.findById(paraCat.getId()).get();
        // Disconnect from session so that the updates on updatedParaCat are not directly saved in db
        em.detach(updatedParaCat);
        updatedParaCat
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
        ParaCatDTO paraCatDTO = paraCatMapper.toDto(updatedParaCat);

        restParaCatMockMvc.perform(put("/api/para-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraCatDTO)))
            .andExpect(status().isOk());

        // Validate the ParaCat in the database
        List<ParaCat> paraCatList = paraCatRepository.findAll();
        assertThat(paraCatList).hasSize(databaseSizeBeforeUpdate);
        ParaCat testParaCat = paraCatList.get(paraCatList.size() - 1);
        assertThat(testParaCat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaCat.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaCat.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaCat.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaCat.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaCat.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaCat.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaCat.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaCat.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaCat.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaCat.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaCat.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaCat.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaCat.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaCat.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaCat in Elasticsearch
        verify(mockParaCatSearchRepository, times(1)).save(testParaCat);
    }

    @Test
    @Transactional
    public void updateNonExistingParaCat() throws Exception {
        int databaseSizeBeforeUpdate = paraCatRepository.findAll().size();

        // Create the ParaCat
        ParaCatDTO paraCatDTO = paraCatMapper.toDto(paraCat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaCatMockMvc.perform(put("/api/para-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaCat in the database
        List<ParaCat> paraCatList = paraCatRepository.findAll();
        assertThat(paraCatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaCat in Elasticsearch
        verify(mockParaCatSearchRepository, times(0)).save(paraCat);
    }

    @Test
    @Transactional
    public void deleteParaCat() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);

        int databaseSizeBeforeDelete = paraCatRepository.findAll().size();

        // Delete the paraCat
        restParaCatMockMvc.perform(delete("/api/para-cats/{id}", paraCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaCat> paraCatList = paraCatRepository.findAll();
        assertThat(paraCatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaCat in Elasticsearch
        verify(mockParaCatSearchRepository, times(1)).deleteById(paraCat.getId());
    }

    @Test
    @Transactional
    public void searchParaCat() throws Exception {
        // Initialize the database
        paraCatRepository.saveAndFlush(paraCat);
        when(mockParaCatSearchRepository.search(queryStringQuery("id:" + paraCat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraCat), PageRequest.of(0, 1), 1));
        // Search the paraCat
        restParaCatMockMvc.perform(get("/api/_search/para-cats?query=id:" + paraCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraCat.getId().intValue())))
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
        TestUtil.equalsVerifier(ParaCat.class);
        ParaCat paraCat1 = new ParaCat();
        paraCat1.setId(1L);
        ParaCat paraCat2 = new ParaCat();
        paraCat2.setId(paraCat1.getId());
        assertThat(paraCat1).isEqualTo(paraCat2);
        paraCat2.setId(2L);
        assertThat(paraCat1).isNotEqualTo(paraCat2);
        paraCat1.setId(null);
        assertThat(paraCat1).isNotEqualTo(paraCat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaCatDTO.class);
        ParaCatDTO paraCatDTO1 = new ParaCatDTO();
        paraCatDTO1.setId(1L);
        ParaCatDTO paraCatDTO2 = new ParaCatDTO();
        assertThat(paraCatDTO1).isNotEqualTo(paraCatDTO2);
        paraCatDTO2.setId(paraCatDTO1.getId());
        assertThat(paraCatDTO1).isEqualTo(paraCatDTO2);
        paraCatDTO2.setId(2L);
        assertThat(paraCatDTO1).isNotEqualTo(paraCatDTO2);
        paraCatDTO1.setId(null);
        assertThat(paraCatDTO1).isNotEqualTo(paraCatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraCatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraCatMapper.fromId(null)).isNull();
    }
}
