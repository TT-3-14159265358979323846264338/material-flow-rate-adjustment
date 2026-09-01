import Dropdown from "../../components/Dropdown";
import { monthArray, allYearArray } from "../../utils/termArray";
import CommonSort from "./CommonSort";
import { CommentViewCode, CommentViewConfig } from "../types/commentView";
import { OrderCodeConfig } from "../../types/orderConfig";

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

export type HistorySortConfig<U extends readonly CommentViewConfig[]> = {
  minYear: string;
  minMonth: string;
  maxYear: string;
  maxMonth: string;
  order: OrderCodeConfig;
  target: CommentViewCode<U>;
};

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
        <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
          <div className="flex items-center gap-3 *:flex-1 *:block">
            <Dropdown name="minYear" value={sortData.minYear} onChange={setSort} list={allYearArray()}>
              年
            </Dropdown>
            <Dropdown name="minMonth" value={sortData.minMonth} onChange={setSort} list={monthArray()}>
              月
            </Dropdown>
            <span>～</span>
            <Dropdown name="maxYear" value={sortData.maxYear} onChange={setSort} list={allYearArray()}>
              年
            </Dropdown>
            <Dropdown name="maxMonth" value={sortData.maxMonth} onChange={setSort} list={monthArray()}>
              月
            </Dropdown>
          </div>
        </div>
        {children}
      </div>
    </CommonSort>
  );
};

export default HistorySort;
