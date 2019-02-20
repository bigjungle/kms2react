package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaSource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaSourceRepository extends JpaRepository<ParaSource, Long>, JpaSpecificationExecutor<ParaSource> {

}
