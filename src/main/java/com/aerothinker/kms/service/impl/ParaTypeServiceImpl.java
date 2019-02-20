package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaTypeService;
import com.aerothinker.kms.domain.ParaType;
import com.aerothinker.kms.repository.ParaTypeRepository;
import com.aerothinker.kms.repository.search.ParaTypeSearchRepository;
import com.aerothinker.kms.service.dto.ParaTypeDTO;
import com.aerothinker.kms.service.mapper.ParaTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaType.
 */
@Service
@Transactional
public class ParaTypeServiceImpl implements ParaTypeService {

    private final Logger log = LoggerFactory.getLogger(ParaTypeServiceImpl.class);

    private final ParaTypeRepository paraTypeRepository;

    private final ParaTypeMapper paraTypeMapper;

    private final ParaTypeSearchRepository paraTypeSearchRepository;

    public ParaTypeServiceImpl(ParaTypeRepository paraTypeRepository, ParaTypeMapper paraTypeMapper, ParaTypeSearchRepository paraTypeSearchRepository) {
        this.paraTypeRepository = paraTypeRepository;
        this.paraTypeMapper = paraTypeMapper;
        this.paraTypeSearchRepository = paraTypeSearchRepository;
    }

    /**
     * Save a paraType.
     *
     * @param paraTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaTypeDTO save(ParaTypeDTO paraTypeDTO) {
        log.debug("Request to save ParaType : {}", paraTypeDTO);
        ParaType paraType = paraTypeMapper.toEntity(paraTypeDTO);
        paraType = paraTypeRepository.save(paraType);
        ParaTypeDTO result = paraTypeMapper.toDto(paraType);
        paraTypeSearchRepository.save(paraType);
        return result;
    }

    /**
     * Get all the paraTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaTypes");
        return paraTypeRepository.findAll(pageable)
            .map(paraTypeMapper::toDto);
    }


    /**
     * Get one paraType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaTypeDTO> findOne(Long id) {
        log.debug("Request to get ParaType : {}", id);
        return paraTypeRepository.findById(id)
            .map(paraTypeMapper::toDto);
    }

    /**
     * Delete the paraType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaType : {}", id);        paraTypeRepository.deleteById(id);
        paraTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaTypes for query {}", query);
        return paraTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraTypeMapper::toDto);
    }
}
