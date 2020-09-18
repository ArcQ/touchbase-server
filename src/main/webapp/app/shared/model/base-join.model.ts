import { BaseJoinAction } from 'app/shared/model/enumerations/base-join-action.model';

export interface IBaseJoin {
  id?: number;
  baseJoin?: BaseJoinAction;
  baseId?: number;
}

export const defaultValue: Readonly<IBaseJoin> = {};
