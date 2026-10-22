import { InspectionCodeConfig } from "../common-page/types/inspectionConfig";

export type AchievementResponse = {
  id: number;
  name: string;
  date: string;
  lot: string;
  flow: string;
  inspection: InspectionCodeConfig;
};