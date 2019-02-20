package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaDep;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaDep entity.
 */
public interface ParaDepSearchRepository extends ElasticsearchRepository<ParaDep, Long> {
}
