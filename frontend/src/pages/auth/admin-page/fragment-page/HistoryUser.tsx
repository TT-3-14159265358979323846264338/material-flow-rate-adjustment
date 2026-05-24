import { useState, useEffect } from "react";
import axios from 'axios';
import DefaultButton from "../../components/DefaultButton";
import { type HistoryUser } from "../../../types/historyUserConfig";
import { type Role } from "../../../types/roleConfig";
import { errorHandling } from "../../../utils/errorHandling";

type HistoryUserResponse = {
  targetId: number;
  oldUser: string;
  newUser: string;
  oldRole: Role;
  newRole: Role;
  action: HistoryUser;
  actionUser: string;
  date: string;
};

const HistoryUser = () => {
  const [history, setHistory] = useState<HistoryUserResponse[]>([]);
  const [sortHistory, setSortHistory] = useState<HistoryUserResponse[]>([]);
  const [selectedId, setSelectedId] = useState<number>();
  const [isFilter, setIsFilter] = useState<boolean>(false);

  useEffect(() => {
    const getHistoryData = async() => {
      try{
        const response = await axios.get<HistoryUserResponse[]>("http://localhost:8080/api/history/user/get/data");
        setHistory(response.data);
        setSortHistory(response.data);
      } catch (error) {
        errorHandling(error);
      }
    };
    getHistoryData();
  }, []);

  const filterHandle = () => {
    if(!selectedId){
      alert("ユーザーが選択されていません。\n" + 
        "ユーザーを選択するとそのユーザーの修正履歴が抽出されます。");
      return;
    }
    if(isFilter){
      setSortHistory(history);
      setIsFilter(false);
      return;
    }
    const filterArray = history.filter((data) => data.targetId === selectedId);
    setSortHistory(filterArray);
    setIsFilter(true);
  };

  return(
    <div className="flex flex-col">
      <h2>ユーザー情報変更履歴</h2>
      <ul className="w-195 border rounded-t-md bg-white">
        <li className="flex items-center ml-2 mr-2">
          <span className="block w-30 ml-1">修正内容</span>
          <div className="block w-50 ml-1">
            <span className="flex justify-center border-b border-b-gray-200">修正前 ユーザー名</span>
            <span className="flex justify-center">修正後 ユーザー名</span>
          </div>
          <div className="block w-30 ml-1">
            <span className="flex justify-center border-b border-b-gray-200">修正前 権限</span>
            <span className="flex justify-center">修正後 権限</span>
          </div>
          <span className="block w-30 ml-1">実行管理者</span>
          <span className="flex-1 ml-1">日付</span>
        </li>
      </ul>
      <ul className="w-195 h-63 overflow-y-auto border border-b-black rounded-b-md bg-white">
        {sortHistory.map((history) => (
          <li 
          key={history.date} 
          onClick={() => setSelectedId(history.targetId)}
          className={`flex ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer
            ${history.targetId === selectedId? " bg-gray-200": " bg-white"}`
          }>
            <span className="block w-30 ml-1">{history.action}</span>
            <div className="block w-50 ml-1">
              <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{history.oldUser}</span>
              <span className="flex justify-center after:content-['\00a0']">{history.newUser}</span>
            </div>
            <div className="block w-30 ml-1">
              <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{history.oldRole}</span>
              <span className="flex justify-center after:content-['\00a0']">{history.newRole}</span>
            </div>
            <span className="block w-30 ml-1">{history.actionUser}</span>
            <span className="flex-1 ml-1">{history.date}</span>
          </li>
        ))}
      </ul>
      <div className="flex justify-center">
        <DefaultButton onClick={filterHandle}>{isFilter? "抽出解除": "ユーザー抽出"}</DefaultButton>
      </div>
    </div>
  );
};

export default HistoryUser;