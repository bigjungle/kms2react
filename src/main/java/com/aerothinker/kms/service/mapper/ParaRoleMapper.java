package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaRole and its DTO ParaRoleDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class, ParaNodeMapper.class})
public interface ParaRoleMapper extends EntityMapper<ParaRoleDTO, ParaRole> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaRoleDTO toDto(ParaRole paraRole);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "paraUsers", ignore = true)
    ParaRole toEntity(ParaRoleDTO paraRoleDTO);

    default ParaRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaRole paraRole = new ParaRole();
        paraRole.setId(id);
        return paraRole;
    }
}
