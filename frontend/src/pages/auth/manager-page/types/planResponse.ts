import { MaterialResponse } from "../../types/materialResponse";

export type PlanResponse = {
  id: number;
  material: MaterialResponse;
  year: string;
  month: string;
  flow: number;
  achievement: number;
  shipping: number;
  adjustment: number;
  remaining: number;
};