package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of VerifyRecSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class VerifyRecSearchRepositoryMockConfiguration {

    @MockBean
    private VerifyRecSearchRepository mockVerifyRecSearchRepository;

}
