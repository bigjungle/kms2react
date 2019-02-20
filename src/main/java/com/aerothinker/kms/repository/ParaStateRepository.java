package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaState;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaStateRepository extends JpaRepository<ParaState, Long>, JpaSpecificationExecutor<ParaState> {

}
