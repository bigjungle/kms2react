package com.aerothinker.kms.web.rest;

import com.aerothinker.kms.KmsApp;

import com.aerothinker.kms.domain.ParaNode;
import com.aerothinker.kms.domain.ParaUser;
import com.aerothinker.kms.domain.ParaNode;
import com.aerothinker.kms.domain.ParaRole;
import com.aerothinker.kms.repository.ParaNodeRepository;
import com.aerothinker.kms.repository.search.ParaNodeSearchRepository;
import com.aerothinker.kms.service.ParaNodeService;
import com.aerothinker.kms.service.dto.ParaNodeDTO;
import com.aerothinker.kms.service.mapper.ParaNodeMapper;
import com.aerothinker.kms.web.rest.errors.ExceptionTranslator;
import com.aerothinker.kms.service.dto.ParaNodeCriteria;
import com.aerothinker.kms.service.ParaNodeQueryService;

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
 * Test class for the ParaNodeResource REST controller.
 *
 * @see ParaNodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmsApp.class)
public class ParaNodeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

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
    private ParaNodeRepository paraNodeRepository;

    @Autowired
    private ParaNodeMapper paraNodeMapper;

    @Autowired
    private ParaNodeService paraNodeService;

    /**
     * This repository is mocked in the com.aerothinker.kms.repository.search test package.
     *
     * @see com.aerothinker.kms.repository.search.ParaNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParaNodeSearchRepository mockParaNodeSearchRepository;

    @Autowired
    private ParaNodeQueryService paraNodeQueryService;

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

    private MockMvc restParaNodeMockMvc;

    private ParaNode paraNode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParaNodeResource paraNodeResource = new ParaNodeResource(paraNodeService, paraNodeQueryService);
        this.restParaNodeMockMvc = MockMvcBuilders.standaloneSetup(paraNodeResource)
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
    public static ParaNode createEntity(EntityManager em) {
        ParaNode paraNode = new ParaNode()
            .name(DEFAULT_NAME)
            .link(DEFAULT_LINK)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .sortString(DEFAULT_SORT_STRING)
            .descString(DEFAULT_DESC_STRING)
            .imageBlob(DEFAULT_IMAGE_BLOB)
            .imageBlobContentType(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(DEFAULT_IMAGE_BLOB_NAME)
            .usingFlag(DEFAULT_USING_FLAG)
            .remarks(DEFAULT_REMARKS);
        return paraNode;
    }

    @Before
    public void initTest() {
        paraNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createParaNode() throws Exception {
        int databaseSizeBeforeCreate = paraNodeRepository.findAll().size();

        // Create the ParaNode
        ParaNodeDTO paraNodeDTO = paraNodeMapper.toDto(paraNode);
        restParaNodeMockMvc.perform(post("/api/para-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraNodeDTO)))
            .andExpect(status().isCreated());

        // Validate the ParaNode in the database
        List<ParaNode> paraNodeList = paraNodeRepository.findAll();
        assertThat(paraNodeList).hasSize(databaseSizeBeforeCreate + 1);
        ParaNode testParaNode = paraNodeList.get(paraNodeList.size() - 1);
        assertThat(testParaNode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParaNode.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testParaNode.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testParaNode.getSortString()).isEqualTo(DEFAULT_SORT_STRING);
        assertThat(testParaNode.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testParaNode.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testParaNode.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaNode.getImageBlobName()).isEqualTo(DEFAULT_IMAGE_BLOB_NAME);
        assertThat(testParaNode.isUsingFlag()).isEqualTo(DEFAULT_USING_FLAG);
        assertThat(testParaNode.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the ParaNode in Elasticsearch
        verify(mockParaNodeSearchRepository, times(1)).save(testParaNode);
    }

    @Test
    @Transactional
    public void createParaNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paraNodeRepository.findAll().size();

        // Create the ParaNode with an existing ID
        paraNode.setId(1L);
        ParaNodeDTO paraNodeDTO = paraNodeMapper.toDto(paraNode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaNodeMockMvc.perform(post("/api/para-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraNodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaNode in the database
        List<ParaNode> paraNodeList = paraNodeRepository.findAll();
        assertThat(paraNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParaNode in Elasticsearch
        verify(mockParaNodeSearchRepository, times(0)).save(paraNode);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraNodeRepository.findAll().size();
        // set the field null
        paraNode.setName(null);

        // Create the ParaNode, which fails.
        ParaNodeDTO paraNodeDTO = paraNodeMapper.toDto(paraNode);

        restParaNodeMockMvc.perform(post("/api/para-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraNodeDTO)))
            .andExpect(status().isBadRequest());

        List<ParaNode> paraNodeList = paraNodeRepository.findAll();
        assertThat(paraNodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParaNodes() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList
        restParaNodeMockMvc.perform(get("/api/para-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getParaNode() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get the paraNode
        restParaNodeMockMvc.perform(get("/api/para-nodes/{id}", paraNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paraNode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
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
    public void getAllParaNodesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where name equals to DEFAULT_NAME
        defaultParaNodeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paraNodeList where name equals to UPDATED_NAME
        defaultParaNodeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaNodesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParaNodeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paraNodeList where name equals to UPDATED_NAME
        defaultParaNodeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllParaNodesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where name is not null
        defaultParaNodeShouldBeFound("name.specified=true");

        // Get all the paraNodeList where name is null
        defaultParaNodeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where link equals to DEFAULT_LINK
        defaultParaNodeShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the paraNodeList where link equals to UPDATED_LINK
        defaultParaNodeShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllParaNodesByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where link in DEFAULT_LINK or UPDATED_LINK
        defaultParaNodeShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the paraNodeList where link equals to UPDATED_LINK
        defaultParaNodeShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllParaNodesByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where link is not null
        defaultParaNodeShouldBeFound("link.specified=true");

        // Get all the paraNodeList where link is null
        defaultParaNodeShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultParaNodeShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the paraNodeList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaNodeShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaNodesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultParaNodeShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the paraNodeList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultParaNodeShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllParaNodesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where serialNumber is not null
        defaultParaNodeShouldBeFound("serialNumber.specified=true");

        // Get all the paraNodeList where serialNumber is null
        defaultParaNodeShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesBySortStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where sortString equals to DEFAULT_SORT_STRING
        defaultParaNodeShouldBeFound("sortString.equals=" + DEFAULT_SORT_STRING);

        // Get all the paraNodeList where sortString equals to UPDATED_SORT_STRING
        defaultParaNodeShouldNotBeFound("sortString.equals=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaNodesBySortStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where sortString in DEFAULT_SORT_STRING or UPDATED_SORT_STRING
        defaultParaNodeShouldBeFound("sortString.in=" + DEFAULT_SORT_STRING + "," + UPDATED_SORT_STRING);

        // Get all the paraNodeList where sortString equals to UPDATED_SORT_STRING
        defaultParaNodeShouldNotBeFound("sortString.in=" + UPDATED_SORT_STRING);
    }

    @Test
    @Transactional
    public void getAllParaNodesBySortStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where sortString is not null
        defaultParaNodeShouldBeFound("sortString.specified=true");

        // Get all the paraNodeList where sortString is null
        defaultParaNodeShouldNotBeFound("sortString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where descString equals to DEFAULT_DESC_STRING
        defaultParaNodeShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the paraNodeList where descString equals to UPDATED_DESC_STRING
        defaultParaNodeShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaNodesByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultParaNodeShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the paraNodeList where descString equals to UPDATED_DESC_STRING
        defaultParaNodeShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllParaNodesByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where descString is not null
        defaultParaNodeShouldBeFound("descString.specified=true");

        // Get all the paraNodeList where descString is null
        defaultParaNodeShouldNotBeFound("descString.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesByImageBlobNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where imageBlobName equals to DEFAULT_IMAGE_BLOB_NAME
        defaultParaNodeShouldBeFound("imageBlobName.equals=" + DEFAULT_IMAGE_BLOB_NAME);

        // Get all the paraNodeList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaNodeShouldNotBeFound("imageBlobName.equals=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaNodesByImageBlobNameIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where imageBlobName in DEFAULT_IMAGE_BLOB_NAME or UPDATED_IMAGE_BLOB_NAME
        defaultParaNodeShouldBeFound("imageBlobName.in=" + DEFAULT_IMAGE_BLOB_NAME + "," + UPDATED_IMAGE_BLOB_NAME);

        // Get all the paraNodeList where imageBlobName equals to UPDATED_IMAGE_BLOB_NAME
        defaultParaNodeShouldNotBeFound("imageBlobName.in=" + UPDATED_IMAGE_BLOB_NAME);
    }

    @Test
    @Transactional
    public void getAllParaNodesByImageBlobNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where imageBlobName is not null
        defaultParaNodeShouldBeFound("imageBlobName.specified=true");

        // Get all the paraNodeList where imageBlobName is null
        defaultParaNodeShouldNotBeFound("imageBlobName.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesByUsingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where usingFlag equals to DEFAULT_USING_FLAG
        defaultParaNodeShouldBeFound("usingFlag.equals=" + DEFAULT_USING_FLAG);

        // Get all the paraNodeList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaNodeShouldNotBeFound("usingFlag.equals=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaNodesByUsingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where usingFlag in DEFAULT_USING_FLAG or UPDATED_USING_FLAG
        defaultParaNodeShouldBeFound("usingFlag.in=" + DEFAULT_USING_FLAG + "," + UPDATED_USING_FLAG);

        // Get all the paraNodeList where usingFlag equals to UPDATED_USING_FLAG
        defaultParaNodeShouldNotBeFound("usingFlag.in=" + UPDATED_USING_FLAG);
    }

    @Test
    @Transactional
    public void getAllParaNodesByUsingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where usingFlag is not null
        defaultParaNodeShouldBeFound("usingFlag.specified=true");

        // Get all the paraNodeList where usingFlag is null
        defaultParaNodeShouldNotBeFound("usingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where remarks equals to DEFAULT_REMARKS
        defaultParaNodeShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the paraNodeList where remarks equals to UPDATED_REMARKS
        defaultParaNodeShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaNodesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultParaNodeShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the paraNodeList where remarks equals to UPDATED_REMARKS
        defaultParaNodeShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllParaNodesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        // Get all the paraNodeList where remarks is not null
        defaultParaNodeShouldBeFound("remarks.specified=true");

        // Get all the paraNodeList where remarks is null
        defaultParaNodeShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllParaNodesByCreatedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser createdUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(createdUser);
        em.flush();
        paraNode.setCreatedUser(createdUser);
        paraNodeRepository.saveAndFlush(paraNode);
        Long createdUserId = createdUser.getId();

        // Get all the paraNodeList where createdUser equals to createdUserId
        defaultParaNodeShouldBeFound("createdUserId.equals=" + createdUserId);

        // Get all the paraNodeList where createdUser equals to createdUserId + 1
        defaultParaNodeShouldNotBeFound("createdUserId.equals=" + (createdUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaNodesByModifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser modifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(modifiedUser);
        em.flush();
        paraNode.setModifiedUser(modifiedUser);
        paraNodeRepository.saveAndFlush(paraNode);
        Long modifiedUserId = modifiedUser.getId();

        // Get all the paraNodeList where modifiedUser equals to modifiedUserId
        defaultParaNodeShouldBeFound("modifiedUserId.equals=" + modifiedUserId);

        // Get all the paraNodeList where modifiedUser equals to modifiedUserId + 1
        defaultParaNodeShouldNotBeFound("modifiedUserId.equals=" + (modifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaNodesByVerifiedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaUser verifiedUser = ParaUserResourceIntTest.createEntity(em);
        em.persist(verifiedUser);
        em.flush();
        paraNode.setVerifiedUser(verifiedUser);
        paraNodeRepository.saveAndFlush(paraNode);
        Long verifiedUserId = verifiedUser.getId();

        // Get all the paraNodeList where verifiedUser equals to verifiedUserId
        defaultParaNodeShouldBeFound("verifiedUserId.equals=" + verifiedUserId);

        // Get all the paraNodeList where verifiedUser equals to verifiedUserId + 1
        defaultParaNodeShouldNotBeFound("verifiedUserId.equals=" + (verifiedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllParaNodesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaNode parent = ParaNodeResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        paraNode.setParent(parent);
        paraNodeRepository.saveAndFlush(paraNode);
        Long parentId = parent.getId();

        // Get all the paraNodeList where parent equals to parentId
        defaultParaNodeShouldBeFound("parentId.equals=" + parentId);

        // Get all the paraNodeList where parent equals to parentId + 1
        defaultParaNodeShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllParaNodesByParaRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ParaRole paraRole = ParaRoleResourceIntTest.createEntity(em);
        em.persist(paraRole);
        em.flush();
        paraNode.addParaRole(paraRole);
        paraNodeRepository.saveAndFlush(paraNode);
        Long paraRoleId = paraRole.getId();

        // Get all the paraNodeList where paraRole equals to paraRoleId
        defaultParaNodeShouldBeFound("paraRoleId.equals=" + paraRoleId);

        // Get all the paraNodeList where paraRole equals to paraRoleId + 1
        defaultParaNodeShouldNotBeFound("paraRoleId.equals=" + (paraRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParaNodeShouldBeFound(String filter) throws Exception {
        restParaNodeMockMvc.perform(get("/api/para-nodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sortString").value(hasItem(DEFAULT_SORT_STRING.toString())))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING.toString())))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].imageBlobName").value(hasItem(DEFAULT_IMAGE_BLOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].usingFlag").value(hasItem(DEFAULT_USING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));

        // Check, that the count call also returns 1
        restParaNodeMockMvc.perform(get("/api/para-nodes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParaNodeShouldNotBeFound(String filter) throws Exception {
        restParaNodeMockMvc.perform(get("/api/para-nodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParaNodeMockMvc.perform(get("/api/para-nodes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParaNode() throws Exception {
        // Get the paraNode
        restParaNodeMockMvc.perform(get("/api/para-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParaNode() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        int databaseSizeBeforeUpdate = paraNodeRepository.findAll().size();

        // Update the paraNode
        ParaNode updatedParaNode = paraNodeRepository.findById(paraNode.getId()).get();
        // Disconnect from session so that the updates on updatedParaNode are not directly saved in db
        em.detach(updatedParaNode);
        updatedParaNode
            .name(UPDATED_NAME)
            .link(UPDATED_LINK)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .sortString(UPDATED_SORT_STRING)
            .descString(UPDATED_DESC_STRING)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .imageBlobName(UPDATED_IMAGE_BLOB_NAME)
            .usingFlag(UPDATED_USING_FLAG)
            .remarks(UPDATED_REMARKS);
        ParaNodeDTO paraNodeDTO = paraNodeMapper.toDto(updatedParaNode);

        restParaNodeMockMvc.perform(put("/api/para-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraNodeDTO)))
            .andExpect(status().isOk());

        // Validate the ParaNode in the database
        List<ParaNode> paraNodeList = paraNodeRepository.findAll();
        assertThat(paraNodeList).hasSize(databaseSizeBeforeUpdate);
        ParaNode testParaNode = paraNodeList.get(paraNodeList.size() - 1);
        assertThat(testParaNode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParaNode.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testParaNode.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testParaNode.getSortString()).isEqualTo(UPDATED_SORT_STRING);
        assertThat(testParaNode.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testParaNode.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testParaNode.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testParaNode.getImageBlobName()).isEqualTo(UPDATED_IMAGE_BLOB_NAME);
        assertThat(testParaNode.isUsingFlag()).isEqualTo(UPDATED_USING_FLAG);
        assertThat(testParaNode.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the ParaNode in Elasticsearch
        verify(mockParaNodeSearchRepository, times(1)).save(testParaNode);
    }

    @Test
    @Transactional
    public void updateNonExistingParaNode() throws Exception {
        int databaseSizeBeforeUpdate = paraNodeRepository.findAll().size();

        // Create the ParaNode
        ParaNodeDTO paraNodeDTO = paraNodeMapper.toDto(paraNode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaNodeMockMvc.perform(put("/api/para-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paraNodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParaNode in the database
        List<ParaNode> paraNodeList = paraNodeRepository.findAll();
        assertThat(paraNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParaNode in Elasticsearch
        verify(mockParaNodeSearchRepository, times(0)).save(paraNode);
    }

    @Test
    @Transactional
    public void deleteParaNode() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);

        int databaseSizeBeforeDelete = paraNodeRepository.findAll().size();

        // Delete the paraNode
        restParaNodeMockMvc.perform(delete("/api/para-nodes/{id}", paraNode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParaNode> paraNodeList = paraNodeRepository.findAll();
        assertThat(paraNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParaNode in Elasticsearch
        verify(mockParaNodeSearchRepository, times(1)).deleteById(paraNode.getId());
    }

    @Test
    @Transactional
    public void searchParaNode() throws Exception {
        // Initialize the database
        paraNodeRepository.saveAndFlush(paraNode);
        when(mockParaNodeSearchRepository.search(queryStringQuery("id:" + paraNode.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paraNode), PageRequest.of(0, 1), 1));
        // Search the paraNode
        restParaNodeMockMvc.perform(get("/api/_search/para-nodes?query=id:" + paraNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paraNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
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
        TestUtil.equalsVerifier(ParaNode.class);
        ParaNode paraNode1 = new ParaNode();
        paraNode1.setId(1L);
        ParaNode paraNode2 = new ParaNode();
        paraNode2.setId(paraNode1.getId());
        assertThat(paraNode1).isEqualTo(paraNode2);
        paraNode2.setId(2L);
        assertThat(paraNode1).isNotEqualTo(paraNode2);
        paraNode1.setId(null);
        assertThat(paraNode1).isNotEqualTo(paraNode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParaNodeDTO.class);
        ParaNodeDTO paraNodeDTO1 = new ParaNodeDTO();
        paraNodeDTO1.setId(1L);
        ParaNodeDTO paraNodeDTO2 = new ParaNodeDTO();
        assertThat(paraNodeDTO1).isNotEqualTo(paraNodeDTO2);
        paraNodeDTO2.setId(paraNodeDTO1.getId());
        assertThat(paraNodeDTO1).isEqualTo(paraNodeDTO2);
        paraNodeDTO2.setId(2L);
        assertThat(paraNodeDTO1).isNotEqualTo(paraNodeDTO2);
        paraNodeDTO1.setId(null);
        assertThat(paraNodeDTO1).isNotEqualTo(paraNodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paraNodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paraNodeMapper.fromId(null)).isNull();
    }
}
