import { CommentViewCode, CommentViewConfig, getCommentView } from "./commentView";

const HOLIDAY_CODE = [
  { code: "ALL_DAY", view: "全休" },
  { code: "MORNING", view: "午前半休" },
  { code: "AFTERNOON", view: "午後半休" },
] as const satisfies readonly CommentViewConfig[];

export type HolidayCodeConfig = CommentViewCode<typeof HOLIDAY_CODE>;

export const HolidayView = (code: HolidayCodeConfig) => getCommentView(HOLIDAY_CODE, code);

export const IsAllDayHoliday = (code: HolidayCodeConfig) => {
  return code === "ALL_DAY";
}