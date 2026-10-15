import { InspectionCodeConfig } from "../common-page/types/inspectionConfig";

export type AchievementConfig = {
  id: number;
  name: string;
  date: string;
  lot: string;
  flow: number;
  inspection: InspectionCodeConfig;
};