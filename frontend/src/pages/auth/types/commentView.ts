export type CommentViewConfig = {
  code: string;
  view: string;
};

export type CommentViewCode<T extends readonly CommentViewConfig[]> = T[number]["code"];

export const GetCommentView = (Commentview: readonly CommentViewConfig[], code: string | null) => {
  return Commentview.find((item) => item.code === code)?.view;
};

export const GetAllCommentView = (Commentview: readonly CommentViewConfig[]) => {
  return Commentview.map((item) => item.view);
};

export const GetCommentCode = (Commentview: readonly CommentViewConfig[], view: string | null) => {
  return Commentview.find((item) => item.view === view)?.code;
};