export const ROLES = ["ADMIN", "USER", "MANAGER"] as const;
export type Role = typeof ROLES[number];