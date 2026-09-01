import { HISTORY_SORT_CODE, HistorySortConfig, InitialHistorySort } from "../types/historyConfig";
import HistorySort from "./HistorySort";

type HistoryMaterialSortProps = {
  finalSort: HistorySortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<HistorySortConfig>>;
  sortData: HistorySortConfig;
  setSortData: React.Dispatch<React.SetStateAction<HistorySortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

const HistoryMaterialSort = ({
  finalSort,
  setFinalSort,
  sortData,
  setSortData,
  setSort,
  setIsOpen,
}: HistoryMaterialSortProps) => {
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

export default HistoryMaterialSort;
