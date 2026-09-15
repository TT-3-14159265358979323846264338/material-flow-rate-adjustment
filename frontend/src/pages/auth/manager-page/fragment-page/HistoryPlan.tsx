import { ActionCodeConfig, ActionView } from "../../types/actionConfig";
import { useHistorySort } from "../../admin-page/hooks/useHistory";
import DefaultButton from "../../components/DefaultButton";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { Base } from "../../hooks/useView";
import { HistorySortConfig, InitialHistorySort } from "../../types/historyConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryPlanSort from "./HistoryPlanSort";
import { dateView } from "../../utils/dateView";

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
  action: ActionCodeConfig;
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
            <li className="flex items-center ml-2 mr-2 *:ml-1 **:block">
              <span className="w-35">修正内容</span>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 製品名</span>
                <span>後 製品名</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 向け先</span>
                <span>後 向け先</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 計画年</span>
                <span>後 計画年</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 計画月</span>
                <span>後 計画月</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 計画量</span>
                <span>後 計画量</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 実績</span>
                <span>後 実績</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 出荷量</span>
                <span>後 出荷量</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 調整量</span>
                <span>後 調整量</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 残数</span>
                <span>後 残数</span>
              </div>
              <span className="w-35">実行管理者</span>
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
                <span className="w-35">{ActionView(item.action)}</span>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldName}</span>
                  <span>{item.newName}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldDestination}</span>
                  <span>{item.newDestination}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldYear}</span>
                  <span>{item.newYear}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldMonth}</span>
                  <span>{item.newMonth}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldFlow}</span>
                  <span>{item.newFlow}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldAchievement}</span>
                  <span>{item.newAchievement}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldShipping}</span>
                  <span>{item.newShipping}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldAdjustment}</span>
                  <span>{item.newAdjustment}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldRemaining}</span>
                  <span>{item.newRemaining}</span>
                </div>
                <span className="w-35">{item.actionUser}</span>
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