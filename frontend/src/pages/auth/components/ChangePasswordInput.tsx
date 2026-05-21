import { Dispatch, SetStateAction } from "react";
import TextInput from "./TextInput"
import { onlyHalfWidthCharacters } from '../../utils/characterLimit';

type ChangePasswordInputProps = {
  newPass: string;
  setNewPass: Dispatch<SetStateAction<string>>;
  oldPass: string;
  setOldPass: Dispatch<SetStateAction<string>>;
};

const ChangePasswordInput = ({newPass, setNewPass, oldPass, setOldPass}: ChangePasswordInputProps) => (
  <div>
    <TextInput
      children="以前のパスワード" 
      value={oldPass}
      maxLength={50}
      onChange={(e) => setOldPass(onlyHalfWidthCharacters(e.target.value))}>
    </TextInput>
    <TextInput
      children="新規のパスワード" 
      value={newPass}
      maxLength={50}
      onChange={(e) => setNewPass(onlyHalfWidthCharacters(e.target.value))}>
    </TextInput>
  </div>
);

export default ChangePasswordInput;