import { Moment } from 'moment';

export interface IChat {
  id?: number;
  createdAt?: string;
  updatedAt?: string;
  chatpiChatId?: string;
  baseId?: number;
}

export const defaultValue: Readonly<IChat> = {};
