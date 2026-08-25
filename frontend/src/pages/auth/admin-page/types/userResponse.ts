import { AuthorityCodeConfig } from "../../../types/roleConfig";

export type UserResponse = {
  id: number;
  loginName: string;
  displayedName: string;
  role: AuthorityCodeConfig;
};