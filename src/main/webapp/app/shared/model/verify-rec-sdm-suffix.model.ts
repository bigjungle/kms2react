import { Moment } from 'moment';

export interface IVerifyRecSdmSuffix {
  id?: number;
  name?: string;
  descString?: string;
  verifyResult?: boolean;
  verifyScore?: number;
  remarks?: string;
  createTime?: Moment;
  modifyTime?: Moment;
  createdUserName?: string;
  createdUserId?: number;
  modifiedUserName?: string;
  modifiedUserId?: number;
}

export const defaultValue: Readonly<IVerifyRecSdmSuffix> = {
  verifyResult: false
};
