package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaDep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaDep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaDepRepository extends JpaRepository<ParaDep, Long>, JpaSpecificationExecutor<ParaDep> {

}
