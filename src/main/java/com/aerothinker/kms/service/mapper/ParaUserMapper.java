package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaUser and its DTO ParaUserDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaRoleMapper.class})
public interface ParaUserMapper extends EntityMapper<ParaUserDTO, ParaUser> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    ParaUserDTO toDto(ParaUser paraUser);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    ParaUser toEntity(ParaUserDTO paraUserDTO);

    default ParaUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaUser paraUser = new ParaUser();
        paraUser.setId(id);
        return paraUser;
    }
}
