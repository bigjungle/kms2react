package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaNode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParaNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaNodeRepository extends JpaRepository<ParaNode, Long>, JpaSpecificationExecutor<ParaNode> {

}
