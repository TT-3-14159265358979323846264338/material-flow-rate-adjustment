import { MaterialResponse } from "../../types/materialResponse";

export type PlanResponse = {
  id: number;
  material: MaterialResponse;
  year: string;
  month: string;
  flow: string;
  achievement: string;
  /* いずれ追加するかも
  shipping: string;
  adjustment: string;
  remaining: string;
  */
};