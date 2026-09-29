import { useStateElements } from "../../hooks/useStateElements";
import { AuthorityCodeConfig } from "../../../types/roleConfig";

export type CorrectUserConfig = {
  loginName: string;
  displayedName: string;
  role: AuthorityCodeConfig;
  isDeleted: boolean;
};

const initialUser: CorrectUserConfig = {
  loginName: "",
  displayedName: "",
  role: "USER",
  isDeleted: false,
};

export const useCorrectUser = (initial?: CorrectUserConfig) => {
  const { data: user, setData, setState: setUser } = useStateElements<CorrectUserConfig>(initial ?? initialUser);
  const reset = () => setData(initial ?? initialUser);
  return { user, setUser, reset };
};