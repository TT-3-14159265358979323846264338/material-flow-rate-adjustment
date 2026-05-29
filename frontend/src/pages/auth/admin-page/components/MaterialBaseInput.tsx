import { Dispatch, SetStateAction } from "react";
import TextInput from "../../components/TextInput";
import { onlyNumber } from "../../../utils/characterLimit";

type MaterialBaseInputProps = {
  base: string;
  setBase: Dispatch<SetStateAction<string>>;
};

const MaterialBaseInput = ({ base, setBase }: MaterialBaseInputProps) => (
  <div className="flex-1">
    <TextInput value={base} maxLength={10} onChange={(e) => setBase(onlyNumber(e.target.value))}>
      基本製造量
    </TextInput>
  </div>
);

export default MaterialBaseInput;
