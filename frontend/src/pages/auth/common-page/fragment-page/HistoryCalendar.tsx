import { ActionCodeConfig, ActionView } from "../../types/actionConfig";
import { useHistorySort } from "../../admin-page/hooks/useHistory";
import DefaultButton from "../../components/DefaultButton";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { Base } from "../../hooks/useView";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { ReturnProps } from "../../types/returnProps";
import { dateView } from "../../utils/dateView";
import { HolidayCodeConfig, HolidayView } from "../../types/holidayConfig";
import { DeletedView } from "../../types/deletedConfig";
import HistoryCalendarSort from "./HistoryCalendarSort";

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
    <div className="flex flex-col">
      <h2>休日変更履歴</h2>
      <div className="w-195 h-75 overflow-y-auto overflow-x-auto rounded-s-md bg-white">
        <div className="min-w-max">
          <ul className="sticky top-0 text-sm border rounded-t-md bg-white">
            <li className="flex items-center ml-2 mr-2 *:ml-1 **:block">
              <span className="w-20">修正内容</span>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 製品名</span>
                <span>後 製品名</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 向け先</span>
                <span>後 向け先</span>
              </div>
              <div className="w-20">
                <span className="border-b border-b-gray-200">前 日付</span>
                <span>後 日付</span>
              </div>
              <div className="w-25">
                <span className="border-b border-b-gray-200">前 休日タイプ</span>
                <span>後 休日タイプ</span>
              </div>
              <div className="w-20">
                <span className="border-b border-b-gray-200">前 論理削除</span>
                <span>後 論理削除</span>
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
                <span className="w-20">{ActionView(item.action)}</span>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldName}</span>
                  <span>{item.newName}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldDestination}</span>
                  <span>{item.newDestination}</span>
                </div>
                <div className="w-20">
                  <span className="border-b border-b-gray-200">{item.oldHoliday}</span>
                  <span>{item.newHoliday}</span>
                </div>
                <div className="w-25">
                  <span className="border-b border-b-gray-200">{HolidayView(item.oldType)}</span>
                  <span>{HolidayView(item.newType)}</span>
                </div>
                <div className="w-20">
                  <span className="border-b border-b-gray-200">{DeletedView(item.hasDeletedOld)}</span>
                  <span>{DeletedView(item.hasDeletedNew)}</span>
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
        <HistoryCalendarSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryCalendarSort>
      </DefaultModal>
    </div>
  );
};

export default HistoryCalendar;