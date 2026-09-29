import { CommentViewCode, CommentViewConfig } from "./commentView";

export const ORDER_CODE = [
  { code: "ASCENDING", view: "昇順" },
  { code: "DESCENDING", view: "降順" },
] as const satisfies readonly CommentViewConfig[];

export type OrderCodeConfig = CommentViewCode<typeof ORDER_CODE>;