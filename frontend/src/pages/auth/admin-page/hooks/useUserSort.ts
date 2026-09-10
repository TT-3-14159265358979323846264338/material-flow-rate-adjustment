import { CommentViewConfig} from "../../types/commentView";
import { SortConfig, SortOrderConfig } from "../../types/sortConfig";
import { useStateElements } from "../../hooks/useStateElements";

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
  const { data: sortData, setData: setSortData, setState: setSort } = useStateElements<UserSortConfig>(InitialUserSort);
  return { sortData, setSortData, setSort };
};