import DefaultButton from "../../components/DefaultButton";
import { type HistoryUserConfig } from "../../../types/historyUserConfig";
import { type Role } from "../../../types/roleConfig";
import { useHistory } from "../../hooks/useHistory";
import { ReturnProps } from "../../types/returnProps";

type HistoryUserResponse = {
  targetId: number;
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

const HistoryUser = ({returnTop}:ReturnProps) => {
  const { sortHistory, selectedId, isFilter, liHandle, filterHandle } = useHistory<HistoryUserResponse>({URL: "/api/history/user/get/data",});

  return (
    <div className="flex flex-col">
      <h2>ユーザー情報変更履歴</h2>
      <div className="w-195 overflow-x-auto whitespace-nowrap rounded-s-md bg-white">
        <ul className="min-w-max text-sm border rounded-t-md">
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
        <ul className="min-w-max text-xs h-61 overflow-y-auto border border-b-black rounded-b-md">
          {sortHistory.map((history) => (
            <li
              key={history.date}
              onClick={() => liHandle(history)}
              className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer
            ${history.targetId === selectedId ? " bg-gray-200" : " bg-white"}`}
            >
              <span className="block w-35 ml-1">{history.action}</span>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                  {history.oldLoginUser}
                </span>
                <span className="flex justify-center after:content-['\00a0']">{history.newLoginUser}</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                  {history.oldDisplayedUser}
                </span>
                <span className="flex justify-center after:content-['\00a0']">{history.newDisplayedUser}</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{history.oldRole}</span>
                <span className="flex justify-center after:content-['\00a0']">{history.newRole}</span>
              </div>
              <span className="block w-35 ml-1">{history.actionUser}</span>
              <span className="block w-35 ml-1">{history.date}</span>
            </li>
          ))}
        </ul>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={filterHandle}>{isFilter ? "抽出解除" : "ユーザー抽出"}</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default HistoryUser;