import { useState } from "react";
import axios from 'axios';
import DefaultButton from "../../components/DefaultButton";
import LoginUserNameInput from "../components/LoginUserNameInput";
import RoleDropdown from "../components/RoleDropdown";
import CheckInput from "../../components/CheckInput";
import ChangePasswordInput from "../../components/ChangePasswordInput";
import { isThisAccountId } from "../../utils/isThisAccountId";
import { errorHandling } from "../../../utils/errorHandling";
import { ROLES, type Role } from "../../../types/roleConfig";
import CorrectUserSort from "./CorrectUserSort";
import NewUser from "./NewUser";
import DisplayedUserNameInput from "../components/DisplyedUserNameInput";
import { useGetMapping } from "../../hooks/useGetMapping";
import { useView } from "../../hooks/useView";
import { CommentPostResponse } from "../../types/commentPostResponse";
import HistoryUser from "./HistoryUser";

type ViewConfig = "Top" | "Sort" | "New" | "History";

type CorrectUserResponse = {
  id: number;
  loginName: string;
  displayedName: string;
  role: Role;
};

const CorrectUser = () => {
  const {
    data: accountData,
    sortData: sortData,
    setSortData: setSortData,
    getData: getAccountData,
  } = useGetMapping<CorrectUserResponse>({ URL: "/api/correct/user/admin/get/data" });
  const { view, setView, returnTop, newDataReturnTop } = useView<ViewConfig>({ getData: getAccountData });
  const [selectedAccount, setSelectedAccount] = useState<CorrectUserResponse>();
  const [newLoginName, setNewLoginName] = useState<string>("");
  const [newDisplayedName, setDisplayedName] = useState<string>("");
  const [newRole, setNewRole] = useState<Role>(ROLES[1]);
  const [isDeleted, setIsDeleted] = useState<boolean>(false);
  const [newPass, setNewPass] = useState<string>("");
  const [oldPass, setOldPass] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const correctHandle = async () => {
    if (!selectedAccount) {
      alert("修正したいユーザーを選択して、必要項目に入力してください。");
      return;
    }
    const canCorrect = isDeleted
      ? confirm("対象のアカウント削除を本当に実行してもよいですか。")
      : confirm("対象のアカウントを修正しますか。");
    if (!canCorrect) {
      return;
    }
    if (isSubmitting) {
      return;
    }
    setIsSubmitting(true);
    try {
      const targetId = selectedAccount.id;
      const response = isThisAccountId(targetId)
        ? await axios.post<CommentPostResponse>(
            "http://localhost:8080/api/correct/user/admin/own",
            { newLoginName, newDisplayedName, newPass, oldPass },
          )
        : await axios.post<CommentPostResponse>(
            "http://localhost:8080/api/correct/user/admin/user",
            { targetId, newLoginName, newDisplayedName, newRole, isDeleted },
          );
      alert(response.data.comment);
      setSelectedAccount(undefined);
      await getAccountData();
    } catch (error) {
      errorHandling(error);
    } finally {
      setIsSubmitting(false);
    }
  };

  if(view === "Sort") {
    return <CorrectUserSort accountData={accountData} setSortData={setSortData} returnTop={returnTop}></CorrectUserSort>;
  }
  if(view === "New"){
    return <NewUser returnTop={newDataReturnTop}></NewUser>;
  }
  if(view === "History"){
    return <HistoryUser returnTop={returnTop}></HistoryUser>;
  }
  return (
    <div className="flex flex-col items-stretch">
      <div className="flex">
        <div className="flex flex-col w-130 h-83 mr-5">
          <h2>ユーザー一覧</h2>
          <ul className="border rounded-t-md bg-white">
            <li className="ml-2 mr-2 gap-2 flex items-centers">
              <span className="block w-45 text-left">ログインユーザー名</span>
              <span className="block w-45 text-left">表示ユーザー名</span>
              <span className="flex-1 text-left">権限</span>
            </li>
          </ul>
          <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
            {sortData.map((data) => (
              <li
                key={data.id}
                onClick={() => {
                  setSelectedAccount(data);
                  setNewLoginName(data.loginName);
                  setDisplayedName(data.displayedName);
                  setNewRole(data.role);
                  setIsDeleted(false);
                  setNewPass("");
                  setOldPass("");
                }}
                className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer
                  ${data.id === selectedAccount?.id ? " bg-gray-200" : " bg-white"}`}
              >
                <span className="block w-45 text-left">{data.loginName}</span>
                <span className="block w-45 text-left">{data.displayedName}</span>
                <span className="flex-1 text-left">{data.role}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="w-50 ml-5">
          <h2>修正内容</h2>
          <span className="text-xs text-left pb-2">※空欄及び未変更の項目は修正しない。</span>
          {selectedAccount ? (
            isThisAccountId(selectedAccount.id) ? (
              <div>
                <LoginUserNameInput name={newLoginName} setName={setNewLoginName}></LoginUserNameInput>
                <DisplayedUserNameInput name={newDisplayedName} setName={setDisplayedName}></DisplayedUserNameInput>
                <ChangePasswordInput
                  newPass={newPass}
                  setNewPass={setNewPass}
                  oldPass={oldPass}
                  setOldPass={setOldPass}
                ></ChangePasswordInput>
              </div>
            ) : (
              <div>
                <div className="mb-5">
                  <LoginUserNameInput name={newLoginName} setName={setNewLoginName}></LoginUserNameInput>
                  <DisplayedUserNameInput name={newDisplayedName} setName={setDisplayedName}></DisplayedUserNameInput>
                  <RoleDropdown role={newRole} setRole={setNewRole}></RoleDropdown>
                </div>
                <CheckInput isChecked={isDeleted} setChecked={setIsDeleted}>アカウント削除</CheckInput>
              </div>
            )
          ) : (
            <div></div>
          )}
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setView("Sort")}>ソート</DefaultButton>
        <DefaultButton onClick={correctHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        <DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectUser;