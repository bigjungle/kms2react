package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaClassService;
import com.aerothinker.kms.domain.ParaClass;
import com.aerothinker.kms.repository.ParaClassRepository;
import com.aerothinker.kms.repository.search.ParaClassSearchRepository;
import com.aerothinker.kms.service.dto.ParaClassDTO;
import com.aerothinker.kms.service.mapper.ParaClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaClass.
 */
@Service
@Transactional
public class ParaClassServiceImpl implements ParaClassService {

    private final Logger log = LoggerFactory.getLogger(ParaClassServiceImpl.class);

    private final ParaClassRepository paraClassRepository;

    private final ParaClassMapper paraClassMapper;

    private final ParaClassSearchRepository paraClassSearchRepository;

    public ParaClassServiceImpl(ParaClassRepository paraClassRepository, ParaClassMapper paraClassMapper, ParaClassSearchRepository paraClassSearchRepository) {
        this.paraClassRepository = paraClassRepository;
        this.paraClassMapper = paraClassMapper;
        this.paraClassSearchRepository = paraClassSearchRepository;
    }

    /**
     * Save a paraClass.
     *
     * @param paraClassDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaClassDTO save(ParaClassDTO paraClassDTO) {
        log.debug("Request to save ParaClass : {}", paraClassDTO);
        ParaClass paraClass = paraClassMapper.toEntity(paraClassDTO);
        paraClass = paraClassRepository.save(paraClass);
        ParaClassDTO result = paraClassMapper.toDto(paraClass);
        paraClassSearchRepository.save(paraClass);
        return result;
    }

    /**
     * Get all the paraClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaClasses");
        return paraClassRepository.findAll(pageable)
            .map(paraClassMapper::toDto);
    }


    /**
     * Get one paraClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaClassDTO> findOne(Long id) {
        log.debug("Request to get ParaClass : {}", id);
        return paraClassRepository.findById(id)
            .map(paraClassMapper::toDto);
    }

    /**
     * Delete the paraClass by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaClass : {}", id);        paraClassRepository.deleteById(id);
        paraClassSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraClass corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaClassDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaClasses for query {}", query);
        return paraClassSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraClassMapper::toDto);
    }
}
