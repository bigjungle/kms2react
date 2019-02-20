package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaState;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaState entity.
 */
public interface ParaStateSearchRepository extends ElasticsearchRepository<ParaState, Long> {
}
