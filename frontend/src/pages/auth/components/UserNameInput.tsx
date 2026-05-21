import { Dispatch, SetStateAction } from "react";
import TextInput from "./TextInput"
import { onlyHalfWidthAlphanumericCharacters } from '../../utils/characterLimit';

type UserNameInputProps = {
  name: string;
  setName: Dispatch<SetStateAction<string>>;
};

const UserNameInput = ({name, setName}: UserNameInputProps) => (
  <TextInput
    value={name}
    maxLength={20}
    onChange={(e) => setName(onlyHalfWidthAlphanumericCharacters(e.target.value))}
  >
  ユーザー名
  </TextInput>
);

export default UserNameInput;