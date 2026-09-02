import DefaultButton from "../../components/DefaultButton";
import NewMaterial from "./NewMaterial";
import CorrectMaterialSort from "./CorrectMaterialSort";
import HistoryMaterial from "./HistoryMaterial";
import { MaterialResponse } from "../../types/materialResponse";
import { MaterialSortConfig, useMaterialSort } from "../hooks/useMaterialSort";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import CorrectMatterial from "./CorrectMaterial";
import { useCorrect } from "../hooks/useCorrect";

type MaterialViewConfig = "Top" | "New" | "History";

const MaterialManegement = () => {
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
  } = useSortGetMapping<MaterialSortConfig, MaterialResponse, MaterialViewConfig>({
    useSort: useMaterialSort,
    URL: "/api/material",
  });
  const {
    selectedItem,
    setSelectedItem,
    isOpen: isOpenCorrect,
    setIsOpen: setIsOpenCorrect,
    correctHandle,
    returnFromNotCorrect,
    returnFromCorrect,
  } = useCorrect<MaterialResponse>(getMappingData);

  if (view === "New") {
    return <NewMaterial returnTop={returnFromNew}></NewMaterial>;
  }
  if (view === "History") {
    return <HistoryMaterial returnTop={returnFromHistory}></HistoryMaterial>;
  }
  return (
    <div className="flex flex-col items-stretch">
      <div className="flex flex-col w-160 h-83">
        <h2>製品一覧</h2>
        <ul className="border rounded-t-md bg-white">
          <li className="ml-2 mr-2 gap-2 flex items-center *:text-left">
            <span className="block w-60">製品名</span>
            <span className="flex-1">向け先</span>
          </li>
        </ul>
        <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
          {mappingData.map((data) => (
            <li
              key={data.id}
              onClick={() => setSelectedItem(data)}
              className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer *:text-left
                  ${data.id === selectedItem?.id ? " bg-gray-200" : " bg-white"}`}
            >
              <span className="block w-60">{data.name}</span>
              <span className="flex-1">{data.destination}</span>
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
        <CorrectMaterialSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
        ></CorrectMaterialSort>
      </DefaultModal>
      <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
        <CorrectMatterial
          selectedMaterial={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
        ></CorrectMatterial>
      </DefaultModal>
    </div>
  );
};

export default MaterialManegement;