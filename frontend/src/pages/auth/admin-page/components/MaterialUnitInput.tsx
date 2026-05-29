import { Dispatch, SetStateAction } from "react";
import TextInput from "../../components/TextInput";

type MaterialUnitInputProps = {
  unit: string;
  setUnit: Dispatch<SetStateAction<string>>;
};

const MaterialUnitInput = ({ unit, setUnit }: MaterialUnitInputProps) => (
  <div className="flex-1">
    <TextInput value={unit} maxLength={10} onChange={(e) => setUnit(e.target.value)}>
      納入単位
    </TextInput>
  </div>
);

export default MaterialUnitInput;
