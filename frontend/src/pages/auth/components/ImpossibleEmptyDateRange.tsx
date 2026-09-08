import { allYearArray, monthArray } from "../utils/termArray";
import DateRangeDropdown from "./DateRangeDropdown";
import { DateRangeConfig } from "../types/sortConfig";

type ImpossibleEmptyDateRangeProps<T> = {
  sortData: T;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
};

const ImpossibleEmptyDateRange = <T extends DateRangeConfig>({ sortData, setSort }: ImpossibleEmptyDateRangeProps<T>) => {
  return (
    <DateRangeDropdown
      sortData={sortData}
      setSort={setSort}
      yearArray={allYearArray()}
      monthArray={monthArray()}
    ></DateRangeDropdown>
  );
};

export default ImpossibleEmptyDateRange;