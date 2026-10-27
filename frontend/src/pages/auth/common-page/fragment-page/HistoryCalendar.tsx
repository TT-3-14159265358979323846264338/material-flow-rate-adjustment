import { ActionCodeConfig, ActionView } from "../../types/actionConfig";
import { useHistorySort } from "../../admin-page/hooks/useHistory";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { Base } from "../../hooks/useView";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { ReturnProps } from "../../types/returnProps";
import { DateView } from "../../utils/dateView";
import { HolidayCodeConfig, HolidayView } from "../../types/holidayConfig";
import { DeletedView } from "../../types/deletedConfig";
import HistoryCalendarSort from "./HistoryCalendarSort";
import HistoryComment from "../../components/HistoryComment";
import HistoryData from "../../components/HistoryData";
import CommonHistory from "../../components/CommonHistory";

type HistoryCalendarResponse = {
  id: number;
  oldName: string | null;
  newName: string | null;
  oldDestination: string | null;
  newDestination: string | null;
  oldHoliday: string | null;
  newHoliday: string | null;
  oldType: HolidayCodeConfig | null;
  newType: HolidayCodeConfig | null;
  hasDeletedOld: boolean | null;
  hasDeletedNew: boolean | null;
  action: ActionCodeConfig;
  actionUser: string;
  date: string;
};

const HistoryCalendar = ({ returnTop }: ReturnProps) => {
  const { finalSort, setFinalSort, sortData, setSortData, setSort, mappingData, isOpen, setIsOpen } = useSortGetMapping<
    HistorySortConfig,
    HistoryCalendarResponse,
    Base
  >({
    useSort: () => useHistorySort<HistorySortConfig>(InitialHistorySort),
    URL: "/api/history/calendar",
  });
  return (
    <CommonHistory
      title="休日変更履歴"
      isOpen={isOpen}
      setIsOpen={setIsOpen}
      returnTop={returnTop}
      mappingData={mappingData}
      comment={
        <>
          <span className="w-20">修正内容</span>
          <HistoryComment w={35} comment="製品名"></HistoryComment>
          <HistoryComment w={35} comment="向け先"></HistoryComment>
          <HistoryComment w={20} comment="日付"></HistoryComment>
          <HistoryComment w={25} comment="休日タイプ"></HistoryComment>
          <HistoryComment w={20} comment="論理削除"></HistoryComment>
          <span className="w-35">実行管理者</span>
          <span className="w-35">日付</span>
        </>
      }
      data={(item: HistoryCalendarResponse) => (
        <>
          <span className="w-20">{ActionView(item.action)}</span>
          <HistoryData w={35} before={item.oldName} after={item.newName}></HistoryData>
          <HistoryData w={35} before={item.oldDestination} after={item.newDestination}></HistoryData>
          <HistoryData w={20} before={item.oldHoliday} after={item.newHoliday}></HistoryData>
          <HistoryData w={25} before={item.oldType} after={item.newType} trans={HolidayView}></HistoryData>
          <HistoryData w={20} before={item.hasDeletedOld} after={item.hasDeletedNew} trans={DeletedView}></HistoryData>
          <span className="w-35">{item.actionUser}</span>
          <span className="w-35">{DateView(item.date)}</span>
        </>
      )}
      modal={
        <HistoryCalendarSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryCalendarSort>
      }
    ></CommonHistory>
  );
};

export default HistoryCalendar;