import { useMemo } from "react";
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
import CorrectCalender from "./CorrectCalender";
import { useCorrect } from "../../hooks/useCorrect";

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
  } = useSortGetMapping<CalenderSortConfig, CalenderResponse, CalenderViewConfig>({
    useSort: () => useCalenderSort(materialArray),
    URL: "/api/calender",
  });
  const {
    isOpen: isOpenCorrect,
    setIsOpen: setIsOpenCorrect,
    returnFromNotCorrect,
    returnFromCorrect,
  } = useCorrect<CalenderResponse>(getMappingData);
  const { materialDropList, material } = useMaterialView<CalenderSortConfig>({ materialArray, sortData });
  const calenderName: string = useMemo(() => {
    if (!finalSort.material || !materialArray[finalSort.material]) {
      return "カレンダーの取り込みに失敗しました";
    }
    const material = materialArray[finalSort.material];
    return `${finalSort.year}年${finalSort.month}月の${material.name}操業日程`;
  }, [materialArray, finalSort]);
  const sortHandle = () => {
    if (!materialArray[sortData.material?? -1]) {
      alert("対象製品を入力してください");
      return;
    }
    setFinalSort(sortData);
    setIsOpenCorrect(true);
  };
  
  if (view === "History") {
    //後で変更履歴書く
  }
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

      <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
        <CorrectCalender
          calenderName={calenderName}
          calender={mappingData}
          targetCalender={finalSort}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
        ></CorrectCalender>
      </DefaultModal>
    </div>
  );
}

export default CalenderManegement;
