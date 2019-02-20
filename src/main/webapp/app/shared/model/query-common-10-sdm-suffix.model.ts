import { Moment } from 'moment';

export interface IQueryCommon10SdmSuffix {
  id?: number;
  queryName?: string;
  queryDate?: Moment;
  queryUser?: string;
  col01?: string;
  col02?: string;
  col03?: string;
  col04?: string;
  col05?: string;
  col06?: string;
  col07?: string;
  col08?: string;
  col09?: string;
  col10?: string;
}

export const defaultValue: Readonly<IQueryCommon10SdmSuffix> = {};
