import { useState } from "react";
import { SortConfig } from "../../types/sortConfig";
import { MaterialResponse } from "../../types/materialResponse";
import { InitialPlanSort, PlanSortConfig } from "../types/planSortConfig";

export const usePlanSort = (materialArray: MaterialResponse[]): SortConfig<PlanSortConfig> => {
  const [sortData, setSortData] = useState<PlanSortConfig>(InitialPlanSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    const resultValue = name === "material" ? materialArray?.find((item) => item.name === value)?.id: value;
    setSortData((prev) => ({ ...prev, [name as keyof PlanSortConfig]: resultValue }));
  };
  return { sortData, setSortData, setSort };
};