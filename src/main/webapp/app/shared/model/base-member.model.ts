import { Moment } from 'moment';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IBaseMember {
  id?: number;
  createdDate?: string;
  lastModifiedDate?: string;
  role?: Role;
  memberId?: number;
  baseId?: number;
}

export const defaultValue: Readonly<IBaseMember> = {};
