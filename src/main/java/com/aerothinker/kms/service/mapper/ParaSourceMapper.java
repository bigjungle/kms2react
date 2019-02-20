package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaSourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaSource and its DTO ParaSourceDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaSourceMapper extends EntityMapper<ParaSourceDTO, ParaSource> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaSourceDTO toDto(ParaSource paraSource);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaSource toEntity(ParaSourceDTO paraSourceDTO);

    default ParaSource fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaSource paraSource = new ParaSource();
        paraSource.setId(id);
        return paraSource;
    }
}
