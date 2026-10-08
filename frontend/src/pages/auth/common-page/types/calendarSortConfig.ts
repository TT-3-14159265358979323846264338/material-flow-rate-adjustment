import { DateConfig } from "../../types/sortConfig";

export type CalendarSortConfig = DateConfig & {
    material: number | undefined;
  };

const defaultMinTerm = () => {
  const date = new Date();
  return {
    year: String(date.getFullYear()),
    month: String(date.getMonth() + 1),
  };
};

export const InitialCalendarSort: CalendarSortConfig = {
  year: defaultMinTerm().year,
  month: defaultMinTerm().month,
  material: undefined,
};