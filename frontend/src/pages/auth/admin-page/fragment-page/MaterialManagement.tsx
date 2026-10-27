import NewMaterial from "./NewMaterial";
import CorrectMaterialSort from "./CorrectMaterialSort";
import HistoryMaterial from "./HistoryMaterial";
import { MaterialResponse } from "../../types/materialResponse";
import { useMaterialSort } from "../hooks/useMaterialSort";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import CorrectMatterial from "./CorrectMaterial";
import { useCorrect } from "../../hooks/useCorrect";
import { MaterialSortConfig } from "../types/materialSortConfig";
import CommonManegement from "../../components/CommonManegement";

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
    <CommonManegement
      title="製品一覧"
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
        <>
          <span className="block w-60">製品名</span>
          <span className="flex-1">向け先</span>
        </>
      }
      data={(item: MaterialResponse) => (
        <>
          <span className="block w-60">{item.name}</span>
          <span className="flex-1">{item.destination}</span>
        </>
      )}
      sortModal={
        <CorrectMaterialSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
        ></CorrectMaterialSort>
      }
      correctModal={
        <CorrectMatterial
          selectedMaterial={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
        ></CorrectMatterial>
      }
    ></CommonManegement>
  );
};

export default MaterialManegement;