import { useState } from "react";
import { CommentViewCode, CommentViewConfig } from "../../admin-page/types/commentView";
import { OrderCodeConfig } from "../../types/orderConfig";
import { SortConfig } from "../../types/sortConfig";

export const PLAN_SORT_CODE = [
  { code: "DATE", view: "計画日時" },
  { code: "MATERIAL", view: "製品名" },
  { code: "UPDATE_DATE", view: "更新日" },
] as const satisfies readonly CommentViewConfig[];

type PlanSortCodeConfig = CommentViewCode<typeof PLAN_SORT_CODE>;

export type PlanSortConfig = {
  order: OrderCodeConfig;
  target: PlanSortCodeConfig;
  maxSize: string;
};

export const InitialPlanSort: PlanSortConfig = {
  order: "DESCENDING",
  target: "DATE",
  maxSize: "100",
};

export const usePlanSort = (): SortConfig<PlanSortConfig> => {
  const [sortData, setSortData] = useState<PlanSortConfig>(InitialPlanSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    setSortData((prev) => ({ ...prev, [name as keyof PlanSortConfig]: value }));
  };
  return { sortData, setSortData, setSort };
};