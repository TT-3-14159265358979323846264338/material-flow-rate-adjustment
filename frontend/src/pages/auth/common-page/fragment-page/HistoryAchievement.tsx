import { useHistorySort } from "../../admin-page/hooks/useHistory";
import CommonHistory from "../../components/CommonHistory";
import HistoryComment from "../../components/HistoryComment";
import HistoryData from "../../components/HistoryData";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { Base } from "../../hooks/useView";
import { ActionCodeConfig, ActionView } from "../../types/actionConfig";
import { DeletedView } from "../../types/deletedConfig";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { ReturnProps } from "../../types/returnProps";
import { DateView } from "../../utils/dateView";
import { InspectionCodeConfig, InspectionView } from "../types/inspectionConfig";
import HistoryAchievementSort from "./HistoryAchievementSort";

type HistoryAchievementResponse = {
  id: number;
  oldUserName: string | null;
  newUserName: string | null;
  oldMaterialName: string | null;
  newMaterialName: string | null;
  oldDestination: string | null;
  newDestination: string | null;
  oldYear: number | null;
  newYear: number | null;
  oldMonth: number | null;
  newMonth: number | null;
  oldDate: string | null;
  newDate: string | null;
  oldLot: string | null;
  newLot: string | null;
  oldFlow: string | null;
  newFlow: string | null;
  oldInspection: InspectionCodeConfig | null;
  newInspection: InspectionCodeConfig | null;
  hasDeletedOld: boolean | null;
  hasDeletedNew: boolean | null;
  action: ActionCodeConfig;
  actionUser: string;
  date: string;
};

const HistoryAchievement = ({ returnTop }: ReturnProps) => {
  const { finalSort, setFinalSort, sortData, setSortData, setSort, mappingData, isOpen, setIsOpen } = useSortGetMapping<
    HistorySortConfig,
    HistoryAchievementResponse,
    Base
  >({
    useSort: () => useHistorySort<HistorySortConfig>(InitialHistorySort),
    URL: "/api/history/achievement",
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
          <HistoryComment w={35} comment="作業者"></HistoryComment>
          <HistoryComment w={35} comment="作業日"></HistoryComment>
          <HistoryComment w={75} comment="Lot"></HistoryComment>
          <HistoryComment w={20} comment="製造数量"></HistoryComment>
          <HistoryComment w={25} comment="検査結果"></HistoryComment>
          <HistoryComment w={20} comment="計画年"></HistoryComment>
          <HistoryComment w={20} comment="計画月"></HistoryComment>
          <HistoryComment w={35} comment="製品名"></HistoryComment>
          <HistoryComment w={35} comment="向け先"></HistoryComment>
          <HistoryComment w={20} comment="論理削除"></HistoryComment>
          <span className="w-35">実行管理者</span>
          <span className="w-35">日付</span>
        </>
      }
      data={(item: HistoryAchievementResponse) => (
        <>
          <span className="w-20">{ActionView(item.action)}</span>
          <HistoryData w={35} before={item.oldUserName} after={item.newUserName}></HistoryData>
          <HistoryData w={35} before={item.oldDate} after={item.newDate} trans={DateView}></HistoryData>
          <HistoryData w={75} before={item.oldLot} after={item.newLot}></HistoryData>
          <HistoryData w={20} before={item.oldFlow} after={item.newFlow}></HistoryData>
          <HistoryData w={25} before={item.oldInspection} after={item.newInspection} trans={InspectionView}></HistoryData>
          <HistoryData w={20} before={item.oldYear} after={item.newYear}></HistoryData>
          <HistoryData w={20} before={item.oldMonth} after={item.newMonth}></HistoryData>
          <HistoryData w={35} before={item.oldMaterialName} after={item.newMaterialName}></HistoryData>
          <HistoryData w={35} before={item.oldDestination} after={item.newDestination}></HistoryData>
          <HistoryData w={20} before={item.hasDeletedOld} after={item.hasDeletedNew} trans={DeletedView}></HistoryData>
          <span className="w-35">{item.actionUser}</span>
          <span className="w-35">{DateView(item.date)}</span>
        </>
      )}
      modal={
        <HistoryAchievementSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryAchievementSort>
      }
    ></CommonHistory>
  );
};

export default HistoryAchievement;