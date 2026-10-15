import { useMemo, useState } from "react";
import { useCalendarSort } from "../hooks/useCalendarSort";
import { CalendarSortConfig, InitialCalendarSort } from "../types/calendarSortConfig";
import DateDropdown from "../../components/DateDropDown";
import DefaultButton from "../../components/DefaultButton";
import Dropdown from "../../components/Dropdown";
import { useGetMapping } from "../../hooks/useGetMapping";
import { useMaterialView } from "../../hooks/useMaterialView";
import { useView } from "../../hooks/useView";
import { MaterialResponse } from "../../types/materialResponse";
import { PlanResponse } from "../../manager-page/types/planResponse";
import axios from "axios";
import { errorHandling } from "../../../utils/errorHandling";
import AchievementCalendar from "./AchievementCalendar";
import { AchievementConfig } from "../../types/achievementConfig";

type AchievementViewConfig = "Top" | "Achievement";

type AchievementManegementProps = {
  newHandle?: (date: string) => boolean;
  correctHandle?: (achievement: AchievementConfig) => boolean;
};

const AchievementManegement = ({ newHandle, correctHandle }: AchievementManegementProps) => {
  const { data: materialArray, getData: getMaterialArray } = useGetMapping<MaterialResponse>({ URL: "/api/material/all" });
  const { sortData, setSortData, setSort } = useCalendarSort(materialArray);
  const { view, setView, returnTop } = useView<AchievementViewConfig>({ getData: getMaterialArray });
  const { materialDropList, material } = useMaterialView<CalendarSortConfig>({ materialArray, sortData });
  const selectedMaterial: MaterialResponse | undefined = useMemo(
    () => materialArray.find((i) => i.id === sortData.material),
    [materialArray, sortData.material],
  );
  const [plan, usePlan] = useState<PlanResponse | null>(null);
  const sortHandle = async () => {
    if (!sortData.material || !selectedMaterial) {
      alert("対象製品を入力してください");
      return;
    }
    try {
      const params = { year: sortData.year, month: sortData.month };
      const response = await axios.get<PlanResponse | null>(
        `${import.meta.env.VITE_BACK_BASE_API}/api/plan/${sortData.material}`,
        {
          params,
        },
      );
      if (!response) {
        alert("指定の計画が存在しません");
        return;
      }
      usePlan(response.data);
      setView("Achievement");
    } catch (error) {
      errorHandling(error);
    }
  };

  if (view === "Achievement") {
    return (
      <AchievementCalendar
        material={selectedMaterial}
        plan={plan}
        newHandle={newHandle}
        correctHandle={correctHandle}
        returnTop={returnTop}
      ></AchievementCalendar>
    );
  }
  return (
    <div className="flex flex-col items-stretch">
      <h3 className="text-left ml-5">表示する実績</h3>
      <div className="border rounded-md bg-white px-10 py-5 mb-3">
        <Dropdown name="material" value={material} onChange={setSort} list={materialDropList}>
          製品名
        </Dropdown>
      </div>
      <DateDropdown sortData={sortData} setSort={setSort}></DateDropdown>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>実績確認</DefaultButton>
        <DefaultButton onClick={() => setSortData(InitialCalendarSort)}>リセット</DefaultButton>
      </div>
    </div>
  );
};

export default AchievementManegement;