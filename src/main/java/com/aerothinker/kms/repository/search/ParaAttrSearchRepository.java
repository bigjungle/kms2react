package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaAttr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaAttr entity.
 */
public interface ParaAttrSearchRepository extends ElasticsearchRepository<ParaAttr, Long> {
}
