import { useState } from "react";
import { CommentViewConfig} from "../types/commentView";
import { SortConfig, SortOrderConfig } from "../../types/sortConfig";

export const USER_SORT_CODE = [
  { code: "ID", view: "ID" },
  { code: "NAME", view: "ユーザー名" },
  { code: "AUTHORITY", view: "権限" },
] as const satisfies readonly CommentViewConfig[];

export type UserSortConfig = SortOrderConfig<typeof USER_SORT_CODE> & {
  isAdmin: boolean;
  isUser: boolean;
  isManager: boolean;
};

export const InitialUserSort: UserSortConfig = {
  isAdmin: false,
  isUser: false,
  isManager: false,
  order: "ASCENDING",
  target: "ID",
};

export const useUserSort = (): SortConfig<UserSortConfig> => {
  const [sortData, setSortData] = useState<UserSortConfig>(InitialUserSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    if (e.currentTarget instanceof HTMLInputElement) {
      const { name, type, checked, value } = e.currentTarget;
      const sortValue = type === "checkbox" ? checked : value;
      setSortData((prev) => ({ ...prev, [name as keyof UserSortConfig]: sortValue }));
    }
  };
  return { sortData, setSortData, setSort };
};