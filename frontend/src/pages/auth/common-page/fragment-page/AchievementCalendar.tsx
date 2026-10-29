import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";
import jaLocale from "@fullcalendar/core/locales/ja";
import { EventClickArg, EventInput } from "@fullcalendar/core/index.js";
import DefaultButton from "../../components/DefaultButton";
import { useGetMapping } from "../../hooks/useGetMapping";
import { PlanResponse } from "../../manager-page/types/planResponse";
import { CalendarConfig } from "../types/calendarResponse";
import { useCallback, useEffect, useMemo, useState } from "react";
import { HolidayView } from "../../types/holidayConfig";
import { AchievementResponse } from "../../types/achievementConfig";
import axios from "axios";
import { errorHandling } from "../../../utils/errorHandling";

type CorrectCalendarProps = {
  plan: PlanResponse | null;
  newHandle?: (planId: number, unit: string, date: string, base: string) => Promise<boolean>;
  correctHandle?: (planId: number, unit: string, achievement: AchievementResponse) => Promise<boolean>;
  returnTop: () => void;
};

type TotalAchievementResponse = {
  passed: string;
  uninspection: string;
  allAchievement: AchievementResponse[];
  schedule: ScheduleConfig[];
  unit: string;
};

type ScheduleConfig = {
  date: string;
  quantity: string;
};

const initial: TotalAchievementResponse = {
  passed: "",
  uninspection: "",
  allAchievement: [],
  schedule: [],
  unit: "",
};

const AchievementCalendar = ({ plan, newHandle, correctHandle, returnTop }: CorrectCalendarProps) => {
  const { data: calendar } = useGetMapping<CalendarConfig>({
    URL: "/api/calendar",
    params: { year: plan?.year, month: plan?.month, material: plan?.material.id },
  });
  const [achievement, setAchievement] = useState<TotalAchievementResponse>(initial);
  const getAchievement = useCallback(async () => {
    try {
      const response = await axios.get<TotalAchievementResponse>(
        import.meta.env.VITE_BACK_BASE_API + `/api/achievement/${plan?.id}`,
      );
      setAchievement(response.data);
    } catch (error) {
      errorHandling(error);
    }
  }, [plan?.id]);
  useEffect(() => {
    getAchievement();
  }, [getAchievement]);
  const digit: number = useMemo(
    () =>
      [plan?.flow, achievement.passed, achievement.uninspection]
        .map((i) => String(i)?.split(".")[1]?.length ?? 0)
        .reduce((a, b) => Math.max(a, b), 0),
    [plan?.flow, achievement],
  );
  const valueView = (value: string) =>
    new Intl.NumberFormat("ja-JP", { minimumFractionDigits: digit, maximumFractionDigits: digit }).format(Number(value));
  const events: EventInput[] = useMemo(() => {
    let index = 0;
    return [
      ...calendar.map((i) => ({
        title: HolidayView(i.code),
        start: i.holiday,
        borderColor: "transparent",
        textColor: "#000000",
        backgroundColor: { ALL_DAY: "#FFB4B4", MORNING: "#FFE6E6", AFTERNOON: "#FFE6E6" }[i.code] ?? "#3788d8",
        extendedProps: { existsClickHandle: false, index: index++ },
      })),
      ...achievement.schedule.map((i) => ({
        title: `予定: ${i.quantity}${achievement.unit}`,
        start: i.date,
        borderColor: "transparent",
        textColor: "#000000",
        backgroundColor: "#B2D1F0",
        extendedProps: { existsClickHandle: false, index: index++ },
      })),
      ...achievement.allAchievement.map((i) => ({
        title: `${i.lot} ${valueView(i.flow)}${plan?.material.unit}`,
        start: i.date,
        borderColor: "transparent",
        textColor: { PASSED: "#0000FF", FAILED: "#FF0000", UNINSPECTION: "#000000" }[i.inspection],
        backgroundColor: "#FFFFFF",
        extendedProps: { achievement: i, existsClickHandle: correctHandle ? true : false, index: index++ },
      })),
    ];
  }, [calendar, achievement, plan?.material.unit, correctHandle]);
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const dateClick = async (arg: DateClickArg) => {
    if (!newHandle || calendar.find((i) => i.holiday === arg.dateStr && i.code === "ALL_DAY") || !plan) {
      return;
    }
    setIsOpen(true);
    if (await newHandle(plan.id, plan.material.unit, arg.dateStr, plan.material.base)) {
      getAchievement();
    }
    setIsOpen(false);
  };
  const eventClick = async (arg: EventClickArg) => {
    if ((!arg.event.extendedProps.existsClickHandle as boolean) || !plan) {
      return;
    }
    setIsOpen(true);
    if (await correctHandle?.(plan.id, plan.material.unit, arg.event.extendedProps.achievement as AchievementResponse)) {
      getAchievement();
    }
    setIsOpen(false);
  };

  return (
    <div className="flex flex-col items-stretch w-195">
      <h2 className="text-left pl-10">
        {plan ? `${plan.material.name} / ${plan.material.destination} 実績` : "カレンダーの取り込みに失敗しました"}
      </h2>
      <div className="flex justify-center gap-10 pb-2 *:bg-pink-200 *:py-1 *:px-5 *:rounded-md">
        <span>{`予定数量:　${valueView(plan?.flow ?? "0")} ${plan?.material.unit}`}</span>
        <span>{`確定実績:　${valueView(achievement.passed)} ${plan?.material.unit}`}</span>
        <span>{`未検査:　${valueView(achievement.uninspection)} ${plan?.material.unit}`}</span>
      </div>
      <div className="bg-white h-95">
        <div className={`h-full ${isOpen ? "hidden" : ""}`}>
          <FullCalendar
            plugins={[dayGridPlugin, interactionPlugin]}
            locale={jaLocale}
            initialView="dayGridMonth"
            initialDate={`${plan?.year}-${String(plan?.month).padStart(2, "0")}-01`}
            headerToolbar={{ left: "", center: "title", right: "" }}
            height="100%"
            showNonCurrentDates={false}
            fixedWeekCount={false}
            dateClick={dateClick}
            dayCellClassNames={(arg) => (!arg.isOther && newHandle ? ["cursor-pointer"] : [])}
            events={events}
            eventClick={eventClick}
            eventClassNames={(arg) => ((arg.event.extendedProps.existsClickHandle as boolean) ? ["cursor-pointer"] : [])}
            eventOrder="extendedProps.sortIndex"
            eventOrderStrict={true}
          ></FullCalendar>
        </div>
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default AchievementCalendar;