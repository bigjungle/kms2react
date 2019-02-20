package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of QueryCommon10SearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class QueryCommon10SearchRepositoryMockConfiguration {

    @MockBean
    private QueryCommon10SearchRepository mockQueryCommon10SearchRepository;

}
