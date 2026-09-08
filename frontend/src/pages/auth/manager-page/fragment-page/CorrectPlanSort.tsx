import CommonSort from "../../admin-page/fragment-page/CommonSort";
import { InitialPlanSort, PLAN_SORT_CODE, PlanSortConfig } from "../hooks/usePlanSort";
import PossibleEmptyDateRange from "../../components/PossibleEmptyDateRange";
import Dropdown from "../../components/Dropdown";
import { MaterialResponse } from "./PlanManagement";
import { useMemo } from "react";

type CorrentPlanSortProps = {
  finalSort: PlanSortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<PlanSortConfig>>;
  sortData: PlanSortConfig;
  setSortData: React.Dispatch<React.SetStateAction<PlanSortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
  returnTop: () => void;
  materialArray: MaterialResponse[];
};

const noSort = "指定なし";

const CorrectPlanSort = ({
  finalSort,
  setFinalSort,
  sortData,
  setSortData,
  setSort,
  returnTop,
  materialArray,
}: CorrentPlanSortProps) => {
  const materialDropList = useMemo(() => [noSort, ...materialArray?.map((item) => item.name)], [materialArray]);
  const material = useMemo(
    () => materialArray?.find((item) => item.id === sortData.material)?.name ?? noSort,
    [materialArray, sortData.material],
  );
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
        <PossibleEmptyDateRange sortData={sortData} setSort={setSort}></PossibleEmptyDateRange>
        <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
          <Dropdown name="material" value={material} onChange={setSort} list={materialDropList}>製品名</Dropdown>
        </div>
      </div>
    </CommonSort>
  );
};

export default CorrectPlanSort;
