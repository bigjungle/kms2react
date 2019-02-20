package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of KmsInfoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class KmsInfoSearchRepositoryMockConfiguration {

    @MockBean
    private KmsInfoSearchRepository mockKmsInfoSearchRepository;

}
