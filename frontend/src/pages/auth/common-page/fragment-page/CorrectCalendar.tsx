import DefaultButton from "../../components/DefaultButton";
import { CalendarSortConfig } from "../types/calendarSortConfig";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";
import jaLocale from "@fullcalendar/core/locales/ja";
import { useEffect, useMemo, useState } from "react";
import { EventClickArg, EventInput } from "@fullcalendar/core/index.js";
import { HolidayView } from "../../types/holidayConfig";
import { useDialog } from "../../hooks/useDialog";
import DefaultModal from "../../components/DefaultModal";
import SelectHoliday from "../components/SelectHoliday";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { MaterialResponse } from "../../types/materialResponse";
import { useGetMapping } from "../../hooks/useGetMapping";

type CorrectCalendarProps = {
  selectedMaterial: MaterialResponse | undefined;
  sortData: CalendarSortConfig;
  returnTop: () => void;
};

type Calendar = {
  holiday: string;
  code: string;
};

const CorrectCalendar = ({ selectedMaterial, sortData, returnTop}: CorrectCalendarProps) => {
  const { data: calendarArray } = useGetMapping<Calendar>({ URL: "/api/calendar", params: sortData });
  const [correctCalendar, setCorrectCalendar] = useState<Calendar[]>(calendarArray);
  useEffect(() => setCorrectCalendar(calendarArray), [calendarArray]);
  const events: EventInput[] = useMemo(
    () =>
      correctCalendar
        .filter((i) => i.code !== "WORKING")
        .map((i) => ({
          title: HolidayView(i.code),
          start: i.holiday,
          borderColor: "transparent",
          textColor: "#000000",
          backgroundColor: { ALL_DAY: "#FFB4B4", MORNING: "#FFE6E6", AFTERNOON: "#FFE6E6" }[i.code] ?? "#3788d8",
        })),
    [correctCalendar],
  );
  const { dialog, isOpen, value, setValue, confirmDialog, returnDialog } = useDialog<string>();
  const clickHandle = async (info: DateClickArg | EventClickArg) => {
    const day = "dateStr" in info ? info.dateStr : info.event.startStr;
    const isHoliday = (i: Calendar) => i.holiday === day;
    const selectedDate = correctCalendar.find(isHoliday);
    const type = await dialog(selectedDate?.code);
    if (!type) {
      return;
    }
    setCorrectCalendar((prev) =>
      selectedDate ? prev.map((i) => (isHoliday(i) ? { ...i, code: type } : i)) : [...prev, { holiday: day, code: type }],
    );
  };
  const { post } = useCommentPostMapping();
  const correctCalenderHandle = async () => {
    if (!correctCalendar || correctCalendar.length === 0) {
      alert("休日を設定してください");
      return;
    }
    const params = {
      calendars: correctCalendar,
      year: sortData.year,
      month: sortData.month,
      material: sortData.material,
    };
    await post({ URL: `/api/calendar`, params, handle: returnTop });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>{selectedMaterial ? `${selectedMaterial.name}操業日程` : "カレンダーの取り込みに失敗しました"}</h2>
      <div className="bg-white">
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          locale={jaLocale}
          initialView="dayGridMonth"
          initialDate={`${sortData.year}-${String(sortData.month).padStart(2, "0")}-01`}
          headerToolbar={{ left: "", center: "title", right: "" }}
          showNonCurrentDates={false}
          fixedWeekCount={false}
          events={events}
          dateClick={clickHandle}
          eventClick={clickHandle}
          dayCellClassNames={(arg) => (!arg.isOther ? ["cursor-pointer"] : "")}
        ></FullCalendar>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctCalenderHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setCorrectCalendar(calendarArray)}>リセット</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpen}>
        <SelectHoliday
          value={value}
          setValue={setValue}
          confirmDialog={confirmDialog}
          returnDialog={returnDialog}
        ></SelectHoliday>
      </DefaultModal>
    </div>
  );
};

export default CorrectCalendar;