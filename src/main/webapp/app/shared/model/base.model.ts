import { Moment } from 'moment';
import { IChat } from 'app/shared/model/chat.model';

export interface IBase {
  id?: number;
  createdDate?: string;
  lastModifiedDate?: string;
  name?: string;
  score?: number;
  imageUrl?: string;
  isActive?: boolean;
  creatorId?: number;
  chats?: IChat[];
}

export const defaultValue: Readonly<IBase> = {
  isActive: false,
};
