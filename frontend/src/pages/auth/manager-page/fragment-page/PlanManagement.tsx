import DefaultButton from "../../components/DefaultButton";
import NewPlan from "./NewPlan";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useCorrect } from "../../hooks/useCorrect";
import { PlanSortConfig, usePlanSort } from "../hooks/usePlanSort";
import DefaultModal from "../../components/DefaultModal";
import CorrectPlanSort from "./CorrectPlanSort";
import { useGetMapping } from "../../hooks/useGetMapping";
import CorrectPlan from "./CorrectPlan";
import { PlanResponse } from "../types/planResponse";
import { MaterialResponse } from "../../types/materialResponse";
import HistoryPlan from "./HistoryPlan";

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
    <div className="flex flex-col items-stretch">
      <div className="flex flex-col h-83">
        <h2>計画一覧</h2>
        <ul className="border rounded-t-md bg-white">
          <li className="ml-2 mr-2 gap-2 flex items-center text-sm *:text-center *:block">
            <span className="w-35">製品名</span>
            <span className="w-35">向け先</span>
            <span className="w-10">年</span>
            <span className="w-10">月</span>
            <span className="w-15">予定数量</span>
            <span className="w-15">実績</span>
            <span className="w-20">月間出荷量</span>
            <span className="w-20">在庫調整分</span>
            <span className="w-15">月末在庫</span>
          </li>
        </ul>
        <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
          {mappingData.map((data) => (
            <li
              key={data.id}
              onClick={() => setSelectedItem(data)}
              className={`ml-2 mr-2 gap-2 flex items-center text-xs border-b border-b-gray-300 cursor-pointer *:text-center *:block
                  ${data.id === selectedItem?.id ? " bg-gray-200" : " bg-white"}`}
            >
              <span className="w-35">{data.material.name}</span>
              <span className="w-35">{data.material.destination}</span>
              <span className="w-10">{data.year}</span>
              <span className="w-10">{data.month}</span>
              <span className="w-15">{data.flow}</span>
              <span className="w-15">{data.achievement}</span>
              <span className="w-20">{data.shipping}</span>
              <span className="w-20">{data.adjustment}</span>
              <span className="w-15">{data.remaining}</span>
            </li>
          ))}
        </ul>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setIsOpenSort(true)}>ソート</DefaultButton>
        <DefaultButton onClick={() => correctHandle()}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        <DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpenSort} setIsOpen={setIsOpenSort}>
        <CorrectPlanSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
          materialArray={materialArray}
        ></CorrectPlanSort>
      </DefaultModal>
      <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
        <CorrectPlan
          selectedPlan={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
          materialData={materialArray}
        ></CorrectPlan>
      </DefaultModal>
    </div>
  );
};

export default PlanManagement;
