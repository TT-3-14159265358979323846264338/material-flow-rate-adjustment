import { useState } from "react";
import axios from "axios";
import ChangePasswordInput from "../components/ChangePasswordInput";
import DefaultButton from "../components/DefaultButton";
import { errorHandling } from "../../utils/errorHandling";
import { CommentPostResponse } from "../types/commentPostResponse";

const CorrectPassword = () => {
  const [oldPass, setOldPass] = useState<string>("");
  const [newPass, setNewPass] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const handle = async() => {
    if (newPass.trim().length === 0 || oldPass.trim().length === 0) {
      alert("以前のパスワードと新規のパスワードを共に入力してください。");
      return;
    }
    if(!confirm("パスワードを変更しますか。")){
      return;
    }
    if (isSubmitting) {
      return;
    }
    setIsSubmitting(true);
    try {
      const response = await axios.post<CommentPostResponse>("http://localhost:8080/api/correct/password", {
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
      <DefaultButton onClick={handle}>パス変更</DefaultButton>
    </div>
  );
};

export default CorrectPassword;
