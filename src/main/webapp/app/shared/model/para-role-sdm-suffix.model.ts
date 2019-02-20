import { IParaNodeSdmSuffix } from 'app/shared/model/para-node-sdm-suffix.model';
import { IParaUserSdmSuffix } from 'app/shared/model/para-user-sdm-suffix.model';

export interface IParaRoleSdmSuffix {
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
  createdUserName?: string;
  createdUserId?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
  verifiedUserName?: string;
  verifiedUserId?: number;
  parentName?: string;
  parentId?: number;
  paraNodes?: IParaNodeSdmSuffix[];
  paraUsers?: IParaUserSdmSuffix[];
}

export const defaultValue: Readonly<IParaRoleSdmSuffix> = {
  usingFlag: false
};
