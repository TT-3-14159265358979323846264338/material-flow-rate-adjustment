import { CommentViewCode, CommentViewConfig, GetAllCommentView, GetCommentCode, GetCommentView } from "../../types/commentView";

export const INSPECTION_CODE = [
  { code: "PASSED", view: "合格" },
  { code: "FAILED", view: "不合格" },
  { code: "UNINSPECTION", view: "未検査" },
] as const satisfies readonly CommentViewConfig[];

export type InspectionCodeConfig = CommentViewCode<typeof INSPECTION_CODE>;

export const InspectionCode = (view: string) => GetCommentCode(INSPECTION_CODE, view);

export const InspectionView = (code: string) => GetCommentView(INSPECTION_CODE, code);

export const AllInspectionView = () => GetAllCommentView(INSPECTION_CODE);