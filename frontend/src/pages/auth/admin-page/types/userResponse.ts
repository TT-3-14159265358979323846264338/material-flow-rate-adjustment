import { Role } from "../../../types/roleConfig";

export type UserResponse = {
  id: number;
  loginName: string;
  displayedName: string;
  role: Role;
};