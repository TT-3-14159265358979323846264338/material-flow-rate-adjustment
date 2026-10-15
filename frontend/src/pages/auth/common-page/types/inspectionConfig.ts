import { CommentViewCode, CommentViewConfig, GetCommentView } from "../../types/commentView";

export const INSPECTION_CODE = [
  { code: "PASSED", view: "合格" },
  { code: "FAILED", view: "不合格" },
  { code: "UNINSPECTION", view: "未検査" },
] as const satisfies readonly CommentViewConfig[];

export type InspectionCodeConfig = CommentViewCode<typeof INSPECTION_CODE>;

export const InspectionView = (code: string) => GetCommentView(INSPECTION_CODE, code);