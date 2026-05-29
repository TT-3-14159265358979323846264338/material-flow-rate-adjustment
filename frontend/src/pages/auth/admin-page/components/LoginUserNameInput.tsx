import { Dispatch, SetStateAction } from "react";
import TextInput from "../../components/TextInput"
import { onlyHalfWidthAlphanumericCharacters } from '../../../utils/characterLimit';

type LoginUserNameInputProps = {
  name: string;
  setName: Dispatch<SetStateAction<string>>;
};

const LoginUserNameInput = ({name, setName}: LoginUserNameInputProps) => (
  <TextInput
    value={name}
    maxLength={20}
    onChange={(e) => setName(onlyHalfWidthAlphanumericCharacters(e.target.value))}
  >
  ログインユーザー名
  </TextInput>
);

export default LoginUserNameInput;