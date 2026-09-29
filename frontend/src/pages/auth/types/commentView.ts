export type CommentViewConfig = {
  code: string;
  view: string;
};

export type CommentViewCode<T extends readonly CommentViewConfig[]> = T[number]["code"];

export const getCommentView = (Commentview: readonly CommentViewConfig[], code: string) => {
  return Commentview.find((item) => item.code === code)?.view;
};

export const getAllCommentView = (Commentview: readonly CommentViewConfig[]) => {
  return Commentview.map((item) => item.view);
};

export const getCommentCode = (Commentview: readonly CommentViewConfig[], view: string) => {
  return Commentview.find((item) => item.view === view)?.code;
};