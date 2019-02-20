package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaAttrDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaAttr and its DTO ParaAttrDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaAttrMapper extends EntityMapper<ParaAttrDTO, ParaAttr> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaAttrDTO toDto(ParaAttr paraAttr);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaAttr toEntity(ParaAttrDTO paraAttrDTO);

    default ParaAttr fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaAttr paraAttr = new ParaAttr();
        paraAttr.setId(id);
        return paraAttr;
    }
}
