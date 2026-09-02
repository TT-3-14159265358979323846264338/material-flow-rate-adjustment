import { useState } from "react";
import MaterialNameInput from "../components/MaterialNameInput";
import MaterialDestinationInput from "../components/MaterialDestinationInput";
import CheckInput from "../../components/CheckInput";
import DefaultButton from "../../components/DefaultButton";
import { MaterialResponse } from "../../types/materialResponse";
import MaterialBaseInput from "../components/MaterialBaseInput";
import MaterialUnitInput from "../components/MaterialUnitInput";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";

type CorrectMatterialProps = {
  selectedMaterial: MaterialResponse | undefined;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

const CorrectMatterial = ({ selectedMaterial, returnFromNotCorrect, returnFromCorrect }: CorrectMatterialProps) => {
  const [newName, setNewName] = useState<string>(selectedMaterial?.name ?? "");
  const [newDestination, setDestination] = useState<string>(selectedMaterial?.destination ?? "");
  const [newBase, setNewBase] = useState<string>(selectedMaterial?.base ?? "");
  const [newUnit, setNewUnit] = useState<string>(selectedMaterial?.unit ?? "");
  const [isDeleted, setIsDeleted] = useState<boolean>(false);
  const { post } = useCommentPostMapping();

  const correctMaterialHandle = async () => {
    if (!selectedMaterial) {
      return;
    }
    const canCorrect = isDeleted
      ? confirm("対象の製品削除を本当に実行してもよいですか。")
      : confirm("対象の製品を修正しますか。");
    if (!canCorrect) {
      return;
    }
    const params = {
      newName,
      newDestination,
      newBase,
      newUnit,
      isDeleted,
    };
    await post({ URL: `/api/material/${selectedMaterial.id}`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>修正内容</h2>
      <span className="text-xs text-left mb-5">※空欄/未変更項目は修正しない。</span>
      {selectedMaterial ? (
        <div className="*:mb-5">
          <div className="w-80">
            <MaterialNameInput name={newName} setName={setNewName}></MaterialNameInput>
            <MaterialDestinationInput name={newDestination} setName={setDestination}></MaterialDestinationInput>
            <div className="flex gap-5">
              <MaterialBaseInput base={newBase} setBase={setNewBase}></MaterialBaseInput>
              <MaterialUnitInput unit={newUnit} setUnit={setNewUnit}></MaterialUnitInput>
            </div>
          </div>
          <div className="flex justify-center">
            <CheckInput isChecked={isDeleted} setChecked={(e) => setIsDeleted(e.target.checked)}>
              製品削除
            </CheckInput>
          </div>
        </div>
      ) : (
        <div></div>
      )}

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctMaterialHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={returnFromNotCorrect}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectMatterial;
