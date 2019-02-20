package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaOther;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaOther entity.
 */
public interface ParaOtherSearchRepository extends ElasticsearchRepository<ParaOther, Long> {
}
