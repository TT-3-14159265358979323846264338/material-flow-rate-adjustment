import { InitialMaterialSort, MATERAIL_SORT_CODE, MaterialSortConfig } from "../hooks/useMaterialSort";
import CommonSort from "./CommonSort";

type CorrentMaterialSortProps = {
  finalSort: MaterialSortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<MaterialSortConfig>>;
  sortData: MaterialSortConfig;
  setSortData: React.Dispatch<React.SetStateAction<MaterialSortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement>) => void;
  returnTop: () => void;
};

const CorrectMaterialSort = ({ finalSort, setFinalSort, sortData, setSortData, setSort, returnTop }: CorrentMaterialSortProps) => {
  return (
    <CommonSort
      SortCode={MATERAIL_SORT_CODE}
      InitialSort={InitialMaterialSort}
      finalSort={finalSort}
      setFinalSort={setFinalSort}
      sortData={sortData}
      setSortData={setSortData}
      setSort={setSort}
      returnTop={returnTop}
    >
    </CommonSort>
  );
};

export default CorrectMaterialSort;
