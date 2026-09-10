import DefaultButton from "../../components/DefaultButton";
import CheckInput from "../../components/CheckInput";
import { UserResponse } from "../types/userResponse";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { CorrectUserConfig, useCorrectUser } from "../hooks/useCorrectUser";
import CommonUser from "../components/CommonUser";

type CorrectUserProps = {
  selectedUser: UserResponse | undefined;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

const initialCorrectUser = (originalUser: UserResponse | undefined): CorrectUserConfig => ({
  loginName: originalUser?.loginName ?? "",
  displayedName: originalUser?.displayedName ?? "",
  role: originalUser?.role ?? "USER",
  isDeleted: false,
});

const CorrectUser = ({ selectedUser, returnFromNotCorrect, returnFromCorrect }: CorrectUserProps) => {
  const { user, setUser } = useCorrectUser(initialCorrectUser(selectedUser));
  const { post } = useCommentPostMapping();

  const correctUserHandle = async () => {
    if (!selectedUser) {
      return;
    }
    const canCorrect = user.isDeleted
      ? confirm("対象のアカウント削除を本当に実行してもよいですか。")
      : confirm("対象のアカウントを修正しますか。");
    if (!canCorrect) {
      return;
    }
    const params = {
      newLoginName: user.loginName,
      newDisplayedName: user.displayedName,
      newRole: user.role,
      isDeleted: user.isDeleted,
    };
    await post({ URL: `/api/user/${selectedUser.id}`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>修正内容</h2>
      <span className="text-xs text-left mb-5">※空欄/未変更項目は修正しない。</span>
      {selectedUser ? (
        <div className="*:mb-5">
          <CommonUser user={user} setUser={setUser}></CommonUser>
          <div className="flex justify-center">
            <CheckInput isChecked={user.isDeleted} setChecked={setUser}>
              アカウント削除
            </CheckInput>
          </div>
        </div>
      ) : (
        <div></div>
      )}

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctUserHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={returnFromNotCorrect}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectUser;