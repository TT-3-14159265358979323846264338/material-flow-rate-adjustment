import { HolidayCodeConfig } from "../../types/holidayConfig";

export type CalendarResponse = {
  id: number;
  holiday: string;
  code: HolidayCodeConfig;
};