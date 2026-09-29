import { CommentViewConfig } from "../../types/commentView";
import { SortOrderConfig } from "../../types/sortConfig";

export const MATERAIL_SORT_CODE = [
  { code: "ID", view: "ID" },
  { code: "MATERIAL", view: "製品名" },
  { code: "DESTINATION", view: "向け先" },
] as const satisfies readonly CommentViewConfig[];

export type MaterialSortConfig = SortOrderConfig<typeof MATERAIL_SORT_CODE>;

export const InitialMaterialSort: MaterialSortConfig = {
  order: "ASCENDING",
  target: "ID",
};