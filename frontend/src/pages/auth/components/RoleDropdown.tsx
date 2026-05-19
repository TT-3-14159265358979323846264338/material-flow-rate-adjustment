import { Dispatch, SetStateAction } from "react";
import Dropdown from "./Dropdown";
import { ROLES, Role } from "../../types/roleConfig"

type RoleDropdownProps = {
  role: string;
  setRole: Dispatch<SetStateAction<Role>>;
};

const RoleDropdown = ({role, setRole}: RoleDropdownProps) => (
  <Dropdown
    children="付与権限"
    value={role}
    onChange={(e) => setRole(e.target.value as Role)}
    list={ROLES}>
  </Dropdown>
);

export default RoleDropdown;