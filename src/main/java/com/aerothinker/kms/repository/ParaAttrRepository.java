package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaAttr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaAttrRepository extends JpaRepository<ParaAttr, Long>, JpaSpecificationExecutor<ParaAttr> {

}
