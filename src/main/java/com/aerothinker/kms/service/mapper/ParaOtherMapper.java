package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.ParaOtherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParaOther and its DTO ParaOtherDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface ParaOtherMapper extends EntityMapper<ParaOtherDTO, ParaOther> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    ParaOtherDTO toDto(ParaOther paraOther);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(target = "kmsInfos", ignore = true)
    ParaOther toEntity(ParaOtherDTO paraOtherDTO);

    default ParaOther fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParaOther paraOther = new ParaOther();
        paraOther.setId(id);
        return paraOther;
    }
}
