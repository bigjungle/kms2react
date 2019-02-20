import { IParaRoleSdmSuffix } from 'app/shared/model/para-role-sdm-suffix.model';

export interface IParaUserSdmSuffix {
  id?: number;
  userId?: string;
  name?: string;
  serialNumber?: string;
  descString?: string;
  jobId?: string;
  firstName?: string;
  lastName?: string;
  mobile?: string;
  mail?: string;
  usingFlag?: boolean;
  remarks?: string;
  createdUserName?: string;
  createdUserId?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
  verifiedUserName?: string;
  verifiedUserId?: number;
  paraRoles?: IParaRoleSdmSuffix[];
}

export const defaultValue: Readonly<IParaUserSdmSuffix> = {
  usingFlag: false
};
