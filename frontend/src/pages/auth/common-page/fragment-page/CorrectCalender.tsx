import DefaultButton from "../../components/DefaultButton";
import { CalenderResponse } from "../types/calenderResponse";
import { CalenderSortConfig } from "../types/calenderSortConfig";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";
import jaLocale from "@fullcalendar/core/locales/ja";
import { useMemo, useState } from "react";
import { EventInput } from "@fullcalendar/core/index.js";
import { IsAllDayHoliday, HolidayView, HolidayCodeConfig } from "../../types/holidayConfig";

type CorrectCalenderProps = {
  calenderName: string;
  calender: CalenderResponse[];
  targetCalender: CalenderSortConfig;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

type Calender = {
  holiday: string;
  code: HolidayCodeConfig;
};

const CorrectCalender = ({
  calenderName,
  calender,
  targetCalender,
  returnFromNotCorrect,
  returnFromCorrect,
}: CorrectCalenderProps) => {
  const defaultCalender = useMemo(
    () => calender.filter((i) => 0 <= i.id).map((i) => ({ holiday: i.holiday, code: i.code })),
    [calender],
  );
  const [correctCalender, setcorrectCalender] = useState<Calender[]>(defaultCalender);
  const events: EventInput[] = useMemo(
    () =>
      correctCalender.map((i) => ({
        title: HolidayView(i.code),
        start: i.holiday,
        borderColor: "transparent",
        textColor: "#000000",
        backgroundColor: IsAllDayHoliday(i.code) ? "#FFB4B4" : "#FFE6E6",
      })),
    [correctCalender],
  );
  const dateClick = (info: DateClickArg) => {
    //setcorrectCalenderで日付の修正・追加処理
  };
  const correctCalenderHandle = async () => {
    //await post({ URL: `後で`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>{calenderName}</h2>
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        locale={jaLocale}
        initialView="dayGridMonth"
        initialDate={`${targetCalender.year}-${String(targetCalender.month).padStart(2, "0")}-01`}
        events={events}
        dateClick={dateClick}
      ></FullCalendar>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctCalenderHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setcorrectCalender(defaultCalender)}>リセット</DefaultButton>
        <DefaultButton onClick={returnFromNotCorrect}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectCalender;