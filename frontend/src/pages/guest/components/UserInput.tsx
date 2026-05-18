import { ChangeEvent } from 'react';

type UserInputProps = {
  value: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

const UserInput = ({value = "", onChange = () => {}}: UserInputProps) => (
  <div className="flex flex-col items-stretch">
    <label>ユーザー名</label>
    <input
      type="text"
      data-testid="username-input"
      placeholder="user name"
      value={value}
      onChange={onChange}
      inputMode="url"
      autoCapitalize="none"
      spellCheck="false"
      className="border border-b-black rounded-md focus:outline-none focus:ring-2 focus:ring-amber-200 bg-white mb-2"
      required
    />
  </div>
)

export default UserInput;