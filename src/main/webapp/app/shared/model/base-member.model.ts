import { Moment } from 'moment';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IBaseMember {
  id?: number;
  createdDate?: string;
  lastModifiedDate?: string;
  role?: Role;
  baseId?: number;
  memberId?: number;
}

export const defaultValue: Readonly<IBaseMember> = {};
