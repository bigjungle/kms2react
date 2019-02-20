package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaClass and its DTO ParaClassDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaClassMapper extends EntityMapper<ParaClassDTO, ParaClass> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaClassDTO toDto(ParaClass paraClass);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaClass toEntity(ParaClassDTO paraClassDTO);

    default ParaClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaClass paraClass = new ParaClass();
        paraClass.setId(id);
        return paraClass;
    }
}
