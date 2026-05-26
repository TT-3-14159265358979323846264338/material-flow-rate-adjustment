export const HISTORY_USER = ["CREATE", "CHANGE", "DELETE"] as const;
export type HistoryUserConfig = typeof HISTORY_USER[number];