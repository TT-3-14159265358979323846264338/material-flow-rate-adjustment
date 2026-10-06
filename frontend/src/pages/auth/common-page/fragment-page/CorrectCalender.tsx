import DefaultButton from "../../components/DefaultButton";
import { CalenderResponse } from "../types/calenderResponse";
import { CalenderSortConfig } from "../types/calenderSortConfig";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";
import jaLocale from "@fullcalendar/core/locales/ja";
import { useMemo, useState } from "react";
import { EventInput } from "@fullcalendar/core/index.js";
import { HolidayView } from "../../types/holidayConfig";
import { useDialog } from "../../hooks/useDialog";
import DefaultModal from "../../components/DefaultModal";
import SelectHoliday from "../components/SelectHoliday";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";

type CorrectCalenderProps = {
  calenderName: string;
  calender: CalenderResponse[];
  targetCalender: CalenderSortConfig;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

type Calender = {
  holiday: string;
  code: string;
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
  const [correctCalender, setCorrectCalender] = useState<Calender[]>(defaultCalender);
  const events: EventInput[] = useMemo(
    () =>
      correctCalender.map((i) => ({
        title: HolidayView(i.code),
        start: i.holiday,
        borderColor: "transparent",
        textColor: "#000000",
        backgroundColor: { ALL_DAY: "#FFB4B4", MORNING: "#FFE6E6", AFTERNOON: "#FFE6E6" }[i.code] ?? "#3788d8",
      })),
    [correctCalender],
  );
  const { dialog, isOpen, value, setValue, confirmDialog, returnDialog } = useDialog<string>();
  const dateClick = async(info: DateClickArg) => {
    const isHoliday = (i: Calender) => i.holiday === info.dateStr;
    const selectedDate = correctCalender.find(isHoliday);
    const type = await dialog(selectedDate?.code);
    if (!type) {
      return;
    }
    setCorrectCalender((prev) =>
      selectedDate
        ? prev.map((i) => (isHoliday(i) ? { ...i, code: type } : i))
        : [...prev, { holiday: info.dateStr, code: type }],
    );
  };
  const { post } = useCommentPostMapping();
  const correctCalenderHandle = async () => {
    if (!correctCalender || correctCalender.length === 0) {
      alert("休日を設定してください");
      return;
    }
    const params = {
      calenders: correctCalender,
      year: targetCalender.year,
      month: targetCalender.month,
      material: targetCalender.material,
    };
    await post({ URL: `/api/calender`, params, handle: returnFromCorrect });
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
        <DefaultButton onClick={() => setCorrectCalender(defaultCalender)}>リセット</DefaultButton>
        <DefaultButton onClick={returnFromNotCorrect}>戻る</DefaultButton>
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

export default CorrectCalender;