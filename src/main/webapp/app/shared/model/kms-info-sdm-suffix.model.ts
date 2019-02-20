import { Moment } from 'moment';
import { IParaOtherSdmSuffix } from 'app/shared/model/para-other-sdm-suffix.model';

export const enum ValidType {
  LONG = 'LONG',
  SCOPE = 'SCOPE'
}

export const enum ViewPermission {
  PRIVATEVIEW = 'PRIVATEVIEW',
  DEFAULTVIEW = 'DEFAULTVIEW',
  PROTECTVIEW = 'PROTECTVIEW',
  PUBLICVIEW = 'PUBLICVIEW',
  CUSTOMVIEW = 'CUSTOMVIEW'
}

export interface IKmsInfoSdmSuffix {
  id?: number;
  name?: string;
  serialNumber?: string;
  sortString?: string;
  descString?: string;
  answer?: string;
  usingFlag?: boolean;
  versionNumber?: string;
  remarks?: string;
  attachmentPath?: string;
  attachmentBlobContentType?: string;
  attachmentBlob?: any;
  attachmentName?: string;
  textBlob?: any;
  imageBlobContentType?: string;
  imageBlob?: any;
  imageBlobName?: string;
  validType?: ValidType;
  validBegin?: Moment;
  validEnd?: Moment;
  createTime?: Moment;
  modifyTime?: Moment;
  verifyTime?: Moment;
  publishedTime?: Moment;
  verifyNeed?: boolean;
  markAsVerified?: boolean;
  verifyResult?: boolean;
  verifyOpinion?: string;
  viewCount?: number;
  viewPermission?: ViewPermission;
  viewPermPerson?: string;
  paraSourceString?: string;
  verifyRecName?: string;
  verifyRecId?: number;
  paraTypeName?: string;
  paraTypeId?: number;
  paraClassName?: string;
  paraClassId?: number;
  paraCatName?: string;
  paraCatId?: number;
  paraStateName?: string;
  paraStateId?: number;
  paraSourceName?: string;
  paraSourceId?: number;
  paraAttrName?: string;
  paraAttrId?: number;
  paraPropName?: string;
  paraPropId?: number;
  createdUserName?: string;
  createdUserId?: number;
  createdDepByName?: string;
  createdDepById?: number;
  ownerByName?: string;
  ownerById?: number;
  ownerDepByName?: string;
  ownerDepById?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
  modifiedUserDepName?: string;
  modifiedUserDepId?: number;
  verifiedUserName?: string;
  verifiedUserId?: number;
  verifiedDepByName?: string;
  verifiedDepById?: number;
  publishedByName?: string;
  publishedById?: number;
  publishedDepByName?: string;
  publishedDepById?: number;
  parentName?: string;
  parentId?: number;
  paraOthers?: IParaOtherSdmSuffix[];
}

export const defaultValue: Readonly<IKmsInfoSdmSuffix> = {
  usingFlag: false,
  verifyNeed: false,
  markAsVerified: false,
  verifyResult: false
};
