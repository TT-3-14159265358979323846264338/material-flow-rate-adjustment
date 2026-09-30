import { DateRangeConfig, SortOrderConfig } from "./sortConfig";
import { CommentViewConfig } from "./commentView";

export const HISTORY_SORT_CODE = [{ code: "DATE", view: "日付" }] as const satisfies readonly CommentViewConfig[];

export type HistorySortConfig = DateRangeConfig & SortOrderConfig<typeof HISTORY_SORT_CODE>;

const defaultMinTerm = () => {
  const minDate = new Date();
  minDate.setDate(1);
  minDate.setMonth(minDate.getMonth() - 6);
  const maxDate = new Date();
  return {
    minYear: String(minDate.getFullYear()),
    minMonth: String(minDate.getMonth() + 1),
    maxYear: String(maxDate.getFullYear()),
    maxMonth: String(maxDate.getMonth() + 1),
  };
};

export const InitialHistorySort: HistorySortConfig = {
  minYear: defaultMinTerm().minYear,
  minMonth: defaultMinTerm().minMonth,
  maxYear: defaultMinTerm().maxYear,
  maxMonth: defaultMinTerm().maxMonth,
  order: "ASCENDING",
  target: "DATE",
};