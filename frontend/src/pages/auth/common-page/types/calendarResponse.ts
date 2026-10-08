import { HolidayCodeConfig } from "../../types/holidayConfig";

export type CalenderResponse = {
  id: number;
  holiday: string;
  code: HolidayCodeConfig;
};
