import { Dispatch, SetStateAction } from "react";
import TextInput from "../../components/TextInput";

type MaterialNameInputProps = {
  name: string;
  setName: Dispatch<SetStateAction<string>>;
};

const MaterialNameInput = ({ name, setName }: MaterialNameInputProps) => (
  <TextInput value={name} maxLength={10} onChange={(e) => setName(e.target.value)}>
    製品名
  </TextInput>
);

export default MaterialNameInput;
