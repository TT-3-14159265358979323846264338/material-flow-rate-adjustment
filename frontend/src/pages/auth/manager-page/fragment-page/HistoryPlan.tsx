import { HistoryUserConfig } from "../../../types/historyUserConfig";
import { useHistorySort } from "../../admin-page/hooks/useHistory";
import DefaultButton from "../../components/DefaultButton";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { Base } from "../../hooks/useView";
import { HistorySortConfig, InitialHistorySort } from "../../types/historyConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryPlanSort from "./HistoryPlanSort";

type HistoryPlanResponse = {
  id: number;
  oldName: string;
  newName: string;
  oldDestination: string;
  newDestination: string;
  oldYear: number;
  newYear: number;
  oldMonth: number;
  newMonth: number;
  oldFlow: number;
  newFlow: number;
  oldAchievement: number;
  newAchievement: number;
  oldShipping: number;
  newShipping: number;
  oldAdjustment: number;
  newAdjustment: number;
  oldRemaining: number;
  newRemaining: number;
  action: HistoryUserConfig;
  actionUser: string;
  date: string;
};

const HistoryPlan = ({ returnTop }: ReturnProps) => {
  const { finalSort, setFinalSort, sortData, setSortData, setSort, mappingData, isOpen, setIsOpen } = 
    useSortGetMapping<HistorySortConfig, HistoryPlanResponse, Base>({
      useSort: () => useHistorySort<HistorySortConfig>(InitialHistorySort),
      URL: "/api/history/plan",
    });
  return (
    <div className="flex flex-col">
      <h2>計画情報変更履歴</h2>
      <div className="w-195 h-75 overflow-y-auto overflow-x-auto rounded-s-md bg-white">
        <div className="min-w-max">
          <ul className="sticky top-0 text-sm border rounded-t-md bg-white">
            <li className="flex items-center ml-2 mr-2">
              <span className="block w-35 ml-1">修正内容</span>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 製品名</span>
                <span className="flex justify-center">後 製品名</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 向け先</span>
                <span className="flex justify-center">後 向け先</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 計画年</span>
                <span className="flex justify-center">後 計画年</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 計画月</span>
                <span className="flex justify-center">後 計画月</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 計画量</span>
                <span className="flex justify-center">後 計画量</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 実績</span>
                <span className="flex justify-center">後 実績</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 出荷量</span>
                <span className="flex justify-center">後 出荷量</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 調整量</span>
                <span className="flex justify-center">後 調整量</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200">前 残数</span>
                <span className="flex justify-center">後 残数</span>
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
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{item.oldName}</span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newName}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldDestination}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newDestination}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{item.oldYear}</span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newYear}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{item.oldMonth}</span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newMonth}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{item.oldFlow}</span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newFlow}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldAchievement}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newAchievement}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldShipping}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newShipping}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldAdjustment}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newAdjustment}</span>
                </div>
                <div className="block w-35 ml-1">
                  <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                    {item.oldRemaining}
                  </span>
                  <span className="flex justify-center after:content-['\00a0']">{item.newRemaining}</span>
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
        <HistoryPlanSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryPlanSort>
      </DefaultModal>
    </div>
  );
}

export default HistoryPlan;