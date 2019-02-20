package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.KmsInfoService;
import com.aerothinker.kms.domain.KmsInfo;
import com.aerothinker.kms.repository.KmsInfoRepository;
import com.aerothinker.kms.repository.search.KmsInfoSearchRepository;
import com.aerothinker.kms.service.dto.KmsInfoDTO;
import com.aerothinker.kms.service.mapper.KmsInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing KmsInfo.
 */
@Service
@Transactional
public class KmsInfoServiceImpl implements KmsInfoService {

    private final Logger log = LoggerFactory.getLogger(KmsInfoServiceImpl.class);

    private final KmsInfoRepository kmsInfoRepository;

    private final KmsInfoMapper kmsInfoMapper;

    private final KmsInfoSearchRepository kmsInfoSearchRepository;

    public KmsInfoServiceImpl(KmsInfoRepository kmsInfoRepository, KmsInfoMapper kmsInfoMapper, KmsInfoSearchRepository kmsInfoSearchRepository) {
        this.kmsInfoRepository = kmsInfoRepository;
        this.kmsInfoMapper = kmsInfoMapper;
        this.kmsInfoSearchRepository = kmsInfoSearchRepository;
    }

    /**
     * Save a kmsInfo.
     *
     * @param kmsInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KmsInfoDTO save(KmsInfoDTO kmsInfoDTO) {
        log.debug("Request to save KmsInfo : {}", kmsInfoDTO);
        KmsInfo kmsInfo = kmsInfoMapper.toEntity(kmsInfoDTO);
        kmsInfo = kmsInfoRepository.save(kmsInfo);
        KmsInfoDTO result = kmsInfoMapper.toDto(kmsInfo);
        kmsInfoSearchRepository.save(kmsInfo);
        return result;
    }

    /**
     * Get all the kmsInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KmsInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KmsInfos");
        return kmsInfoRepository.findAll(pageable)
            .map(kmsInfoMapper::toDto);
    }

    /**
     * Get all the KmsInfo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<KmsInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return kmsInfoRepository.findAllWithEagerRelationships(pageable).map(kmsInfoMapper::toDto);
    }
    

    /**
     * Get one kmsInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KmsInfoDTO> findOne(Long id) {
        log.debug("Request to get KmsInfo : {}", id);
        return kmsInfoRepository.findOneWithEagerRelationships(id)
            .map(kmsInfoMapper::toDto);
    }

    /**
     * Delete the kmsInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmsInfo : {}", id);        kmsInfoRepository.deleteById(id);
        kmsInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the kmsInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KmsInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of KmsInfos for query {}", query);
        return kmsInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(kmsInfoMapper::toDto);
    }
}
