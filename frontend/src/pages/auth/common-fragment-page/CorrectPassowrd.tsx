import { useState } from "react";
import ChangePasswordInput from "../components/ChangePasswordInput";
import DefaultButton from "../components/DefaultButton";
import { useCommentPostMapping } from "../hooks/useCommentPostMapping";

type CorrectPasswordProps = {
  returnTop: () => void;
};

const CorrectPassword = ({ returnTop }: CorrectPasswordProps) => {
  const [oldPass, setOldPass] = useState<string>("");
  const [newPass, setNewPass] = useState<string>("");
  const {post} = useCommentPostMapping();

  const passwordHandle = async () => {
    if (newPass.trim().length === 0 || oldPass.trim().length === 0) {
      alert("以前のパスワードと新規のパスワードを共に入力してください。");
      return;
    }
    if (!confirm("パスワードを変更しますか。")) {
      return;
    }
    const params = {
      oldPass,
      newPass,
    };
    const handle = () => {
      setOldPass("");
      setNewPass("");
    };
    await post({ URL: "/api/password", params, handle });
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
        <DefaultButton onClick={passwordHandle}>パスワード変更</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectPassword;
