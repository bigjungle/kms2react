package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaAttr;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaAttr;
import com.aerothinker.kms.repository.ParaAttrRepository;
import com.aerothinker.kms.repository.search.ParaAttrSearchRepository;
import com.aerothinker.kms.service.ParaAttrService;
import com.aerothinker.kms.service.dto.ParaAttrDTO;
import com.aerothinker.kms.service.mapper.ParaAttrMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaAttrCriteria;
import com.aerothinker.kms.service.ParaAttrQueryService;

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
 * Test class for the ParaAttrResource REST controller.
 *
 * @see ParaAttrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaAttrResourceIntTest {

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
    private ParaAttrRepository paraAttrRepository;

    @Autowired
    private ParaAttrMapper paraAttrMapper;

    @Autowired
    private ParaAttrService paraAttrService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaAttrSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaAttrSearchRepository mockParaAttrSearchRepository;

    @Autowired
    private ParaAttrQueryService paraAttrQueryService;

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

    private MockMvc restParaAttrMockMvc;

    private ParaAttr paraAttr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaAttrResource paraAttrResource = new ParaAttrResource(paraAttrService, paraAttrQueryService);
        this.restParaAttrMockMvc = MockMvcBuilders.standaloneSetup(paraAttrResource)
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
    public static ParaAttr createEntity(EntityManager em) {
        ParaAttr paraAttr = new ParaAttr()
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
        return paraAttr;
    }

    @Before
    public void initTest() {
        paraAttr = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaAttr() throws Exception {
        int databaseSizeBeforeCreate = paraAttrRepository.findAll().size();

        // Create the ParaAttr
        ParaAttrDTO paraAttrDTO = paraAttrMapper.toDto(paraAttr);
        restParaAttrMockMvc.perform(post("/api/para-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraAttrDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaAttr in the database
        List<ParaAttr> paraAttrList = paraAttrRepository.findAll();
        assertThat(paraAttrList).hasSize(databaseSizeBeforeCreate + 1);
        ParaAttr testParaAttr = paraAttrList.get(paraAttrList.size() - 1);
        assertThat(testParaAttr.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaAttr.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaAttr.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaAttr.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaAttr.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaAttr.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaAttr.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaAttr.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaAttr.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaAttr.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaAttr.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaAttr.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaAttr.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaAttr.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaAttr.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaAttr in Elasticsearch
        verify(mockParaAttrSearchRepository, times(1)).save(testParaAttr);
    }

    @Test
    @Transactional
    public void createParaAttrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraAttrRepository.findAll().size();

        // Create the ParaAttr with an existing ID
        paraAttr.setId(1L);
        ParaAttrDTO paraAttrDTO = paraAttrMapper.toDto(paraAttr);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaAttrMockMvc.perform(post("/api/para-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraAttrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaAttr in the database
        List<ParaAttr> paraAttrList = paraAttrRepository.findAll();
        assertThat(paraAttrList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaAttr in Elasticsearch
        verify(mockParaAttrSearchRepository, times(0)).save(paraAttr);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraAttrRepository.findAll().size();
        // set the field null
        paraAttr.setName(null);

        // Create the ParaAttr, which fails.
        ParaAttrDTO paraAttrDTO = paraAttrMapper.toDto(paraAttr);

        restParaAttrMockMvc.perform(post("/api/para-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraAttrDTO)))
            .andExpect(status().isBadRequest());

        List<ParaAttr> paraAttrList = paraAttrRepository.findAll();
        assertThat(paraAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaAttrs() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList
        restParaAttrMockMvc.perform(get("/api/para-attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraAttr.getId().intValue())))
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
    public void getParaAttr() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get the paraAttr
        restParaAttrMockMvc.perform(get("/api/para-attrs/{id}", paraAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraAttr.getId().intValue()))
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
    public void getAllParaAttrsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where name equals to DEFAULT_NAME
        defaultParaAttrShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraAttrList where name equals to UPDATED_NAME
        defaultParaAttrShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaAttrShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraAttrList where name equals to UPDATED_NAME
        defaultParaAttrShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where name is not null
        defaultParaAttrShouldBeFound("name.specified=true");

        // Get all the paraAttrList where name is null
        defaultParaAttrShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaAttrShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraAttrList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaAttrShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaAttrsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaAttrShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraAttrList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaAttrShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaAttrsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where serialNumber is not null
        defaultParaAttrShouldBeFound("serialNumber.specified=true");

        // Get all the paraAttrList where serialNumber is null
        defaultParaAttrShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where sortString equals to DEFAULT_SORT_STRING
        defaultParaAttrShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraAttrList where sortString equals to UPDATED_SORT_STRING
        defaultParaAttrShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaAttrsBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaAttrShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraAttrList where sortString equals to UPDATED_SORT_STRING
        defaultParaAttrShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaAttrsBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where sortString is not null
        defaultParaAttrShouldBeFound("sortString.specified=true");

        // Get all the paraAttrList where sortString is null
        defaultParaAttrShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where descString equals to DEFAULT_DESC_STRING
        defaultParaAttrShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraAttrList where descString equals to UPDATED_DESC_STRING
        defaultParaAttrShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaAttrShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraAttrList where descString equals to UPDATED_DESC_STRING
        defaultParaAttrShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where descString is not null
        defaultParaAttrShouldBeFound("descString.specified=true");

        // Get all the paraAttrList where descString is null
        defaultParaAttrShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaAttrShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraAttrList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaAttrShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaAttrShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraAttrList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaAttrShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where imageBlobName is not null
        defaultParaAttrShouldBeFound("imageBlobName.specified=true");

        // Get all the paraAttrList where imageBlobName is null
        defaultParaAttrShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaAttrShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraAttrList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaAttrShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaAttrShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraAttrList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaAttrShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where usingFlag is not null
        defaultParaAttrShouldBeFound("usingFlag.specified=true");

        // Get all the paraAttrList where usingFlag is null
        defaultParaAttrShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where remarks equals to DEFAULT_REMARKS
        defaultParaAttrShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraAttrList where remarks equals to UPDATED_REMARKS
        defaultParaAttrShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaAttrShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraAttrList where remarks equals to UPDATED_REMARKS
        defaultParaAttrShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where remarks is not null
        defaultParaAttrShouldBeFound("remarks.specified=true");

        // Get all the paraAttrList where remarks is null
        defaultParaAttrShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validType equals to DEFAULT_VALID_TYPE
        defaultParaAttrShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraAttrList where validType equals to UPDATED_VALID_TYPE
        defaultParaAttrShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaAttrShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraAttrList where validType equals to UPDATED_VALID_TYPE
        defaultParaAttrShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validType is not null
        defaultParaAttrShouldBeFound("validType.specified=true");

        // Get all the paraAttrList where validType is null
        defaultParaAttrShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaAttrShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraAttrList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaAttrShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaAttrShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraAttrList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaAttrShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validBegin is not null
        defaultParaAttrShouldBeFound("validBegin.specified=true");

        // Get all the paraAttrList where validBegin is null
        defaultParaAttrShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validEnd equals to DEFAULT_VALID_END
        defaultParaAttrShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraAttrList where validEnd equals to UPDATED_VALID_END
        defaultParaAttrShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaAttrShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraAttrList where validEnd equals to UPDATED_VALID_END
        defaultParaAttrShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where validEnd is not null
        defaultParaAttrShouldBeFound("validEnd.specified=true");

        // Get all the paraAttrList where validEnd is null
        defaultParaAttrShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaAttrShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraAttrList where createTime equals to UPDATED_CREATE_TIME
        defaultParaAttrShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaAttrShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraAttrList where createTime equals to UPDATED_CREATE_TIME
        defaultParaAttrShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where createTime is not null
        defaultParaAttrShouldBeFound("createTime.specified=true");

        // Get all the paraAttrList where createTime is null
        defaultParaAttrShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaAttrShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraAttrList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaAttrShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaAttrShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraAttrList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaAttrShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where modifyTime is not null
        defaultParaAttrShouldBeFound("modifyTime.specified=true");

        // Get all the paraAttrList where modifyTime is null
        defaultParaAttrShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaAttrShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraAttrList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaAttrShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaAttrShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraAttrList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaAttrShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaAttrsByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        // Get all the paraAttrList where verifyTime is not null
        defaultParaAttrShouldBeFound("verifyTime.specified=true");

        // Get all the paraAttrList where verifyTime is null
        defaultParaAttrShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaAttrsByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraAttr.setCreatedUser(createdUser);
        paraAttrRepository.saveAndFlush(paraAttr);
        Long createdUserId = createdUser.getId();

        // Get all the paraAttrList where createdUser equals to createdUserId
        defaultParaAttrShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraAttrList where createdUser equals to createdUserId + 1
        defaultParaAttrShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaAttrsByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraAttr.setModifiedUser(modifiedUser);
        paraAttrRepository.saveAndFlush(paraAttr);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraAttrList where modifiedUser equals to modifiedUserId
        defaultParaAttrShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraAttrList where modifiedUser equals to modifiedUserId + 1
        defaultParaAttrShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaAttrsByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraAttr.setVerifiedUser(verifiedUser);
        paraAttrRepository.saveAndFlush(paraAttr);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraAttrList where verifiedUser equals to verifiedUserId
        defaultParaAttrShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraAttrList where verifiedUser equals to verifiedUserId + 1
        defaultParaAttrShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaAttrsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaAttr parent = ParaAttrResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraAttr.setParent(parent);
        paraAttrRepository.saveAndFlush(paraAttr);
        Long parentId = parent.getId();

        // Get all the paraAttrList where parent equals to parentId
        defaultParaAttrShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraAttrList where parent equals to parentId + 1
        defaultParaAttrShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaAttrShouldBeFound(String filter) throws Exception {
        restParaAttrMockMvc.perform(get("/api/para-attrs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraAttr.getId().intValue())))
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
        restParaAttrMockMvc.perform(get("/api/para-attrs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaAttrShouldNotBeFound(String filter) throws Exception {
        restParaAttrMockMvc.perform(get("/api/para-attrs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaAttrMockMvc.perform(get("/api/para-attrs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaAttr() throws Exception {
        // Get the paraAttr
        restParaAttrMockMvc.perform(get("/api/para-attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaAttr() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        int databaseSizeBeforeUpdate = paraAttrRepository.findAll().size();

        // Update the paraAttr
        ParaAttr updatedParaAttr = paraAttrRepository.findById(paraAttr.getId()).get();
        // Disconnect from session so that the updates on updatedParaAttr are not directly saved in db
        em.detach(updatedParaAttr);
        updatedParaAttr
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
        ParaAttrDTO paraAttrDTO = paraAttrMapper.toDto(updatedParaAttr);

        restParaAttrMockMvc.perform(put("/api/para-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraAttrDTO)))
            .andExpect(status().isOk());

        // Validate the ParaAttr in the database
        List<ParaAttr> paraAttrList = paraAttrRepository.findAll();
        assertThat(paraAttrList).hasSize(databaseSizeBeforeUpdate);
        ParaAttr testParaAttr = paraAttrList.get(paraAttrList.size() - 1);
        assertThat(testParaAttr.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaAttr.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaAttr.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaAttr.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaAttr.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaAttr.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaAttr.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaAttr.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaAttr.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaAttr.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaAttr.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaAttr.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaAttr.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaAttr.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaAttr.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaAttr in Elasticsearch
        verify(mockParaAttrSearchRepository, times(1)).save(testParaAttr);
    }

    @Test
    @Transactional
    public void updateNonExistingParaAttr() throws Exception {
        int databaseSizeBeforeUpdate = paraAttrRepository.findAll().size();

        // Create the ParaAttr
        ParaAttrDTO paraAttrDTO = paraAttrMapper.toDto(paraAttr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaAttrMockMvc.perform(put("/api/para-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraAttrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaAttr in the database
        List<ParaAttr> paraAttrList = paraAttrRepository.findAll();
        assertThat(paraAttrList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaAttr in Elasticsearch
        verify(mockParaAttrSearchRepository, times(0)).save(paraAttr);
    }

    @Test
    @Transactional
    public void deleteParaAttr() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);

        int databaseSizeBeforeDelete = paraAttrRepository.findAll().size();

        // Delete the paraAttr
        restParaAttrMockMvc.perform(delete("/api/para-attrs/{id}", paraAttr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaAttr> paraAttrList = paraAttrRepository.findAll();
        assertThat(paraAttrList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaAttr in Elasticsearch
        verify(mockParaAttrSearchRepository, times(1)).deleteById(paraAttr.getId());
    }

    @Test
    @Transactional
    public void searchParaAttr() throws Exception {
        // Initialize the database
        paraAttrRepository.saveAndFlush(paraAttr);
        when(mockParaAttrSearchRepository.search(queryStringQuery("id:" + paraAttr.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraAttr), PageRequest.of(0, 1), 1));
        // Search the paraAttr
        restParaAttrMockMvc.perform(get("/api/_search/para-attrs?query=id:" + paraAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraAttr.getId().intValue())))
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
        TestUtil.equalsVerifier(ParaAttr.class);
        ParaAttr paraAttr1 = new ParaAttr();
        paraAttr1.setId(1L);
        ParaAttr paraAttr2 = new ParaAttr();
        paraAttr2.setId(paraAttr1.getId());
        assertThat(paraAttr1).isEqualTo(paraAttr2);
        paraAttr2.setId(2L);
        assertThat(paraAttr1).isNotEqualTo(paraAttr2);
        paraAttr1.setId(null);
        assertThat(paraAttr1).isNotEqualTo(paraAttr2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaAttrDTO.class);
        ParaAttrDTO paraAttrDTO1 = new ParaAttrDTO();
        paraAttrDTO1.setId(1L);
        ParaAttrDTO paraAttrDTO2 = new ParaAttrDTO();
        assertThat(paraAttrDTO1).isNotEqualTo(paraAttrDTO2);
        paraAttrDTO2.setId(paraAttrDTO1.getId());
        assertThat(paraAttrDTO1).isEqualTo(paraAttrDTO2);
        paraAttrDTO2.setId(2L);
        assertThat(paraAttrDTO1).isNotEqualTo(paraAttrDTO2);
        paraAttrDTO1.setId(null);
        assertThat(paraAttrDTO1).isNotEqualTo(paraAttrDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraAttrMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraAttrMapper.fromId(null)).isNull();
    }
}
