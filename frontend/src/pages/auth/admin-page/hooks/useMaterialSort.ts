import { SortConfig } from "../../types/sortConfig";
import { useStateElements } from "../../hooks/useStateElements";
import { InitialMaterialSort, MaterialSortConfig } from "../types/materialSortConfig";

export const useMaterialSort = (): SortConfig<MaterialSortConfig> => {
  const { data: sortData, setData: setSortData, setState: setSort } = useStateElements<MaterialSortConfig>(InitialMaterialSort);
  return { sortData, setSortData, setSort };
};