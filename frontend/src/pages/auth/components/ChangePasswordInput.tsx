import { Dispatch, SetStateAction } from "react";
import TextInput from "./TextInput"
import { onlyHalfWidthCharacters } from '../../utils/characterLimit';

type ChangePasswordInputProps = {
  newPass: string;
  setNewPass: Dispatch<SetStateAction<string>>;
  oldPass: string;
  setOldPass: Dispatch<SetStateAction<string>>;
};

const ChangePasswordInput = ({ newPass, setNewPass, oldPass, setOldPass }: ChangePasswordInputProps) => (
  <div>
    <TextInput value={oldPass} maxLength={72} onChange={(e) => setOldPass(onlyHalfWidthCharacters(e.target.value))}>
      以前のパスワード
    </TextInput>
    <TextInput value={newPass} maxLength={72} onChange={(e) => setNewPass(onlyHalfWidthCharacters(e.target.value))}>
      新規のパスワード
    </TextInput>
  </div>
);

export default ChangePasswordInput;