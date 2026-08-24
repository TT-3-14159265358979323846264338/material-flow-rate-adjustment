import { useState } from "react";
import { AxiosResponse } from 'axios';
import DefaultButton from "../../components/DefaultButton";
import RoleDropdown from "../components/RoleDropdown";
import LoginUserNameInput from "../components/LoginUserNameInput";
import { ROLES, type Role } from "../../../types/roleConfig"
import DisplayedUserNameInput from "../components/DisplyedUserNameInput";
import { ReturnProps } from "../../types/returnProps";
import { usePostMapping } from "../../hooks/usePostMapping";

type NewUserResponse = {
  password: string;
};

const NewUser = ({returnTop}: ReturnProps) => {
  const [loginName, setLoginName] = useState("");
  const [displayedName, setDisplayedName] = useState("");
  const [role, setRole] = useState<Role>(ROLES[1]);
  const { post } = usePostMapping<NewUserResponse>();
  const newUserHandle = async () => {
    if (loginName.trim().length === 0) {
      alert("ユーザー名を入力してください。");
      return;
    }
    if (!confirm(loginName + "に" + role + "権限付与して新規登録しますか。")) {
      return;
    }
    const params = { loginName, displayedName, role };
    const handle = (response: AxiosResponse<NewUserResponse, any, {}>) => {
      alert(
          `${displayedName}が新規登録されました。\n` +
          `初期パスワードは${response.data.password}になっています。\n` +
          "早期にパスワードの変更をお願いします。",
      );
      setLoginName("");
      setDisplayedName("");
    };
    await post({ URL: "/api/user", params, handle});
  };

  return (
    <div className="w-50">
      <LoginUserNameInput name={loginName} setName={setLoginName}></LoginUserNameInput>
      <DisplayedUserNameInput name={displayedName} setName={setDisplayedName}></DisplayedUserNameInput>
      <RoleDropdown role={role} setRole={setRole}></RoleDropdown>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newUserHandle}>新規登録</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewUser;