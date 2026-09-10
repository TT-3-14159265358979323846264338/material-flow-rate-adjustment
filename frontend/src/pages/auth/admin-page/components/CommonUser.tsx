import { AllAuthorityView, AuthorityCode, AuthorityView } from "../../../types/roleConfig";
import { onlyHalfWidthAlphanumericCharacters } from "../../../utils/characterLimit";
import Dropdown from "../../components/Dropdown";
import TextInput from "../../components/TextInput";
import { defaultEvent } from "../../utils/defaultEvent";
import { CorrectUserConfig } from "../hooks/useCorrectUser";

type CommonUserProps = {
  user: CorrectUserConfig;
  setUser: (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement, Element>) => void;
};

const CommonUser = ({ user, setUser }: CommonUserProps) => {
  return (
    <div className="w-80">
      <TextInput
        name="loginName"
        value={user.loginName}
        maxLength={20}
        onChange={(e) => setUser(defaultEvent(e, onlyHalfWidthAlphanumericCharacters))}
      >
        ログインユーザー名
      </TextInput>
      <TextInput name="displayedName" value={user.displayedName} maxLength={20} onChange={setUser}>
        表示ユーザー名
      </TextInput>
      <Dropdown
        name="role"
        value={AuthorityView(user.role)}
        onChange={(e) => setUser(defaultEvent(e, AuthorityCode))}
        list={AllAuthorityView()}
      >
        付与権限
      </Dropdown>
    </div>
  );
};

export default CommonUser;