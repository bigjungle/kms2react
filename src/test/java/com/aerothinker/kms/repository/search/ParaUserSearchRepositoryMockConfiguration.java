package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ParaUserSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParaUserSearchRepositoryMockConfiguration {

    @MockBean
    private ParaUserSearchRepository mockParaUserSearchRepository;

}
