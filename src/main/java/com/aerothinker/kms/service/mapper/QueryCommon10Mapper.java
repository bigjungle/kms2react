package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.QueryCommon10DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QueryCommon10 and its DTO QueryCommon10DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QueryCommon10Mapper extends EntityMapper<QueryCommon10DTO, QueryCommon10> {



    default QueryCommon10 fromId(Long id) {
        if (id == null) {
            return null;
        }
        QueryCommon10 queryCommon10 = new QueryCommon10();
        queryCommon10.setId(id);
        return queryCommon10;
    }
}
