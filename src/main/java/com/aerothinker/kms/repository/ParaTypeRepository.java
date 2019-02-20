package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaTypeRepository extends JpaRepository<ParaType, Long>, JpaSpecificationExecutor<ParaType> {

}
