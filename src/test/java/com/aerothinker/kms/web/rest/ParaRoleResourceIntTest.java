package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaRole;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaRole;
import com.aerothinker.kms.domain.ParaNode;
import com.aerothinker.kms.repository.ParaRoleRepository;
import com.aerothinker.kms.repository.search.ParaRoleSearchRepository;
import com.aerothinker.kms.service.ParaRoleService;
import com.aerothinker.kms.service.dto.ParaRoleDTO;
import com.aerothinker.kms.service.mapper.ParaRoleMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaRoleCriteria;
import com.aerothinker.kms.service.ParaRoleQueryService;

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

/**
 * Test class for the ParaRoleResource REST controller.
 *
 * @see ParaRoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaRoleResourceIntTest {

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

    @Autowired
    private ParaRoleRepository paraRoleRepository;

    @Mock
    private ParaRoleRepository paraRoleRepositoryMock;

    @Autowired
    private ParaRoleMapper paraRoleMapper;

    @Mock
    private ParaRoleService paraRoleServiceMock;

    @Autowired
    private ParaRoleService paraRoleService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaRoleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaRoleSearchRepository mockParaRoleSearchRepository;

    @Autowired
    private ParaRoleQueryService paraRoleQueryService;

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

    private MockMvc restParaRoleMockMvc;

    private ParaRole paraRole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaRoleResource paraRoleResource = new ParaRoleResource(paraRoleService, paraRoleQueryService);
        this.restParaRoleMockMvc = MockMvcBuilders.standaloneSetup(paraRoleResource)
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
    public static ParaRole createEntity(EntityManager em) {
        ParaRole paraRole = new ParaRole()
            .name(DEFAULT_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .sortString(DEFAULT_SORT_STRING)
            .descString(DEFAULT_DESC_STRING)
            .imageBlob(DEFAULT_IMAGE_BLOB)
            .imageBlobContentType(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(DEFAULT_IMAGE_BLOB_NAME)
            .usingFlag(DEFAULT_USING_FLAG)
            .remarks(DEFAULT_REMARKS);
        return paraRole;
    }

    @Before
    public void initTest() {
        paraRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaRole() throws Exception {
        int databaseSizeBeforeCreate = paraRoleRepository.findAll().size();

        // Create the ParaRole
        ParaRoleDTO paraRoleDTO = paraRoleMapper.toDto(paraRole);
        restParaRoleMockMvc.perform(post("/api/para-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaRole in the database
        List<ParaRole> paraRoleList = paraRoleRepository.findAll();
        assertThat(paraRoleList).hasSize(databaseSizeBeforeCreate + 1);
        ParaRole testParaRole = paraRoleList.get(paraRoleList.size() - 1);
        assertThat(testParaRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaRole.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaRole.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaRole.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaRole.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaRole.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaRole.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaRole.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaRole.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the ParaRole in Elasticsearch
        verify(mockParaRoleSearchRepository, times(1)).save(testParaRole);
    }

    @Test
    @Transactional
    public void createParaRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraRoleRepository.findAll().size();

        // Create the ParaRole with an existing ID
        paraRole.setId(1L);
        ParaRoleDTO paraRoleDTO = paraRoleMapper.toDto(paraRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaRoleMockMvc.perform(post("/api/para-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaRole in the database
        List<ParaRole> paraRoleList = paraRoleRepository.findAll();
        assertThat(paraRoleList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaRole in Elasticsearch
        verify(mockParaRoleSearchRepository, times(0)).save(paraRole);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraRoleRepository.findAll().size();
        // set the field null
        paraRole.setName(null);

        // Create the ParaRole, which fails.
        ParaRoleDTO paraRoleDTO = paraRoleMapper.toDto(paraRole);

        restParaRoleMockMvc.perform(post("/api/para-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraRoleDTO)))
            .andExpect(status().isBadRequest());

        List<ParaRole> paraRoleList = paraRoleRepository.findAll();
        assertThat(paraRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaRoles() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList
        restParaRoleMockMvc.perform(get("/api/para-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllParaRolesWithEagerRelationshipsIsEnabled() throws Exception {
        ParaRoleResource paraRoleResource = new ParaRoleResource(paraRoleServiceMock, paraRoleQueryService);
        when(paraRoleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restParaRoleMockMvc = MockMvcBuilders.standaloneSetup(paraRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restParaRoleMockMvc.perform(get("/api/para-roles?eagerload=true"))
        .andExpect(status().isOk());

        verify(paraRoleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllParaRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ParaRoleResource paraRoleResource = new ParaRoleResource(paraRoleServiceMock, paraRoleQueryService);
            when(paraRoleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restParaRoleMockMvc = MockMvcBuilders.standaloneSetup(paraRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restParaRoleMockMvc.perform(get("/api/para-roles?eagerload=true"))
        .andExpect(status().isOk());

            verify(paraRoleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getParaRole() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get the paraRole
        restParaRoleMockMvc.perform(get("/api/para-roles/{id}", paraRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.sortString").value(DEFAULT_SORT_STRING.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.imageBlobContentType").value(DEFAULT_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB)))
            .andExpect(jsonPath("$.imageBlobName").value(DEFAULT_IMAGE_BLOB_NAME.toString()))
            .andExpect(jsonPath("$.usingFlag").value(DEFAULT_USING_FLAG.booleanValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllParaRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where name equals to DEFAULT_NAME
        defaultParaRoleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraRoleList where name equals to UPDATED_NAME
        defaultParaRoleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaRolesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaRoleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraRoleList where name equals to UPDATED_NAME
        defaultParaRoleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaRolesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where name is not null
        defaultParaRoleShouldBeFound("name.specified=true");

        // Get all the paraRoleList where name is null
        defaultParaRoleShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaRoleShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraRoleList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaRoleShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaRolesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaRoleShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraRoleList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaRoleShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaRolesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where serialNumber is not null
        defaultParaRoleShouldBeFound("serialNumber.specified=true");

        // Get all the paraRoleList where serialNumber is null
        defaultParaRoleShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where sortString equals to DEFAULT_SORT_STRING
        defaultParaRoleShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraRoleList where sortString equals to UPDATED_SORT_STRING
        defaultParaRoleShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaRolesBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaRoleShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraRoleList where sortString equals to UPDATED_SORT_STRING
        defaultParaRoleShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaRolesBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where sortString is not null
        defaultParaRoleShouldBeFound("sortString.specified=true");

        // Get all the paraRoleList where sortString is null
        defaultParaRoleShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where descString equals to DEFAULT_DESC_STRING
        defaultParaRoleShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraRoleList where descString equals to UPDATED_DESC_STRING
        defaultParaRoleShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaRolesByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaRoleShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraRoleList where descString equals to UPDATED_DESC_STRING
        defaultParaRoleShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaRolesByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where descString is not null
        defaultParaRoleShouldBeFound("descString.specified=true");

        // Get all the paraRoleList where descString is null
        defaultParaRoleShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaRoleShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraRoleList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaRoleShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaRolesByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaRoleShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraRoleList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaRoleShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaRolesByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where imageBlobName is not null
        defaultParaRoleShouldBeFound("imageBlobName.specified=true");

        // Get all the paraRoleList where imageBlobName is null
        defaultParaRoleShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaRoleShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraRoleList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaRoleShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaRolesByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaRoleShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraRoleList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaRoleShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaRolesByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where usingFlag is not null
        defaultParaRoleShouldBeFound("usingFlag.specified=true");

        // Get all the paraRoleList where usingFlag is null
        defaultParaRoleShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where remarks equals to DEFAULT_REMARKS
        defaultParaRoleShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraRoleList where remarks equals to UPDATED_REMARKS
        defaultParaRoleShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaRolesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaRoleShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraRoleList where remarks equals to UPDATED_REMARKS
        defaultParaRoleShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaRolesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        // Get all the paraRoleList where remarks is not null
        defaultParaRoleShouldBeFound("remarks.specified=true");

        // Get all the paraRoleList where remarks is null
        defaultParaRoleShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaRolesByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraRole.setCreatedUser(createdUser);
        paraRoleRepository.saveAndFlush(paraRole);
        Long createdUserId = createdUser.getId();

        // Get all the paraRoleList where createdUser equals to createdUserId
        defaultParaRoleShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraRoleList where createdUser equals to createdUserId + 1
        defaultParaRoleShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaRolesByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraRole.setModifiedUser(modifiedUser);
        paraRoleRepository.saveAndFlush(paraRole);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraRoleList where modifiedUser equals to modifiedUserId
        defaultParaRoleShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraRoleList where modifiedUser equals to modifiedUserId + 1
        defaultParaRoleShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaRolesByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraRole.setVerifiedUser(verifiedUser);
        paraRoleRepository.saveAndFlush(paraRole);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraRoleList where verifiedUser equals to verifiedUserId
        defaultParaRoleShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraRoleList where verifiedUser equals to verifiedUserId + 1
        defaultParaRoleShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaRolesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaRole parent = ParaRoleResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraRole.setParent(parent);
        paraRoleRepository.saveAndFlush(paraRole);
        Long parentId = parent.getId();

        // Get all the paraRoleList where parent equals to parentId
        defaultParaRoleShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraRoleList where parent equals to parentId + 1
        defaultParaRoleShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllParaRolesByParaNodeIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaNode paraNode = ParaNodeResourceIntTest.createEntity(em);
        em.persist(paraNode);
        em.flush();
        paraRole.addParaNode(paraNode);
        paraRoleRepository.saveAndFlush(paraRole);
        Long paraNodeId = paraNode.getId();

        // Get all the paraRoleList where paraNode equals to paraNodeId
        defaultParaRoleShouldBeFound("paraNodeId.equals=" + paraNodeId);

        // Get all the paraRoleList where paraNode equals to paraNodeId + 1
        defaultParaRoleShouldNotBeFound("paraNodeId.equals=" + (paraNodeId + 1));
    }


    @Test
    @Transactional
    public void getAllParaRolesByParaUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser paraUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(paraUser);
        em.flush();
        paraRole.addParaUser(paraUser);
        paraRoleRepository.saveAndFlush(paraRole);
        Long paraUserId = paraUser.getId();

        // Get all the paraRoleList where paraUser equals to paraUserId
        defaultParaRoleShouldBeFound("paraUserId.equals=" + paraUserId);

        // Get all the paraRoleList where paraUser equals to paraUserId + 1
        defaultParaRoleShouldNotBeFound("paraUserId.equals=" + (paraUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaRoleShouldBeFound(String filter) throws Exception {
        restParaRoleMockMvc.perform(get("/api/para-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));

        // Check, that the count call also returns 1
        restParaRoleMockMvc.perform(get("/api/para-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaRoleShouldNotBeFound(String filter) throws Exception {
        restParaRoleMockMvc.perform(get("/api/para-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaRoleMockMvc.perform(get("/api/para-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaRole() throws Exception {
        // Get the paraRole
        restParaRoleMockMvc.perform(get("/api/para-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaRole() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        int databaseSizeBeforeUpdate = paraRoleRepository.findAll().size();

        // Update the paraRole
        ParaRole updatedParaRole = paraRoleRepository.findById(paraRole.getId()).get();
        // Disconnect from session so that the updates on updatedParaRole are not directly saved in db
        em.detach(updatedParaRole);
        updatedParaRole
            .name(UPDATED_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .sortString(UPDATED_SORT_STRING)
            .descString(UPDATED_DESC_STRING)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(UPDATED_IMAGE_BLOB_NAME)
            .usingFlag(UPDATED_USING_FLAG)
            .remarks(UPDATED_REMARKS);
        ParaRoleDTO paraRoleDTO = paraRoleMapper.toDto(updatedParaRole);

        restParaRoleMockMvc.perform(put("/api/para-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraRoleDTO)))
            .andExpect(status().isOk());

        // Validate the ParaRole in the database
        List<ParaRole> paraRoleList = paraRoleRepository.findAll();
        assertThat(paraRoleList).hasSize(databaseSizeBeforeUpdate);
        ParaRole testParaRole = paraRoleList.get(paraRoleList.size() - 1);
        assertThat(testParaRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaRole.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaRole.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaRole.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaRole.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaRole.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaRole.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaRole.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaRole.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the ParaRole in Elasticsearch
        verify(mockParaRoleSearchRepository, times(1)).save(testParaRole);
    }

    @Test
    @Transactional
    public void updateNonExistingParaRole() throws Exception {
        int databaseSizeBeforeUpdate = paraRoleRepository.findAll().size();

        // Create the ParaRole
        ParaRoleDTO paraRoleDTO = paraRoleMapper.toDto(paraRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaRoleMockMvc.perform(put("/api/para-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaRole in the database
        List<ParaRole> paraRoleList = paraRoleRepository.findAll();
        assertThat(paraRoleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaRole in Elasticsearch
        verify(mockParaRoleSearchRepository, times(0)).save(paraRole);
    }

    @Test
    @Transactional
    public void deleteParaRole() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);

        int databaseSizeBeforeDelete = paraRoleRepository.findAll().size();

        // Delete the paraRole
        restParaRoleMockMvc.perform(delete("/api/para-roles/{id}", paraRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaRole> paraRoleList = paraRoleRepository.findAll();
        assertThat(paraRoleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaRole in Elasticsearch
        verify(mockParaRoleSearchRepository, times(1)).deleteById(paraRole.getId());
    }

    @Test
    @Transactional
    public void searchParaRole() throws Exception {
        // Initialize the database
        paraRoleRepository.saveAndFlush(paraRole);
        when(mockParaRoleSearchRepository.search(queryStringQuery("id:" + paraRole.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraRole), PageRequest.of(0, 1), 1));
        // Search the paraRole
        restParaRoleMockMvc.perform(get("/api/_search/para-roles?query=id:" + paraRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME)))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaRole.class);
        ParaRole paraRole1 = new ParaRole();
        paraRole1.setId(1L);
        ParaRole paraRole2 = new ParaRole();
        paraRole2.setId(paraRole1.getId());
        assertThat(paraRole1).isEqualTo(paraRole2);
        paraRole2.setId(2L);
        assertThat(paraRole1).isNotEqualTo(paraRole2);
        paraRole1.setId(null);
        assertThat(paraRole1).isNotEqualTo(paraRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaRoleDTO.class);
        ParaRoleDTO paraRoleDTO1 = new ParaRoleDTO();
        paraRoleDTO1.setId(1L);
        ParaRoleDTO paraRoleDTO2 = new ParaRoleDTO();
        assertThat(paraRoleDTO1).isNotEqualTo(paraRoleDTO2);
        paraRoleDTO2.setId(paraRoleDTO1.getId());
        assertThat(paraRoleDTO1).isEqualTo(paraRoleDTO2);
        paraRoleDTO2.setId(2L);
        assertThat(paraRoleDTO1).isNotEqualTo(paraRoleDTO2);
        paraRoleDTO1.setId(null);
        assertThat(paraRoleDTO1).isNotEqualTo(paraRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraRoleMapper.fromId(null)).isNull();
    }
}
