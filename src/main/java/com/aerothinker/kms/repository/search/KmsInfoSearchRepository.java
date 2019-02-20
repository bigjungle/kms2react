package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.KmsInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the KmsInfo entity.
 */
public interface KmsInfoSearchRepository extends ElasticsearchRepository<KmsInfo, Long> {
}
