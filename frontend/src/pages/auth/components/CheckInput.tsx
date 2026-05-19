import { Dispatch, SetStateAction, useId } from 'react';

type CheckInputProps = {
  children: string;
  isChecked: boolean;
  setChecked: Dispatch<SetStateAction<boolean>>;
};

const CheckInput = ({children, isChecked, setChecked}: CheckInputProps) => {
  const id = useId();
  return (
  <div>
    <input
      id={id}
      type="checkbox"
      data-testid="check-input"
      onChange={(e) => setChecked(e.target.checked)}
      checked={isChecked}
      className="focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white cursor-pointer"
    />
    <label htmlFor={id} className="cursor-pointer">{children}</label>
  </div>
  )
}

export default CheckInput;