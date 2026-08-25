import { Dispatch, SetStateAction } from "react";
import Dropdown from "../../components/Dropdown";
import { AllAuthorityView, AuthorityCode, AuthorityCodeConfig } from "../../../types/roleConfig"

type RoleDropdownProps = {
  role: string;
  setRole: Dispatch<SetStateAction<AuthorityCodeConfig>>;
};

const RoleDropdown = ({role, setRole}: RoleDropdownProps) => (
  <Dropdown
    value={role}
    onChange={(e) => setRole(AuthorityCode(e.target.value) as AuthorityCodeConfig)}
    list={AllAuthorityView()}
  >
  付与権限
  </Dropdown>
);

export default RoleDropdown;