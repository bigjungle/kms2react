package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.ParaClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParaClass entity.
 */
public interface ParaClassSearchRepository extends ElasticsearchRepository<ParaClass, Long> {
}
