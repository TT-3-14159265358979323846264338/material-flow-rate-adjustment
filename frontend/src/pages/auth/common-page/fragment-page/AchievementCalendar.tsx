import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";
import jaLocale from "@fullcalendar/core/locales/ja";
import { EventClickArg, EventInput } from "@fullcalendar/core/index.js";
import DefaultButton from "../../components/DefaultButton";
import { useGetMapping } from "../../hooks/useGetMapping";
import { PlanResponse } from "../../manager-page/types/planResponse";
import { CalendarConfig } from "../types/calendarResponse";
import { useMemo, useState } from "react";
import { HolidayView } from "../../types/holidayConfig";
import { AchievementResponse } from "../../types/achievementConfig";

type CorrectCalendarProps = {
  plan: PlanResponse | null;
  newHandle?: (planId: number, unit: string, date: string, base: string) => Promise<boolean>;
  correctHandle?: (planId: number, unit: string, achievement: AchievementResponse) => Promise<boolean>;
  returnTop: () => void;
};

const AchievementCalendar = ({ plan, newHandle, correctHandle, returnTop }: CorrectCalendarProps) => {
  const { data: calendar } = useGetMapping<CalendarConfig>({
    URL: "/api/calendar",
    params: { year: plan?.year, month: plan?.month, material: plan?.material.id },
  });
  const { data: achievement, getData: getAchievement } = useGetMapping<AchievementResponse>({
    URL: `/api/achievement/${plan?.id}`,
  });
  const events: EventInput[] = useMemo(
    () => {
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
        ...achievement.map((i) => ({
          title: `${i.lot} ${i.flow}${plan?.material.unit}`,
          start: i.date,
          borderColor: "transparent",
          textColor: { PASSED: "#0000FF", FAILED: "#FF0000", UNINSPECTION: "#000000" }[i.inspection],
          backgroundColor: "#FFFFFF",
          extendedProps: { achievement: i, existsClickHandle: correctHandle ? true : false, index: index++ },
        })),
      ];
    },
    [calendar, achievement, plan?.material.unit, correctHandle],
  );
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
      <h2 className="text-left pl-10">{plan ? `${plan?.material.name}実績` : "カレンダーの取り込みに失敗しました"}</h2>
      <div className="bg-white h-75">
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