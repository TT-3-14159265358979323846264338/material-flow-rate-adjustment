import { CommentViewConfig } from "../../types/commentView";
import { DateRangeConfig, SortOrderConfig } from "../../types/sortConfig";

export const CALENDER_SORT_CODE = [
  { code: "ID", view: "製品ID" },
  { code: "NAME", view: "製品名" },
  { code: "DATE", view: "日時" },
] as const satisfies readonly CommentViewConfig[];

export type CalenderSortConfig = DateRangeConfig &
  SortOrderConfig<typeof CALENDER_SORT_CODE> & {
    material: number | undefined;
  };

const defaultMinTerm = () => {
  const minDate = new Date();
  const maxDate = new Date();
  maxDate.setDate(1);
  maxDate.setMonth(maxDate.getMonth() + 1);
  return {
    minYear: String(minDate.getFullYear()),
    minMonth: String(minDate.getMonth() + 1),
    maxYear: String(maxDate.getFullYear()),
    maxMonth: String(maxDate.getMonth() + 1),
  };
};

export const InitialCalenderSort: CalenderSortConfig = {
  minYear: defaultMinTerm().minYear,
  minMonth: defaultMinTerm().minMonth,
  maxYear: defaultMinTerm().maxYear,
  maxMonth: defaultMinTerm().maxMonth,
  material: undefined,
  order: "ASCENDING",
  target: "ID",
};