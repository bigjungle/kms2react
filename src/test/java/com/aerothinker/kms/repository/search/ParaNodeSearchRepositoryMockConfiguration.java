package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ParaNodeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParaNodeSearchRepositoryMockConfiguration {

    @MockBean
    private ParaNodeSearchRepository mockParaNodeSearchRepository;

}
