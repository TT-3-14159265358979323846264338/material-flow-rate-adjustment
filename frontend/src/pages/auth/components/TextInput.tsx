import { ChangeEvent } from 'react';

type TextInputProps = {
  children: string;
  value: string;
  maxLength: number;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

const TextInput = ({children, value, maxLength, onChange}: TextInputProps) => (
  <div className="flex flex-col items-stretch">
    <label>{children}</label>
    <input
      type="text"
      data-testid="text-input"
      value={value}
      maxLength={maxLength}
      onChange={onChange}
      spellCheck="false"
      className="text-center border border-b-black rounded-md focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white mb-2"
      required
    />
  </div>
)

export default TextInput;