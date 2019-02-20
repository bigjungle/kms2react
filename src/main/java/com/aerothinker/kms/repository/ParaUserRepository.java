package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.ParaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ParaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaUserRepository extends JpaRepository<ParaUser, Long>, JpaSpecificationExecutor<ParaUser> {

    @Query(value = "select distinct para_user from ParaUser para_user left join fetch para_user.paraRoles",
        countQuery = "select count(distinct para_user) from ParaUser para_user")
    Page<ParaUser> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct para_user from ParaUser para_user left join fetch para_user.paraRoles")
    List<ParaUser> findAllWithEagerRelationships();

    @Query("select para_user from ParaUser para_user left join fetch para_user.paraRoles where para_user.id =:id")
    Optional<ParaUser> findOneWithEagerRelationships(@Param("id") Long id);

}
