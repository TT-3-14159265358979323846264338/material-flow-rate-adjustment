import { Dispatch, SetStateAction } from "react";
import Dropdown from "../../components/Dropdown";
import { ROLES, Role } from "../../../types/roleConfig"

type RoleDropdownProps = {
  role: string;
  setRole: Dispatch<SetStateAction<Role>>;
};

const RoleDropdown = ({role, setRole}: RoleDropdownProps) => (
  <Dropdown
    value={role}
    onChange={(e) => setRole(e.target.value as Role)}
    list={ROLES}
  >
  付与権限
  </Dropdown>
);

export default RoleDropdown;