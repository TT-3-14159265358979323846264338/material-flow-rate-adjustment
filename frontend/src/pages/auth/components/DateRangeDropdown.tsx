import { DateRangeConfig } from "../types/sortConfig";
import Dropdown from "./Dropdown";

type DateRangeDropdownProps<T> = {
  sortData: T;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
  yearArray: string[];
  monthArray: string[];
};

const DateRangeDropdown = <T extends DateRangeConfig>({ sortData, setSort, yearArray, monthArray }: DateRangeDropdownProps<T>) => {
  return (
    <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
      <div className="flex items-center gap-3 *:flex-1 *:block">
        <Dropdown name="minYear" value={sortData.minYear} onChange={setSort} list={yearArray}>
          年
        </Dropdown>
        <Dropdown name="minMonth" value={sortData.minMonth} onChange={setSort} list={monthArray}>
          月
        </Dropdown>
        <span>～</span>
        <Dropdown name="maxYear" value={sortData.maxYear} onChange={setSort} list={yearArray}>
          年
        </Dropdown>
        <Dropdown name="maxMonth" value={sortData.maxMonth} onChange={setSort} list={monthArray}>
          月
        </Dropdown>
      </div>
    </div>
  );
};

export default DateRangeDropdown;