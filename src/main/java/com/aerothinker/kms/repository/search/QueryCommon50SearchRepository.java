package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.QueryCommon50;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QueryCommon50 entity.
 */
public interface QueryCommon50SearchRepository extends ElasticsearchRepository<QueryCommon50, Long> {
}
