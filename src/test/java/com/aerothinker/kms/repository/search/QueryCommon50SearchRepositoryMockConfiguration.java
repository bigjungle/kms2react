package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of QueryCommon50SearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class QueryCommon50SearchRepositoryMockConfiguration {

    @MockBean
    private QueryCommon50SearchRepository mockQueryCommon50SearchRepository;

}
