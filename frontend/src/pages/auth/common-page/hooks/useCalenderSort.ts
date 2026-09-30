import { useState } from "react";
import { SortConfig } from "../../types/sortConfig";
import { MaterialResponse } from "../../types/materialResponse";
import { CalenderSortConfig, InitialCalenderSort } from "../types/calenderSortConfig";

export const useCalenderSort = (materialArray: MaterialResponse[]): SortConfig<CalenderSortConfig> => {
  const [sortData, setSortData] = useState<CalenderSortConfig>(InitialCalenderSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    const resultValue = name === "material" ? materialArray?.find((item) => item.name === value)?.id: value;
    setSortData((prev) => ({ ...prev, [name as keyof CalenderSortConfig]: resultValue }));
  };
  return { sortData, setSortData, setSort };
};