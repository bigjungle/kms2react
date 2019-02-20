package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaDepDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaDep and its DTO ParaDepDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaDepMapper extends EntityMapper<ParaDepDTO, ParaDep> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaDepDTO toDto(ParaDep paraDep);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaDep toEntity(ParaDepDTO paraDepDTO);

    default ParaDep fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaDep paraDep = new ParaDep();
        paraDep.setId(id);
        return paraDep;
    }
}
