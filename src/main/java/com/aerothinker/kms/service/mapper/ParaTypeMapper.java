package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaType and its DTO ParaTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaTypeMapper extends EntityMapper<ParaTypeDTO, ParaType> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaTypeDTO toDto(ParaType paraType);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaType toEntity(ParaTypeDTO paraTypeDTO);

    default ParaType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaType paraType = new ParaType();
        paraType.setId(id);
        return paraType;
    }
}
