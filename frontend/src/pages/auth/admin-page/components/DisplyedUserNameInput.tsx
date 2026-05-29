import { Dispatch, SetStateAction } from "react";
import TextInput from "../../components/TextInput";

type DisplayedUserNameInputProps = {
  name: string;
  setName: Dispatch<SetStateAction<string>>;
};

const DisplayedUserNameInput = ({ name, setName }: DisplayedUserNameInputProps) => (
  <TextInput value={name} maxLength={20} onChange={(e) => setName(e.target.value)}>
    表示ユーザー名
  </TextInput>
);

export default DisplayedUserNameInput;
