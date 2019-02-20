package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaOther;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaOther entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaOtherRepository extends JpaRepository<ParaOther, Long>, JpaSpecificationExecutor<ParaOther> {

}
