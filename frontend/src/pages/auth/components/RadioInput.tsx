import { useId } from 'react';

type RadioInputProps<T extends string> = {
  children: React.ReactNode;
  selected: T;
  setSelected: (e: React.ChangeEvent<HTMLInputElement>) => void;
  value: T;
  groupName: string;
};

const RadioInput = <T extends string>({children, selected, setSelected, value, groupName}: RadioInputProps<T>) => {
  const id = useId();
  return (
  <div>
    <input
      id={id}
      type="radio"
      name={groupName}
      data-testid="radio-input"
      value={value}
      onChange={(e) => setSelected(e)}
      checked={selected === value}
      className="focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white cursor-pointer"
    />
    <label htmlFor={id} className="cursor-pointer">{children}</label>
  </div>
  )
}

export default RadioInput;