import { useStateElements } from "../../hooks/useStateElements";
import { MaterialResponse } from "../../types/materialResponse";

export type CorrectPlanConfig = {
  material: MaterialResponse | undefined;
  year: string;
  month: string;
  flow: string;
  isDeleted: boolean;
};

const initialPlan: CorrectPlanConfig = {
  material: undefined,
  year: String(new Date().getFullYear()),
  month: String(new Date().getMonth() + 2),
  flow: "",
  isDeleted: false,
};

export const useCorrectPlan = (initial?: CorrectPlanConfig) => {
  const { data: plan, setData, setState: setPlan } = useStateElements<CorrectPlanConfig>(initial ?? initialPlan);
  const reset = () => setData(initial ?? initialPlan);
  return { plan, setPlan, reset };
};