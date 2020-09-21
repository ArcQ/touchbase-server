import { Moment } from 'moment';
import { IChat } from 'app/shared/model/chat.model';
import { IBaseMember } from 'app/shared/model/base-member.model';

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
  members?: IBaseMember[];
}

export const defaultValue: Readonly<IBase> = {
  isActive: false,
};
