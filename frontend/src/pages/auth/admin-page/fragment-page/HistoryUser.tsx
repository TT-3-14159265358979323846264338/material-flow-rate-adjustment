import DefaultButton from "../../components/DefaultButton";
import { type HistoryUserConfig } from "../../../types/historyUserConfig";
import { type Role } from "../../../types/roleConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryUserSort from "./HistoryUserSort";
import { useHistory } from "../hooks/useHistory";

type HistoryUserResponse = {
  id: number;
  oldLoginUser: string;
  newLoginUser: string;
  oldDisplayedUser: string;
  newDisplayedUser: string;
  oldRole: Role;
  newRole: Role;
  action: HistoryUserConfig;
  actionUser: string;
  date: string;
};

const HistoryUser = ({ returnTop }: ReturnProps) => {
  const { setDownloadRecord, history, view, setView, returnHistory, selectedId, setSelectedId } = useHistory<HistoryUserResponse>(
    { historyURL: "/api/history/user" },
  );

  if (view === "Sort") {
    return <HistoryUserSort returnHistory={returnHistory} setDownloadRecord={setDownloadRecord}></HistoryUserSort>;
  }
  return (
    <div className="flex flex-col">
      <h2>ユーザー情報変更履歴</h2>
      <div className="w-195 h-75 overflow-y-auto overflow-x-auto rounded-s-md bg-white">
        <div className="min-w-max">
          <ul className="sticky top-0 text-sm border rounded-t-md bg-white">
            <li className="flex items-center ml-2 mr-2">
              <span className="block w-35 ml-1">修正内容</span>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 ログイン名</span>
                <span className="flex justify-center">後 ログイン名</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 表示ユーザー名</span>
                <span className="flex justify-center">後 表示ユーザー名</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 権限</span>
                <span className="flex justify-center">後 権限</span>
              </div>
              <span className="block w-35 ml-1">実行管理者</span>
              <span className="block w-35 ml-1">日付</span>
            </li>
          </ul>
          <ul className="min-h-max text-xs border border-b-black rounded-b-md">
            {history.map((item) => (
              <li
                key={item.date}
                onClick={() => setSelectedId(item.id)}
                className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer
            ${item.id === selectedId ? " bg-gray-200" : " bg-white"}`}
              >
                <span className="block w-35 ml-1">{item.action}</span>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldLoginUser}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newLoginUser}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldDisplayedUser}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newDisplayedUser}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{item.oldRole}</span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newRole}</span>
                </div>
                <span className="block w-35 ml-1">{item.actionUser}</span>
                <span className="block w-35 ml-1">{item.date}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setView("Sort")}>ソート</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default HistoryUser;
