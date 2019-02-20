package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.QueryCommon50DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QueryCommon50 and its DTO QueryCommon50DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QueryCommon50Mapper extends EntityMapper<QueryCommon50DTO, QueryCommon50> {



    default QueryCommon50 fromId(Long id) {
        if (id == null) {
            return null;
        }
        QueryCommon50 queryCommon50 = new QueryCommon50();
        queryCommon50.setId(id);
        return queryCommon50;
    }
}
