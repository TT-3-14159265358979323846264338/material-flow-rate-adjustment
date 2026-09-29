import { useCorrect } from "../../hooks/useCorrect";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { CalenderResponse } from "../types/calenderResponse";

type CalenderViewConfig = "Top" | "New" | "History";

const CalenderManegement = () => {
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
  } = useSortGetMapping<UserSortConfig, CalenderResponse, CalenderViewConfig>({
    useSort: useUserSort,
    URL: "/api/user",
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