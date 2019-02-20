package com.aerothinker.kms.service.impl;

import com.aerothinker.kms.service.VerifyRecService;
import com.aerothinker.kms.domain.VerifyRec;
import com.aerothinker.kms.repository.VerifyRecRepository;
import com.aerothinker.kms.repository.search.VerifyRecSearchRepository;
import com.aerothinker.kms.service.dto.VerifyRecDTO;
import com.aerothinker.kms.service.mapper.VerifyRecMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VerifyRec.
 */
@Service
@Transactional
public class VerifyRecServiceImpl implements VerifyRecService {

    private final Logger log = LoggerFactory.getLogger(VerifyRecServiceImpl.class);

    private final VerifyRecRepository verifyRecRepository;

    private final VerifyRecMapper verifyRecMapper;

    private final VerifyRecSearchRepository verifyRecSearchRepository;

    public VerifyRecServiceImpl(VerifyRecRepository verifyRecRepository, VerifyRecMapper verifyRecMapper, VerifyRecSearchRepository verifyRecSearchRepository) {
        this.verifyRecRepository = verifyRecRepository;
        this.verifyRecMapper = verifyRecMapper;
        this.verifyRecSearchRepository = verifyRecSearchRepository;
    }

    /**
     * Save a verifyRec.
     *
     * @param verifyRecDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VerifyRecDTO save(VerifyRecDTO verifyRecDTO) {
        log.debug("Request to save VerifyRec : {}", verifyRecDTO);
        VerifyRec verifyRec = verifyRecMapper.toEntity(verifyRecDTO);
        verifyRec = verifyRecRepository.save(verifyRec);
        VerifyRecDTO result = verifyRecMapper.toDto(verifyRec);
        verifyRecSearchRepository.save(verifyRec);
        return result;
    }

    /**
     * Get all the verifyRecs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VerifyRecDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VerifyRecs");
        return verifyRecRepository.findAll(pageable)
            .map(verifyRecMapper::toDto);
    }


    /**
     * Get one verifyRec by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VerifyRecDTO> findOne(Long id) {
        log.debug("Request to get VerifyRec : {}", id);
        return verifyRecRepository.findById(id)
            .map(verifyRecMapper::toDto);
    }

    /**
     * Delete the verifyRec by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VerifyRec : {}", id);        verifyRecRepository.deleteById(id);
        verifyRecSearchRepository.deleteById(id);
    }

    /**
     * Search for the verifyRec corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VerifyRecDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VerifyRecs for query {}", query);
        return verifyRecSearchRepository.search(queryStringQuery(query), pageable)
            .map(verifyRecMapper::toDto);
    }
}
