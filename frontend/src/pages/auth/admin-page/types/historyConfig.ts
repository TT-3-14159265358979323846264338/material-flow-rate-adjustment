import { OrderCodeConfig } from "../../types/orderConfig";
import { CommentViewCode, CommentViewConfig } from "./commentView";

export const HISTORY_SORT_CODE = [{ code: "DATE", view: "日付" }] as const satisfies readonly CommentViewConfig[];

type HistorySortCodeConfig = CommentViewCode<typeof HISTORY_SORT_CODE>;

export type HistorySortConfig = {
  minYear: string;
  minMonth: string;
  maxYear: string;
  maxMonth: string;
  order: OrderCodeConfig;
  target: HistorySortCodeConfig;
};

const defaultMinTerm = () => {
  const date = new Date();
  date.setMonth(date.getMonth() - 6);
  return {
    minYear: String(date.getFullYear()),
    minMonth: String(date.getMonth() + 1),
  };
};

export const InitialHistorySort: HistorySortConfig = {
  minYear: defaultMinTerm().minYear,
  minMonth: defaultMinTerm().minMonth,
  maxYear: String(new Date().getFullYear()),
  maxMonth: String(new Date().getMonth() + 1),
  order: "ASCENDING",
  target: "DATE",
};