import { IParaRoleSdmSuffix } from 'app/shared/model/para-role-sdm-suffix.model';

export interface IParaNodeSdmSuffix {
  id?: number;
  name?: string;
  link?: string;
  serialNumber?: string;
  sortString?: string;
  descString?: string;
  imageBlobContentType?: string;
  imageBlob?: any;
  imageBlobName?: string;
  usingFlag?: boolean;
  remarks?: string;
  createdUserName?: string;
  createdUserId?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
  verifiedUserName?: string;
  verifiedUserId?: number;
  parentName?: string;
  parentId?: number;
  paraRoles?: IParaRoleSdmSuffix[];
}

export const defaultValue: Readonly<IParaNodeSdmSuffix> = {
  usingFlag: false
};
