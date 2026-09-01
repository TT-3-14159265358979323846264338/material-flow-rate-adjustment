import DefaultButton from "../../components/DefaultButton";
import { type HistoryUserConfig } from "../../../types/historyUserConfig";
import { AuthorityCodeConfig } from "../../../types/roleConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryUserSort from "./HistoryUserSort";
import { useHistorySort } from "../hooks/useHistory";
import { Base } from "../../hooks/useView";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import DefaultModal from "../../components/DefaultModal";
import { HistorySortConfig, InitialHistorySort } from "../types/historyConfig";

type HistoryUserResponse = {
  id: number;
  oldLoginUser: string;
  newLoginUser: string;
  oldDisplayedUser: string;
  newDisplayedUser: string;
  oldRole: AuthorityCodeConfig;
  newRole: AuthorityCodeConfig;
  action: HistoryUserConfig;
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
            {mappingData.map((item) => (
              <li key={item.date} className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer`}>
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
