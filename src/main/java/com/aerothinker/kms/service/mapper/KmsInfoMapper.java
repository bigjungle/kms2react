package com.aerothinker.kms.service.mapper;

import com.aerothinker.kms.domain.*;
import com.aerothinker.kms.service.dto.KmsInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KmsInfo and its DTO KmsInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {VerifyRecMapper.class, ParaTypeMapper.class, ParaClassMapper.class, ParaCatMapper.class, ParaStateMapper.class, ParaSourceMapper.class, ParaAttrMapper.class, ParaPropMapper.class, ParaUserMapper.class, ParaDepMapper.class, ParaOtherMapper.class})
public interface KmsInfoMapper extends EntityMapper<KmsInfoDTO, KmsInfo> {

    @Mapping(source = "verifyRec.id", target = "verifyRecId")
    @Mapping(source = "verifyRec.name", target = "verifyRecName")
    @Mapping(source = "paraType.id", target = "paraTypeId")
    @Mapping(source = "paraType.name", target = "paraTypeName")
    @Mapping(source = "paraClass.id", target = "paraClassId")
    @Mapping(source = "paraClass.name", target = "paraClassName")
    @Mapping(source = "paraCat.id", target = "paraCatId")
    @Mapping(source = "paraCat.name", target = "paraCatName")
    @Mapping(source = "paraState.id", target = "paraStateId")
    @Mapping(source = "paraState.name", target = "paraStateName")
    @Mapping(source = "paraSource.id", target = "paraSourceId")
    @Mapping(source = "paraSource.name", target = "paraSourceName")
    @Mapping(source = "paraAttr.id", target = "paraAttrId")
    @Mapping(source = "paraAttr.name", target = "paraAttrName")
    @Mapping(source = "paraProp.id", target = "paraPropId")
    @Mapping(source = "paraProp.name", target = "paraPropName")
    @Mapping(source = "createdUser.id", target = "createdUserId")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "createdDepBy.id", target = "createdDepById")
    @Mapping(source = "createdDepBy.name", target = "createdDepByName")
    @Mapping(source = "ownerBy.id", target = "ownerById")
    @Mapping(source = "ownerBy.name", target = "ownerByName")
    @Mapping(source = "ownerDepBy.id", target = "ownerDepById")
    @Mapping(source = "ownerDepBy.name", target = "ownerDepByName")
    @Mapping(source = "modifiedUser.id", target = "modifiedUserId")
    @Mapping(source = "modifiedUser.name", target = "modifiedUserName")
    @Mapping(source = "modifiedUserDep.id", target = "modifiedUserDepId")
    @Mapping(source = "modifiedUserDep.name", target = "modifiedUserDepName")
    @Mapping(source = "verifiedUser.id", target = "verifiedUserId")
    @Mapping(source = "verifiedUser.name", target = "verifiedUserName")
    @Mapping(source = "verifiedDepBy.id", target = "verifiedDepById")
    @Mapping(source = "verifiedDepBy.name", target = "verifiedDepByName")
    @Mapping(source = "publishedBy.id", target = "publishedById")
    @Mapping(source = "publishedBy.name", target = "publishedByName")
    @Mapping(source = "publishedDepBy.id", target = "publishedDepById")
    @Mapping(source = "publishedDepBy.name", target = "publishedDepByName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    KmsInfoDTO toDto(KmsInfo kmsInfo);

    @Mapping(source = "verifyRecId", target = "verifyRec")
    @Mapping(source = "paraTypeId", target = "paraType")
    @Mapping(source = "paraClassId", target = "paraClass")
    @Mapping(source = "paraCatId", target = "paraCat")
    @Mapping(source = "paraStateId", target = "paraState")
    @Mapping(source = "paraSourceId", target = "paraSource")
    @Mapping(source = "paraAttrId", target = "paraAttr")
    @Mapping(source = "paraPropId", target = "paraProp")
    @Mapping(source = "createdUserId", target = "createdUser")
    @Mapping(source = "createdDepById", target = "createdDepBy")
    @Mapping(source = "ownerById", target = "ownerBy")
    @Mapping(source = "ownerDepById", target = "ownerDepBy")
    @Mapping(source = "modifiedUserId", target = "modifiedUser")
    @Mapping(source = "modifiedUserDepId", target = "modifiedUserDep")
    @Mapping(source = "verifiedUserId", target = "verifiedUser")
    @Mapping(source = "verifiedDepById", target = "verifiedDepBy")
    @Mapping(source = "publishedById", target = "publishedBy")
    @Mapping(source = "publishedDepById", target = "publishedDepBy")
    @Mapping(source = "parentId", target = "parent")
    KmsInfo toEntity(KmsInfoDTO kmsInfoDTO);

    default KmsInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        KmsInfo kmsInfo = new KmsInfo();
        kmsInfo.setId(id);
        return kmsInfo;
    }
}
