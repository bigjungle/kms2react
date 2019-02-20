package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaType entity.
 */
public interface ParaTypeSearchRepository extends ElasticsearchRepository<ParaType, Long> {
}
