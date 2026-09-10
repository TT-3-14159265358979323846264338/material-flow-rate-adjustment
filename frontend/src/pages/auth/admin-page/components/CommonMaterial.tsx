import { onlyNumber } from "../../../utils/characterLimit";
import TextInput from "../../components/TextInput";
import { defaultEvent } from "../../utils/defaultEvent";
import { CorrectMaterialConfig } from "../hooks/useCorrectMaterial";

type CommonMaterialProps = {
  material: CorrectMaterialConfig;
  setMaterial: (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement, Element>) => void;
};

const CommonMaterial = ({ material, setMaterial }: CommonMaterialProps) => {
  return (
    <div className="w-80">
      <TextInput name="name" value={material.name} maxLength={10} onChange={setMaterial}>
        製品名
      </TextInput>
      <TextInput name="destination" value={material.destination} maxLength={10} onChange={setMaterial}>
        向け先
      </TextInput>
      <div className="flex gap-5">
        <div className="flex-1">
          <TextInput
            name="base"
            value={material.base}
            maxLength={10}
            onChange={(e) => setMaterial(defaultEvent(e, onlyNumber))}
          >
            基本製造量
          </TextInput>
        </div>
        <div className="flex-1">
          <TextInput name="unit" value={material.unit} maxLength={10} onChange={setMaterial}>
            納入単位
          </TextInput>
        </div>
      </div>
    </div>
  );
};

export default CommonMaterial;