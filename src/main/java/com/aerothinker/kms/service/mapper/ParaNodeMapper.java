package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaNodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaNode and its DTO ParaNodeDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaNodeMapper extends EntityMapper<ParaNodeDTO, ParaNode> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaNodeDTO toDto(ParaNode paraNode);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "paraRoles", ignore = true)
    ParaNode toEntity(ParaNodeDTO paraNodeDTO);

    default ParaNode fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaNode paraNode = new ParaNode();
        paraNode.setId(id);
        return paraNode;
    }
}
