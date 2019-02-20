package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaSource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaSource entity.
 */
public interface ParaSourceSearchRepository extends ElasticsearchRepository<ParaSource, Long> {
}
