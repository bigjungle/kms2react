package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaAttrService;
import com.aerothinker.kms.domain.ParaAttr;
import com.aerothinker.kms.repository.ParaAttrRepository;
import com.aerothinker.kms.repository.search.ParaAttrSearchRepository;
import com.aerothinker.kms.service.dto.ParaAttrDTO;
import com.aerothinker.kms.service.mapper.ParaAttrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaAttr.
 */
@Service
@Transactional
public class ParaAttrServiceImpl implements ParaAttrService {

    private final Logger log = LoggerFactory.getLogger(ParaAttrServiceImpl.class);

    private final ParaAttrRepository paraAttrRepository;

    private final ParaAttrMapper paraAttrMapper;

    private final ParaAttrSearchRepository paraAttrSearchRepository;

    public ParaAttrServiceImpl(ParaAttrRepository paraAttrRepository, ParaAttrMapper paraAttrMapper, ParaAttrSearchRepository paraAttrSearchRepository) {
        this.paraAttrRepository = paraAttrRepository;
        this.paraAttrMapper = paraAttrMapper;
        this.paraAttrSearchRepository = paraAttrSearchRepository;
    }

    /**
     * Save a paraAttr.
     *
     * @param paraAttrDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaAttrDTO save(ParaAttrDTO paraAttrDTO) {
        log.debug("Request to save ParaAttr : {}", paraAttrDTO);
        ParaAttr paraAttr = paraAttrMapper.toEntity(paraAttrDTO);
        paraAttr = paraAttrRepository.save(paraAttr);
        ParaAttrDTO result = paraAttrMapper.toDto(paraAttr);
        paraAttrSearchRepository.save(paraAttr);
        return result;
    }

    /**
     * Get all the paraAttrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaAttrDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaAttrs");
        return paraAttrRepository.findAll(pageable)
            .map(paraAttrMapper::toDto);
    }


    /**
     * Get one paraAttr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaAttrDTO> findOne(Long id) {
        log.debug("Request to get ParaAttr : {}", id);
        return paraAttrRepository.findById(id)
            .map(paraAttrMapper::toDto);
    }

    /**
     * Delete the paraAttr by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaAttr : {}", id);        paraAttrRepository.deleteById(id);
        paraAttrSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraAttr corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaAttrDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaAttrs for query {}", query);
        return paraAttrSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraAttrMapper::toDto);
    }
}
