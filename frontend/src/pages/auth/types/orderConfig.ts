export const ORDER = ["昇順", "降順"] as const;
export type Order = (typeof ORDER)[number];
