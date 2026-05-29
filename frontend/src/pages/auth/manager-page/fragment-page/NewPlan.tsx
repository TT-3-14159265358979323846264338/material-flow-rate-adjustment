import { useState } from "react";
import DefaultButton from "../../components/DefaultButton";
import Dropdown from "../../components/Dropdown";
import { useGetMapping } from "../../hooks/useGetMapping";
import { MaterialResponse } from "../../types/materialResponse";
import { ReturnProps } from "../../types/returnProps";
import { materialArray } from "../../utils/materialArray";
import { monthArray, nowYearArray } from "../../utils/termArray";
import TextInput from "../../components/TextInput";

const NewPlan = ({ returnTop }: ReturnProps) => {
  const { data: materialData} = useGetMapping<MaterialResponse>({ URL: "/api/correct/material/get/data" });
  const [material, setMaterial] = useState<string>("");
  const [year, setYear] = useState<string>(String(new Date().getFullYear()));
  const [month, setMonth] = useState<string>(String(new Date().getMonth() + 2));
  const handle = () => {

  };

  return (
    <div>
      <Dropdown value={material} onChange={(e) => setMaterial(e.target.value)} list={materialArray(materialData)}>
        対象製品
      </Dropdown>
      <div>
        <Dropdown value={year} onChange={(e) => setYear(e.target.value)} list={nowYearArray()}>
          年
        </Dropdown>
        <Dropdown value={month} onChange={(e) => setMonth(e.target.value)} list={monthArray()}>
          月
        </Dropdown>
      </div>
      <TextInput>予定数量</TextInput>
      <div>
        <DefaultButton onClick={handle}>新規作成</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewPlan;