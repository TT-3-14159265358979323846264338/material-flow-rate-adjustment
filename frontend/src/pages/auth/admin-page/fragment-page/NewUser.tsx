import { AxiosResponse } from 'axios';
import DefaultButton from "../../components/DefaultButton";
import { ReturnProps } from "../../types/returnProps";
import { usePostMapping } from "../../hooks/usePostMapping";
import { useCorrectUser } from "../hooks/useCorrectUser";
import CommonUser from "../components/CommonUser";

type NewUserResponse = {
  password: string;
};

const NewUser = ({returnTop}: ReturnProps) => {
  const { user, setUser, reset } = useCorrectUser();
  const { post } = usePostMapping<NewUserResponse>();
  const newUserHandle = async () => {
    if (user.loginName.trim().length === 0 || user.displayedName.trim().length === 0) {
      alert("ユーザー名を入力してください。");
      return;
    }
    if (!confirm(user.loginName + "に" + user.role + "権限付与して新規登録しますか。")) {
      return;
    }
    const params = { loginName: user.loginName, displayedName: user.displayedName, role: user.role };
    const handle = (response: AxiosResponse<NewUserResponse, any, {}>) => {
      alert(
        `${user.displayedName}が新規登録されました。\n` +
          `初期パスワードは${response.data.password}になっています。\n` +
          "早期にパスワードの変更をお願いします。",
      );
      reset();
    };
    await post({ URL: "/api/user", params, handle});
  };

  return (
    <div className="w-50">
      <CommonUser user={user} setUser={setUser}></CommonUser>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newUserHandle}>新規登録</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewUser;