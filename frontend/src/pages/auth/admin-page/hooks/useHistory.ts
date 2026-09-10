import { SortConfig } from "../../types/sortConfig";
import { useStateElements } from "../../hooks/useStateElements";

export const useHistorySort = <T>(InitialHistorySort: T): SortConfig<T> => {
  const { data: sortData, setData: setSortData, setState: setSort } = useStateElements<T>(InitialHistorySort);
  return { sortData, setSortData, setSort };
};