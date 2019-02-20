package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.KmsInfo;
import com.aerothinker.kms.domain.VerifyRec;
import com.aerothinker.kms.domain.ParaType;
import com.aerothinker.kms.domain.ParaClass;
import com.aerothinker.kms.domain.ParaCat;
import com.aerothinker.kms.domain.ParaState;
import com.aerothinker.kms.domain.ParaSource;
import com.aerothinker.kms.domain.ParaAttr;
import com.aerothinker.kms.domain.ParaProp;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaDep;
import com.aerothinker.kms.domain.KmsInfo;
import com.aerothinker.kms.domain.ParaOther;
import com.aerothinker.kms.repository.KmsInfoRepository;
import com.aerothinker.kms.repository.search.KmsInfoSearchRepository;
import com.aerothinker.kms.service.KmsInfoService;
import com.aerothinker.kms.service.dto.KmsInfoDTO;
import com.aerothinker.kms.service.mapper.KmsInfoMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.KmsInfoCriteria;
import com.aerothinker.kms.service.KmsInfoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
import com.aerothinker.kms.domain.enumeration.ViewPermission;
/**
 * Test class for the KmsInfoResource REST controller.
 *
 * @see KmsInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class KmsInfoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SORT_STRING = "AAAAAAAAAA";
    private static final String UPDATED_SORT_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USING_FLAG = false;
    private static final Boolean UPDATED_USING_FLAG = true;

    private static final String DEFAULT_VERSION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VERSION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_PATH = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENT_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_BLOB = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_BLOB = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMAGE_BLOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_BLOB_NAME = "BBBBBBBBBB";

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

    private static final Instant DEFAULT_PUBLISHED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLISHED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_VERIFY_NEED = false;
    private static final Boolean UPDATED_VERIFY_NEED = true;

    private static final Boolean DEFAULT_MARK_AS_VERIFIED = false;
    private static final Boolean UPDATED_MARK_AS_VERIFIED = true;

    private static final Boolean DEFAULT_VERIFY_RESULT = false;
    private static final Boolean UPDATED_VERIFY_RESULT = true;

    private static final String DEFAULT_VERIFY_OPINION = "AAAAAAAAAA";
    private static final String UPDATED_VERIFY_OPINION = "BBBBBBBBBB";

    private static final Integer DEFAULT_VIEW_COUNT = 1;
    private static final Integer UPDATED_VIEW_COUNT = 2;

    private static final ViewPermission DEFAULT_VIEW_PERMISSION = ViewPermission.PRIVATEVIEW;
    private static final ViewPermission UPDATED_VIEW_PERMISSION = ViewPermission.DEFAULTVIEW;

    private static final String DEFAULT_VIEW_PERM_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_VIEW_PERM_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_PARA_SOURCE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_PARA_SOURCE_STRING = "BBBBBBBBBB";

    @Autowired
    private KmsInfoRepository kmsInfoRepository;

    @Mock
    private KmsInfoRepository kmsInfoRepositoryMock;

    @Autowired
    private KmsInfoMapper kmsInfoMapper;

    @Mock
    private KmsInfoService kmsInfoServiceMock;

    @Autowired
    private KmsInfoService kmsInfoService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.KmsInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private KmsInfoSearchRepository mockKmsInfoSearchRepository;

    @Autowired
    private KmsInfoQueryService kmsInfoQueryService;

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

    private MockMvc restKmsInfoMockMvc;

    private KmsInfo kmsInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KmsInfoResource kmsInfoResource = new KmsInfoResource(kmsInfoService, kmsInfoQueryService);
        this.restKmsInfoMockMvc = MockMvcBuilders.standaloneSetup(kmsInfoResource)
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
    public static KmsInfo createEntity(EntityManager em) {
        KmsInfo kmsInfo = new KmsInfo()
            .name(DEFAULT_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .sortString(DEFAULT_SORT_STRING)
            .descString(DEFAULT_DESC_STRING)
            .answer(DEFAULT_ANSWER)
            .usingFlag(DEFAULT_USING_FLAG)
            .versionNumber(DEFAULT_VERSION_NUMBER)
            .remarks(DEFAULT_REMARKS)
            .attachmentPath(DEFAULT_ATTACHMENT_PATH)
            .attachmentBlob(DEFAULT_ATTACHMENT_BLOB)
            .attachmentBlobContentType(DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE)
            .attachmentName(DEFAULT_ATTACHMENT_NAME)
            .textBlob(DEFAULT_TEXT_BLOB)
            .imageBlob(DEFAULT_IMAGE_BLOB)
            .imageBlobContentType(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(DEFAULT_IMAGE_BLOB_NAME)
            .validType(DEFAULT_VALID_TYPE)
            .validBegin(DEFAULT_VALID_BEGIN)
            .validEnd(DEFAULT_VALID_END)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME)
            .verifyTime(DEFAULT_VERIFY_TIME)
            .publishedTime(DEFAULT_PUBLISHED_TIME)
            .verifyNeed(DEFAULT_VERIFY_NEED)
            .markAsVerified(DEFAULT_MARK_AS_VERIFIED)
            .verifyResult(DEFAULT_VERIFY_RESULT)
            .verifyOpinion(DEFAULT_VERIFY_OPINION)
            .viewCount(DEFAULT_VIEW_COUNT)
            .viewPermission(DEFAULT_VIEW_PERMISSION)
            .viewPermPerson(DEFAULT_VIEW_PERM_PERSON)
            .paraSourceString(DEFAULT_PARA_SOURCE_STRING);
        return kmsInfo;
    }

    @Before
    public void initTest() {
        kmsInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createKmsInfo() throws Exception {
        int databaseSizeBeforeCreate = kmsInfoRepository.findAll().size();

        // Create the KmsInfo
        KmsInfoDTO kmsInfoDTO = kmsInfoMapper.toDto(kmsInfo);
        restKmsInfoMockMvc.perform(post("/api/kms-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmsInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the KmsInfo in the database
        List<KmsInfo> kmsInfoList = kmsInfoRepository.findAll();
        assertThat(kmsInfoList).hasSize(databaseSizeBeforeCreate + 1);
        KmsInfo testKmsInfo = kmsInfoList.get(kmsInfoList.size() - 1);
        assertThat(testKmsInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKmsInfo.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testKmsInfo.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testKmsInfo.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testKmsInfo.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testKmsInfo.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testKmsInfo.getVersionNumber()).isEqualTo(DEFAULT_VERSION_NUMBER);
        assertThat(testKmsInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testKmsInfo.getAttachmentPath()).isEqualTo(DEFAULT_ATTACHMENT_PATH);
        assertThat(testKmsInfo.getAttachmentBlob()).isEqualTo(DEFAULT_ATTACHMENT_BLOB);
        assertThat(testKmsInfo.getAttachmentBlobContentType()).isEqualTo(DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE);
        assertThat(testKmsInfo.getAttachmentName()).isEqualTo(DEFAULT_ATTACHMENT_NAME);
        assertThat(testKmsInfo.getTextBlob()).isEqualTo(DEFAULT_TEXT_BLOB);
        assertThat(testKmsInfo.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testKmsInfo.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testKmsInfo.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testKmsInfo.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testKmsInfo.getValidBegin()).isEqualTo(DEFAULT_VALID_BEGIN);
        assertThat(testKmsInfo.getValidEnd()).isEqualTo(DEFAULT_VALID_END);
        assertThat(testKmsInfo.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testKmsInfo.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testKmsInfo.getVerifyTime()).isEqualTo(DEFAULT_VERIFY_TIME);
        assertThat(testKmsInfo.getPublishedTime()).isEqualTo(DEFAULT_PUBLISHED_TIME);
        assertThat(testKmsInfo.isVerifyNeed()).isEqualTo(DEFAULT_VERIFY_NEED);
        assertThat(testKmsInfo.isMarkAsVerified()).isEqualTo(DEFAULT_MARK_AS_VERIFIED);
        assertThat(testKmsInfo.isVerifyResult()).isEqualTo(DEFAULT_VERIFY_RESULT);
        assertThat(testKmsInfo.getVerifyOpinion()).isEqualTo(DEFAULT_VERIFY_OPINION);
        assertThat(testKmsInfo.getViewCount()).isEqualTo(DEFAULT_VIEW_COUNT);
        assertThat(testKmsInfo.getViewPermission()).isEqualTo(DEFAULT_VIEW_PERMISSION);
        assertThat(testKmsInfo.getViewPermPerson()).isEqualTo(DEFAULT_VIEW_PERM_PERSON);
        assertThat(testKmsInfo.getParaSourceString()).isEqualTo(DEFAULT_PARA_SOURCE_STRING);

        // Validate the KmsInfo in Elasticsearch
        verify(mockKmsInfoSearchRepository, times(1)).save(testKmsInfo);
    }

    @Test
    @Transactional
    public void createKmsInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kmsInfoRepository.findAll().size();

        // Create the KmsInfo with an existing ID
        kmsInfo.setId(1L);
        KmsInfoDTO kmsInfoDTO = kmsInfoMapper.toDto(kmsInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmsInfoMockMvc.perform(post("/api/kms-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmsInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KmsInfo in the database
        List<KmsInfo> kmsInfoList = kmsInfoRepository.findAll();
        assertThat(kmsInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the KmsInfo in Elasticsearch
        verify(mockKmsInfoSearchRepository, times(0)).save(kmsInfo);
    }

    @Test
    @Transactional
    public void getAllKmsInfos() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList
        restKmsInfoMockMvc.perform(get("/api/kms-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].attachmentPath").value(hasItem(DEFAULT_ATTACHMENT_PATH.toString())))
            .andExpect(jsonPath("$.[*].attachmentBlobContentType").value(hasItem(DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachmentBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_BLOB))))
            .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].textBlob").value(hasItem(DEFAULT_TEXT_BLOB.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validBegin").value(hasItem(DEFAULT_VALID_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].validEnd").value(hasItem(DEFAULT_VALID_END.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyTime").value(hasItem(DEFAULT_VERIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].publishedTime").value(hasItem(DEFAULT_PUBLISHED_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyNeed").value(hasItem(DEFAULT_VERIFY_NEED.booleanValue())))
            .andExpect(jsonPath("$.[*].markAsVerified").value(hasItem(DEFAULT_MARK_AS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyResult").value(hasItem(DEFAULT_VERIFY_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyOpinion").value(hasItem(DEFAULT_VERIFY_OPINION.toString())))
            .andExpect(jsonPath("$.[*].viewCount").value(hasItem(DEFAULT_VIEW_COUNT)))
            .andExpect(jsonPath("$.[*].viewPermission").value(hasItem(DEFAULT_VIEW_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].viewPermPerson").value(hasItem(DEFAULT_VIEW_PERM_PERSON.toString())))
            .andExpect(jsonPath("$.[*].paraSourceString").value(hasItem(DEFAULT_PARA_SOURCE_STRING.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllKmsInfosWithEagerRelationshipsIsEnabled() throws Exception {
        KmsInfoResource kmsInfoResource = new KmsInfoResource(kmsInfoServiceMock, kmsInfoQueryService);
        when(kmsInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restKmsInfoMockMvc = MockMvcBuilders.standaloneSetup(kmsInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restKmsInfoMockMvc.perform(get("/api/kms-infos?eagerload=true"))
        .andExpect(status().isOk());

        verify(kmsInfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllKmsInfosWithEagerRelationshipsIsNotEnabled() throws Exception {
        KmsInfoResource kmsInfoResource = new KmsInfoResource(kmsInfoServiceMock, kmsInfoQueryService);
            when(kmsInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restKmsInfoMockMvc = MockMvcBuilders.standaloneSetup(kmsInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restKmsInfoMockMvc.perform(get("/api/kms-infos?eagerload=true"))
        .andExpect(status().isOk());

            verify(kmsInfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getKmsInfo() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get the kmsInfo
        restKmsInfoMockMvc.perform(get("/api/kms-infos/{id}", kmsInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kmsInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.sortString").value(DEFAULT_SORT_STRING.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()))
            .andExpect(jsonPath("$.usingFlag").value(DEFAULT_USING_FLAG.booleanValue()))
            .andExpect(jsonPath("$.versionNumber").value(DEFAULT_VERSION_NUMBER.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.attachmentPath").value(DEFAULT_ATTACHMENT_PATH.toString()))
            .andExpect(jsonPath("$.attachmentBlobContentType").value(DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachmentBlob").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_BLOB)))
            .andExpect(jsonPath("$.attachmentName").value(DEFAULT_ATTACHMENT_NAME.toString()))
            .andExpect(jsonPath("$.textBlob").value(DEFAULT_TEXT_BLOB.toString()))
            .andExpect(jsonPath("$.imageBlobContentType").value(DEFAULT_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB)))
            .andExpect(jsonPath("$.imageBlobName").value(DEFAULT_IMAGE_BLOB_NAME.toString()))
            .andExpect(jsonPath("$.validType").value(DEFAULT_VALID_TYPE.toString()))
            .andExpect(jsonPath("$.validBegin").value(DEFAULT_VALID_BEGIN.toString()))
            .andExpect(jsonPath("$.validEnd").value(DEFAULT_VALID_END.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()))
            .andExpect(jsonPath("$.verifyTime").value(DEFAULT_VERIFY_TIME.toString()))
            .andExpect(jsonPath("$.publishedTime").value(DEFAULT_PUBLISHED_TIME.toString()))
            .andExpect(jsonPath("$.verifyNeed").value(DEFAULT_VERIFY_NEED.booleanValue()))
            .andExpect(jsonPath("$.markAsVerified").value(DEFAULT_MARK_AS_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.verifyResult").value(DEFAULT_VERIFY_RESULT.booleanValue()))
            .andExpect(jsonPath("$.verifyOpinion").value(DEFAULT_VERIFY_OPINION.toString()))
            .andExpect(jsonPath("$.viewCount").value(DEFAULT_VIEW_COUNT))
            .andExpect(jsonPath("$.viewPermission").value(DEFAULT_VIEW_PERMISSION.toString()))
            .andExpect(jsonPath("$.viewPermPerson").value(DEFAULT_VIEW_PERM_PERSON.toString()))
            .andExpect(jsonPath("$.paraSourceString").value(DEFAULT_PARA_SOURCE_STRING.toString()));
    }

    @Test
    @Transactional
    public void getAllKmsInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where name equals to DEFAULT_NAME
        defaultKmsInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the kmsInfoList where name equals to UPDATED_NAME
        defaultKmsInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultKmsInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the kmsInfoList where name equals to UPDATED_NAME
        defaultKmsInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where name is not null
        defaultKmsInfoShouldBeFound("name.specified=true");

        // Get all the kmsInfoList where name is null
        defaultKmsInfoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultKmsInfoShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the kmsInfoList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultKmsInfoShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKmsInfosBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultKmsInfoShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the kmsInfoList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultKmsInfoShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKmsInfosBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where serialNumber is not null
        defaultKmsInfoShouldBeFound("serialNumber.specified=true");

        // Get all the kmsInfoList where serialNumber is null
        defaultKmsInfoShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where sortString equals to DEFAULT_SORT_STRING
        defaultKmsInfoShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the kmsInfoList where sortString equals to UPDATED_SORT_STRING
        defaultKmsInfoShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllKmsInfosBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultKmsInfoShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the kmsInfoList where sortString equals to UPDATED_SORT_STRING
        defaultKmsInfoShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllKmsInfosBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where sortString is not null
        defaultKmsInfoShouldBeFound("sortString.specified=true");

        // Get all the kmsInfoList where sortString is null
        defaultKmsInfoShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where descString equals to DEFAULT_DESC_STRING
        defaultKmsInfoShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the kmsInfoList where descString equals to UPDATED_DESC_STRING
        defaultKmsInfoShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultKmsInfoShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the kmsInfoList where descString equals to UPDATED_DESC_STRING
        defaultKmsInfoShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where descString is not null
        defaultKmsInfoShouldBeFound("descString.specified=true");

        // Get all the kmsInfoList where descString is null
        defaultKmsInfoShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where answer equals to DEFAULT_ANSWER
        defaultKmsInfoShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the kmsInfoList where answer equals to UPDATED_ANSWER
        defaultKmsInfoShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultKmsInfoShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the kmsInfoList where answer equals to UPDATED_ANSWER
        defaultKmsInfoShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where answer is not null
        defaultKmsInfoShouldBeFound("answer.specified=true");

        // Get all the kmsInfoList where answer is null
        defaultKmsInfoShouldNotBeFound("answer.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where usingFlag equals to DEFAULT_USING_FLAG
        defaultKmsInfoShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the kmsInfoList where usingFlag equals to UPDATED_USING_FLAG
        defaultKmsInfoShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultKmsInfoShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the kmsInfoList where usingFlag equals to UPDATED_USING_FLAG
        defaultKmsInfoShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where usingFlag is not null
        defaultKmsInfoShouldBeFound("usingFlag.specified=true");

        // Get all the kmsInfoList where usingFlag is null
        defaultKmsInfoShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVersionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where versionNumber equals to DEFAULT_VERSION_NUMBER
        defaultKmsInfoShouldBeFound("versionNumber.equals=" + DEFAULT_VERSION_NUMBER);

        // Get all the kmsInfoList where versionNumber equals to UPDATED_VERSION_NUMBER
        defaultKmsInfoShouldNotBeFound("versionNumber.equals=" + UPDATED_VERSION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVersionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where versionNumber in DEFAULT_VERSION_NUMBER or UPDATED_VERSION_NUMBER
        defaultKmsInfoShouldBeFound("versionNumber.in=" + DEFAULT_VERSION_NUMBER + "," + UPDATED_VERSION_NUMBER);

        // Get all the kmsInfoList where versionNumber equals to UPDATED_VERSION_NUMBER
        defaultKmsInfoShouldNotBeFound("versionNumber.in=" + UPDATED_VERSION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVersionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where versionNumber is not null
        defaultKmsInfoShouldBeFound("versionNumber.specified=true");

        // Get all the kmsInfoList where versionNumber is null
        defaultKmsInfoShouldNotBeFound("versionNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where remarks equals to DEFAULT_REMARKS
        defaultKmsInfoShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the kmsInfoList where remarks equals to UPDATED_REMARKS
        defaultKmsInfoShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultKmsInfoShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the kmsInfoList where remarks equals to UPDATED_REMARKS
        defaultKmsInfoShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where remarks is not null
        defaultKmsInfoShouldBeFound("remarks.specified=true");

        // Get all the kmsInfoList where remarks is null
        defaultKmsInfoShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAttachmentPathIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where attachmentPath equals to DEFAULT_ATTACHMENT_PATH
        defaultKmsInfoShouldBeFound("attachmentPath.equals=" + DEFAULT_ATTACHMENT_PATH);

        // Get all the kmsInfoList where attachmentPath equals to UPDATED_ATTACHMENT_PATH
        defaultKmsInfoShouldNotBeFound("attachmentPath.equals=" + UPDATED_ATTACHMENT_PATH);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAttachmentPathIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where attachmentPath in DEFAULT_ATTACHMENT_PATH or UPDATED_ATTACHMENT_PATH
        defaultKmsInfoShouldBeFound("attachmentPath.in=" + DEFAULT_ATTACHMENT_PATH + "," + UPDATED_ATTACHMENT_PATH);

        // Get all the kmsInfoList where attachmentPath equals to UPDATED_ATTACHMENT_PATH
        defaultKmsInfoShouldNotBeFound("attachmentPath.in=" + UPDATED_ATTACHMENT_PATH);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAttachmentPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where attachmentPath is not null
        defaultKmsInfoShouldBeFound("attachmentPath.specified=true");

        // Get all the kmsInfoList where attachmentPath is null
        defaultKmsInfoShouldNotBeFound("attachmentPath.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAttachmentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where attachmentName equals to DEFAULT_ATTACHMENT_NAME
        defaultKmsInfoShouldBeFound("attachmentName.equals=" + DEFAULT_ATTACHMENT_NAME);

        // Get all the kmsInfoList where attachmentName equals to UPDATED_ATTACHMENT_NAME
        defaultKmsInfoShouldNotBeFound("attachmentName.equals=" + UPDATED_ATTACHMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAttachmentNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where attachmentName in DEFAULT_ATTACHMENT_NAME or UPDATED_ATTACHMENT_NAME
        defaultKmsInfoShouldBeFound("attachmentName.in=" + DEFAULT_ATTACHMENT_NAME + "," + UPDATED_ATTACHMENT_NAME);

        // Get all the kmsInfoList where attachmentName equals to UPDATED_ATTACHMENT_NAME
        defaultKmsInfoShouldNotBeFound("attachmentName.in=" + UPDATED_ATTACHMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByAttachmentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where attachmentName is not null
        defaultKmsInfoShouldBeFound("attachmentName.specified=true");

        // Get all the kmsInfoList where attachmentName is null
        defaultKmsInfoShouldNotBeFound("attachmentName.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultKmsInfoShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the kmsInfoList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultKmsInfoShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultKmsInfoShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the kmsInfoList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultKmsInfoShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where imageBlobName is not null
        defaultKmsInfoShouldBeFound("imageBlobName.specified=true");

        // Get all the kmsInfoList where imageBlobName is null
        defaultKmsInfoShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validType equals to DEFAULT_VALID_TYPE
        defaultKmsInfoShouldBeFound("validType.equals=" + DEFAULT_VALID_TYPE);

        // Get all the kmsInfoList where validType equals to UPDATED_VALID_TYPE
        defaultKmsInfoShouldNotBeFound("validType.equals=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidTypeIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validType in DEFAULT_VALID_TYPE or UPDATED_VALID_TYPE
        defaultKmsInfoShouldBeFound("validType.in=" + DEFAULT_VALID_TYPE + "," + UPDATED_VALID_TYPE);

        // Get all the kmsInfoList where validType equals to UPDATED_VALID_TYPE
        defaultKmsInfoShouldNotBeFound("validType.in=" + UPDATED_VALID_TYPE);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validType is not null
        defaultKmsInfoShouldBeFound("validType.specified=true");

        // Get all the kmsInfoList where validType is null
        defaultKmsInfoShouldNotBeFound("validType.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validBegin equals to DEFAULT_VALID_BEGIN
        defaultKmsInfoShouldBeFound("validBegin.equals=" + DEFAULT_VALID_BEGIN);

        // Get all the kmsInfoList where validBegin equals to UPDATED_VALID_BEGIN
        defaultKmsInfoShouldNotBeFound("validBegin.equals=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidBeginIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validBegin in DEFAULT_VALID_BEGIN or UPDATED_VALID_BEGIN
        defaultKmsInfoShouldBeFound("validBegin.in=" + DEFAULT_VALID_BEGIN + "," + UPDATED_VALID_BEGIN);

        // Get all the kmsInfoList where validBegin equals to UPDATED_VALID_BEGIN
        defaultKmsInfoShouldNotBeFound("validBegin.in=" + UPDATED_VALID_BEGIN);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validBegin is not null
        defaultKmsInfoShouldBeFound("validBegin.specified=true");

        // Get all the kmsInfoList where validBegin is null
        defaultKmsInfoShouldNotBeFound("validBegin.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidEndIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validEnd equals to DEFAULT_VALID_END
        defaultKmsInfoShouldBeFound("validEnd.equals=" + DEFAULT_VALID_END);

        // Get all the kmsInfoList where validEnd equals to UPDATED_VALID_END
        defaultKmsInfoShouldNotBeFound("validEnd.equals=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidEndIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validEnd in DEFAULT_VALID_END or UPDATED_VALID_END
        defaultKmsInfoShouldBeFound("validEnd.in=" + DEFAULT_VALID_END + "," + UPDATED_VALID_END);

        // Get all the kmsInfoList where validEnd equals to UPDATED_VALID_END
        defaultKmsInfoShouldNotBeFound("validEnd.in=" + UPDATED_VALID_END);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByValidEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where validEnd is not null
        defaultKmsInfoShouldBeFound("validEnd.specified=true");

        // Get all the kmsInfoList where validEnd is null
        defaultKmsInfoShouldNotBeFound("validEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where createTime equals to DEFAULT_CREATE_TIME
        defaultKmsInfoShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the kmsInfoList where createTime equals to UPDATED_CREATE_TIME
        defaultKmsInfoShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultKmsInfoShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the kmsInfoList where createTime equals to UPDATED_CREATE_TIME
        defaultKmsInfoShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where createTime is not null
        defaultKmsInfoShouldBeFound("createTime.specified=true");

        // Get all the kmsInfoList where createTime is null
        defaultKmsInfoShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where modifyTime equals to DEFAULT_MODIFY_TIME
        defaultKmsInfoShouldBeFound("modifyTime.equals=" + DEFAULT_MODIFY_TIME);

        // Get all the kmsInfoList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultKmsInfoShouldNotBeFound("modifyTime.equals=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where modifyTime in DEFAULT_MODIFY_TIME or UPDATED_MODIFY_TIME
        defaultKmsInfoShouldBeFound("modifyTime.in=" + DEFAULT_MODIFY_TIME + "," + UPDATED_MODIFY_TIME);

        // Get all the kmsInfoList where modifyTime equals to UPDATED_MODIFY_TIME
        defaultKmsInfoShouldNotBeFound("modifyTime.in=" + UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where modifyTime is not null
        defaultKmsInfoShouldBeFound("modifyTime.specified=true");

        // Get all the kmsInfoList where modifyTime is null
        defaultKmsInfoShouldNotBeFound("modifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyTime equals to DEFAULT_VERIFY_TIME
        defaultKmsInfoShouldBeFound("verifyTime.equals=" + DEFAULT_VERIFY_TIME);

        // Get all the kmsInfoList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultKmsInfoShouldNotBeFound("verifyTime.equals=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyTime in DEFAULT_VERIFY_TIME or UPDATED_VERIFY_TIME
        defaultKmsInfoShouldBeFound("verifyTime.in=" + DEFAULT_VERIFY_TIME + "," + UPDATED_VERIFY_TIME);

        // Get all the kmsInfoList where verifyTime equals to UPDATED_VERIFY_TIME
        defaultKmsInfoShouldNotBeFound("verifyTime.in=" + UPDATED_VERIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyTime is not null
        defaultKmsInfoShouldBeFound("verifyTime.specified=true");

        // Get all the kmsInfoList where verifyTime is null
        defaultKmsInfoShouldNotBeFound("verifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByPublishedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where publishedTime equals to DEFAULT_PUBLISHED_TIME
        defaultKmsInfoShouldBeFound("publishedTime.equals=" + DEFAULT_PUBLISHED_TIME);

        // Get all the kmsInfoList where publishedTime equals to UPDATED_PUBLISHED_TIME
        defaultKmsInfoShouldNotBeFound("publishedTime.equals=" + UPDATED_PUBLISHED_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByPublishedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where publishedTime in DEFAULT_PUBLISHED_TIME or UPDATED_PUBLISHED_TIME
        defaultKmsInfoShouldBeFound("publishedTime.in=" + DEFAULT_PUBLISHED_TIME + "," + UPDATED_PUBLISHED_TIME);

        // Get all the kmsInfoList where publishedTime equals to UPDATED_PUBLISHED_TIME
        defaultKmsInfoShouldNotBeFound("publishedTime.in=" + UPDATED_PUBLISHED_TIME);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByPublishedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where publishedTime is not null
        defaultKmsInfoShouldBeFound("publishedTime.specified=true");

        // Get all the kmsInfoList where publishedTime is null
        defaultKmsInfoShouldNotBeFound("publishedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyNeedIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyNeed equals to DEFAULT_VERIFY_NEED
        defaultKmsInfoShouldBeFound("verifyNeed.equals=" + DEFAULT_VERIFY_NEED);

        // Get all the kmsInfoList where verifyNeed equals to UPDATED_VERIFY_NEED
        defaultKmsInfoShouldNotBeFound("verifyNeed.equals=" + UPDATED_VERIFY_NEED);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyNeedIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyNeed in DEFAULT_VERIFY_NEED or UPDATED_VERIFY_NEED
        defaultKmsInfoShouldBeFound("verifyNeed.in=" + DEFAULT_VERIFY_NEED + "," + UPDATED_VERIFY_NEED);

        // Get all the kmsInfoList where verifyNeed equals to UPDATED_VERIFY_NEED
        defaultKmsInfoShouldNotBeFound("verifyNeed.in=" + UPDATED_VERIFY_NEED);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyNeedIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyNeed is not null
        defaultKmsInfoShouldBeFound("verifyNeed.specified=true");

        // Get all the kmsInfoList where verifyNeed is null
        defaultKmsInfoShouldNotBeFound("verifyNeed.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByMarkAsVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where markAsVerified equals to DEFAULT_MARK_AS_VERIFIED
        defaultKmsInfoShouldBeFound("markAsVerified.equals=" + DEFAULT_MARK_AS_VERIFIED);

        // Get all the kmsInfoList where markAsVerified equals to UPDATED_MARK_AS_VERIFIED
        defaultKmsInfoShouldNotBeFound("markAsVerified.equals=" + UPDATED_MARK_AS_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByMarkAsVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where markAsVerified in DEFAULT_MARK_AS_VERIFIED or UPDATED_MARK_AS_VERIFIED
        defaultKmsInfoShouldBeFound("markAsVerified.in=" + DEFAULT_MARK_AS_VERIFIED + "," + UPDATED_MARK_AS_VERIFIED);

        // Get all the kmsInfoList where markAsVerified equals to UPDATED_MARK_AS_VERIFIED
        defaultKmsInfoShouldNotBeFound("markAsVerified.in=" + UPDATED_MARK_AS_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByMarkAsVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where markAsVerified is not null
        defaultKmsInfoShouldBeFound("markAsVerified.specified=true");

        // Get all the kmsInfoList where markAsVerified is null
        defaultKmsInfoShouldNotBeFound("markAsVerified.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyResultIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyResult equals to DEFAULT_VERIFY_RESULT
        defaultKmsInfoShouldBeFound("verifyResult.equals=" + DEFAULT_VERIFY_RESULT);

        // Get all the kmsInfoList where verifyResult equals to UPDATED_VERIFY_RESULT
        defaultKmsInfoShouldNotBeFound("verifyResult.equals=" + UPDATED_VERIFY_RESULT);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyResultIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyResult in DEFAULT_VERIFY_RESULT or UPDATED_VERIFY_RESULT
        defaultKmsInfoShouldBeFound("verifyResult.in=" + DEFAULT_VERIFY_RESULT + "," + UPDATED_VERIFY_RESULT);

        // Get all the kmsInfoList where verifyResult equals to UPDATED_VERIFY_RESULT
        defaultKmsInfoShouldNotBeFound("verifyResult.in=" + UPDATED_VERIFY_RESULT);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyResult is not null
        defaultKmsInfoShouldBeFound("verifyResult.specified=true");

        // Get all the kmsInfoList where verifyResult is null
        defaultKmsInfoShouldNotBeFound("verifyResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyOpinionIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyOpinion equals to DEFAULT_VERIFY_OPINION
        defaultKmsInfoShouldBeFound("verifyOpinion.equals=" + DEFAULT_VERIFY_OPINION);

        // Get all the kmsInfoList where verifyOpinion equals to UPDATED_VERIFY_OPINION
        defaultKmsInfoShouldNotBeFound("verifyOpinion.equals=" + UPDATED_VERIFY_OPINION);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyOpinionIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyOpinion in DEFAULT_VERIFY_OPINION or UPDATED_VERIFY_OPINION
        defaultKmsInfoShouldBeFound("verifyOpinion.in=" + DEFAULT_VERIFY_OPINION + "," + UPDATED_VERIFY_OPINION);

        // Get all the kmsInfoList where verifyOpinion equals to UPDATED_VERIFY_OPINION
        defaultKmsInfoShouldNotBeFound("verifyOpinion.in=" + UPDATED_VERIFY_OPINION);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyOpinionIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where verifyOpinion is not null
        defaultKmsInfoShouldBeFound("verifyOpinion.specified=true");

        // Get all the kmsInfoList where verifyOpinion is null
        defaultKmsInfoShouldNotBeFound("verifyOpinion.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewCountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewCount equals to DEFAULT_VIEW_COUNT
        defaultKmsInfoShouldBeFound("viewCount.equals=" + DEFAULT_VIEW_COUNT);

        // Get all the kmsInfoList where viewCount equals to UPDATED_VIEW_COUNT
        defaultKmsInfoShouldNotBeFound("viewCount.equals=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewCountIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewCount in DEFAULT_VIEW_COUNT or UPDATED_VIEW_COUNT
        defaultKmsInfoShouldBeFound("viewCount.in=" + DEFAULT_VIEW_COUNT + "," + UPDATED_VIEW_COUNT);

        // Get all the kmsInfoList where viewCount equals to UPDATED_VIEW_COUNT
        defaultKmsInfoShouldNotBeFound("viewCount.in=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewCount is not null
        defaultKmsInfoShouldBeFound("viewCount.specified=true");

        // Get all the kmsInfoList where viewCount is null
        defaultKmsInfoShouldNotBeFound("viewCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewCount greater than or equals to DEFAULT_VIEW_COUNT
        defaultKmsInfoShouldBeFound("viewCount.greaterOrEqualThan=" + DEFAULT_VIEW_COUNT);

        // Get all the kmsInfoList where viewCount greater than or equals to UPDATED_VIEW_COUNT
        defaultKmsInfoShouldNotBeFound("viewCount.greaterOrEqualThan=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewCountIsLessThanSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewCount less than or equals to DEFAULT_VIEW_COUNT
        defaultKmsInfoShouldNotBeFound("viewCount.lessThan=" + DEFAULT_VIEW_COUNT);

        // Get all the kmsInfoList where viewCount less than or equals to UPDATED_VIEW_COUNT
        defaultKmsInfoShouldBeFound("viewCount.lessThan=" + UPDATED_VIEW_COUNT);
    }


    @Test
    @Transactional
    public void getAllKmsInfosByViewPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewPermission equals to DEFAULT_VIEW_PERMISSION
        defaultKmsInfoShouldBeFound("viewPermission.equals=" + DEFAULT_VIEW_PERMISSION);

        // Get all the kmsInfoList where viewPermission equals to UPDATED_VIEW_PERMISSION
        defaultKmsInfoShouldNotBeFound("viewPermission.equals=" + UPDATED_VIEW_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewPermissionIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewPermission in DEFAULT_VIEW_PERMISSION or UPDATED_VIEW_PERMISSION
        defaultKmsInfoShouldBeFound("viewPermission.in=" + DEFAULT_VIEW_PERMISSION + "," + UPDATED_VIEW_PERMISSION);

        // Get all the kmsInfoList where viewPermission equals to UPDATED_VIEW_PERMISSION
        defaultKmsInfoShouldNotBeFound("viewPermission.in=" + UPDATED_VIEW_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewPermissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewPermission is not null
        defaultKmsInfoShouldBeFound("viewPermission.specified=true");

        // Get all the kmsInfoList where viewPermission is null
        defaultKmsInfoShouldNotBeFound("viewPermission.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewPermPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewPermPerson equals to DEFAULT_VIEW_PERM_PERSON
        defaultKmsInfoShouldBeFound("viewPermPerson.equals=" + DEFAULT_VIEW_PERM_PERSON);

        // Get all the kmsInfoList where viewPermPerson equals to UPDATED_VIEW_PERM_PERSON
        defaultKmsInfoShouldNotBeFound("viewPermPerson.equals=" + UPDATED_VIEW_PERM_PERSON);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewPermPersonIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewPermPerson in DEFAULT_VIEW_PERM_PERSON or UPDATED_VIEW_PERM_PERSON
        defaultKmsInfoShouldBeFound("viewPermPerson.in=" + DEFAULT_VIEW_PERM_PERSON + "," + UPDATED_VIEW_PERM_PERSON);

        // Get all the kmsInfoList where viewPermPerson equals to UPDATED_VIEW_PERM_PERSON
        defaultKmsInfoShouldNotBeFound("viewPermPerson.in=" + UPDATED_VIEW_PERM_PERSON);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByViewPermPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where viewPermPerson is not null
        defaultKmsInfoShouldBeFound("viewPermPerson.specified=true");

        // Get all the kmsInfoList where viewPermPerson is null
        defaultKmsInfoShouldNotBeFound("viewPermPerson.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByParaSourceStringIsEqualToSomething() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where paraSourceString equals to DEFAULT_PARA_SOURCE_STRING
        defaultKmsInfoShouldBeFound("paraSourceString.equals=" + DEFAULT_PARA_SOURCE_STRING);

        // Get all the kmsInfoList where paraSourceString equals to UPDATED_PARA_SOURCE_STRING
        defaultKmsInfoShouldNotBeFound("paraSourceString.equals=" + UPDATED_PARA_SOURCE_STRING);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByParaSourceStringIsInShouldWork() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where paraSourceString in DEFAULT_PARA_SOURCE_STRING or UPDATED_PARA_SOURCE_STRING
        defaultKmsInfoShouldBeFound("paraSourceString.in=" + DEFAULT_PARA_SOURCE_STRING + "," + UPDATED_PARA_SOURCE_STRING);

        // Get all the kmsInfoList where paraSourceString equals to UPDATED_PARA_SOURCE_STRING
        defaultKmsInfoShouldNotBeFound("paraSourceString.in=" + UPDATED_PARA_SOURCE_STRING);
    }

    @Test
    @Transactional
    public void getAllKmsInfosByParaSourceStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        // Get all the kmsInfoList where paraSourceString is not null
        defaultKmsInfoShouldBeFound("paraSourceString.specified=true");

        // Get all the kmsInfoList where paraSourceString is null
        defaultKmsInfoShouldNotBeFound("paraSourceString.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmsInfosByVerifyRecIsEqualToSomething() throws Exception {
        // Initialize the database
        VerifyRec verifyRec = VerifyRecResourceIntTest.createEntity(em);
        em.persist(verifyRec);
        em.flush();
        kmsInfo.setVerifyRec(verifyRec);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long verifyRecId = verifyRec.getId();

        // Get all the kmsInfoList where verifyRec equals to verifyRecId
        defaultKmsInfoShouldBeFound("verifyRecId.equals=" + verifyRecId);

        // Get all the kmsInfoList where verifyRec equals to verifyRecId + 1
        defaultKmsInfoShouldNotBeFound("verifyRecId.equals=" + (verifyRecId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaType paraType = ParaTypeResourceIntTest.createEntity(em);
        em.persist(paraType);
        em.flush();
        kmsInfo.setParaType(paraType);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraTypeId = paraType.getId();

        // Get all the kmsInfoList where paraType equals to paraTypeId
        defaultKmsInfoShouldBeFound("paraTypeId.equals=" + paraTypeId);

        // Get all the kmsInfoList where paraType equals to paraTypeId + 1
        defaultKmsInfoShouldNotBeFound("paraTypeId.equals=" + (paraTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaClassIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaClass paraClass = ParaClassResourceIntTest.createEntity(em);
        em.persist(paraClass);
        em.flush();
        kmsInfo.setParaClass(paraClass);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraClassId = paraClass.getId();

        // Get all the kmsInfoList where paraClass equals to paraClassId
        defaultKmsInfoShouldBeFound("paraClassId.equals=" + paraClassId);

        // Get all the kmsInfoList where paraClass equals to paraClassId + 1
        defaultKmsInfoShouldNotBeFound("paraClassId.equals=" + (paraClassId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaCatIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaCat paraCat = ParaCatResourceIntTest.createEntity(em);
        em.persist(paraCat);
        em.flush();
        kmsInfo.setParaCat(paraCat);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraCatId = paraCat.getId();

        // Get all the kmsInfoList where paraCat equals to paraCatId
        defaultKmsInfoShouldBeFound("paraCatId.equals=" + paraCatId);

        // Get all the kmsInfoList where paraCat equals to paraCatId + 1
        defaultKmsInfoShouldNotBeFound("paraCatId.equals=" + (paraCatId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaStateIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaState paraState = ParaStateResourceIntTest.createEntity(em);
        em.persist(paraState);
        em.flush();
        kmsInfo.setParaState(paraState);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraStateId = paraState.getId();

        // Get all the kmsInfoList where paraState equals to paraStateId
        defaultKmsInfoShouldBeFound("paraStateId.equals=" + paraStateId);

        // Get all the kmsInfoList where paraState equals to paraStateId + 1
        defaultKmsInfoShouldNotBeFound("paraStateId.equals=" + (paraStateId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaSource paraSource = ParaSourceResourceIntTest.createEntity(em);
        em.persist(paraSource);
        em.flush();
        kmsInfo.setParaSource(paraSource);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraSourceId = paraSource.getId();

        // Get all the kmsInfoList where paraSource equals to paraSourceId
        defaultKmsInfoShouldBeFound("paraSourceId.equals=" + paraSourceId);

        // Get all the kmsInfoList where paraSource equals to paraSourceId + 1
        defaultKmsInfoShouldNotBeFound("paraSourceId.equals=" + (paraSourceId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaAttrIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaAttr paraAttr = ParaAttrResourceIntTest.createEntity(em);
        em.persist(paraAttr);
        em.flush();
        kmsInfo.setParaAttr(paraAttr);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraAttrId = paraAttr.getId();

        // Get all the kmsInfoList where paraAttr equals to paraAttrId
        defaultKmsInfoShouldBeFound("paraAttrId.equals=" + paraAttrId);

        // Get all the kmsInfoList where paraAttr equals to paraAttrId + 1
        defaultKmsInfoShouldNotBeFound("paraAttrId.equals=" + (paraAttrId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaPropIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaProp paraProp = ParaPropResourceIntTest.createEntity(em);
        em.persist(paraProp);
        em.flush();
        kmsInfo.setParaProp(paraProp);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraPropId = paraProp.getId();

        // Get all the kmsInfoList where paraProp equals to paraPropId
        defaultKmsInfoShouldBeFound("paraPropId.equals=" + paraPropId);

        // Get all the kmsInfoList where paraProp equals to paraPropId + 1
        defaultKmsInfoShouldNotBeFound("paraPropId.equals=" + (paraPropId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        kmsInfo.setCreatedUser(createdUser);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long createdUserId = createdUser.getId();

        // Get all the kmsInfoList where createdUser equals to createdUserId
        defaultKmsInfoShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the kmsInfoList where createdUser equals to createdUserId + 1
        defaultKmsInfoShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByCreatedDepByIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaDep createdDepBy = ParaDepResourceIntTest.createEntity(em);
        em.persist(createdDepBy);
        em.flush();
        kmsInfo.setCreatedDepBy(createdDepBy);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long createdDepById = createdDepBy.getId();

        // Get all the kmsInfoList where createdDepBy equals to createdDepById
        defaultKmsInfoShouldBeFound("createdDepById.equals=" + createdDepById);

        // Get all the kmsInfoList where createdDepBy equals to createdDepById + 1
        defaultKmsInfoShouldNotBeFound("createdDepById.equals=" + (createdDepById + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByOwnerByIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser ownerBy = ParaUserResourceIntTest.createEntity(em);
        em.persist(ownerBy);
        em.flush();
        kmsInfo.setOwnerBy(ownerBy);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long ownerById = ownerBy.getId();

        // Get all the kmsInfoList where ownerBy equals to ownerById
        defaultKmsInfoShouldBeFound("ownerById.equals=" + ownerById);

        // Get all the kmsInfoList where ownerBy equals to ownerById + 1
        defaultKmsInfoShouldNotBeFound("ownerById.equals=" + (ownerById + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByOwnerDepByIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaDep ownerDepBy = ParaDepResourceIntTest.createEntity(em);
        em.persist(ownerDepBy);
        em.flush();
        kmsInfo.setOwnerDepBy(ownerDepBy);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long ownerDepById = ownerDepBy.getId();

        // Get all the kmsInfoList where ownerDepBy equals to ownerDepById
        defaultKmsInfoShouldBeFound("ownerDepById.equals=" + ownerDepById);

        // Get all the kmsInfoList where ownerDepBy equals to ownerDepById + 1
        defaultKmsInfoShouldNotBeFound("ownerDepById.equals=" + (ownerDepById + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        kmsInfo.setModifiedUser(modifiedUser);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the kmsInfoList where modifiedUser equals to modifiedUserId
        defaultKmsInfoShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the kmsInfoList where modifiedUser equals to modifiedUserId + 1
        defaultKmsInfoShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByModifiedUserDepIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaDep modifiedUserDep = ParaDepResourceIntTest.createEntity(em);
        em.persist(modifiedUserDep);
        em.flush();
        kmsInfo.setModifiedUserDep(modifiedUserDep);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long modifiedUserDepId = modifiedUserDep.getId();

        // Get all the kmsInfoList where modifiedUserDep equals to modifiedUserDepId
        defaultKmsInfoShouldBeFound("modifiedUserDepId.equals=" + modifiedUserDepId);

        // Get all the kmsInfoList where modifiedUserDep equals to modifiedUserDepId + 1
        defaultKmsInfoShouldNotBeFound("modifiedUserDepId.equals=" + (modifiedUserDepId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        kmsInfo.setVerifiedUser(verifiedUser);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the kmsInfoList where verifiedUser equals to verifiedUserId
        defaultKmsInfoShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the kmsInfoList where verifiedUser equals to verifiedUserId + 1
        defaultKmsInfoShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByVerifiedDepByIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaDep verifiedDepBy = ParaDepResourceIntTest.createEntity(em);
        em.persist(verifiedDepBy);
        em.flush();
        kmsInfo.setVerifiedDepBy(verifiedDepBy);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long verifiedDepById = verifiedDepBy.getId();

        // Get all the kmsInfoList where verifiedDepBy equals to verifiedDepById
        defaultKmsInfoShouldBeFound("verifiedDepById.equals=" + verifiedDepById);

        // Get all the kmsInfoList where verifiedDepBy equals to verifiedDepById + 1
        defaultKmsInfoShouldNotBeFound("verifiedDepById.equals=" + (verifiedDepById + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByPublishedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser publishedBy = ParaUserResourceIntTest.createEntity(em);
        em.persist(publishedBy);
        em.flush();
        kmsInfo.setPublishedBy(publishedBy);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long publishedById = publishedBy.getId();

        // Get all the kmsInfoList where publishedBy equals to publishedById
        defaultKmsInfoShouldBeFound("publishedById.equals=" + publishedById);

        // Get all the kmsInfoList where publishedBy equals to publishedById + 1
        defaultKmsInfoShouldNotBeFound("publishedById.equals=" + (publishedById + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByPublishedDepByIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaDep publishedDepBy = ParaDepResourceIntTest.createEntity(em);
        em.persist(publishedDepBy);
        em.flush();
        kmsInfo.setPublishedDepBy(publishedDepBy);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long publishedDepById = publishedDepBy.getId();

        // Get all the kmsInfoList where publishedDepBy equals to publishedDepById
        defaultKmsInfoShouldBeFound("publishedDepById.equals=" + publishedDepById);

        // Get all the kmsInfoList where publishedDepBy equals to publishedDepById + 1
        defaultKmsInfoShouldNotBeFound("publishedDepById.equals=" + (publishedDepById + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        KmsInfo parent = KmsInfoResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        kmsInfo.setParent(parent);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long parentId = parent.getId();

        // Get all the kmsInfoList where parent equals to parentId
        defaultKmsInfoShouldBeFound("parentId.equals=" + parentId);

        // Get all the kmsInfoList where parent equals to parentId + 1
        defaultKmsInfoShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllKmsInfosByParaOtherIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaOther paraOther = ParaOtherResourceIntTest.createEntity(em);
        em.persist(paraOther);
        em.flush();
        kmsInfo.addParaOther(paraOther);
        kmsInfoRepository.saveAndFlush(kmsInfo);
        Long paraOtherId = paraOther.getId();

        // Get all the kmsInfoList where paraOther equals to paraOtherId
        defaultKmsInfoShouldBeFound("paraOtherId.equals=" + paraOtherId);

        // Get all the kmsInfoList where paraOther equals to paraOtherId + 1
        defaultKmsInfoShouldNotBeFound("paraOtherId.equals=" + (paraOtherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultKmsInfoShouldBeFound(String filter) throws Exception {
        restKmsInfoMockMvc.perform(get("/api/kms-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].attachmentPath").value(hasItem(DEFAULT_ATTACHMENT_PATH.toString())))
            .andExpect(jsonPath("$.[*].attachmentBlobContentType").value(hasItem(DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachmentBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_BLOB))))
            .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].textBlob").value(hasItem(DEFAULT_TEXT_BLOB.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validBegin").value(hasItem(DEFAULT_VALID_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].validEnd").value(hasItem(DEFAULT_VALID_END.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyTime").value(hasItem(DEFAULT_VERIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].publishedTime").value(hasItem(DEFAULT_PUBLISHED_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyNeed").value(hasItem(DEFAULT_VERIFY_NEED.booleanValue())))
            .andExpect(jsonPath("$.[*].markAsVerified").value(hasItem(DEFAULT_MARK_AS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyResult").value(hasItem(DEFAULT_VERIFY_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyOpinion").value(hasItem(DEFAULT_VERIFY_OPINION.toString())))
            .andExpect(jsonPath("$.[*].viewCount").value(hasItem(DEFAULT_VIEW_COUNT)))
            .andExpect(jsonPath("$.[*].viewPermission").value(hasItem(DEFAULT_VIEW_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].viewPermPerson").value(hasItem(DEFAULT_VIEW_PERM_PERSON.toString())))
            .andExpect(jsonPath("$.[*].paraSourceString").value(hasItem(DEFAULT_PARA_SOURCE_STRING.toString())));

        // Check, that the count call also returns 1
        restKmsInfoMockMvc.perform(get("/api/kms-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultKmsInfoShouldNotBeFound(String filter) throws Exception {
        restKmsInfoMockMvc.perform(get("/api/kms-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmsInfoMockMvc.perform(get("/api/kms-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingKmsInfo() throws Exception {
        // Get the kmsInfo
        restKmsInfoMockMvc.perform(get("/api/kms-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKmsInfo() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        int databaseSizeBeforeUpdate = kmsInfoRepository.findAll().size();

        // Update the kmsInfo
        KmsInfo updatedKmsInfo = kmsInfoRepository.findById(kmsInfo.getId()).get();
        // Disconnect from session so that the updates on updatedKmsInfo are not directly saved in db
        em.detach(updatedKmsInfo);
        updatedKmsInfo
            .name(UPDATED_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .sortString(UPDATED_SORT_STRING)
            .descString(UPDATED_DESC_STRING)
            .answer(UPDATED_ANSWER)
            .usingFlag(UPDATED_USING_FLAG)
            .versionNumber(UPDATED_VERSION_NUMBER)
            .remarks(UPDATED_REMARKS)
            .attachmentPath(UPDATED_ATTACHMENT_PATH)
            .attachmentBlob(UPDATED_ATTACHMENT_BLOB)
            .attachmentBlobContentType(UPDATED_ATTACHMENT_BLOB_CONTENT_TYPE)
            .attachmentName(UPDATED_ATTACHMENT_NAME)
            .textBlob(UPDATED_TEXT_BLOB)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(UPDATED_IMAGE_BLOB_NAME)
            .validType(UPDATED_VALID_TYPE)
            .validBegin(UPDATED_VALID_BEGIN)
            .validEnd(UPDATED_VALID_END)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME)
            .verifyTime(UPDATED_VERIFY_TIME)
            .publishedTime(UPDATED_PUBLISHED_TIME)
            .verifyNeed(UPDATED_VERIFY_NEED)
            .markAsVerified(UPDATED_MARK_AS_VERIFIED)
            .verifyResult(UPDATED_VERIFY_RESULT)
            .verifyOpinion(UPDATED_VERIFY_OPINION)
            .viewCount(UPDATED_VIEW_COUNT)
            .viewPermission(UPDATED_VIEW_PERMISSION)
            .viewPermPerson(UPDATED_VIEW_PERM_PERSON)
            .paraSourceString(UPDATED_PARA_SOURCE_STRING);
        KmsInfoDTO kmsInfoDTO = kmsInfoMapper.toDto(updatedKmsInfo);

        restKmsInfoMockMvc.perform(put("/api/kms-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmsInfoDTO)))
            .andExpect(status().isOk());

        // Validate the KmsInfo in the database
        List<KmsInfo> kmsInfoList = kmsInfoRepository.findAll();
        assertThat(kmsInfoList).hasSize(databaseSizeBeforeUpdate);
        KmsInfo testKmsInfo = kmsInfoList.get(kmsInfoList.size() - 1);
        assertThat(testKmsInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKmsInfo.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testKmsInfo.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testKmsInfo.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testKmsInfo.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testKmsInfo.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testKmsInfo.getVersionNumber()).isEqualTo(UPDATED_VERSION_NUMBER);
        assertThat(testKmsInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testKmsInfo.getAttachmentPath()).isEqualTo(UPDATED_ATTACHMENT_PATH);
        assertThat(testKmsInfo.getAttachmentBlob()).isEqualTo(UPDATED_ATTACHMENT_BLOB);
        assertThat(testKmsInfo.getAttachmentBlobContentType()).isEqualTo(UPDATED_ATTACHMENT_BLOB_CONTENT_TYPE);
        assertThat(testKmsInfo.getAttachmentName()).isEqualTo(UPDATED_ATTACHMENT_NAME);
        assertThat(testKmsInfo.getTextBlob()).isEqualTo(UPDATED_TEXT_BLOB);
        assertThat(testKmsInfo.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testKmsInfo.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testKmsInfo.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testKmsInfo.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testKmsInfo.getValidBegin()).isEqualTo(UPDATED_VALID_BEGIN);
        assertThat(testKmsInfo.getValidEnd()).isEqualTo(UPDATED_VALID_END);
        assertThat(testKmsInfo.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testKmsInfo.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testKmsInfo.getVerifyTime()).isEqualTo(UPDATED_VERIFY_TIME);
        assertThat(testKmsInfo.getPublishedTime()).isEqualTo(UPDATED_PUBLISHED_TIME);
        assertThat(testKmsInfo.isVerifyNeed()).isEqualTo(UPDATED_VERIFY_NEED);
        assertThat(testKmsInfo.isMarkAsVerified()).isEqualTo(UPDATED_MARK_AS_VERIFIED);
        assertThat(testKmsInfo.isVerifyResult()).isEqualTo(UPDATED_VERIFY_RESULT);
        assertThat(testKmsInfo.getVerifyOpinion()).isEqualTo(UPDATED_VERIFY_OPINION);
        assertThat(testKmsInfo.getViewCount()).isEqualTo(UPDATED_VIEW_COUNT);
        assertThat(testKmsInfo.getViewPermission()).isEqualTo(UPDATED_VIEW_PERMISSION);
        assertThat(testKmsInfo.getViewPermPerson()).isEqualTo(UPDATED_VIEW_PERM_PERSON);
        assertThat(testKmsInfo.getParaSourceString()).isEqualTo(UPDATED_PARA_SOURCE_STRING);

        // Validate the KmsInfo in Elasticsearch
        verify(mockKmsInfoSearchRepository, times(1)).save(testKmsInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingKmsInfo() throws Exception {
        int databaseSizeBeforeUpdate = kmsInfoRepository.findAll().size();

        // Create the KmsInfo
        KmsInfoDTO kmsInfoDTO = kmsInfoMapper.toDto(kmsInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmsInfoMockMvc.perform(put("/api/kms-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmsInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KmsInfo in the database
        List<KmsInfo> kmsInfoList = kmsInfoRepository.findAll();
        assertThat(kmsInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the KmsInfo in Elasticsearch
        verify(mockKmsInfoSearchRepository, times(0)).save(kmsInfo);
    }

    @Test
    @Transactional
    public void deleteKmsInfo() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);

        int databaseSizeBeforeDelete = kmsInfoRepository.findAll().size();

        // Delete the kmsInfo
        restKmsInfoMockMvc.perform(delete("/api/kms-infos/{id}", kmsInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KmsInfo> kmsInfoList = kmsInfoRepository.findAll();
        assertThat(kmsInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the KmsInfo in Elasticsearch
        verify(mockKmsInfoSearchRepository, times(1)).deleteById(kmsInfo.getId());
    }

    @Test
    @Transactional
    public void searchKmsInfo() throws Exception {
        // Initialize the database
        kmsInfoRepository.saveAndFlush(kmsInfo);
        when(mockKmsInfoSearchRepository.search(queryStringQuery("id:" + kmsInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(kmsInfo), PageRequest.of(0, 1), 1));
        // Search the kmsInfo
        restKmsInfoMockMvc.perform(get("/api/_search/kms-infos?query=id:" + kmsInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(DEFAULT_VERSION_NUMBER)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].attachmentPath").value(hasItem(DEFAULT_ATTACHMENT_PATH)))
            .andExpect(jsonPath("$.[*].attachmentBlobContentType").value(hasItem(DEFAULT_ATTACHMENT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachmentBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_BLOB))))
            .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME)))
            .andExpect(jsonPath("$.[*].textBlob").value(hasItem(DEFAULT_TEXT_BLOB.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME)))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validBegin").value(hasItem(DEFAULT_VALID_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].validEnd").value(hasItem(DEFAULT_VALID_END.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyTime").value(hasItem(DEFAULT_VERIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].publishedTime").value(hasItem(DEFAULT_PUBLISHED_TIME.toString())))
            .andExpect(jsonPath("$.[*].verifyNeed").value(hasItem(DEFAULT_VERIFY_NEED.booleanValue())))
            .andExpect(jsonPath("$.[*].markAsVerified").value(hasItem(DEFAULT_MARK_AS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyResult").value(hasItem(DEFAULT_VERIFY_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].verifyOpinion").value(hasItem(DEFAULT_VERIFY_OPINION)))
            .andExpect(jsonPath("$.[*].viewCount").value(hasItem(DEFAULT_VIEW_COUNT)))
            .andExpect(jsonPath("$.[*].viewPermission").value(hasItem(DEFAULT_VIEW_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].viewPermPerson").value(hasItem(DEFAULT_VIEW_PERM_PERSON)))
            .andExpect(jsonPath("$.[*].paraSourceString").value(hasItem(DEFAULT_PARA_SOURCE_STRING)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmsInfo.class);
        KmsInfo kmsInfo1 = new KmsInfo();
        kmsInfo1.setId(1L);
        KmsInfo kmsInfo2 = new KmsInfo();
        kmsInfo2.setId(kmsInfo1.getId());
        assertThat(kmsInfo1).isEqualTo(kmsInfo2);
        kmsInfo2.setId(2L);
        assertThat(kmsInfo1).isNotEqualTo(kmsInfo2);
        kmsInfo1.setId(null);
        assertThat(kmsInfo1).isNotEqualTo(kmsInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmsInfoDTO.class);
        KmsInfoDTO kmsInfoDTO1 = new KmsInfoDTO();
        kmsInfoDTO1.setId(1L);
        KmsInfoDTO kmsInfoDTO2 = new KmsInfoDTO();
        assertThat(kmsInfoDTO1).isNotEqualTo(kmsInfoDTO2);
        kmsInfoDTO2.setId(kmsInfoDTO1.getId());
        assertThat(kmsInfoDTO1).isEqualTo(kmsInfoDTO2);
        kmsInfoDTO2.setId(2L);
        assertThat(kmsInfoDTO1).isNotEqualTo(kmsInfoDTO2);
        kmsInfoDTO1.setId(null);
        assertThat(kmsInfoDTO1).isNotEqualTo(kmsInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kmsInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kmsInfoMapper.fromId(null)).isNull();
    }
}
