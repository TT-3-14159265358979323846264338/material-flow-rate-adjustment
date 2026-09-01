export type SortConfig<T> = {
  sortData: T;
  setSortData: React.Dispatch<React.SetStateAction<T>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
};