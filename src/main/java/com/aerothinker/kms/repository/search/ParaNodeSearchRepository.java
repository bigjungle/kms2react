package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaNode entity.
 */
public interface ParaNodeSearchRepository extends ElasticsearchRepository<ParaNode, Long> {
}
