import CheckInput from "../../components/CheckInput";
import { InitialUserSort, USER_SORT_CODE, UserSortConfig } from "../hooks/useUserSort";
import { AUTHORITY_CODE } from "../../../types/roleConfig";
import CommonSort from "./CommonSort";

type CorrentUserSortProps = {
  finalSort: UserSortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<UserSortConfig>>;
  sortData: UserSortConfig;
  setSortData: React.Dispatch<React.SetStateAction<UserSortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement>) => void;
  returnTop: () => void;
};

const CorrectUserSort = ({ finalSort, setFinalSort, sortData, setSortData, setSort, returnTop }: CorrentUserSortProps) => {
  return (
    <CommonSort
      sortCode={USER_SORT_CODE}
      initialSort={InitialUserSort}
      finalSort={finalSort}
      setFinalSort={setFinalSort}
      sortData={sortData}
      setSortData={setSortData}
      setSort={setSort}
      returnTop={returnTop}
    >
      <div>
        <h3 className="text-left ml-5">絞り込み</h3>
        <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
          <CheckInput key={AUTHORITY_CODE[0].code} isChecked={sortData.isAdmin} setChecked={setSort} name="isAdmin">
            {AUTHORITY_CODE[0].view}
          </CheckInput>
          <CheckInput key={AUTHORITY_CODE[1].code} isChecked={sortData.isUser} setChecked={setSort} name="isUser">
            {AUTHORITY_CODE[1].view}
          </CheckInput>
          <CheckInput key={AUTHORITY_CODE[2].code} isChecked={sortData.isManager} setChecked={setSort} name="isManager">
            {AUTHORITY_CODE[2].view}
          </CheckInput>
        </div>
      </div>
    </CommonSort>
  );
};

export default CorrectUserSort;