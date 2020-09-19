import { Moment } from 'moment';

export interface IChat {
  id?: number;
  createdDate?: string;
  lastModifiedDate?: string;
  chatpiChatId?: string;
  baseId?: number;
}

export const defaultValue: Readonly<IChat> = {};
