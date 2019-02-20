package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.QueryCommon50;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QueryCommon50 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QueryCommon50Repository extends JpaRepository<QueryCommon50, Long>, JpaSpecificationExecutor<QueryCommon50> {

}
