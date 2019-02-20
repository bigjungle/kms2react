package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaUser entity.
 */
public interface ParaUserSearchRepository extends ElasticsearchRepository<ParaUser, Long> {
}
