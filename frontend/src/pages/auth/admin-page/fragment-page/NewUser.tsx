import { useState } from "react";
import axios from 'axios';
import DefaultButton from "../../components/DefaultButton";
import RoleDropdown from "../../components/RoleDropdown";
import UserNameInput from "../../components/UserNameInput";
import { ROLES, type Role } from "../../../types/roleConfig"
import { errorHandling } from "../../../utils/errorHandling";

type NewUserResponse = {
  password: string;
};

const NewUser = () => {
  const [name, setName] = useState("");
  const [role, setRole] = useState<Role>(ROLES[1]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const handle = async() => {
    if(name.trim().length === 0){
      alert("ユーザー名を入力してください。");
      return;
    }
    if(!confirm(name + "に" + role + "権限付与して新規登録しますか。")){
      return;
    }
    if(isSubmitting){
      return;
    }
    setIsSubmitting(true);
    try {
      const response = await axios.post<NewUserResponse>('http://localhost:8080/api/user/new', {name, role});
      const password = response.data.password;
      alert(name + "が新規登録されました。\n"
        + "初期パスワードは" + password + "になっています。\n"
        + "早期にパスワードの変更をお願いします。");
      setName("");
    } catch (error) {
      errorHandling(error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="w-50">
      <UserNameInput name={name} setName={setName}></UserNameInput>
      <RoleDropdown role={role}   setRole={setRole}></RoleDropdown>
      <DefaultButton onClick={handle}>新規登録</DefaultButton>
  </div>
  );
}

export default NewUser;