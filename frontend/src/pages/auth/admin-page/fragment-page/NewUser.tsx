import { useState, SyntheticEvent } from "react";
import axios from 'axios';
import TextInput from "../../components/TextInput";
import SubmitButton from "../../components/SubmitButton";
import DropdownInputProps from "../../components/Dropdown";
import { ROLES, Role } from "../../../types/roleConfig"
import { onlyHalfWidthAlphanumericCharacters } from '../../../utils/characterLimit';

type NewUserResponse = {
  password: string;
};

const NewUser = () => {
  const [name, setName] = useState("");
  const [role, setRole] = useState<Role>(ROLES[1]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const handle = async(e: SyntheticEvent) => {
    e.preventDefault();
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
      if (axios.isAxiosError(error) && error.response) {
        if (error.response.status === 400) {
          alert(name + "の新規登録に失敗しました。\n"
            + error.response.data);
        }else{
          alert("システムエラーが発生しました。");
        }
      }else{
        alert("ネットワークに接続できません。通信環境を確認してください。");
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form
      onSubmit={handle}
      className="w-50">
        <TextInput
          children="ユーザー名" 
          value={name}
          maxLength={20}
          onChange={(e) => setName(onlyHalfWidthAlphanumericCharacters(e.target.value))}>
        </TextInput>
        <DropdownInputProps
          children="付与権限"
          value={role}
          onChange={(e) => setRole(e.target.value as Role)}
          list={ROLES}>
        </DropdownInputProps>
        <SubmitButton>新規登録</SubmitButton>
    </form>
  );
}

export default NewUser;