import { SortConfig } from "../../types/sortConfig";
import { useStateElements } from "../../hooks/useStateElements";
import { InitialUserSort, UserSortConfig } from "../types/userSortConfig";

export const useUserSort = (): SortConfig<UserSortConfig> => {
  const { data: sortData, setData: setSortData, setState: setSort } = useStateElements<UserSortConfig>(InitialUserSort);
  return { sortData, setSortData, setSort };
};