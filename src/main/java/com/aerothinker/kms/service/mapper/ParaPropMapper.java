package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaPropDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaProp and its DTO ParaPropDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaPropMapper extends EntityMapper<ParaPropDTO, ParaProp> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaPropDTO toDto(ParaProp paraProp);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaProp toEntity(ParaPropDTO paraPropDTO);

    default ParaProp fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaProp paraProp = new ParaProp();
        paraProp.setId(id);
        return paraProp;
    }
}
