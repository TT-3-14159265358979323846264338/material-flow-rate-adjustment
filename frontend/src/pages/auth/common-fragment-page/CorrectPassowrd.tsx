import { useState } from "react";
import axios from "axios";
import ChangePasswordInput from "../components/ChangePasswordInput";
import DefaultButton from "../components/DefaultButton";
import { errorHandling } from "../../utils/errorHandling";
import { CommentPostResponse } from "../types/commentPostResponse";

type CorrectPasswordProps = {
  returnTop: () => void;
};

const CorrectPassword = ({ returnTop }: CorrectPasswordProps) => {
  const [oldPass, setOldPass] = useState<string>("");
  const [newPass, setNewPass] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const handle = async () => {
    if (newPass.trim().length === 0 || oldPass.trim().length === 0) {
      alert("以前のパスワードと新規のパスワードを共に入力してください。");
      return;
    }
    if (!confirm("パスワードを変更しますか。")) {
      return;
    }
    if (isSubmitting) {
      return;
    }
    setIsSubmitting(true);
    try {
      const response = await axios.post<CommentPostResponse>(import.meta.env.VITE_BACK_BASE_API + "/api/password", {
        oldPass,
        newPass,
      });
      alert(response.data.comment);
      setOldPass("");
      setNewPass("");
    } catch (error) {
      errorHandling(error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div>
      <ChangePasswordInput
        newPass={newPass}
        setNewPass={setNewPass}
        oldPass={oldPass}
        setOldPass={setOldPass}
      ></ChangePasswordInput>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={handle}>パスワード変更</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectPassword;
