import { useState } from "react";
import DefaultButton from "../../components/DefaultButton";
import Dropdown from "../../components/Dropdown";
import { useGetMapping } from "../../hooks/useGetMapping";
import { MaterialResponse } from "../../types/materialResponse";
import { ReturnProps } from "../../types/returnProps";
import { materialArray } from "../../utils/materialArray";
import { monthArray, nowYearArray } from "../../utils/termArray";
import TextInput from "../../components/TextInput";
import { onlyNumber } from "../../../utils/characterLimit";
import axios from "axios";
import { CommentPostResponse } from "../../types/commentPostResponse";
import { errorHandling } from "../../../utils/errorHandling";

const NewPlan = ({ returnTop }: ReturnProps) => {
  const { data: materialData} = useGetMapping<MaterialResponse>({ URL: "/api/correct/material/get/data" });
  const [material, setMaterial] = useState<string>("");
  const [year, setYear] = useState<string>(String(new Date().getFullYear()));
  const [month, setMonth] = useState<string>(String(new Date().getMonth() + 2));
  const [flow, setflow] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const getSelectedMaterial = () => materialData[materialArray(materialData).indexOf(material)];
  const handle = async() => {
    if (material.trim().length === 0 || year.trim().length === 0 || month.trim().length === 0 || flow.trim().length === 0) {
      alert("全項目を入力してください。");
      return;
    }
    if (!confirm(year + "/" + month + "の" + material + "を新規登録しますか。")) {
      return;
    }
    if (isSubmitting) {
      return;
    }
    setIsSubmitting(true);
    try {
      const materialId = getSelectedMaterial().id;
      const response = await axios.post<CommentPostResponse>(
        import.meta.env.VITE_BACK_BASE_API + "/api/plan/new",
        {
          materialId,
          year,
          month,
          flow,
        },
      );
      alert(response.data.comment);
      setMaterial("");
      setflow("");
    } catch (error) {
      errorHandling(error);
    } finally {
      setIsSubmitting(false);
    }
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
      <div>
        <TextInput value={flow} maxLength={1} onChange={(e) => setflow(onlyNumber(e.target.value))}>
          予定数量
        </TextInput>
        <span>{getSelectedMaterial()?.unit || ""}</span>
      </div>
      <div>
        <DefaultButton onClick={handle}>新規作成</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewPlan;