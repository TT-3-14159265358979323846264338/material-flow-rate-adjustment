import { ActionCodeConfig, ActionView } from "../../types/actionConfig";
import { useHistorySort } from "../../admin-page/hooks/useHistory";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { Base } from "../../hooks/useView";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryPlanSort from "./HistoryPlanSort";
import { DateView } from "../../utils/dateView";
import { DeletedView } from "../../types/deletedConfig";
import HistoryComment from "../../components/HistoryComment";
import HistoryData from "../../components/HistoryData";
import CommonHistory from "../../components/CommonHistory";

type HistoryPlanResponse = {
  id: number;
  oldName: string | null;
  newName: string | null;
  oldDestination: string | null;
  newDestination: string | null;
  oldYear: number | null;
  newYear: number | null;
  oldMonth: number | null;
  newMonth: number | null;
  oldFlow: string | null;
  newFlow: string | null;
  oldAchievement: string | null;
  newAchievement: string | null;
  oldShipping: string | null;
  newShipping: string | null;
  oldAdjustment: string | null;
  newAdjustment: string | null;
  oldRemaining: string | null;
  newRemaining: string | null;
  hasDeletedOld: boolean | null;
  hasDeletedNew: boolean | null;
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
    <CommonHistory
      title="計画情報変更履歴"
      isOpen={isOpen}
      setIsOpen={setIsOpen}
      returnTop={returnTop}
      mappingData={mappingData}
      comment={
        <>
          <span className="w-20">修正内容</span>
          <HistoryComment w={20} comment="計画年"></HistoryComment>
          <HistoryComment w={20} comment="計画月"></HistoryComment>
          <HistoryComment w={35} comment="製品名"></HistoryComment>
          <HistoryComment w={35} comment="向け先"></HistoryComment>
          <HistoryComment w={20} comment="計画量"></HistoryComment>
          <HistoryComment w={20} comment="実績"></HistoryComment>
          <HistoryComment w={20} comment="出荷量"></HistoryComment>
          <HistoryComment w={20} comment="調整量"></HistoryComment>
          <HistoryComment w={20} comment="残数"></HistoryComment>
          <HistoryComment w={20} comment="論理削除"></HistoryComment>
          <span className="w-35">実行管理者</span>
          <span className="w-35">日付</span>
        </>
      }
      data={(item: HistoryPlanResponse) => (
        <>
          <span className="w-20">{ActionView(item.action)}</span>
          <HistoryData w={20} before={item.oldYear} after={item.newYear}></HistoryData>
          <HistoryData w={20} before={item.oldMonth} after={item.newMonth}></HistoryData>
          <HistoryData w={35} before={item.oldName} after={item.newName}></HistoryData>
          <HistoryData w={35} before={item.oldDestination} after={item.newDestination}></HistoryData>
          <HistoryData w={20} before={item.oldFlow} after={item.newFlow}></HistoryData>
          <HistoryData w={20} before={item.oldAchievement} after={item.newAchievement}></HistoryData>
          <HistoryData w={20} before={item.oldShipping} after={item.newShipping}></HistoryData>
          <HistoryData w={20} before={item.oldAdjustment} after={item.newAdjustment}></HistoryData>
          <HistoryData w={20} before={item.oldRemaining} after={item.newRemaining}></HistoryData>
          <HistoryData w={20} before={item.hasDeletedOld} after={item.hasDeletedNew} trans={DeletedView}></HistoryData>
          <span className="w-35">{item.actionUser}</span>
          <span className="w-35">{DateView(item.date)}</span>
        </>
      )}
      modal={
        <HistoryPlanSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryPlanSort>
      }
    ></CommonHistory>
  );
}

export default HistoryPlan;