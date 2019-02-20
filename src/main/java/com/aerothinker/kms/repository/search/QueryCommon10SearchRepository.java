package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.QueryCommon10;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QueryCommon10 entity.
 */
public interface QueryCommon10SearchRepository extends ElasticsearchRepository<QueryCommon10, Long> {
}
