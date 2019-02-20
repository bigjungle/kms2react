import { Moment } from 'moment';
import { IKmsInfoSdmSuffix } from 'app/shared/model/kms-info-sdm-suffix.model';

export const enum ParaOtherValueType {
  STRING = 'STRING',
  INSTANT = 'INSTANT',
  BOOLEAN = 'BOOLEAN',
  JSON = 'JSON',
  BLOB = 'BLOB'
}

export const enum ValidType {
  LONG = 'LONG',
  SCOPE = 'SCOPE'
}

export interface IParaOtherSdmSuffix {
  id?: number;
  name?: string;
  serialNumber?: string;
  sortString?: string;
  descString?: string;
  paraOtherValueType?: ParaOtherValueType;
  paraValueSt?: string;
  paraValueIn?: Moment;
  paraValueBo?: boolean;
  paraValueJs?: string;
  paraValueBlContentType?: string;
  paraValueBl?: any;
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
  kmsInfos?: IKmsInfoSdmSuffix[];
}

export const defaultValue: Readonly<IParaOtherSdmSuffix> = {
  paraValueBo: false,
  usingFlag: false
};
