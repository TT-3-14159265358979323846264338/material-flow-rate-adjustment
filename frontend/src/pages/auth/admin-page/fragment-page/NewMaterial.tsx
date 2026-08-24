import { useState } from "react";
import DefaultButton from "../../components/DefaultButton";
import MaterialNameInput from "../components/MaterialNameInput";
import MaterialDestinationInput from "../components/MaterialDestinationInput";
import { ReturnProps } from "../../types/returnProps";
import MaterialBaseInput from "../components/MaterialBaseInput";
import MaterialUnitInput from "../components/MaterialUnitInput";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";

const NewMaterial = ({ returnTop }: ReturnProps) => {
  const [name, setName] = useState("");
  const [destination, setDestination] = useState("");
  const [base, setBase] = useState<string>("");
  const [unit, setUnit] = useState<string>("");
  const {post} = useCommentPostMapping();
  const newMaterialHandle = async () => {
    if (name.trim().length === 0 || destination.trim().length === 0 || unit.trim().length === 0) {
      alert("製品名・向け先・納入単位を入力してください。");
      return;
    }
    if (!confirm(destination + "向け" + name + "を新規登録しますか。")) {
      return;
    }
    const params = {
      name,
      destination,
      base,
      unit,
    };
    const handle = () => {
      setName("");
      setDestination("");
      setBase("");
      setUnit("");
    };
    await post({ URL: "/api/material", params, handle });
  };

  return (
    <div className="w-70">
      <MaterialNameInput name={name} setName={setName}></MaterialNameInput>
      <MaterialDestinationInput name={destination} setName={setDestination}></MaterialDestinationInput>
      <div className="flex gap-5">
        <MaterialBaseInput base={base} setBase={setBase}></MaterialBaseInput>
        <MaterialUnitInput unit={unit} setUnit={setUnit}></MaterialUnitInput>
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newMaterialHandle}>新規登録</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewMaterial;
