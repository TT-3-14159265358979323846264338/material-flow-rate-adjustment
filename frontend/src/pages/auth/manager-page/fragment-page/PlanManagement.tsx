import DefaultButton from "../../components/DefaultButton";
import NewPlan from "./NewPlan";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useCorrect } from "../../admin-page/hooks/useCorrect";
import { PlanSortConfig, usePlanSort } from "../hooks/usePlanSort";

type DefaultViewConfig = "Top" | "New";

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
  } = useSortGetMapping<PlanSortConfig, PlanManagementResponse, DefaultViewConfig>({
    useSort: usePlanSort,
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
  } = useCorrect<PlanManagementResponse>(getMappingData);

  if (view === "New") {
    return <NewPlan returnTop={returnFromNew}></NewPlan>;
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
            {mappingData.map((data) => (
              <li
                key={data.id}
                onClick={() => setSelectedItem(data)}
                className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer *:block *:w-35 *:text-left
                  ${data.id === selectedItem?.id ? " bg-gray-200" : " bg-white"}`}
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
        <DefaultButton onClick={() => setIsOpenSort(true)}>ソート</DefaultButton>
        <DefaultButton onClick={() => correctHandle()}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        {/*<DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>*/}
      </div>

      {/*<DefaultModal isOpen={isOpenSort} setIsOpen={setIsOpenSort}>
        <CorrectUserSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
        ></CorrectUserSort>
      </DefaultModal>
      <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
        <CorrectUser
          selectedUser={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
        ></CorrectUser>
      </DefaultModal>*/}
    </div>
  );
};

export default PlanManagement;
