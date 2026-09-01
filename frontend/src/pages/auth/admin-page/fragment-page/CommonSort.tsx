import RadioInput from "../../components/RadioInput";
import DefaultButton from "../../components/DefaultButton";
import { ORDER_CODE, OrderCodeConfig } from "../../types/orderConfig";
import { CommentViewCode, CommentViewConfig } from "../types/commentView";

type CommonSortProps<T, U> = {
  sortCode: U;
  initialSort: T;
  finalSort: T;
  setFinalSort: React.Dispatch<React.SetStateAction<T>>;
  sortData: T;
  setSortData: React.Dispatch<React.SetStateAction<T>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement>) => void;
  returnTop: () => void;
  children?: React.ReactElement;
};

const CommonSort = <T extends { order: OrderCodeConfig, target: CommentViewCode<U> }, U extends readonly CommentViewConfig[]>({
  sortCode,
  initialSort,
  finalSort,
  setFinalSort,
  sortData,
  setSortData,
  setSort,
  returnTop,
  children,
}: CommonSortProps<T, U>) => {
  const sortHandle = () => {
    setFinalSort(sortData);
    returnTop();
  };
  const resetHandle = () => {
    setSortData(initialSort);
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
        {sortCode.map((item) => (
          <RadioInput key={item.code} selected={sortData.target} setSelected={setSort} value={item.code} groupName="target">
            {item.view}
          </RadioInput>
        ))}
      </div>
      {children}
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>ソート</DefaultButton>
        <DefaultButton onClick={resetHandle}>リセット</DefaultButton>
        <DefaultButton onClick={returnHandle}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CommonSort;