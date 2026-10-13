import { CommentViewCode, CommentViewConfig, GetAllCommentView, GetCommentView, GetCommentCode } from "./commentView";

const HOLIDAY_CODE = [
  { code: "WORKING", view: "稼働日" },
  { code: "ALL_DAY", view: "全休" },
  { code: "MORNING", view: "午前半休" },
  { code: "AFTERNOON", view: "午後半休" },
] as const satisfies readonly CommentViewConfig[];

export type HolidayCodeConfig = CommentViewCode<typeof HOLIDAY_CODE>;

export const HolidayView = (code: string | null) => GetCommentView(HOLIDAY_CODE, code);

export const HolidayList = () => GetAllCommentView(HOLIDAY_CODE);

export const HolidayCode = (view: string | null) => GetCommentCode(HOLIDAY_CODE, view);