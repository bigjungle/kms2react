package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.QueryCommon10;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QueryCommon10 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QueryCommon10Repository extends JpaRepository<QueryCommon10, Long>, JpaSpecificationExecutor<QueryCommon10> {

}
