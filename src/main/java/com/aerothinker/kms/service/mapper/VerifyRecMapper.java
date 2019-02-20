package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.VerifyRecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VerifyRec and its DTO VerifyRecDTO.
 */
@Mapper(componentModel = "spring", uses = {ParaUserMapper.class})
public interface VerifyRecMapper extends EntityMapper<VerifyRecDTO, VerifyRec> {

    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    VerifyRecDTO toDto(VerifyRec verifyRec);

    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    VerifyRec toEntity(VerifyRecDTO verifyRecDTO);

    default VerifyRec fromId(Long id) {
        if (id == null) {
            return null;
        }
        VerifyRec verifyRec = new VerifyRec();
        verifyRec.setId(id);
        return verifyRec;
    }
}
