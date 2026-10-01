import DateDropdown from "../../components/DateDropDown";
import DefaultButton from "../../components/DefaultButton";
import DefaultModal from "../../components/DefaultModal";
import Dropdown from "../../components/Dropdown";
import { useGetMapping } from "../../hooks/useGetMapping";
import { useMaterialView } from "../../hooks/useMaterialView";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { MaterialResponse } from "../../types/materialResponse";
import { useCalenderSort } from "../hooks/useCalenderSort";
import { CalenderResponse } from "../types/calenderResponse";
import { CalenderSortConfig, InitialCalenderSort } from "../types/calenderSortConfig";

type CalenderViewConfig = "Top" | "History";

const CalenderManegement = () => {
  const { data: materialArray } = useGetMapping<MaterialResponse>({ URL: "/api/material/all" });
  const {
    finalSort,
    setFinalSort,
    sortData,
    setSortData,
    setSort,
    mappingData,
    getMappingData,
    view,
    setView,
    returnTop: returnFromHistory,
    newDataReturnTop: returnFromNew,
    isOpen: isOpenSort,
    setIsOpen: setIsOpenSort,
  } = useSortGetMapping<CalenderSortConfig, CalenderResponse, CalenderViewConfig>({
    useSort: () => useCalenderSort(materialArray),
    URL: "/api/calender",
  });
  const { materialDropList, material } = useMaterialView<CalenderSortConfig>({ materialArray, sortData });
  const sortHandle = () => {
    setFinalSort(sortData);
    setIsOpenSort(true);
  };

  return (
    <div className="flex flex-col items-stretch">
      <h3 className="text-left ml-5">日程指定</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        <Dropdown name="material" value={material} onChange={setSort} list={materialDropList}>
          製品名
        </Dropdown>
        <DateDropdown sortData={sortData} setSort={setSort}></DateDropdown>
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>日程確認</DefaultButton>
        <DefaultButton onClick={() => setSortData(InitialCalenderSort)}>リセット</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpenSort} setIsOpen={setIsOpenSort}>
        <div></div>
      </DefaultModal>
    </div>
  );
}

export default CalenderManegement;