import DefaultButton from "../../components/DefaultButton";
import { ReturnProps } from "../../types/returnProps";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { useCorrectMaterial } from "../hooks/useCorrectMaterial";
import CommonMaterial from "../components/CommonMaterial";

const NewMaterial = ({ returnTop }: ReturnProps) => {
  const { material, setMaterial, reset } = useCorrectMaterial();
  const {post} = useCommentPostMapping();
  const newMaterialHandle = async () => {
    if (material.name.trim().length === 0 || material.destination.trim().length === 0 || material.unit.trim().length === 0) {
      alert("製品名・向け先・納入単位を入力してください。");
      return;
    }
    if (!confirm(material.destination + "向け" + material.name + "を新規登録しますか。")) {
      return;
    }
    const params = {
      name: material.name,
      destination: material.destination,
      base: material.base,
      unit: material.unit,
    };
    await post({ URL: "/api/material", params, handle: reset });
  };

  return (
    <div className="w-70">
      <CommonMaterial material={material} setMaterial={setMaterial}></CommonMaterial>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newMaterialHandle}>新規登録</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewMaterial;
