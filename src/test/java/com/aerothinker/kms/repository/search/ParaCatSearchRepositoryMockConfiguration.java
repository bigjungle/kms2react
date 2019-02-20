package com.aerothinker.kms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ParaCatSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParaCatSearchRepositoryMockConfiguration {

    @MockBean
    private ParaCatSearchRepository mockParaCatSearchRepository;

}
