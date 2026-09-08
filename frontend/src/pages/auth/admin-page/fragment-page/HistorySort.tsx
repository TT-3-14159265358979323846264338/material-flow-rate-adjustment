import CommonSort from "../../components/CommonSort";
import { CommentViewConfig } from "../types/commentView";
import ImpossibleEmptyDateRange from "../../components/ImpossibleEmptyDateRange";
import { DateRangeConfig, SortOrderConfig } from "../../types/sortConfig";

type HistorySortProps<T, U> = {
  sortCode: U;
  initialSort: T;
  finalSort: T;
  setFinalSort: React.Dispatch<React.SetStateAction<T>>;
  sortData: T;
  setSortData: React.Dispatch<React.SetStateAction<T>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  children?: React.ReactElement;
};

export type HistorySortConfig<U extends readonly CommentViewConfig[]> = DateRangeConfig & SortOrderConfig<U>;

const HistorySort = <T extends HistorySortConfig<U> , U extends readonly CommentViewConfig[]>({
  sortCode,
  initialSort,
  finalSort,
  setFinalSort,
  sortData,
  setSortData,
  setSort,
  setIsOpen,
  children,
}: HistorySortProps<T, U>) => {
  
  return (
    <CommonSort
      sortCode={sortCode}
      initialSort={initialSort}
      finalSort={finalSort}
      setFinalSort={setFinalSort}
      sortData={sortData}
      setSortData={setSortData}
      setSort={setSort}
      returnTop={() => setIsOpen(false)}
    >
      <div className="flex flex-col">
        <h3 className="text-left ml-5">絞り込み</h3>
        <ImpossibleEmptyDateRange sortData={sortData} setSort={setSort}></ImpossibleEmptyDateRange>
        {children}
      </div>
    </CommonSort>
  );
};

export default HistorySort;
