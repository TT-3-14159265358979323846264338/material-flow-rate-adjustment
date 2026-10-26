import NewPlan from "./NewPlan";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useCorrect } from "../../hooks/useCorrect";
import { usePlanSort } from "../hooks/usePlanSort";
import CorrectPlanSort from "./CorrectPlanSort";
import { useGetMapping } from "../../hooks/useGetMapping";
import CorrectPlan from "./CorrectPlan";
import { PlanResponse } from "../types/planResponse";
import { MaterialResponse } from "../../types/materialResponse";
import HistoryPlan from "./HistoryPlan";
import { PlanSortConfig } from "../types/planSortConfig";
import CommonManegement from "../../components/CommonManegement";

type DefaultViewConfig = "Top" | "New" | "History";

const PlanManagement = () => {
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
  } = useSortGetMapping<PlanSortConfig, PlanResponse, DefaultViewConfig>({
    useSort: () => usePlanSort(materialArray),
    URL: "/api/plan",
  });
  const {
    selectedItem,
    setSelectedItem,
    isOpen: isOpenCorrect,
    setIsOpen: setIsOpenCorrect,
    correctHandle,
    returnFromNotCorrect,
    returnFromCorrect,
  } = useCorrect<PlanResponse>(getMappingData);

  if (view === "New") {
    return <NewPlan materialData={materialArray} returnTop={returnFromNew}></NewPlan>;
  }
  if (view === "History") {
    return <HistoryPlan returnTop={returnFromHistory}></HistoryPlan>;
  }
  return (
    <CommonManegement
      title="計画一覧"
      selectedItem={selectedItem}
      setSelectedItem={setSelectedItem}
      isOpenSort={isOpenSort}
      setIsOpenSort={setIsOpenSort}
      isOpenCorrect={isOpenCorrect}
      setIsOpenCorrect={setIsOpenCorrect}
      setView={setView}
      mappingData={mappingData}
      correctHandle={correctHandle}
      comment={
        <div>
          <span className="w-35">製品名</span>
          <span className="w-35">向け先</span>
          <span className="w-10">年</span>
          <span className="w-10">月</span>
          <span className="w-15">予定数量</span>
          <span className="w-15">実績</span>
          <span className="w-20">月間出荷量</span>
          <span className="w-20">在庫調整分</span>
          <span className="flex-1">月末在庫</span>
        </div>
      }
      data={(item: PlanResponse) => (
        <div>
          <span className="w-35">{item.material.name}</span>
          <span className="w-35">{item.material.destination}</span>
          <span className="w-10">{item.year}</span>
          <span className="w-10">{item.month}</span>
          <span className="w-15">{item.flow}</span>
          <span className="w-15">{item.achievement}</span>
          <span className="w-20">{item.shipping}</span>
          <span className="w-20">{item.adjustment}</span>
          <span className="flex-1">{item.remaining}</span>
        </div>
      )}
      sortModal={
        <CorrectPlanSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
          materialArray={materialArray}
        ></CorrectPlanSort>
      }
      correctModal={
        <CorrectPlan
          selectedPlan={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
          materialData={materialArray}
        ></CorrectPlan>
      }
    ></CommonManegement>
  );
};

export default PlanManagement;