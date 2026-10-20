import { MaterialResponse } from "../../types/materialResponse";

export type PlanResponse = {
  id: number;
  material: MaterialResponse;
  year: string;
  month: string;
  flow: string;
  achievement: string;
  shipping: string;
  adjustment: string;
  remaining: string;
};