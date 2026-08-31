import { CommentViewCode, CommentViewConfig, getAllCommentView, getCommentCode, getCommentView } from "../auth/admin-page/types/commentView";

export const AUTHORITY_CODE = [
  { code: "ADMIN", view: "管理者" },
  { code: "USER", view: "ユーザー" },
  { code: "MANAGER", view: "マネージャー" },
] as const satisfies readonly CommentViewConfig[];

export type AuthorityCodeConfig = CommentViewCode<typeof AUTHORITY_CODE>;

export const AllAuthorityView = () => getAllCommentView(AUTHORITY_CODE);

export const AuthorityCode = (view: string) => getCommentCode(AUTHORITY_CODE, view);

export const AuthorityView = (code: string) => getCommentView(AUTHORITY_CODE, code);