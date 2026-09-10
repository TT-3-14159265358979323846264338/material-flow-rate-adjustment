import { CommentViewConfig,} from "../../types/commentView";
import { SortConfig, SortOrderConfig } from "../../types/sortConfig";
import { useStateElements } from "../../hooks/useStateElements";

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

export const useMaterialSort = (): SortConfig<MaterialSortConfig> => {
  const { data: sortData, setData: setSortData, setState: setSort } = useStateElements<MaterialSortConfig>(InitialMaterialSort);
  return { sortData, setSortData, setSort };
};