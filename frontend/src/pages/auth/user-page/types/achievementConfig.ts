import { AchievementConfig } from "../../types/achievementConfig";

export type NewAchivementConfig = {
  planId: number;
  unit: string;
  date: string;
};

export type CorrectAchivementConfig = {
  planId: number;
  unit: string;
  achievement: AchievementConfig;
};

export type AchivementConfig = {
  date: string;
  lot: string;
  flow: string;
  inspection: string;
};