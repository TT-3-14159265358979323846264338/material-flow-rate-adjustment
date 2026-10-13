import HistorySort from "../../components/HistorySort";
import { HISTORY_SORT_CODE, HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";

type HistoryCalendarSortProps = {
  finalSort: HistorySortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<HistorySortConfig>>;
  sortData: HistorySortConfig;
  setSortData: React.Dispatch<React.SetStateAction<HistorySortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

const HistoryCalendarSort = ({ finalSort, setFinalSort, sortData, setSortData, setSort, setIsOpen }: HistoryCalendarSortProps) => {
  return (
    <HistorySort
      sortCode={HISTORY_SORT_CODE}
      initialSort={InitialHistorySort}
      finalSort={finalSort}
      setFinalSort={setFinalSort}
      sortData={sortData}
      setSortData={setSortData}
      setSort={setSort}
      setIsOpen={setIsOpen}
    ></HistorySort>
  );
};

export default HistoryCalendarSort;