import { useState } from "react";
import MaterialNameInput from "../components/MaterialNameInput";
import MaterialDestinationInput from "../components/MaterialDestinationInput";
import CheckInput from "../../components/CheckInput";
import DefaultButton from "../../components/DefaultButton";
import NewMaterial from "./NewMaterial";
import CorrectMaterialSort from "./CorrectMaterialSort";
import HistoryMaterial from "./HistoryMaterial";
import { MaterialResponse } from "../../types/materialResponse";
import MaterialBaseInput from "../components/MaterialBaseInput";
import MaterialUnitInput from "../components/MaterialUnitInput";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { MaterialSortConfig, useMaterialSort } from "../hooks/useMaterialSort";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";

type MaterialViewConfig = "Top" | "New" | "History";

const CorrectMatterial = () => {
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
    returnTop,
    newDataReturnTop,
    isOpen,
    setIsOpen,
  } = useSortGetMapping<MaterialSortConfig, MaterialResponse, MaterialViewConfig>({
    useSort: useMaterialSort,
    URL: "/api/material",
  });

  const [selectedMaterial, setSelectedMaterial] = useState<MaterialResponse>();
  const [newName, setNewName] = useState<string>("");
  const [newDestination, setDestination] = useState<string>("");
  const [newBase, setNewBase] = useState<string>("");
  const [newUnit, setNewUnit] = useState<string>("");
  const [isDeleted, setIsDeleted] = useState<boolean>(false);
  const {post} = useCommentPostMapping();

  const correctMaterialHandle = async () => {
    if (!selectedMaterial) {
      alert("修正したい製品を選択して、必要項目に入力してください。");
      return;
    }
    const canCorrect = isDeleted
      ? confirm("対象の製品削除を本当に実行してもよいですか。")
      : confirm("対象の製品を修正しますか。");
    if (!canCorrect) {
      return;
    }
    const params = {
      newName,
      newDestination,
      newBase,
      newUnit,
      isDeleted,
    };
    const handle = async() => {
      setSelectedMaterial(undefined);
      await getMappingData();
    };
    await post({ URL: `/api/material/${selectedMaterial.id}`, params, handle });
  };
  if (view === "New") {
    return <NewMaterial returnTop={newDataReturnTop}></NewMaterial>;
  }
  if(view === "History"){
    return <HistoryMaterial returnTop={returnTop}></HistoryMaterial>;
  }
  return (
    <div className="flex flex-col items-stretch">
      <div className="flex">
        <div className="flex flex-col w-130 h-83 mr-5">
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
                onClick={() => {
                  setSelectedMaterial(data);
                  setNewName(data.name);
                  setDestination(data.destination);
                  setIsDeleted(false);
                  data.base ? setNewBase(data.base) : setNewBase("");
                  setNewUnit(data.unit);
                }}
                className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer *:text-left
                  ${data.id === selectedMaterial?.id ? " bg-gray-200" : " bg-white"}`}
              >
                <span className="block w-60">{data.name}</span>
                <span className="flex-1">{data.destination}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="w-50 ml-5">
          <h2>修正内容</h2>
          <span className="text-xs text-left pb-2">※空欄/未変更項目は修正しない。</span>
          {selectedMaterial ? (
            <div>
              <div className="mb-5">
                <MaterialNameInput name={newName} setName={setNewName}></MaterialNameInput>
                <MaterialDestinationInput name={newDestination} setName={setDestination}></MaterialDestinationInput>
                <div className="flex gap-5">
                  <MaterialBaseInput base={newBase} setBase={setNewBase}></MaterialBaseInput>
                  <MaterialUnitInput unit={newUnit} setUnit={setNewUnit}></MaterialUnitInput>
                </div>
              </div>
              <CheckInput isChecked={isDeleted} setChecked={(e) => setIsDeleted(e.target.checked)}>
                製品削除
              </CheckInput>
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setIsOpen(true)}>ソート</DefaultButton>
        <DefaultButton onClick={correctMaterialHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        <DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpen} setIsOpen={setIsOpen}>
        <CorrectMaterialSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpen(false)}
        ></CorrectMaterialSort>
      </DefaultModal>
    </div>
  );
};

export default CorrectMatterial;
