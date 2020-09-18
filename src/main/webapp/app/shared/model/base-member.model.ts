import { Moment } from 'moment';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IBaseMember {
  id?: number;
  createdAt?: string;
  updatedAt?: string;
  role?: Role;
  memberId?: number;
  baseId?: number;
}

export const defaultValue: Readonly<IBaseMember> = {};
