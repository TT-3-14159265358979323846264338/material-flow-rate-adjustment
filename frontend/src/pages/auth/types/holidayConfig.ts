import { CommentViewCode, CommentViewConfig, getAllCommentView, getCommentView, getCommentCode } from "./commentView";

const HOLIDAY_CODE = [
  { code: "ALL_DAY", view: "全休" },
  { code: "MORNING", view: "午前半休" },
  { code: "AFTERNOON", view: "午後半休" },
] as const satisfies readonly CommentViewConfig[];

export type HolidayCodeConfig = CommentViewCode<typeof HOLIDAY_CODE>;

export const HolidayView = (code: string) => getCommentView(HOLIDAY_CODE, code);

export const HolidayList = () => getAllCommentView(HOLIDAY_CODE);

export const HolidayCode = (view: string) => getCommentCode(HOLIDAY_CODE, view);

export const IsAllDayHoliday = (code: string) => {
  return code === "ALL_DAY";
}