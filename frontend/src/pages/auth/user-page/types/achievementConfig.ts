import { AchievementResponse } from "../../types/achievementConfig";

export type NewAchivementConfig = {
  planId: number;
  unit: string;
  date: string;
  base: string;
};

export type CorrectAchivementConfig = {
  planId: number;
  unit: string;
  achievement: AchievementResponse;
};

export type AchievementConfig = {
  date: string;
  lot: string;
  flow: string;
  inspection: string;
  isDeleted: boolean;
};