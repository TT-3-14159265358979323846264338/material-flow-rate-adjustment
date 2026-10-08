import { useMemo, useState } from "react";
import DateDropdown from "../../components/DateDropDown";
import DefaultButton from "../../components/DefaultButton";
import DefaultModal from "../../components/DefaultModal";
import Dropdown from "../../components/Dropdown";
import { useGetMapping } from "../../hooks/useGetMapping";
import { useMaterialView } from "../../hooks/useMaterialView";
import { MaterialResponse } from "../../types/materialResponse";
import { useCalendarSort } from "../hooks/useCalendarSort";
import { CalendarSortConfig, InitialCalendarSort } from "../types/calendarSortConfig";
import CorrectCalendar from "./CorrectCalendar";
import { useView } from "../../hooks/useView";

type CalendarViewConfig = "Top" | "History";

const CalendarManegement = () => {
  const { data: materialArray, getData: getMaterialArray } = useGetMapping<MaterialResponse>({ URL: "/api/material/all" });
  const { sortData, setSortData, setSort } = useCalendarSort(materialArray);
  const { view, setView, returnTop: returnFromHistory } = useView<CalendarViewConfig>({ getData: getMaterialArray });
  const [isOpenCorrect, setIsOpenCorrect] = useState<boolean>(false);
  const returnFromCorrect = () => setIsOpenCorrect(false);
  const { materialDropList, material } = useMaterialView<CalendarSortConfig>({ materialArray, sortData });
  const selectedMaterial: MaterialResponse | undefined = useMemo(
    () => materialArray.find((i) => i.id === sortData.material),
    [materialArray, sortData.material],
  );
  const sortHandle = () => {
    if (!selectedMaterial) {
      alert("対象製品を入力してください");
      return;
    }
    setIsOpenCorrect(true);
  };
  
  if (view === "History") {
    //後で変更履歴書く
  }
  return (
    <div className="flex flex-col items-stretch">
      <h3 className="text-left ml-5">表示する日程</h3>
      <div className="border rounded-md bg-white px-10 py-5 mb-3">
        <Dropdown name="material" value={material} onChange={setSort} list={materialDropList}>
          製品名
        </Dropdown>
      </div>
      <DateDropdown sortData={sortData} setSort={setSort}></DateDropdown>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>日程確認</DefaultButton>
        <DefaultButton onClick={() => setSortData(InitialCalendarSort)}>リセット</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
        <CorrectCalendar
          selectedMaterial={selectedMaterial}
          sortData={sortData}
          returnTop={returnFromCorrect}
        ></CorrectCalendar>
      </DefaultModal>
    </div>
  );
}

export default CalendarManegement;