import { emptyAllYearArray, emptyMonthArray } from "../utils/termArray";
import DateRangeDropdown from "./DateRangeDropdown";
import { DateRangeConfig } from "../types/sortConfig";

type PossibleEmptyDateRangeProps<T> = {
  sortData: T;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
};

const PossibleEmptyDateRange = <T extends DateRangeConfig>({ sortData, setSort }: PossibleEmptyDateRangeProps<T>) => {
  return (
    <DateRangeDropdown
      sortData={sortData}
      setSort={setSort}
      yearArray={emptyAllYearArray()}
      monthArray={emptyMonthArray()}
    ></DateRangeDropdown>
  );
};

export default PossibleEmptyDateRange;
