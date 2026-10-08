import { useState } from "react";
import { SortConfig } from "../../types/sortConfig";
import { MaterialResponse } from "../../types/materialResponse";
import { CalendarSortConfig, InitialCalendarSort } from "../types/calendarSortConfig";

export const useCalendarSort = (materialArray: MaterialResponse[]): SortConfig<CalendarSortConfig> => {
  const [sortData, setSortData] = useState<CalendarSortConfig>(InitialCalendarSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    const resultValue = name === "material" ? materialArray?.find((item) => item.name === value)?.id: value;
    setSortData((prev) => ({ ...prev, [name as keyof CalendarSortConfig]: resultValue }));
  };
  return { sortData, setSortData, setSort };
};