package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.ParaOtherService;
import com.aerothinker.kms.domain.ParaOther;
import com.aerothinker.kms.repository.ParaOtherRepository;
import com.aerothinker.kms.repository.search.ParaOtherSearchRepository;
import com.aerothinker.kms.service.dto.ParaOtherDTO;
import com.aerothinker.kms.service.mapper.ParaOtherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParaOther.
 */
@Service
@Transactional
public class ParaOtherServiceImpl implements ParaOtherService {

    private final Logger log = LoggerFactory.getLogger(ParaOtherServiceImpl.class);

    private final ParaOtherRepository paraOtherRepository;

    private final ParaOtherMapper paraOtherMapper;

    private final ParaOtherSearchRepository paraOtherSearchRepository;

    public ParaOtherServiceImpl(ParaOtherRepository paraOtherRepository, ParaOtherMapper paraOtherMapper, ParaOtherSearchRepository paraOtherSearchRepository) {
        this.paraOtherRepository = paraOtherRepository;
        this.paraOtherMapper = paraOtherMapper;
        this.paraOtherSearchRepository = paraOtherSearchRepository;
    }

    /**
     * Save a paraOther.
     *
     * @param paraOtherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParaOtherDTO save(ParaOtherDTO paraOtherDTO) {
        log.debug("Request to save ParaOther : {}", paraOtherDTO);
        ParaOther paraOther = paraOtherMapper.toEntity(paraOtherDTO);
        paraOther = paraOtherRepository.save(paraOther);
        ParaOtherDTO result = paraOtherMapper.toDto(paraOther);
        paraOtherSearchRepository.save(paraOther);
        return result;
    }

    /**
     * Get all the paraOthers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaOtherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParaOthers");
        return paraOtherRepository.findAll(pageable)
            .map(paraOtherMapper::toDto);
    }


    /**
     * Get one paraOther by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParaOtherDTO> findOne(Long id) {
        log.debug("Request to get ParaOther : {}", id);
        return paraOtherRepository.findById(id)
            .map(paraOtherMapper::toDto);
    }

    /**
     * Delete the paraOther by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParaOther : {}", id);        paraOtherRepository.deleteById(id);
        paraOtherSearchRepository.deleteById(id);
    }

    /**
     * Search for the paraOther corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParaOtherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParaOthers for query {}", query);
        return paraOtherSearchRepository.search(queryStringQuery(query), pageable)
            .map(paraOtherMapper::toDto);
    }
}
