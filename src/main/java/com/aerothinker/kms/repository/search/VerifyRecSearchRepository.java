package com.aerothinker.kms.repository.search;

import com.aerothinker.kms.domain.VerifyRec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VerifyRec entity.
 */
public interface VerifyRecSearchRepository extends ElasticsearchRepository<VerifyRec, Long> {
}
