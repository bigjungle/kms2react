package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaDep;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaDep;
import com.aerothinker.kms.repository.ParaDepRepository;
import com.aerothinker.kms.repository.search.ParaDepSearchRepository;
import com.aerothinker.kms.service.ParaDepService;
import com.aerothinker.kms.service.dto.ParaDepDTO;
import com.aerothinker.kms.service.mapper.ParaDepMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaDepCriteria;
import com.aerothinker.kms.service.ParaDepQueryService;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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
 * Test class for the ParaDepResource REST controller.
 *
 * @see ParaDepResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaDepResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private ParaDepRepository paraDepRepository;

    @Autowired
    private ParaDepMapper paraDepMapper;

    @Autowired
    private ParaDepService paraDepService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaDepSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaDepSearchRepository mockParaDepSearchRepository;

    @Autowired
    private ParaDepQueryService paraDepQueryService;

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

    private MockMvc restParaDepMockMvc;

    private ParaDep paraDep;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaDepResource paraDepResource = new ParaDepResource(paraDepService, paraDepQueryService);
        this.restParaDepMockMvc = MockMvcBuilders.standaloneSetup(paraDepResource)
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
    public static ParaDep createEntity(EntityManager em) {
        ParaDep paraDep = new ParaDep()
            .name(DEFAULT_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .descString(DEFAULT_DESC_STRING)
            .tel(DEFAULT_TEL)
            .address(DEFAULT_ADDRESS)
            .remarks(DEFAULT_REMARKS);
        return paraDep;
    }

    @Before
    public void initTest() {
        paraDep = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaDep() throws Exception {
        int databaseSizeBeforeCreate = paraDepRepository.findAll().size();

        // Create the ParaDep
        ParaDepDTO paraDepDTO = paraDepMapper.toDto(paraDep);
        restParaDepMockMvc.perform(post("/api/para-deps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraDepDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaDep in the database
        List<ParaDep> paraDepList = paraDepRepository.findAll();
        assertThat(paraDepList).hasSize(databaseSizeBeforeCreate + 1);
        ParaDep testParaDep = paraDepList.get(paraDepList.size() - 1);
        assertThat(testParaDep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaDep.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaDep.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaDep.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testParaDep.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testParaDep.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the ParaDep in Elasticsearch
        verify(mockParaDepSearchRepository, times(1)).save(testParaDep);
    }

    @Test
    @Transactional
    public void createParaDepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraDepRepository.findAll().size();

        // Create the ParaDep with an existing ID
        paraDep.setId(1L);
        ParaDepDTO paraDepDTO = paraDepMapper.toDto(paraDep);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaDepMockMvc.perform(post("/api/para-deps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraDepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaDep in the database
        List<ParaDep> paraDepList = paraDepRepository.findAll();
        assertThat(paraDepList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaDep in Elasticsearch
        verify(mockParaDepSearchRepository, times(0)).save(paraDep);
    }

    @Test
    @Transactional
    public void getAllParaDeps() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList
        restParaDepMockMvc.perform(get("/api/para-deps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraDep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getParaDep() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get the paraDep
        restParaDepMockMvc.perform(get("/api/para-deps/{id}", paraDep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraDep.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllParaDepsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where name equals to DEFAULT_NAME
        defaultParaDepShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraDepList where name equals to UPDATED_NAME
        defaultParaDepShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaDepsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaDepShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraDepList where name equals to UPDATED_NAME
        defaultParaDepShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaDepsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where name is not null
        defaultParaDepShouldBeFound("name.specified=true");

        // Get all the paraDepList where name is null
        defaultParaDepShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaDepsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaDepShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraDepList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaDepShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaDepsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaDepShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraDepList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaDepShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaDepsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where serialNumber is not null
        defaultParaDepShouldBeFound("serialNumber.specified=true");

        // Get all the paraDepList where serialNumber is null
        defaultParaDepShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaDepsByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where descString equals to DEFAULT_DESC_STRING
        defaultParaDepShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraDepList where descString equals to UPDATED_DESC_STRING
        defaultParaDepShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaDepsByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaDepShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraDepList where descString equals to UPDATED_DESC_STRING
        defaultParaDepShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaDepsByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where descString is not null
        defaultParaDepShouldBeFound("descString.specified=true");

        // Get all the paraDepList where descString is null
        defaultParaDepShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaDepsByTelIsEqualToSomething() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where tel equals to DEFAULT_TEL
        defaultParaDepShouldBeFound("tel.equals=" + DEFAULT_TEL);

        // Get all the paraDepList where tel equals to UPDATED_TEL
        defaultParaDepShouldNotBeFound("tel.equals=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllParaDepsByTelIsInShouldWork() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where tel in DEFAULT_TEL or UPDATED_TEL
        defaultParaDepShouldBeFound("tel.in=" + DEFAULT_TEL + "," + UPDATED_TEL);

        // Get all the paraDepList where tel equals to UPDATED_TEL
        defaultParaDepShouldNotBeFound("tel.in=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllParaDepsByTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where tel is not null
        defaultParaDepShouldBeFound("tel.specified=true");

        // Get all the paraDepList where tel is null
        defaultParaDepShouldNotBeFound("tel.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaDepsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where address equals to DEFAULT_ADDRESS
        defaultParaDepShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the paraDepList where address equals to UPDATED_ADDRESS
        defaultParaDepShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllParaDepsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultParaDepShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the paraDepList where address equals to UPDATED_ADDRESS
        defaultParaDepShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllParaDepsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where address is not null
        defaultParaDepShouldBeFound("address.specified=true");

        // Get all the paraDepList where address is null
        defaultParaDepShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaDepsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where remarks equals to DEFAULT_REMARKS
        defaultParaDepShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraDepList where remarks equals to UPDATED_REMARKS
        defaultParaDepShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaDepsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaDepShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraDepList where remarks equals to UPDATED_REMARKS
        defaultParaDepShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaDepsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        // Get all the paraDepList where remarks is not null
        defaultParaDepShouldBeFound("remarks.specified=true");

        // Get all the paraDepList where remarks is null
        defaultParaDepShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaDepsByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraDep.setCreatedUser(createdUser);
        paraDepRepository.saveAndFlush(paraDep);
        Long createdUserId = createdUser.getId();

        // Get all the paraDepList where createdUser equals to createdUserId
        defaultParaDepShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraDepList where createdUser equals to createdUserId + 1
        defaultParaDepShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaDepsByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraDep.setModifiedUser(modifiedUser);
        paraDepRepository.saveAndFlush(paraDep);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraDepList where modifiedUser equals to modifiedUserId
        defaultParaDepShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraDepList where modifiedUser equals to modifiedUserId + 1
        defaultParaDepShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaDepsByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraDep.setVerifiedUser(verifiedUser);
        paraDepRepository.saveAndFlush(paraDep);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraDepList where verifiedUser equals to verifiedUserId
        defaultParaDepShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraDepList where verifiedUser equals to verifiedUserId + 1
        defaultParaDepShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaDepsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaDep parent = ParaDepResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraDep.setParent(parent);
        paraDepRepository.saveAndFlush(paraDep);
        Long parentId = parent.getId();

        // Get all the paraDepList where parent equals to parentId
        defaultParaDepShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraDepList where parent equals to parentId + 1
        defaultParaDepShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaDepShouldBeFound(String filter) throws Exception {
        restParaDepMockMvc.perform(get("/api/para-deps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraDep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));

        // Check, that the count call also returns 1
        restParaDepMockMvc.perform(get("/api/para-deps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaDepShouldNotBeFound(String filter) throws Exception {
        restParaDepMockMvc.perform(get("/api/para-deps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaDepMockMvc.perform(get("/api/para-deps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaDep() throws Exception {
        // Get the paraDep
        restParaDepMockMvc.perform(get("/api/para-deps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaDep() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        int databaseSizeBeforeUpdate = paraDepRepository.findAll().size();

        // Update the paraDep
        ParaDep updatedParaDep = paraDepRepository.findById(paraDep.getId()).get();
        // Disconnect from session so that the updates on updatedParaDep are not directly saved in db
        em.detach(updatedParaDep);
        updatedParaDep
            .name(UPDATED_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .descString(UPDATED_DESC_STRING)
            .tel(UPDATED_TEL)
            .address(UPDATED_ADDRESS)
            .remarks(UPDATED_REMARKS);
        ParaDepDTO paraDepDTO = paraDepMapper.toDto(updatedParaDep);

        restParaDepMockMvc.perform(put("/api/para-deps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraDepDTO)))
            .andExpect(status().isOk());

        // Validate the ParaDep in the database
        List<ParaDep> paraDepList = paraDepRepository.findAll();
        assertThat(paraDepList).hasSize(databaseSizeBeforeUpdate);
        ParaDep testParaDep = paraDepList.get(paraDepList.size() - 1);
        assertThat(testParaDep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaDep.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaDep.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaDep.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testParaDep.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testParaDep.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the ParaDep in Elasticsearch
        verify(mockParaDepSearchRepository, times(1)).save(testParaDep);
    }

    @Test
    @Transactional
    public void updateNonExistingParaDep() throws Exception {
        int databaseSizeBeforeUpdate = paraDepRepository.findAll().size();

        // Create the ParaDep
        ParaDepDTO paraDepDTO = paraDepMapper.toDto(paraDep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaDepMockMvc.perform(put("/api/para-deps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraDepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaDep in the database
        List<ParaDep> paraDepList = paraDepRepository.findAll();
        assertThat(paraDepList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaDep in Elasticsearch
        verify(mockParaDepSearchRepository, times(0)).save(paraDep);
    }

    @Test
    @Transactional
    public void deleteParaDep() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);

        int databaseSizeBeforeDelete = paraDepRepository.findAll().size();

        // Delete the paraDep
        restParaDepMockMvc.perform(delete("/api/para-deps/{id}", paraDep.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaDep> paraDepList = paraDepRepository.findAll();
        assertThat(paraDepList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaDep in Elasticsearch
        verify(mockParaDepSearchRepository, times(1)).deleteById(paraDep.getId());
    }

    @Test
    @Transactional
    public void searchParaDep() throws Exception {
        // Initialize the database
        paraDepRepository.saveAndFlush(paraDep);
        when(mockParaDepSearchRepository.search(queryStringQuery("id:" + paraDep.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraDep), PageRequest.of(0, 1), 1));
        // Search the paraDep
        restParaDepMockMvc.perform(get("/api/_search/para-deps?query=id:" + paraDep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraDep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaDep.class);
        ParaDep paraDep1 = new ParaDep();
        paraDep1.setId(1L);
        ParaDep paraDep2 = new ParaDep();
        paraDep2.setId(paraDep1.getId());
        assertThat(paraDep1).isEqualTo(paraDep2);
        paraDep2.setId(2L);
        assertThat(paraDep1).isNotEqualTo(paraDep2);
        paraDep1.setId(null);
        assertThat(paraDep1).isNotEqualTo(paraDep2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaDepDTO.class);
        ParaDepDTO paraDepDTO1 = new ParaDepDTO();
        paraDepDTO1.setId(1L);
        ParaDepDTO paraDepDTO2 = new ParaDepDTO();
        assertThat(paraDepDTO1).isNotEqualTo(paraDepDTO2);
        paraDepDTO2.setId(paraDepDTO1.getId());
        assertThat(paraDepDTO1).isEqualTo(paraDepDTO2);
        paraDepDTO2.setId(2L);
        assertThat(paraDepDTO1).isNotEqualTo(paraDepDTO2);
        paraDepDTO1.setId(null);
        assertThat(paraDepDTO1).isNotEqualTo(paraDepDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraDepMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraDepMapper.fromId(null)).isNull();
    }
}
