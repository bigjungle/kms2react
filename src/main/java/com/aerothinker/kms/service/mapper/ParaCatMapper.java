package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaCatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaCat and its DTO ParaCatDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaCatMapper extends EntityMapper<ParaCatDTO, ParaCat> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ParaCatDTO toDto(ParaCat paraCat);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "parentId", target = "parent")
    ParaCat toEntity(ParaCatDTO paraCatDTO);

    default ParaCat fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaCat paraCat = new ParaCat();
        paraCat.setId(id);
        return paraCat;
    }
}
