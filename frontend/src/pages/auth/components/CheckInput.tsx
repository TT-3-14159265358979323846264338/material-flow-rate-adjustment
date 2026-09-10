import { useId } from 'react';

type CheckInputProps = {
  children: string;
  isChecked: boolean;
  setChecked: (e: React.ChangeEvent<HTMLInputElement>) => void;
  name?: string;
};

const CheckInput = ({ children, isChecked, setChecked, name = "checkbox" }: CheckInputProps) => {
  const id = useId();
  return (
    <div>
      <input
        name={name}
        id={id}
        type="checkbox"
        data-testid="check-input"
        onChange={setChecked}
        checked={isChecked}
        className="focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white cursor-pointer"
      />
      <label htmlFor={id} className="cursor-pointer">
        {children}
      </label>
    </div>
  );
};

export default CheckInput;