import DefaultButton from "../../components/DefaultButton";
import { ActionView, type ActionCodeConfig } from "../../types/actionConfig";
import { AuthorityCodeConfig, AuthorityView } from "../../../types/roleConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryUserSort from "./HistoryUserSort";
import { useHistorySort } from "../hooks/useHistory";
import { Base } from "../../hooks/useView";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import DefaultModal from "../../components/DefaultModal";
import { HistorySortConfig, InitialHistorySort } from "../../types/historyConfig";
import { dateView } from "../../utils/dateView";

type HistoryUserResponse = {
  id: number;
  oldLoginUser: string;
  newLoginUser: string;
  oldDisplayedUser: string;
  newDisplayedUser: string;
  oldRole: AuthorityCodeConfig;
  newRole: AuthorityCodeConfig;
  action: ActionCodeConfig;
  actionUser: string;
  date: string;
};

const HistoryUser = ({ returnTop }: ReturnProps) => {
  const { finalSort, setFinalSort, sortData, setSortData, setSort, mappingData, isOpen, setIsOpen } =
    useSortGetMapping<HistorySortConfig, HistoryUserResponse, Base>({
      useSort: () => useHistorySort<HistorySortConfig>(InitialHistorySort),
      URL: "/api/history/user",
    });
    
  return (
    <div className="flex flex-col">
      <h2>ユーザー情報変更履歴</h2>
      <div className="w-195 h-75 overflow-y-auto overflow-x-auto rounded-s-md bg-white">
        <div className="min-w-max">
          <ul className="sticky top-0 text-sm border rounded-t-md bg-white">
            <li className="flex items-center ml-2 mr-2 *:ml-1 **:block">
              <span className="w-20">修正内容</span>
              <div className="w-50">
                <span className="border-b border-b-gray-200">前 ログイン名</span>
                <span>後 ログイン名</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 表示ユーザー名</span>
                <span>後 表示ユーザー名</span>
              </div>
              <div className="w-20">
                <span className="border-b border-b-gray-200">前 権限</span>
                <span>後 権限</span>
              </div>
              <span className="w-25">実行管理者</span>
              <span className="w-35">日付</span>
            </li>
          </ul>
          <ul className="min-h-max text-xs border border-b-black rounded-b-md">
            {mappingData.map((item) => (
              <li
                key={item.date}
                className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 
                  *:ml-1 **:block **:min-h-lh`}
              >
                <span className="w-20">{ActionView(item.action)}</span>
                <div className="w-50">
                  <span className="border-b border-b-gray-200">{item.oldLoginUser}</span>
                  <span>{item.newLoginUser}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldDisplayedUser}</span>
                  <span>{item.newDisplayedUser}</span>
                </div>
                <div className="w-20">
                  <span className="border-b border-b-gray-200">{AuthorityView(item.oldRole)}</span>
                  <span>{AuthorityView(item.newRole)}</span>
                </div>
                <span className="w-25">{item.actionUser}</span>
                <span className="w-35">{dateView(item.date)}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setIsOpen(true)}>ソート</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpen} setIsOpen={setIsOpen}>
        <HistoryUserSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryUserSort>
      </DefaultModal>
    </div>
  );
};

export default HistoryUser;
