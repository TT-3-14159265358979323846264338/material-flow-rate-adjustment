import { useState } from "react";
import axios from 'axios';
import DefaultButton from "../../components/DefaultButton";
import RoleDropdown from "../components/RoleDropdown";
import LoginUserNameInput from "../components/LoginUserNameInput";
import { ROLES, type Role } from "../../../types/roleConfig"
import { errorHandling } from "../../../utils/errorHandling";
import DisplayedUserNameInput from "../components/DisplyedUserNameInput";
import { ReturnProps } from "../../types/returnProps";

type NewUserResponse = {
  password: string;
};

const NewUser = ({returnTop}: ReturnProps) => {
  const [loginName, setLoginName] = useState("");
  const [displayedName, setDisplayedName] = useState("");
  const [role, setRole] = useState<Role>(ROLES[1]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const handle = async () => {
    if (loginName.trim().length === 0) {
      alert("ユーザー名を入力してください。");
      return;
    }
    if (!confirm(loginName + "に" + role + "権限付与して新規登録しますか。")) {
      return;
    }
    if (isSubmitting) {
      return;
    }
    setIsSubmitting(true);
    try {
      const response = await axios.post<NewUserResponse>(
        "http://localhost:8080/api/user/new",
        { loginName, displayedName, role },
      );
      const password = response.data.password;
      alert(
        displayedName +
          "が新規登録されました。\n" +
          "初期パスワードは" +
          password +
          "になっています。\n" +
          "早期にパスワードの変更をお願いします。",
      );
      setLoginName("");
      setDisplayedName("");
    } catch (error) {
      errorHandling(error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="w-50">
      <LoginUserNameInput name={loginName} setName={setLoginName}></LoginUserNameInput>
      <DisplayedUserNameInput name={displayedName} setName={setDisplayedName}></DisplayedUserNameInput>
      <RoleDropdown role={role} setRole={setRole}></RoleDropdown>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={handle}>新規登録</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewUser;