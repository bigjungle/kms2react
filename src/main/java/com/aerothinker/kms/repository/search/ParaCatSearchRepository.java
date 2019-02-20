package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaCat entity.
 */
public interface ParaCatSearchRepository extends ElasticsearchRepository<ParaCat, Long> {
}
