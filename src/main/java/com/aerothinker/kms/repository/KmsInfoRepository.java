package com.aerothinker.kms.repository;

import com.aerothinker.kms.domain.KmsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the KmsInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmsInfoRepository extends JpaRepository<KmsInfo, Long>, JpaSpecificationExecutor<KmsInfo> {

    @Query(value = "select distinct kms_info from KmsInfo kms_info left join fetch kms_info.paraOthers",
        countQuery = "select count(distinct kms_info) from KmsInfo kms_info")
    Page<KmsInfo> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct kms_info from KmsInfo kms_info left join fetch kms_info.paraOthers")
    List<KmsInfo> findAllWithEagerRelationships();

    @Query("select kms_info from KmsInfo kms_info left join fetch kms_info.paraOthers where kms_info.id =:id")
    Optional<KmsInfo> findOneWithEagerRelationships(@Param("id") Long id);

}
