import { useState } from "react";
import { OrderCodeConfig } from "../../types/orderConfig";
import { CommentViewConfig, CommentViewCode } from "../types/commentView";

export const USER_SORT_CODE = [
  { code: "ID", view: "ID" },
  { code: "NAME", view: "ユーザー名" },
  { code: "AUTHORITY", view: "権限" },
] as const satisfies readonly CommentViewConfig[];

type UserSortCodeConfig = CommentViewCode<typeof USER_SORT_CODE>;

export type UserSortConfig = {
  isAdmin: boolean;
  isUser: boolean;
  isManager: boolean;
  order: OrderCodeConfig;
  target: UserSortCodeConfig;
};

export const InitialUserSort: UserSortConfig = {
  isAdmin: false,
  isUser: false,
  isManager: false,
  order: "ASCENDING",
  target: "ID",
};

export const useUserSort = () => {
  const [sortData, setSortData] = useState<UserSortConfig>(InitialUserSort);
  const setSort = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, type, checked, value } = e.currentTarget;
    const sortValue = type === "checkbox" ? checked : value;
    setSortData((prev) => ({ ...prev, [name as keyof UserSortConfig]: sortValue }));
  };
  return { sortData, setSortData, setSort };
};