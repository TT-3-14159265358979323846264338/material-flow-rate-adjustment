import { onlyNumber } from "../../../utils/characterLimit";
import Dropdown from "../../components/Dropdown";
import TextInput from "../../components/TextInput";
import { MaterialResponse } from "../../types/materialResponse";
import { defaultEvent } from "../../utils/defaultEvent";
import { materialArray, materialView, selectedMaterial } from "../../utils/materialHandling";
import { monthArray, nowYearArray } from "../../utils/termArray";
import { CorrectPlanConfig } from "../hooks/useCorrectPlan";

type CommonPlanProps = {
  plan: CorrectPlanConfig;
  setPlan: (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement, Element>) => void;
  materialData: MaterialResponse[];
};

const CommonPlan = ({ plan, setPlan, materialData }: CommonPlanProps) => {
  return (
    <div className="w-80">
      <Dropdown
        name="material"
        value={materialView(plan.material)}
        onChange={(e) => setPlan(defaultEvent(e, (value) => selectedMaterial(materialData, value)))}
        list={materialArray(materialData)}
      >
        対象製品
      </Dropdown>
      <div>
        <Dropdown name="year" value={plan.year} onChange={setPlan} list={nowYearArray()}>
          年
        </Dropdown>
        <Dropdown name="month" value={plan.month} onChange={setPlan} list={monthArray()}>
          月
        </Dropdown>
      </div>
      <div>
        <TextInput
          name="flow"
          value={plan.flow}
          maxLength={10}
          onChange={(e) => setPlan(defaultEvent(e, onlyNumber))}
        >
          予定数量
        </TextInput>
        <span>{plan.material?.unit ?? ""}</span>
      </div>
    </div>
  );
};

export default CommonPlan;