package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaRole;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaRole entity.
 */
public interface ParaRoleSearchRepository extends ElasticsearchRepository<ParaRole, Long> {
}
