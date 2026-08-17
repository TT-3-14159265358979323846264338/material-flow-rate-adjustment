import { useState } from "react";
import axios from "axios";
import DefaultButton from "../../components/DefaultButton";
import { errorHandling } from "../../../utils/errorHandling";
import MaterialNameInput from "../components/MaterialNameInput";
import MaterialDestinationInput from "../components/MaterialDestinationInput";
import { CommentPostResponse } from "../../types/commentPostResponse";
import { ReturnProps } from "../../types/returnProps";
import MaterialBaseInput from "../components/MaterialBaseInput";
import MaterialUnitInput from "../components/MaterialUnitInput";

const NewMaterial = ({ returnTop }: ReturnProps) => {
  const [name, setName] = useState("");
  const [destination, setDestination] = useState("");
  const [base, setBase] = useState<string>("");
  const [unit, setUnit] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const handle = async () => {
    if (name.trim().length === 0 || destination.trim().length === 0 || unit.trim().length === 0) {
      alert("製品名・向け先・納入単位を入力してください。");
      return;
    }
    if (!confirm(destination + "向け" + name + "を新規登録しますか。")) {
      return;
    }
    if (isSubmitting) {
      return;
    }
    setIsSubmitting(true);
    try {
      const response = await axios.post<CommentPostResponse>(
        import.meta.env.VITE_BACK_BASE_API + "/api/material/new",
        {
          name,
          destination,
          base,
          unit,
        },
      );
      alert(response.data.comment);
      setName("");
      setDestination("");
      setBase("");
      setUnit("");
    } catch (error) {
      errorHandling(error);
    } finally {
      setIsSubmitting(false);
    }
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
        <DefaultButton onClick={handle}>新規登録</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewMaterial;
