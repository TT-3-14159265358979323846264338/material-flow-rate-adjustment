import { useState } from "react";
import { OrderCodeConfig } from "../../types/orderConfig";
import { CommentViewConfig, CommentViewCode } from "../types/commentView";

export const MATERAIL_SORT_CODE = [
  { code: "ID", view: "ID" },
  { code: "MATERIAL", view: "製品名" },
  { code: "DESTINATION", view: "向け先" },
] as const satisfies readonly CommentViewConfig[];

type MaterialSortCodeConfig = CommentViewCode<typeof MATERAIL_SORT_CODE>;

export type MaterialSortConfig = {
  order: OrderCodeConfig;
  target: MaterialSortCodeConfig;
};

const InitialSort: MaterialSortConfig = {
  order: "ASCENDING",
  target: "ID",
};

export const useMaterialSort = () => {
  const [sortData, setSortData] = useState<MaterialSortConfig>(InitialSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.currentTarget;
    setSortData((prev) => ({ ...prev, [name as keyof MaterialSortConfig]: value }));
  };
  return { sortData, setSortData, setSort };
};