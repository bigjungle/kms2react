export interface IParaDepSdmSuffix {
  id?: number;
  name?: string;
  serialNumber?: string;
  descString?: string;
  tel?: string;
  address?: string;
  remarks?: string;
  createdUserName?: string;
  createdUserId?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
  verifiedUserName?: string;
  verifiedUserId?: number;
  parentName?: string;
  parentId?: number;
}

export const defaultValue: Readonly<IParaDepSdmSuffix> = {};
