import { CommentViewCode, CommentViewConfig } from "../admin-page/types/commentView";
import { OrderCodeConfig } from "./orderConfig";

export type SortConfig<T> = {
  sortData: T;
  setSortData: React.Dispatch<React.SetStateAction<T>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
};

export type DateRangeConfig = {
  minYear: string;
  minMonth: string;
  maxYear: string;
  maxMonth: string;
};

export type SortOrderConfig<T extends readonly CommentViewConfig[]> = {
  order: OrderCodeConfig;
  target: CommentViewCode<T>;
};