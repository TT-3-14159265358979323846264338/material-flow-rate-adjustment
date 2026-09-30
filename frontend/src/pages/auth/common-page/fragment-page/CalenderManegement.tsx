import { useCorrect } from "../../hooks/useCorrect";
import { useGetMapping } from "../../hooks/useGetMapping";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { MaterialResponse } from "../../types/materialResponse";
import { useCalenderSort } from "../hooks/useCalenderSort";
import { CalenderResponse } from "../types/calenderResponse";
import { CalenderSortConfig } from "../types/calenderSortConfig";

type CalenderViewConfig = "Top" | "New" | "History";

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
  const {
    selectedItem,
    setSelectedItem,
    isOpen: isOpenCorrect,
    setIsOpen: setIsOpenCorrect,
    correctHandle,
    returnFromNotCorrect,
    returnFromCorrect,
  } = useCorrect<CalenderResponse>(getMappingData);


  return (
    <div>

    </div>
  );
}

export default CalenderManegement;