import CheckInput from "../../components/CheckInput";
import { AUTHORITY_CODE } from "../../../types/roleConfig";
import CommonSort from "../../admin-page/fragment-page/CommonSort";
import { InitialPlanSort, PLAN_SORT_CODE, PlanSortConfig } from "../hooks/usePlanSort";

type CorrentPlanSortProps = {
  finalSort: PlanSortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<PlanSortConfig>>;
  sortData: PlanSortConfig;
  setSortData: React.Dispatch<React.SetStateAction<PlanSortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement>) => void;
  returnTop: () => void;
};

const CorrectPlanSort = ({ finalSort, setFinalSort, sortData, setSortData, setSort, returnTop }: CorrentPlanSortProps) => {
  return (
    <CommonSort
      sortCode={PLAN_SORT_CODE}
      initialSort={InitialPlanSort}
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

export default CorrectPlanSort;
