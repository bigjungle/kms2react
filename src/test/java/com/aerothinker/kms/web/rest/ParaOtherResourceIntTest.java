package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaOther;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.KmsInfo;
import com.aerothinker.kms.repository.ParaOtherRepository;
import com.aerothinker.kms.repository.search.ParaOtherSearchRepository;
import com.aerothinker.kms.service.ParaOtherService;
import com.aerothinker.kms.service.dto.ParaOtherDTO;
import com.aerothinker.kms.service.mapper.ParaOtherMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaOtherCriteria;
import com.aerothinker.kms.service.ParaOtherQueryService;

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

import com.aerothinker.kms.domain.enumeration.ParaOtherValueType;
import com.aerothinker.kms.domain.enumeration.ValidType;
/**
 * Test class for the ParaOtherResource REST controller.
 *
 * @see ParaOtherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaOtherResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SORT_STRING = "AAAAAAAAAA";
    private static final String UPDATED_SORT_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final ParaOtherValueType DEFAULT_PARA_OTHER_VALUE_TYPE = ParaOtherValueType.STRING;
    private static final ParaOtherValueType UPDATED_PARA_OTHER_VALUE_TYPE = ParaOtherValueType.INSTANT;

    private static final String DEFAULT_PARA_VALUE_ST = "AAAAAAAAAA";
    private static final String UPDATED_PARA_VALUE_ST = "BBBBBBBBBB";

    private static final Instant DEFAULT_PARA_VALUE_IN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PARA_VALUE_IN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_PARA_VALUE_BO = false;
    private static final Boolean UPDATED_PARA_VALUE_BO = true;

    private static final String DEFAULT_PARA_VALUE_JS = "AAAAAAAAAA";
    private static final String UPDATED_PARA_VALUE_JS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PARA_VALUE_BL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PARA_VALUE_BL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PARA_VALUE_BL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PARA_VALUE_BL_CONTENT_TYPE = "image/png";

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
    private ParaOtherRepository paraOtherRepository;

    @Autowired
    private ParaOtherMapper paraOtherMapper;

    @Autowired
    private ParaOtherService paraOtherService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaOtherSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaOtherSearchRepository mockParaOtherSearchRepository;

    @Autowired
    private ParaOtherQueryService paraOtherQueryService;

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

    private MockMvc restParaOtherMockMvc;

    private ParaOther paraOther;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaOtherResource paraOtherResource = new ParaOtherResource(paraOtherService, paraOtherQueryService);
        this.restParaOtherMockMvc = MockMvcBuilders.standaloneSetup(paraOtherResource)
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
    public static ParaOther createEntity(EntityManager em) {
        ParaOther paraOther = new ParaOther()
            .name(DEFAULT_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .sortString(DEFAULT_SORT_STRING)
            .descString(DEFAULT_DESC_STRING)
            .paraOtherValueType(DEFAULT_PARA_OTHER_VALUE_TYPE)
            .paraValueSt(DEFAULT_PARA_VALUE_ST)
            .paraValueIn(DEFAULT_PARA_VALUE_IN)
            .paraValueBo(DEFAULT_PARA_VALUE_BO)
            .paraValueJs(DEFAULT_PARA_VALUE_JS)
            .paraValueBl(DEFAULT_PARA_VALUE_BL)
            .paraValueBlContentType(DEFAULT_PARA_VALUE_BL_CONTENT_TYPE)
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
        return paraOther;
    }

    @Before
    public void initTest() {
        paraOther = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaOther() throws Exception {
        int databaseSizeBeforeCreate = paraOtherRepository.findAll().size();

        // Create the ParaOther
        ParaOtherDTO paraOtherDTO = paraOtherMapper.toDto(paraOther);
        restParaOtherMockMvc.perform(post("/api/para-others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraOtherDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaOther in the database
        List<ParaOther> paraOtherList = paraOtherRepository.findAll();
        assertThat(paraOtherList).hasSize(databaseSizeBeforeCreate + 1);
        ParaOther testParaOther = paraOtherList.get(paraOtherList.size() - 1);
        assertThat(testParaOther.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaOther.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaOther.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaOther.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaOther.getParaOtherValueType()).isEqualTo(DEFAULT_PARA_OTHER_VALUE_TYPE);
        assertThat(testParaOther.getParaValueSt()).isEqualTo(DEFAULT_PARA_VALUE_ST);
        assertThat(testParaOther.getParaValueIn()).isEqualTo(DEFAULT_PARA_VALUE_IN);
        assertThat(testParaOther.isParaValueBo()).isEqualTo(DEFAULT_PARA_VALUE_BO);
        assertThat(testParaOther.getParaValueJs()).isEqualTo(DEFAULT_PARA_VALUE_JS);
        assertThat(testParaOther.getParaValueBl()).isEqualTo(DEFAULT_PARA_VALUE_BL);
        assertThat(testParaOther.getParaValueBlContentType()).isEqualTo(DEFAULT_PARA_VALUE_BL_CONTENT_TYPE);
        assertThat(testParaOther.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaOther.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaOther.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaOther.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaOther.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testParaOther.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testParaOther.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testParaOther.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testParaOther.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParaOther.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParaOther.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);

        // Validate the ParaOther in Elasticsearch
        verify(mockParaOtherSearchRepository, times(1)).save(testParaOther);
    }

    @Test
    @Transactional
    public void createParaOtherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraOtherRepository.findAll().size();

        // Create the ParaOther with an existing ID
        paraOther.setId(1L);
        ParaOtherDTO paraOtherDTO = paraOtherMapper.toDto(paraOther);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaOtherMockMvc.perform(post("/api/para-others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraOtherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaOther in the database
        List<ParaOther> paraOtherList = paraOtherRepository.findAll();
        assertThat(paraOtherList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaOther in Elasticsearch
        verify(mockParaOtherSearchRepository, times(0)).save(paraOther);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraOtherRepository.findAll().size();
        // set the field null
        paraOther.setName(null);

        // Create the ParaOther, which fails.
        ParaOtherDTO paraOtherDTO = paraOtherMapper.toDto(paraOther);

        restParaOtherMockMvc.perform(post("/api/para-others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraOtherDTO)))
            .andExpect(status().isBadRequest());

        List<ParaOther> paraOtherList = paraOtherRepository.findAll();
        assertThat(paraOtherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaOthers() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList
        restParaOtherMockMvc.perform(get("/api/para-others?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraOther.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].paraOtherValueType").value(hasItem(DEFAULT_PARA_OTHER_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paraValueSt").value(hasItem(DEFAULT_PARA_VALUE_ST.toString())))
            .andExpect(jsonPath("$.[*].paraValueIn").value(hasItem(DEFAULT_PARA_VALUE_IN.toString())))
            .andExpect(jsonPath("$.[*].paraValueBo").value(hasItem(DEFAULT_PARA_VALUE_BO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraValueJs").value(hasItem(DEFAULT_PARA_VALUE_JS.toString())))
            .andExpect(jsonPath("$.[*].paraValueBlContentType").value(hasItem(DEFAULT_PARA_VALUE_BL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].paraValueBl").value(hasItem(Base64Utils.encodeToString(DEFAULT_PARA_VALUE_BL))))
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
    public void getParaOther() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get the paraOther
        restParaOtherMockMvc.perform(get("/api/para-others/{id}", paraOther.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraOther.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.sortString").value(DEFAULT_SORT_STRING.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.paraOtherValueType").value(DEFAULT_PARA_OTHER_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.paraValueSt").value(DEFAULT_PARA_VALUE_ST.toString()))
            .andExpect(jsonPath("$.paraValueIn").value(DEFAULT_PARA_VALUE_IN.toString()))
            .andExpect(jsonPath("$.paraValueBo").value(DEFAULT_PARA_VALUE_BO.booleanValue()))
            .andExpect(jsonPath("$.paraValueJs").value(DEFAULT_PARA_VALUE_JS.toString()))
            .andExpect(jsonPath("$.paraValueBlContentType").value(DEFAULT_PARA_VALUE_BL_CONTENT_TYPE))
            .andExpect(jsonPath("$.paraValueBl").value(Base64Utils.encodeToString(DEFAULT_PARA_VALUE_BL)))
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
    public void getAllParaOthersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where name equals to DEFAULT_NAME
        defaultParaOtherShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraOtherList where name equals to UPDATED_NAME
        defaultParaOtherShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaOtherShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraOtherList where name equals to UPDATED_NAME
        defaultParaOtherShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where name is not null
        defaultParaOtherShouldBeFound("name.specified=true");

        // Get all the paraOtherList where name is null
        defaultParaOtherShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaOtherShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraOtherList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaOtherShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaOthersBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaOtherShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraOtherList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaOtherShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaOthersBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where serialNumber is not null
        defaultParaOtherShouldBeFound("serialNumber.specified=true");

        // Get all the paraOtherList where serialNumber is null
        defaultParaOtherShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where sortString equals to DEFAULT_SORT_STRING
        defaultParaOtherShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraOtherList where sortString equals to UPDATED_SORT_STRING
        defaultParaOtherShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaOthersBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaOtherShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraOtherList where sortString equals to UPDATED_SORT_STRING
        defaultParaOtherShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaOthersBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where sortString is not null
        defaultParaOtherShouldBeFound("sortString.specified=true");

        // Get all the paraOtherList where sortString is null
        defaultParaOtherShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where descString equals to DEFAULT_DESC_STRING
        defaultParaOtherShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraOtherList where descString equals to UPDATED_DESC_STRING
        defaultParaOtherShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaOthersByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaOtherShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraOtherList where descString equals to UPDATED_DESC_STRING
        defaultParaOtherShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaOthersByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where descString is not null
        defaultParaOtherShouldBeFound("descString.specified=true");

        // Get all the paraOtherList where descString is null
        defaultParaOtherShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaOtherValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraOtherValueType equals to DEFAULT_PARA_OTHER_VALUE_TYPE
        defaultParaOtherShouldBeFound("paraOtherValueType.equals=" + DEFAULT_PARA_OTHER_VALUE_TYPE);

        // Get all the paraOtherList where paraOtherValueType equals to UPDATED_PARA_OTHER_VALUE_TYPE
        defaultParaOtherShouldNotBeFound("paraOtherValueType.equals=" + UPDATED_PARA_OTHER_VALUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaOtherValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraOtherValueType in DEFAULT_PARA_OTHER_VALUE_TYPE or UPDATED_PARA_OTHER_VALUE_TYPE
        defaultParaOtherShouldBeFound("paraOtherValueType.in=" + DEFAULT_PARA_OTHER_VALUE_TYPE + "," + UPDATED_PARA_OTHER_VALUE_TYPE);

        // Get all the paraOtherList where paraOtherValueType equals to UPDATED_PARA_OTHER_VALUE_TYPE
        defaultParaOtherShouldNotBeFound("paraOtherValueType.in=" + UPDATED_PARA_OTHER_VALUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaOtherValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraOtherValueType is not null
        defaultParaOtherShouldBeFound("paraOtherValueType.specified=true");

        // Get all the paraOtherList where paraOtherValueType is null
        defaultParaOtherShouldNotBeFound("paraOtherValueType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueStIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueSt equals to DEFAULT_PARA_VALUE_ST
        defaultParaOtherShouldBeFound("paraValueSt.equals=" + DEFAULT_PARA_VALUE_ST);

        // Get all the paraOtherList where paraValueSt equals to UPDATED_PARA_VALUE_ST
        defaultParaOtherShouldNotBeFound("paraValueSt.equals=" + UPDATED_PARA_VALUE_ST);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueStIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueSt in DEFAULT_PARA_VALUE_ST or UPDATED_PARA_VALUE_ST
        defaultParaOtherShouldBeFound("paraValueSt.in=" + DEFAULT_PARA_VALUE_ST + "," + UPDATED_PARA_VALUE_ST);

        // Get all the paraOtherList where paraValueSt equals to UPDATED_PARA_VALUE_ST
        defaultParaOtherShouldNotBeFound("paraValueSt.in=" + UPDATED_PARA_VALUE_ST);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueStIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueSt is not null
        defaultParaOtherShouldBeFound("paraValueSt.specified=true");

        // Get all the paraOtherList where paraValueSt is null
        defaultParaOtherShouldNotBeFound("paraValueSt.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueInIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueIn equals to DEFAULT_PARA_VALUE_IN
        defaultParaOtherShouldBeFound("paraValueIn.equals=" + DEFAULT_PARA_VALUE_IN);

        // Get all the paraOtherList where paraValueIn equals to UPDATED_PARA_VALUE_IN
        defaultParaOtherShouldNotBeFound("paraValueIn.equals=" + UPDATED_PARA_VALUE_IN);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueInIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueIn in DEFAULT_PARA_VALUE_IN or UPDATED_PARA_VALUE_IN
        defaultParaOtherShouldBeFound("paraValueIn.in=" + DEFAULT_PARA_VALUE_IN + "," + UPDATED_PARA_VALUE_IN);

        // Get all the paraOtherList where paraValueIn equals to UPDATED_PARA_VALUE_IN
        defaultParaOtherShouldNotBeFound("paraValueIn.in=" + UPDATED_PARA_VALUE_IN);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueInIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueIn is not null
        defaultParaOtherShouldBeFound("paraValueIn.specified=true");

        // Get all the paraOtherList where paraValueIn is null
        defaultParaOtherShouldNotBeFound("paraValueIn.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueBoIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueBo equals to DEFAULT_PARA_VALUE_BO
        defaultParaOtherShouldBeFound("paraValueBo.equals=" + DEFAULT_PARA_VALUE_BO);

        // Get all the paraOtherList where paraValueBo equals to UPDATED_PARA_VALUE_BO
        defaultParaOtherShouldNotBeFound("paraValueBo.equals=" + UPDATED_PARA_VALUE_BO);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueBoIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueBo in DEFAULT_PARA_VALUE_BO or UPDATED_PARA_VALUE_BO
        defaultParaOtherShouldBeFound("paraValueBo.in=" + DEFAULT_PARA_VALUE_BO + "," + UPDATED_PARA_VALUE_BO);

        // Get all the paraOtherList where paraValueBo equals to UPDATED_PARA_VALUE_BO
        defaultParaOtherShouldNotBeFound("paraValueBo.in=" + UPDATED_PARA_VALUE_BO);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueBoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueBo is not null
        defaultParaOtherShouldBeFound("paraValueBo.specified=true");

        // Get all the paraOtherList where paraValueBo is null
        defaultParaOtherShouldNotBeFound("paraValueBo.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueJsIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueJs equals to DEFAULT_PARA_VALUE_JS
        defaultParaOtherShouldBeFound("paraValueJs.equals=" + DEFAULT_PARA_VALUE_JS);

        // Get all the paraOtherList where paraValueJs equals to UPDATED_PARA_VALUE_JS
        defaultParaOtherShouldNotBeFound("paraValueJs.equals=" + UPDATED_PARA_VALUE_JS);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueJsIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueJs in DEFAULT_PARA_VALUE_JS or UPDATED_PARA_VALUE_JS
        defaultParaOtherShouldBeFound("paraValueJs.in=" + DEFAULT_PARA_VALUE_JS + "," + UPDATED_PARA_VALUE_JS);

        // Get all the paraOtherList where paraValueJs equals to UPDATED_PARA_VALUE_JS
        defaultParaOtherShouldNotBeFound("paraValueJs.in=" + UPDATED_PARA_VALUE_JS);
    }

    @Test
    @Transactional
    public void getAllParaOthersByParaValueJsIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where paraValueJs is not null
        defaultParaOtherShouldBeFound("paraValueJs.specified=true");

        // Get all the paraOtherList where paraValueJs is null
        defaultParaOtherShouldNotBeFound("paraValueJs.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaOtherShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraOtherList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaOtherShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaOtherShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraOtherList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaOtherShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where imageBlobName is not null
        defaultParaOtherShouldBeFound("imageBlobName.specified=true");

        // Get all the paraOtherList where imageBlobName is null
        defaultParaOtherShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaOtherShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraOtherList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaOtherShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaOthersByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaOtherShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraOtherList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaOtherShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaOthersByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where usingFlag is not null
        defaultParaOtherShouldBeFound("usingFlag.specified=true");

        // Get all the paraOtherList where usingFlag is null
        defaultParaOtherShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where remarks equals to DEFAULT_REMARKS
        defaultParaOtherShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraOtherList where remarks equals to UPDATED_REMARKS
        defaultParaOtherShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaOthersByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaOtherShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraOtherList where remarks equals to UPDATED_REMARKS
        defaultParaOtherShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaOthersByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where remarks is not null
        defaultParaOtherShouldBeFound("remarks.specified=true");

        // Get all the paraOtherList where remarks is null
        defaultParaOtherShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validType equals to DEFAULT_VALID_TYPE
        defaultParaOtherShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the paraOtherList where validType equals to UPDATED_VALID_TYPE
        defaultParaOtherShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultParaOtherShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the paraOtherList where validType equals to UPDATED_VALID_TYPE
        defaultParaOtherShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validType is not null
        defaultParaOtherShouldBeFound("validType.specified=true");

        // Get all the paraOtherList where validType is null
        defaultParaOtherShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultParaOtherShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the paraOtherList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaOtherShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultParaOtherShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the paraOtherList where validBegin equals to UPDATED_VALID_BEGIN
        defaultParaOtherShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validBegin is not null
        defaultParaOtherShouldBeFound("validBegin.specified=true");

        // Get all the paraOtherList where validBegin is null
        defaultParaOtherShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validEnd equals to DEFAULT_VALID_END
        defaultParaOtherShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the paraOtherList where validEnd equals to UPDATED_VALID_END
        defaultParaOtherShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultParaOtherShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the paraOtherList where validEnd equals to UPDATED_VALID_END
        defaultParaOtherShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllParaOthersByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where validEnd is not null
        defaultParaOtherShouldBeFound("validEnd.specified=true");

        // Get all the paraOtherList where validEnd is null
        defaultParaOtherShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where createTime equals to DEFAULT_CREATE_TIME
        defaultParaOtherShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the paraOtherList where createTime equals to UPDATED_CREATE_TIME
        defaultParaOtherShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultParaOtherShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the paraOtherList where createTime equals to UPDATED_CREATE_TIME
        defaultParaOtherShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where createTime is not null
        defaultParaOtherShouldBeFound("createTime.specified=true");

        // Get all the paraOtherList where createTime is null
        defaultParaOtherShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultParaOtherShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the paraOtherList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaOtherShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultParaOtherShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the paraOtherList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultParaOtherShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where modifyTime is not null
        defaultParaOtherShouldBeFound("modifyTime.specified=true");

        // Get all the paraOtherList where modifyTime is null
        defaultParaOtherShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultParaOtherShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the paraOtherList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaOtherShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultParaOtherShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the paraOtherList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultParaOtherShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllParaOthersByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        // Get all the paraOtherList where verifyTime is not null
        defaultParaOtherShouldBeFound("verifyTime.specified=true");

        // Get all the paraOtherList where verifyTime is null
        defaultParaOtherShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaOthersByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraOther.setCreatedUser(createdUser);
        paraOtherRepository.saveAndFlush(paraOther);
        Long createdUserId = createdUser.getId();

        // Get all the paraOtherList where createdUser equals to createdUserId
        defaultParaOtherShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraOtherList where createdUser equals to createdUserId + 1
        defaultParaOtherShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaOthersByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraOther.setModifiedUser(modifiedUser);
        paraOtherRepository.saveAndFlush(paraOther);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraOtherList where modifiedUser equals to modifiedUserId
        defaultParaOtherShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraOtherList where modifiedUser equals to modifiedUserId + 1
        defaultParaOtherShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaOthersByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraOther.setVerifiedUser(verifiedUser);
        paraOtherRepository.saveAndFlush(paraOther);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraOtherList where verifiedUser equals to verifiedUserId
        defaultParaOtherShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraOtherList where verifiedUser equals to verifiedUserId + 1
        defaultParaOtherShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaOthersByKmsInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        KmsInfo kmsInfo = KmsInfoResourceIntTest.createEntity(em);
        em.persist(kmsInfo);
        em.flush();
        paraOther.addKmsInfo(kmsInfo);
        paraOtherRepository.saveAndFlush(paraOther);
        Long kmsInfoId = kmsInfo.getId();

        // Get all the paraOtherList where kmsInfo equals to kmsInfoId
        defaultParaOtherShouldBeFound("kmsInfoId.equals=" + kmsInfoId);

        // Get all the paraOtherList where kmsInfo equals to kmsInfoId + 1
        defaultParaOtherShouldNotBeFound("kmsInfoId.equals=" + (kmsInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaOtherShouldBeFound(String filter) throws Exception {
        restParaOtherMockMvc.perform(get("/api/para-others?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraOther.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].paraOtherValueType").value(hasItem(DEFAULT_PARA_OTHER_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paraValueSt").value(hasItem(DEFAULT_PARA_VALUE_ST.toString())))
            .andExpect(jsonPath("$.[*].paraValueIn").value(hasItem(DEFAULT_PARA_VALUE_IN.toString())))
            .andExpect(jsonPath("$.[*].paraValueBo").value(hasItem(DEFAULT_PARA_VALUE_BO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraValueJs").value(hasItem(DEFAULT_PARA_VALUE_JS.toString())))
            .andExpect(jsonPath("$.[*].paraValueBlContentType").value(hasItem(DEFAULT_PARA_VALUE_BL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].paraValueBl").value(hasItem(Base64Utils.encodeToString(DEFAULT_PARA_VALUE_BL))))
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
        restParaOtherMockMvc.perform(get("/api/para-others/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaOtherShouldNotBeFound(String filter) throws Exception {
        restParaOtherMockMvc.perform(get("/api/para-others?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaOtherMockMvc.perform(get("/api/para-others/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaOther() throws Exception {
        // Get the paraOther
        restParaOtherMockMvc.perform(get("/api/para-others/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaOther() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        int databaseSizeBeforeUpdate = paraOtherRepository.findAll().size();

        // Update the paraOther
        ParaOther updatedParaOther = paraOtherRepository.findById(paraOther.getId()).get();
        // Disconnect from session so that the updates on updatedParaOther are not directly saved in db
        em.detach(updatedParaOther);
        updatedParaOther
            .name(UPDATED_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .sortString(UPDATED_SORT_STRING)
            .descString(UPDATED_DESC_STRING)
            .paraOtherValueType(UPDATED_PARA_OTHER_VALUE_TYPE)
            .paraValueSt(UPDATED_PARA_VALUE_ST)
            .paraValueIn(UPDATED_PARA_VALUE_IN)
            .paraValueBo(UPDATED_PARA_VALUE_BO)
            .paraValueJs(UPDATED_PARA_VALUE_JS)
            .paraValueBl(UPDATED_PARA_VALUE_BL)
            .paraValueBlContentType(UPDATED_PARA_VALUE_BL_CONTENT_TYPE)
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
        ParaOtherDTO paraOtherDTO = paraOtherMapper.toDto(updatedParaOther);

        restParaOtherMockMvc.perform(put("/api/para-others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraOtherDTO)))
            .andExpect(status().isOk());

        // Validate the ParaOther in the database
        List<ParaOther> paraOtherList = paraOtherRepository.findAll();
        assertThat(paraOtherList).hasSize(databaseSizeBeforeUpdate);
        ParaOther testParaOther = paraOtherList.get(paraOtherList.size() - 1);
        assertThat(testParaOther.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaOther.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaOther.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaOther.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaOther.getParaOtherValueType()).isEqualTo(UPDATED_PARA_OTHER_VALUE_TYPE);
        assertThat(testParaOther.getParaValueSt()).isEqualTo(UPDATED_PARA_VALUE_ST);
        assertThat(testParaOther.getParaValueIn()).isEqualTo(UPDATED_PARA_VALUE_IN);
        assertThat(testParaOther.isParaValueBo()).isEqualTo(UPDATED_PARA_VALUE_BO);
        assertThat(testParaOther.getParaValueJs()).isEqualTo(UPDATED_PARA_VALUE_JS);
        assertThat(testParaOther.getParaValueBl()).isEqualTo(UPDATED_PARA_VALUE_BL);
        assertThat(testParaOther.getParaValueBlContentType()).isEqualTo(UPDATED_PARA_VALUE_BL_CONTENT_TYPE);
        assertThat(testParaOther.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaOther.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaOther.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaOther.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaOther.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testParaOther.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testParaOther.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testParaOther.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testParaOther.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParaOther.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParaOther.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);

        // Validate the ParaOther in Elasticsearch
        verify(mockParaOtherSearchRepository, times(1)).save(testParaOther);
    }

    @Test
    @Transactional
    public void updateNonExistingParaOther() throws Exception {
        int databaseSizeBeforeUpdate = paraOtherRepository.findAll().size();

        // Create the ParaOther
        ParaOtherDTO paraOtherDTO = paraOtherMapper.toDto(paraOther);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaOtherMockMvc.perform(put("/api/para-others")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraOtherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaOther in the database
        List<ParaOther> paraOtherList = paraOtherRepository.findAll();
        assertThat(paraOtherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaOther in Elasticsearch
        verify(mockParaOtherSearchRepository, times(0)).save(paraOther);
    }

    @Test
    @Transactional
    public void deleteParaOther() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);

        int databaseSizeBeforeDelete = paraOtherRepository.findAll().size();

        // Delete the paraOther
        restParaOtherMockMvc.perform(delete("/api/para-others/{id}", paraOther.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaOther> paraOtherList = paraOtherRepository.findAll();
        assertThat(paraOtherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaOther in Elasticsearch
        verify(mockParaOtherSearchRepository, times(1)).deleteById(paraOther.getId());
    }

    @Test
    @Transactional
    public void searchParaOther() throws Exception {
        // Initialize the database
        paraOtherRepository.saveAndFlush(paraOther);
        when(mockParaOtherSearchRepository.search(queryStringQuery("id:" + paraOther.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraOther), PageRequest.of(0, 1), 1));
        // Search the paraOther
        restParaOtherMockMvc.perform(get("/api/_search/para-others?query=id:" + paraOther.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraOther.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].paraOtherValueType").value(hasItem(DEFAULT_PARA_OTHER_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paraValueSt").value(hasItem(DEFAULT_PARA_VALUE_ST)))
            .andExpect(jsonPath("$.[*].paraValueIn").value(hasItem(DEFAULT_PARA_VALUE_IN.toString())))
            .andExpect(jsonPath("$.[*].paraValueBo").value(hasItem(DEFAULT_PARA_VALUE_BO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraValueJs").value(hasItem(DEFAULT_PARA_VALUE_JS)))
            .andExpect(jsonPath("$.[*].paraValueBlContentType").value(hasItem(DEFAULT_PARA_VALUE_BL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].paraValueBl").value(hasItem(Base64Utils.encodeToString(DEFAULT_PARA_VALUE_BL))))
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
        TestUtil.equalsVerifier(ParaOther.class);
        ParaOther paraOther1 = new ParaOther();
        paraOther1.setId(1L);
        ParaOther paraOther2 = new ParaOther();
        paraOther2.setId(paraOther1.getId());
        assertThat(paraOther1).isEqualTo(paraOther2);
        paraOther2.setId(2L);
        assertThat(paraOther1).isNotEqualTo(paraOther2);
        paraOther1.setId(null);
        assertThat(paraOther1).isNotEqualTo(paraOther2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaOtherDTO.class);
        ParaOtherDTO paraOtherDTO1 = new ParaOtherDTO();
        paraOtherDTO1.setId(1L);
        ParaOtherDTO paraOtherDTO2 = new ParaOtherDTO();
        assertThat(paraOtherDTO1).isNotEqualTo(paraOtherDTO2);
        paraOtherDTO2.setId(paraOtherDTO1.getId());
        assertThat(paraOtherDTO1).isEqualTo(paraOtherDTO2);
        paraOtherDTO2.setId(2L);
        assertThat(paraOtherDTO1).isNotEqualTo(paraOtherDTO2);
        paraOtherDTO1.setId(null);
        assertThat(paraOtherDTO1).isNotEqualTo(paraOtherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraOtherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraOtherMapper.fromId(null)).isNull();
    }
}
