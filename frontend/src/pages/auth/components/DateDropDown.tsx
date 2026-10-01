import { DateConfig } from "../types/sortConfig";
import { allYearArray, monthArray } from "../utils/termArray";
import Dropdown from "./Dropdown";

type DateDropdownProps<T> = {
  sortData: T;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
};

const DateDropdown = <T extends DateConfig>({ sortData, setSort }: DateDropdownProps<T>) => {
  return (
    <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
      <div className="flex items-center gap-3 *:flex-1 *:block">
        <Dropdown name="year" value={sortData.year} onChange={setSort} list={allYearArray()}>
          年
        </Dropdown>
        <Dropdown name="month" value={sortData.month} onChange={setSort} list={monthArray()}>
          月
        </Dropdown>
      </div>
    </div>
  );
};

export default DateDropdown;