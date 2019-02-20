package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ParaRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaRoleRepository extends JpaRepository<ParaRole, Long>, JpaSpecificationExecutor<ParaRole> {

    @Query(value = "select distinct para_role from ParaRole para_role left join fetch para_role.paraNodes",
        countQuery = "select count(distinct para_role) from ParaRole para_role")
    Page<ParaRole> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct para_role from ParaRole para_role left join fetch para_role.paraNodes")
    List<ParaRole> findAllWithEagerRelationships();

    @Query("select para_role from ParaRole para_role left join fetch para_role.paraNodes where para_role.id =:id")
    Optional<ParaRole> findOneWithEagerRelationships(@Param("id") Long id);

}
