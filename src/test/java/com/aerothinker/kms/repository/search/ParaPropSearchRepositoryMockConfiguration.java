package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ParaPropSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParaPropSearchRepositoryMockConfiguration {

    @MockBean
    private ParaPropSearchRepository mockParaPropSearchRepository;

}
