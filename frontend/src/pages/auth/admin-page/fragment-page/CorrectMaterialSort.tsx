import RadioInput from "../../components/RadioInput";
import DefaultButton from "../../components/DefaultButton";
import { ORDER_CODE } from "../../types/orderConfig";
import { MATERAIL_SORT_CODE, MaterialSortConfig } from "../hooks/useMaterialSort";

type CorrentMaterialSortProps = {
  finalSort: MaterialSortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<MaterialSortConfig>>;
  sortData: MaterialSortConfig;
  setSortData: React.Dispatch<React.SetStateAction<MaterialSortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement>) => void;
  returnTop: () => void;
};

const CorrectMaterialSort = ({ finalSort, setFinalSort, sortData, setSortData, setSort, returnTop }: CorrentMaterialSortProps) => {
  const sortHandle = () => {
    setFinalSort(sortData);
    returnTop();
  };
  const returnHandle = () => {
    setSortData(finalSort);
    returnTop();
  };

  return (
    <div className="flex flex-col items-stretch">
      <h3 className="text-left ml-5">昇降順</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {ORDER_CODE.map((item) => (
          <RadioInput key={item.code} selected={sortData.order} setSelected={setSort} value={item.code} groupName="order">
            {item.view}
          </RadioInput>
        ))}
      </div>
      <h3 className="text-left ml-5">並び替え</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {MATERAIL_SORT_CODE.map((item) => (
          <RadioInput key={item.code} selected={sortData.target} setSelected={setSort} value={item.code} groupName="target">
            {item.view}
          </RadioInput>
        ))}
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>ソート</DefaultButton>
        <DefaultButton onClick={returnHandle}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectMaterialSort;
