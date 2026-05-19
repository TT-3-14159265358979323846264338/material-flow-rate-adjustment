import { Dispatch, SetStateAction } from "react";
import TextInput from "./TextInput"
import { onlyHalfWidthCharacters } from '../../utils/characterLimit';

type ChangePasswordInputProps = {
  setNewPass: Dispatch<SetStateAction<string>>;
  setOldPass: Dispatch<SetStateAction<string>>;
};

const ChangePasswordInput = ({setNewPass, setOldPass}: ChangePasswordInputProps) => (
  <div>
    <TextInput
      children="新規のパスワード" 
      value={""}
      maxLength={50}
      onChange={(e) => setNewPass(onlyHalfWidthCharacters(e.target.value))}>
    </TextInput>
    <TextInput
      children="以前のパスワード" 
      value={""}
      maxLength={50}
      onChange={(e) => setOldPass(onlyHalfWidthCharacters(e.target.value))}>
    </TextInput>
  </div>
);

export default ChangePasswordInput;