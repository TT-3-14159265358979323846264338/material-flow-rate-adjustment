import CheckInput from "../../components/CheckInput";
import DefaultButton from "../../components/DefaultButton";
import { MaterialResponse } from "../../types/materialResponse";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { CorrectMaterialConfig, useCorrectMaterial } from "../hooks/useCorrectMaterial";
import CommonMaterial from "../components/CommonMaterial";

type CorrectMatterialProps = {
  selectedMaterial: MaterialResponse | undefined;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

const initialCorrectMaterial = (originalMaterial: MaterialResponse | undefined): CorrectMaterialConfig => ({
  name: originalMaterial?.name ?? "",
  destination: originalMaterial?.destination ?? "",
  base: originalMaterial?.base ?? "",
  unit: originalMaterial?.unit ?? "",
  isDeleted: false,
});

const CorrectMatterial = ({ selectedMaterial, returnFromNotCorrect, returnFromCorrect }: CorrectMatterialProps) => {
  const { material, setMaterial } = useCorrectMaterial(initialCorrectMaterial(selectedMaterial));
  const { post } = useCommentPostMapping();

  const correctMaterialHandle = async () => {
    if (!selectedMaterial) {
      return;
    }
    const canCorrect = material.isDeleted
      ? confirm("対象の製品削除を本当に実行してもよいですか。")
      : confirm("対象の製品を修正しますか。");
    if (!canCorrect) {
      return;
    }
    const params = {
      newName: material.name,
      newDestination: material.destination,
      newBase: material.base,
      newUnit: material.unit,
      isDeleted: material.isDeleted,
    };
    await post({ URL: `/api/material/${selectedMaterial.id}`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>修正内容</h2>
      <span className="text-xs text-left mb-5">※空欄/未変更項目は修正しない。</span>
      {selectedMaterial ? (
        <div className="*:mb-5">
          <CommonMaterial material={material} setMaterial={setMaterial}></CommonMaterial>
          <div className="flex justify-center">
            <CheckInput isChecked={material.isDeleted} setChecked={setMaterial}>
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
