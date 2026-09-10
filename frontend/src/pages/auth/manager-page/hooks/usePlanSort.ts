import { useState } from "react";
import { CommentViewConfig } from "../../types/commentView";
import { DateRangeConfig, SortConfig, SortOrderConfig } from "../../types/sortConfig";
import { MaterialResponse } from "../../types/materialResponse";

export const PLAN_SORT_CODE = [
  { code: "DATE", view: "計画日時" },
  { code: "MATERIAL", view: "製品名" },
  { code: "UPDATE_DATE", view: "更新日" },
] as const satisfies readonly CommentViewConfig[];

export type PlanSortConfig = DateRangeConfig &
  SortOrderConfig<typeof PLAN_SORT_CODE> & 
  {
    material: number | undefined ;
  };

const defaultMinTerm = () => {
  const date = new Date();
  date.setMonth(date.getMonth() - 6);
  return {
    minYear: String(date.getFullYear()),
    minMonth: String(date.getMonth() + 1),
  };
};

export const InitialPlanSort: PlanSortConfig = {
  minYear: defaultMinTerm().minYear,
  minMonth: defaultMinTerm().minMonth,
  maxYear: "",
  maxMonth: "",
  material: undefined,
  order: "DESCENDING",
  target: "DATE",
};

export const usePlanSort = (materialArray: MaterialResponse[]): SortConfig<PlanSortConfig> => {
  const [sortData, setSortData] = useState<PlanSortConfig>(InitialPlanSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    const resultValue = name === "material" ? materialArray?.find((item) => item.name === value)?.id: value;
    setSortData((prev) => ({ ...prev, [name as keyof PlanSortConfig]: resultValue }));
  };
  return { sortData, setSortData, setSort };
};