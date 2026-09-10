import { ChangeEvent } from 'react';

type DropdownProps = {
  name?: string;
  children: string;
  value: string | undefined;
  onChange: (e: ChangeEvent<HTMLSelectElement>) => void;
  list: readonly string[];
};

const Dropdown = ({name="dropdown", children, value, onChange, list}: DropdownProps) => (
  <div className="flex flex-col items-stretch">
    <label>{children}</label>
    <select
      name={name}
      value={value?? ""}
      onChange={onChange}
      className="text-center border border-b-black rounded-md focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white mb-2 cursor-pointer"
      required
    >
      {list.map((item) => (
        <option key={item} value={item}>
          {item}
        </option>
      ))}
    </select>
  </div>
)

export default Dropdown;