import { Moment } from 'moment';
import { IChat } from 'app/shared/model/chat.model';
import { IBaseMember } from 'app/shared/model/base-member.model';

export interface IBase {
  id?: number;
  createdAt?: string;
  updatedAt?: string;
  name?: string;
  score?: number;
  imageUrl?: string;
  isActive?: boolean;
  chats?: IChat[];
  members?: IBaseMember[];
}

export const defaultValue: Readonly<IBase> = {
  isActive: false,
};
