export const HISTORY_USER = ["CREATE", "CHANGE", "DELETE"] as const;
export type HistoryUser = typeof HISTORY_USER[number];