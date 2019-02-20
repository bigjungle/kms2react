package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaRole;
import com.aerothinker.kms.repository.ParaUserRepository;
import com.aerothinker.kms.repository.search.ParaUserSearchRepository;
import com.aerothinker.kms.service.ParaUserService;
import com.aerothinker.kms.service.dto.ParaUserDTO;
import com.aerothinker.kms.service.mapper.ParaUserMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaUserCriteria;
import com.aerothinker.kms.service.ParaUserQueryService;

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
 * Test class for the ParaUserResource REST controller.
 *
 * @see ParaUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaUserResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_ID = "AAAAAAAAAA";
    private static final String UPDATED_JOB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USING_FLAG = false;
    private static final Boolean UPDATED_USING_FLAG = true;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private ParaUserRepository paraUserRepository;

    @Mock
    private ParaUserRepository paraUserRepositoryMock;

    @Autowired
    private ParaUserMapper paraUserMapper;

    @Mock
    private ParaUserService paraUserServiceMock;

    @Autowired
    private ParaUserService paraUserService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaUserSearchRepository mockParaUserSearchRepository;

    @Autowired
    private ParaUserQueryService paraUserQueryService;

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

    private MockMvc restParaUserMockMvc;

    private ParaUser paraUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaUserResource paraUserResource = new ParaUserResource(paraUserService, paraUserQueryService);
        this.restParaUserMockMvc = MockMvcBuilders.standaloneSetup(paraUserResource)
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
    public static ParaUser createEntity(EntityManager em) {
        ParaUser paraUser = new ParaUser()
            .userId(DEFAULT_USER_ID)
            .name(DEFAULT_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .descString(DEFAULT_DESC_STRING)
            .jobId(DEFAULT_JOB_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .mobile(DEFAULT_MOBILE)
            .mail(DEFAULT_MAIL)
            .usingFlag(DEFAULT_USING_FLAG)
            .remarks(DEFAULT_REMARKS);
        return paraUser;
    }

    @Before
    public void initTest() {
        paraUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaUser() throws Exception {
        int databaseSizeBeforeCreate = paraUserRepository.findAll().size();

        // Create the ParaUser
        ParaUserDTO paraUserDTO = paraUserMapper.toDto(paraUser);
        restParaUserMockMvc.perform(post("/api/para-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaUser in the database
        List<ParaUser> paraUserList = paraUserRepository.findAll();
        assertThat(paraUserList).hasSize(databaseSizeBeforeCreate + 1);
        ParaUser testParaUser = paraUserList.get(paraUserList.size() - 1);
        assertThat(testParaUser.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testParaUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaUser.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaUser.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaUser.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testParaUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testParaUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testParaUser.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testParaUser.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testParaUser.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaUser.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the ParaUser in Elasticsearch
        verify(mockParaUserSearchRepository, times(1)).save(testParaUser);
    }

    @Test
    @Transactional
    public void createParaUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraUserRepository.findAll().size();

        // Create the ParaUser with an existing ID
        paraUser.setId(1L);
        ParaUserDTO paraUserDTO = paraUserMapper.toDto(paraUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaUserMockMvc.perform(post("/api/para-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaUser in the database
        List<ParaUser> paraUserList = paraUserRepository.findAll();
        assertThat(paraUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaUser in Elasticsearch
        verify(mockParaUserSearchRepository, times(0)).save(paraUser);
    }

    @Test
    @Transactional
    public void getAllParaUsers() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList
        restParaUserMockMvc.perform(get("/api/para-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllParaUsersWithEagerRelationshipsIsEnabled() throws Exception {
        ParaUserResource paraUserResource = new ParaUserResource(paraUserServiceMock, paraUserQueryService);
        when(paraUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restParaUserMockMvc = MockMvcBuilders.standaloneSetup(paraUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restParaUserMockMvc.perform(get("/api/para-users?eagerload=true"))
        .andExpect(status().isOk());

        verify(paraUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllParaUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        ParaUserResource paraUserResource = new ParaUserResource(paraUserServiceMock, paraUserQueryService);
            when(paraUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restParaUserMockMvc = MockMvcBuilders.standaloneSetup(paraUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restParaUserMockMvc.perform(get("/api/para-users?eagerload=true"))
        .andExpect(status().isOk());

            verify(paraUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getParaUser() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get the paraUser
        restParaUserMockMvc.perform(get("/api/para-users/{id}", paraUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.usingFlag").value(DEFAULT_USING_FLAG.booleanValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllParaUsersByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where userId equals to DEFAULT_USER_ID
        defaultParaUserShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the paraUserList where userId equals to UPDATED_USER_ID
        defaultParaUserShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllParaUsersByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultParaUserShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the paraUserList where userId equals to UPDATED_USER_ID
        defaultParaUserShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllParaUsersByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where userId is not null
        defaultParaUserShouldBeFound("userId.specified=true");

        // Get all the paraUserList where userId is null
        defaultParaUserShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where name equals to DEFAULT_NAME
        defaultParaUserShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraUserList where name equals to UPDATED_NAME
        defaultParaUserShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaUsersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaUserShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraUserList where name equals to UPDATED_NAME
        defaultParaUserShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaUsersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where name is not null
        defaultParaUserShouldBeFound("name.specified=true");

        // Get all the paraUserList where name is null
        defaultParaUserShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaUserShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraUserList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaUserShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaUsersBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaUserShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraUserList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaUserShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaUsersBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where serialNumber is not null
        defaultParaUserShouldBeFound("serialNumber.specified=true");

        // Get all the paraUserList where serialNumber is null
        defaultParaUserShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where descString equals to DEFAULT_DESC_STRING
        defaultParaUserShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraUserList where descString equals to UPDATED_DESC_STRING
        defaultParaUserShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaUsersByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaUserShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraUserList where descString equals to UPDATED_DESC_STRING
        defaultParaUserShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaUsersByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where descString is not null
        defaultParaUserShouldBeFound("descString.specified=true");

        // Get all the paraUserList where descString is null
        defaultParaUserShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByJobIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where jobId equals to DEFAULT_JOB_ID
        defaultParaUserShouldBeFound("jobId.equals=" + DEFAULT_JOB_ID);

        // Get all the paraUserList where jobId equals to UPDATED_JOB_ID
        defaultParaUserShouldNotBeFound("jobId.equals=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    public void getAllParaUsersByJobIdIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where jobId in DEFAULT_JOB_ID or UPDATED_JOB_ID
        defaultParaUserShouldBeFound("jobId.in=" + DEFAULT_JOB_ID + "," + UPDATED_JOB_ID);

        // Get all the paraUserList where jobId equals to UPDATED_JOB_ID
        defaultParaUserShouldNotBeFound("jobId.in=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    public void getAllParaUsersByJobIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where jobId is not null
        defaultParaUserShouldBeFound("jobId.specified=true");

        // Get all the paraUserList where jobId is null
        defaultParaUserShouldNotBeFound("jobId.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where firstName equals to DEFAULT_FIRST_NAME
        defaultParaUserShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the paraUserList where firstName equals to UPDATED_FIRST_NAME
        defaultParaUserShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllParaUsersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultParaUserShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the paraUserList where firstName equals to UPDATED_FIRST_NAME
        defaultParaUserShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllParaUsersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where firstName is not null
        defaultParaUserShouldBeFound("firstName.specified=true");

        // Get all the paraUserList where firstName is null
        defaultParaUserShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where lastName equals to DEFAULT_LAST_NAME
        defaultParaUserShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the paraUserList where lastName equals to UPDATED_LAST_NAME
        defaultParaUserShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllParaUsersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultParaUserShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the paraUserList where lastName equals to UPDATED_LAST_NAME
        defaultParaUserShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllParaUsersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where lastName is not null
        defaultParaUserShouldBeFound("lastName.specified=true");

        // Get all the paraUserList where lastName is null
        defaultParaUserShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where mobile equals to DEFAULT_MOBILE
        defaultParaUserShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the paraUserList where mobile equals to UPDATED_MOBILE
        defaultParaUserShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllParaUsersByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultParaUserShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the paraUserList where mobile equals to UPDATED_MOBILE
        defaultParaUserShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllParaUsersByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where mobile is not null
        defaultParaUserShouldBeFound("mobile.specified=true");

        // Get all the paraUserList where mobile is null
        defaultParaUserShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where mail equals to DEFAULT_MAIL
        defaultParaUserShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the paraUserList where mail equals to UPDATED_MAIL
        defaultParaUserShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllParaUsersByMailIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultParaUserShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the paraUserList where mail equals to UPDATED_MAIL
        defaultParaUserShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllParaUsersByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where mail is not null
        defaultParaUserShouldBeFound("mail.specified=true");

        // Get all the paraUserList where mail is null
        defaultParaUserShouldNotBeFound("mail.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaUserShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraUserList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaUserShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaUsersByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaUserShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraUserList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaUserShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaUsersByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where usingFlag is not null
        defaultParaUserShouldBeFound("usingFlag.specified=true");

        // Get all the paraUserList where usingFlag is null
        defaultParaUserShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where remarks equals to DEFAULT_REMARKS
        defaultParaUserShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraUserList where remarks equals to UPDATED_REMARKS
        defaultParaUserShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaUsersByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaUserShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraUserList where remarks equals to UPDATED_REMARKS
        defaultParaUserShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaUsersByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        // Get all the paraUserList where remarks is not null
        defaultParaUserShouldBeFound("remarks.specified=true");

        // Get all the paraUserList where remarks is null
        defaultParaUserShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaUsersByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraUser.setCreatedUser(createdUser);
        paraUserRepository.saveAndFlush(paraUser);
        Long createdUserId = createdUser.getId();

        // Get all the paraUserList where createdUser equals to createdUserId
        defaultParaUserShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraUserList where createdUser equals to createdUserId + 1
        defaultParaUserShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaUsersByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraUser.setModifiedUser(modifiedUser);
        paraUserRepository.saveAndFlush(paraUser);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraUserList where modifiedUser equals to modifiedUserId
        defaultParaUserShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraUserList where modifiedUser equals to modifiedUserId + 1
        defaultParaUserShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaUsersByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraUser.setVerifiedUser(verifiedUser);
        paraUserRepository.saveAndFlush(paraUser);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraUserList where verifiedUser equals to verifiedUserId
        defaultParaUserShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraUserList where verifiedUser equals to verifiedUserId + 1
        defaultParaUserShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaUsersByParaRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaRole paraRole = ParaRoleResourceIntTest.createEntity(em);
        em.persist(paraRole);
        em.flush();
        paraUser.addParaRole(paraRole);
        paraUserRepository.saveAndFlush(paraUser);
        Long paraRoleId = paraRole.getId();

        // Get all the paraUserList where paraRole equals to paraRoleId
        defaultParaUserShouldBeFound("paraRoleId.equals=" + paraRoleId);

        // Get all the paraUserList where paraRole equals to paraRoleId + 1
        defaultParaUserShouldNotBeFound("paraRoleId.equals=" + (paraRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaUserShouldBeFound(String filter) throws Exception {
        restParaUserMockMvc.perform(get("/api/para-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));

        // Check, that the count call also returns 1
        restParaUserMockMvc.perform(get("/api/para-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaUserShouldNotBeFound(String filter) throws Exception {
        restParaUserMockMvc.perform(get("/api/para-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaUserMockMvc.perform(get("/api/para-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaUser() throws Exception {
        // Get the paraUser
        restParaUserMockMvc.perform(get("/api/para-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaUser() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        int databaseSizeBeforeUpdate = paraUserRepository.findAll().size();

        // Update the paraUser
        ParaUser updatedParaUser = paraUserRepository.findById(paraUser.getId()).get();
        // Disconnect from session so that the updates on updatedParaUser are not directly saved in db
        em.detach(updatedParaUser);
        updatedParaUser
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .descString(UPDATED_DESC_STRING)
            .jobId(UPDATED_JOB_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mobile(UPDATED_MOBILE)
            .mail(UPDATED_MAIL)
            .usingFlag(UPDATED_USING_FLAG)
            .remarks(UPDATED_REMARKS);
        ParaUserDTO paraUserDTO = paraUserMapper.toDto(updatedParaUser);

        restParaUserMockMvc.perform(put("/api/para-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraUserDTO)))
            .andExpect(status().isOk());

        // Validate the ParaUser in the database
        List<ParaUser> paraUserList = paraUserRepository.findAll();
        assertThat(paraUserList).hasSize(databaseSizeBeforeUpdate);
        ParaUser testParaUser = paraUserList.get(paraUserList.size() - 1);
        assertThat(testParaUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testParaUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaUser.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaUser.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaUser.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testParaUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testParaUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testParaUser.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testParaUser.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testParaUser.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaUser.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the ParaUser in Elasticsearch
        verify(mockParaUserSearchRepository, times(1)).save(testParaUser);
    }

    @Test
    @Transactional
    public void updateNonExistingParaUser() throws Exception {
        int databaseSizeBeforeUpdate = paraUserRepository.findAll().size();

        // Create the ParaUser
        ParaUserDTO paraUserDTO = paraUserMapper.toDto(paraUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaUserMockMvc.perform(put("/api/para-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaUser in the database
        List<ParaUser> paraUserList = paraUserRepository.findAll();
        assertThat(paraUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaUser in Elasticsearch
        verify(mockParaUserSearchRepository, times(0)).save(paraUser);
    }

    @Test
    @Transactional
    public void deleteParaUser() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);

        int databaseSizeBeforeDelete = paraUserRepository.findAll().size();

        // Delete the paraUser
        restParaUserMockMvc.perform(delete("/api/para-users/{id}", paraUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaUser> paraUserList = paraUserRepository.findAll();
        assertThat(paraUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaUser in Elasticsearch
        verify(mockParaUserSearchRepository, times(1)).deleteById(paraUser.getId());
    }

    @Test
    @Transactional
    public void searchParaUser() throws Exception {
        // Initialize the database
        paraUserRepository.saveAndFlush(paraUser);
        when(mockParaUserSearchRepository.search(queryStringQuery("id:" + paraUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraUser), PageRequest.of(0, 1), 1));
        // Search the paraUser
        restParaUserMockMvc.perform(get("/api/_search/para-users?query=id:" + paraUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaUser.class);
        ParaUser paraUser1 = new ParaUser();
        paraUser1.setId(1L);
        ParaUser paraUser2 = new ParaUser();
        paraUser2.setId(paraUser1.getId());
        assertThat(paraUser1).isEqualTo(paraUser2);
        paraUser2.setId(2L);
        assertThat(paraUser1).isNotEqualTo(paraUser2);
        paraUser1.setId(null);
        assertThat(paraUser1).isNotEqualTo(paraUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaUserDTO.class);
        ParaUserDTO paraUserDTO1 = new ParaUserDTO();
        paraUserDTO1.setId(1L);
        ParaUserDTO paraUserDTO2 = new ParaUserDTO();
        assertThat(paraUserDTO1).isNotEqualTo(paraUserDTO2);
        paraUserDTO2.setId(paraUserDTO1.getId());
        assertThat(paraUserDTO1).isEqualTo(paraUserDTO2);
        paraUserDTO2.setId(2L);
        assertThat(paraUserDTO1).isNotEqualTo(paraUserDTO2);
        paraUserDTO1.setId(null);
        assertThat(paraUserDTO1).isNotEqualTo(paraUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraUserMapper.fromId(null)).isNull();
    }
}
