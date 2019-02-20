import { Moment } from 'moment';

export const enum ValidType {
  LONG = 'LONG',
  SCOPE = 'SCOPE'
}

export interface IParaClassSdmSuffix {
  id?: number;
  name?: string;
  serialNumber?: string;
  sortString?: string;
  descString?: string;
  imageBlobContentType?: string;
  imageBlob?: any;
  imageBlobName?: string;
  usingFlag?: boolean;
  remarks?: string;
  validType?: ValidType;
  validBegin?: Moment;
  validEnd?: Moment;
  createTime?: Moment;
  modifyTime?: Moment;
  verifyTime?: Moment;
  createdUserName?: string;
  createdUserId?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
  verifiedUserName?: string;
  verifiedUserId?: number;
  parentName?: string;
  parentId?: number;
}

export const defaultValue: Readonly<IParaClassSdmSuffix> = {
  usingFlag: false
};
