import { CommentViewCode, CommentViewConfig, getCommentView } from "./commentView";

export const ACTION_CODE = [
  { code: "CREATE", view: "新規作成" },
  { code: "CHANGE", view: "変更" },
  { code: "DELETE", view: "論理削除" },
] as const satisfies readonly CommentViewConfig[];

export type ActionCodeConfig = CommentViewCode<typeof ACTION_CODE>;

export const ActionView = (code: string) => getCommentView(ACTION_CODE, code);