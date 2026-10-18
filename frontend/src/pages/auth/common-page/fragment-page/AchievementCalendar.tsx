import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";
import jaLocale from "@fullcalendar/core/locales/ja";
import { EventClickArg, EventInput } from "@fullcalendar/core/index.js";
import DefaultButton from "../../components/DefaultButton";
import { useGetMapping } from "../../hooks/useGetMapping";
import { PlanResponse } from "../../manager-page/types/planResponse";
import { CalendarConfig } from "../types/calendarResponse";
import { useMemo } from "react";
import { HolidayView } from "../../types/holidayConfig";
import { AchievementConfig } from "../../types/achievementConfig";

type CorrectCalendarProps = {
  plan: PlanResponse | null;
  newHandle?: (planId: number, unit: string, date: string) => Promise<boolean>;
  correctHandle?: (planId: number, unit: string, achievement: AchievementConfig) => Promise<boolean>;
  returnTop: () => void;
};

const AchievementCalendar = ({ plan, newHandle, correctHandle, returnTop }: CorrectCalendarProps) => {
  const { data: calendar } = useGetMapping<CalendarConfig>({
    URL: "/api/calendar",
    params: { year: plan?.year, month: plan?.month, material: plan?.material },
  });
  const { data: achievement, getData: getAchievement } = useGetMapping<AchievementConfig>({
    URL: `/api/achievement/${plan?.id}`,
  });
  const events: EventInput[] = useMemo(
    () => [
      ...calendar.map((i) => ({
        title: HolidayView(i.code),
        start: i.holiday,
        borderColor: "transparent",
        textColor: "#000000",
        backgroundColor: { ALL_DAY: "#FFB4B4", MORNING: "#FFE6E6", AFTERNOON: "#FFE6E6" }[i.code] ?? "#3788d8",
        extendedProps: { existsClickHandle: false },
      })),
      ...achievement.map((i) => ({
        title: `${i.lot} ${i.flow}${plan?.material.unit}`,
        start: i.date,
        borderColor: "transparent",
        textColor: { PASSED: "#0000FF", FAILED: "#FF0000", UNINSPECTION: "#000000" }[i.inspection],
        backgroundColor: "#FFFFFF",
        extendedProps: { achievement: i, existsClickHandle: correctHandle ? true : false },
      })),
    ],
    [calendar, achievement, plan?.material.unit, correctHandle],
  );
  const dateClick = async (info: DateClickArg) => {
    if (!newHandle || calendar.find((i) => i.holiday === info.dateStr && i.code === "ALL_DAY") || !plan) {
      return;
    }
    if (await newHandle(plan.id, plan.material.unit, info.dateStr)) {
      getAchievement();
    }
  };
  const eventClick = async (info: EventClickArg) => {
    if ((!info.event.extendedProps.existsClickHandle as boolean) || !plan) {
      return;
    }
    if (await correctHandle?.(plan.id, plan.material.unit, info.event.extendedProps.achievement as AchievementConfig)) {
      getAchievement();
    }
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>{plan? `${plan?.material.name}実績` : "カレンダーの取り込みに失敗しました"}</h2>
      <div className="bg-white">
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          locale={jaLocale}
          initialView="dayGridMonth"
          initialDate={`${plan?.year}-${String(plan?.month).padStart(2, "0")}-01`}
          headerToolbar={{ left: "", center: "title", right: "" }}
          showNonCurrentDates={false}
          fixedWeekCount={false}
          events={events}
          dateClick={dateClick}
          eventClick={eventClick}
          dayCellClassNames={(arg) => (!arg.isOther ? ["cursor-pointer"] : [])}
        ></FullCalendar>
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default AchievementCalendar;