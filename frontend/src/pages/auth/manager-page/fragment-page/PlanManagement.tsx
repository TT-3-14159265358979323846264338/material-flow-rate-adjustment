import { useState } from "react";
import { useGetMapping } from "../../hooks/useGetMapping";
import { useView } from "../../hooks/useView";
import DefaultButton from "../../components/DefaultButton";
import { DefaultViewConfig } from "../../types/ViewConfig";
import NewPlan from "./NewPlan";

type PlanManagementResponse = {
  id: number;
  name: string;
  destination: string;
  year: string;
  month: string;
  flow: number;
  achievement: number;
  shipping: number;
  adjustment: number;
  remaining: number;
};

const PlanManagement = () => {
  const {
    sortData: planData,
    setSortData: setPlanData,
    getData: getPlanData,
  } = useGetMapping<PlanManagementResponse>({
    URL: "/api/correct/plan/get/data",
    params: { number: 50 },
  });
  const { view, setView, returnTop, newDataReturnTop } = useView<DefaultViewConfig>({ getData: getPlanData });
  const [selectedPlan, setSelectedPlan] = useState<PlanManagementResponse>();

  if (view === "Sort") {
    return <div></div>;
  }
  if (view === "Correct") {
    return <div></div>;
  }
  if (view === "New") {
    return <NewPlan returnTop={newDataReturnTop}></NewPlan>;
  }
  if (view === "History") {
    return <div></div>;
  }
  return (
    <div className="flex">
      <div className="flex flex-col w-130 h-83 mr-5">
        <h2>計画一覧</h2>
        <div className="w-325 overflow-x-auto whitespace-nowrap rounded-s-md bg-white">
          <ul className="min-w-max text-sm border rounded-t-md">
            <li className="ml-2 mr-2 gap-2 flex items-center *:block *:w-35 *:text-left">
              <span>製品名</span>
              <span>向け先</span>
              <span>年</span>
              <span>月</span>
              <span>予定数量</span>
              <span>実績</span>
              <span>月間出荷量</span>
              <span>在庫調整分</span>
              <span>月末在庫</span>
            </li>
          </ul>
          <ul className="min-w-max text-xs h-61 overflow-y-auto border border-b-black rounded-b-md">
            {planData.map((data) => (
              <li
                key={data.id}
                onClick={() => setSelectedPlan(data)}
                className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer *:block *:w-35 *:text-left
                  ${data.id === selectedPlan?.id ? " bg-gray-200" : " bg-white"}`}
              >
                <span>{data.name}</span>
                <span>{data.destination}</span>
                <span>{data.year}</span>
                <span>{data.month}</span>
                <span>{data.flow}</span>
                <span>{data.achievement}</span>
                <span>{data.shipping}</span>
                <span>{data.adjustment}</span>
                <span>{data.remaining}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setView("Sort")}>ソート</DefaultButton>
        <DefaultButton onClick={() => setView("Correct")}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        <DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>
      </div>
    </div>
  );
};

export default PlanManagement;
