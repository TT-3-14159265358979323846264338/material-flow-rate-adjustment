import { Dispatch, SetStateAction } from "react";
import TextInput from "./TextInput";

type MaterialDestinationInputProps = {
  name: string;
  setName: Dispatch<SetStateAction<string>>;
};

const MaterialDestinationInput = ({ name, setName }: MaterialDestinationInputProps) => (
  <TextInput value={name} maxLength={10} onChange={(e) => setName(e.target.value)}>
    向け先
  </TextInput>
);

export default MaterialDestinationInput;
