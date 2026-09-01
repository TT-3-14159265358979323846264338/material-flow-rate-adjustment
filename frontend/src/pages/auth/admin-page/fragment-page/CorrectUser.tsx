import { useState } from "react";
import DefaultButton from "../../components/DefaultButton";
import LoginUserNameInput from "../components/LoginUserNameInput";
import RoleDropdown from "../components/RoleDropdown";
import CheckInput from "../../components/CheckInput";
import CorrectUserSort from "./CorrectUserSort";
import NewUser from "./NewUser";
import DisplayedUserNameInput from "../components/DisplyedUserNameInput";
import HistoryUser from "./HistoryUser";
import { UserResponse } from "../types/userResponse";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { UserSortConfig, useUserSort } from "../hooks/useUserSort";
import { AuthorityCodeConfig, AuthorityView } from "../../../types/roleConfig";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";

type UserViewConfig = "Top" | "New" | "History";

const CorrectUser = () => {
  const {
    finalSort,
    setFinalSort,
    sortData,
    setSortData,
    setSort,
    mappingData,
    getMappingData,
    view,
    setView,
    returnTop,
    newDataReturnTop,
    isOpen,
    setIsOpen,
  } = useSortGetMapping<UserSortConfig, UserResponse, UserViewConfig>({
    useSort: useUserSort,
    URL: "/api/user",
  });
  
  const [selectedAccount, setSelectedAccount] = useState<UserResponse>();
  const [newLoginName, setNewLoginName] = useState<string>("");
  const [newDisplayedName, setDisplayedName] = useState<string>("");
  const [newRole, setNewRole] = useState<AuthorityCodeConfig>("USER");
  const [isDeleted, setIsDeleted] = useState<boolean>(false);
  const {post} = useCommentPostMapping();

  const correctUserHandle = async () => {
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
    const params = {
      newLoginName,
      newDisplayedName,
      newRole,
      isDeleted,
    };
    const handle = async() => {
      setSelectedAccount(undefined);
      await getMappingData();      
    };
    await post({ URL: `/api/user/${selectedAccount.id}`, params, handle });
  };

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
            <li className="ml-2 mr-2 gap-2 flex items-centers *:text-left">
              <span className="block w-45">ログインユーザー名</span>
              <span className="block w-45">表示ユーザー名</span>
              <span className="flex-1">権限</span>
            </li>
          </ul>
          <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
            {mappingData.map((data) => (
              <li
                key={data.id}
                onClick={() => {
                  setSelectedAccount(data);
                  setNewLoginName(data.loginName);
                  setDisplayedName(data.displayedName);
                  setNewRole(data.role);
                  setIsDeleted(false);
                }}
                className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer *:text-left
                  ${data.id === selectedAccount?.id ? " bg-gray-200" : " bg-white"}`}
              >
                <span className="block w-45">{data.loginName}</span>
                <span className="block w-45">{data.displayedName}</span>
                <span className="flex-1">{AuthorityView(data.role)}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="w-50 ml-5">
          <h2>修正内容</h2>
          <span className="text-xs text-left pb-2">※空欄/未変更項目は修正しない。</span>
          {selectedAccount ? (
            <div>
              <div className="mb-5">
                <LoginUserNameInput name={newLoginName} setName={setNewLoginName}></LoginUserNameInput>
                <DisplayedUserNameInput name={newDisplayedName} setName={setDisplayedName}></DisplayedUserNameInput>
                <RoleDropdown role={newRole} setRole={setNewRole}></RoleDropdown>
              </div>
              <CheckInput isChecked={isDeleted} setChecked={(e) => setIsDeleted(e.target.checked)}>
                アカウント削除
              </CheckInput>
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setIsOpen(true)}>ソート</DefaultButton>
        <DefaultButton onClick={correctUserHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        <DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpen} setIsOpen={setIsOpen}>
        <CorrectUserSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpen(false)}
        ></CorrectUserSort>
      </DefaultModal>
    </div>
  );
};

export default CorrectUser;