import { useState } from "react";
import DefaultButton from "../../components/DefaultButton";
import LoginUserNameInput from "../components/LoginUserNameInput";
import RoleDropdown from "../components/RoleDropdown";
import CheckInput from "../../components/CheckInput";
import DisplayedUserNameInput from "../components/DisplyedUserNameInput";
import { UserResponse } from "../types/userResponse";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { AuthorityCodeConfig } from "../../../types/roleConfig";

type CorrectUserProps = {
  selectedUser: UserResponse | undefined;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

const CorrectUser = ({ selectedUser, returnFromNotCorrect, returnFromCorrect }: CorrectUserProps) => {
  const [newLoginName, setNewLoginName] = useState<string>(selectedUser?.loginName ?? "");
  const [newDisplayedName, setDisplayedName] = useState<string>(selectedUser?.displayedName ?? "");
  const [newRole, setNewRole] = useState<AuthorityCodeConfig>(selectedUser?.role ?? "USER");
  const [isDeleted, setIsDeleted] = useState<boolean>(false);
  const { post } = useCommentPostMapping();

  const correctUserHandle = async () => {
    if (!selectedUser) {
      return;
    }
    const canCorrect = isDeleted
      ? confirm("対象のアカウント削除を本当に実行してもよいですか。")
      : confirm("対象のアカウントを修正しますか。");
    if (!canCorrect) {
      return;
    }
    const params = {
      newLoginName,
      newDisplayedName,
      newRole,
      isDeleted,
    };
    await post({ URL: `/api/user/${selectedUser.id}`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>修正内容</h2>
      <span className="text-xs text-left mb-5">※空欄/未変更項目は修正しない。</span>
      {selectedUser ? (
        <div className="*:mb-5">
          <div className="w-80">
            <LoginUserNameInput name={newLoginName} setName={setNewLoginName}></LoginUserNameInput>
            <DisplayedUserNameInput name={newDisplayedName} setName={setDisplayedName}></DisplayedUserNameInput>
            <RoleDropdown role={newRole} setRole={setNewRole}></RoleDropdown>
          </div>
          <div className="flex justify-center">
            <CheckInput isChecked={isDeleted} setChecked={(e) => setIsDeleted(e.target.checked)}>
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