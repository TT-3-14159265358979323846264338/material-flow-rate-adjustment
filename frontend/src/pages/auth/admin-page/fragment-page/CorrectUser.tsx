import { useState, useEffect } from "react";
import axios from 'axios';
import DefaultButton from "../../components/DefaultButton";
import UserNameInput from "../../components/UserNameInput";
import RoleDropdown from "../../components/RoleDropDown";
import CheckInput from "../../components/CheckInput";
import ChangePasswordInput from "../../components/ChangePassWordInput";
import { isThisAccountId } from "../../utils/isThisAccountId";
import { networkError } from "../../../utils/networkError";
import { ROLES, Role } from "../../../types/roleConfig";

type CorrectUserResponse = {
  id: number;
  username: string;
  role: Role;
};

const CorrectUser = () => {
  const [accountData, setAccountData] = useState<CorrectUserResponse[]>([]);
  const [sortData, setSortData] = useState<CorrectUserResponse[]>([]);
  const [selectedAccount, setSelectedAccount] = useState<CorrectUserResponse>();
  const [newName, setNewName] = useState("");
  const [newRole, setNewRole] = useState<Role>(ROLES[1]);
  const [isDeleted, setIsDeleted] = useState<boolean>(false);
  const [newPass, setNewPass] = useState("");
  const [oldPass, setOldPass] = useState("");
  useEffect(() => {
    const getAccountData = async () => {
      try{
        const response = await axios.get<CorrectUserResponse[]>("http://localhost:8080/api/correct/user/admin/ver");
        setAccountData(response.data);
        setSortData(response.data);
      } catch (error) {
        networkError(error, (_) => {
          alert("データの取得に失敗しました。\n再度やり直してください。");
        });
      }
    };
    getAccountData();
  }, []);
  const correctHandle = () => {
    
  };
  const sortHandle = () => {

  };

  return(
    <div className="flex flex-col items-stretch">
      <div className="flex">
        <div className="flex flex-col w-120 h-83 mr-5">
          <h2>ユーザー一覧</h2>
          <ul className="border rounded-t-md bg-white">
            <li className="ml-2 mr-2 gap-2 flex items-center border-b-gray-300">
              <span className="block w-80 text-left">ユーザー名</span>
              <span className="flex-1 text-left">権限</span>
            </li>
          </ul>
          <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
            {sortData.map((data) => (
              <li
                key={data.id}
                onClick={() => {
                  setSelectedAccount(data);
                  setNewName(data.username);
                  setNewRole(data.role);
                  setIsDeleted(false);
                  setNewPass("")
                  setOldPass("")
                }}
                className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer
                  ${data.id === selectedAccount?.id? " bg-gray-200": " bg-white"}`
                }>
                  <span className="block w-80 text-left">{data.username}</span>
                  <span className="flex-1 text-left">{data.role}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="w-50 ml-5">
          <h2>修正内容</h2>
          {selectedAccount?
            isThisAccountId(selectedAccount.id)? (
              <div>
                <UserNameInput name={selectedAccount.username} setName={setNewName}></UserNameInput>
                <RoleDropdown role={selectedAccount.role} setRole={setNewRole}></RoleDropdown>
                <ChangePasswordInput setNewPass={setNewPass} setOldPass={setOldPass}></ChangePasswordInput>
              </div>
            ): (
              <div>
                <div className="mb-10">
                  <UserNameInput name={selectedAccount.username} setName={setNewName}></UserNameInput>
                  <RoleDropdown role={selectedAccount.role} setRole={setNewRole}></RoleDropdown>
                </div>
                <CheckInput children="アカウント削除" isChecked={isDeleted} setChecked={setIsDeleted}></CheckInput>
              </div>
            ): <div></div>}
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>ソート</DefaultButton>
        <DefaultButton onClick={correctHandle}>登録修正</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectUser;