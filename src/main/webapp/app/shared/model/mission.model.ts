import { MissionType } from 'app/shared/model/enumerations/mission-type.model';

export interface IMission {
  id?: number;
  name?: string;
  description?: string;
  scoreReward?: string;
  missonType?: MissionType;
}

export const defaultValue: Readonly<IMission> = {};
