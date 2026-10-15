import { DateConfig } from "../../types/sortConfig";

export type CalendarSortConfig = DateConfig & {
    material: number | undefined;
  };

const defaultDate = () => {
  const date = new Date();
  return {
    year: String(date.getFullYear()),
    month: String(date.getMonth() + 1),
  };
};

export const InitialCalendarSort: CalendarSortConfig = {
  year: defaultDate().year,
  month: defaultDate().month,
  material: undefined,
};