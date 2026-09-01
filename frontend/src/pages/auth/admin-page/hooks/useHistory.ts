import { useState } from "react";
import { SortConfig } from "../../types/sortConfig";

export const useHistorySort = <T>(InitialHistorySort: T): SortConfig<T> => {
  const [sortData, setSortData] = useState<T>(InitialHistorySort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    let sortValue = value as any;
    if (e.currentTarget instanceof HTMLInputElement) {
      const { type, checked } = e.currentTarget;
      sortValue = type === "checkbox" ? checked : value;
    }
    setSortData((prev) => ({ ...prev, [name as keyof T]: sortValue }));
  };
  return { sortData, setSortData, setSort };
};