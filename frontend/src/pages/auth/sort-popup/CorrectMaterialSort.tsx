import { useState, Dispatch, SetStateAction } from "react";
import RadioInput from "../components/RadioInput";
import DefaultButton from "../components/DefaultButton";
import { type Order, ORDER } from "../types/orderConfig";

const ORDER_CODE = ["ID", "製品名", "向け先"] as const;
type OrderCode = (typeof ORDER_CODE)[number];

type CorrectMaterialResponse = {
  id: number;
  name: string;
  destination: string;
};

type CorrentMaterialSortProps = {
  materialData: CorrectMaterialResponse[];
  setSortData: Dispatch<SetStateAction<CorrectMaterialResponse[]>>;
  returnTop: () => void;
};

const CorrectMaterialSort = ({ materialData, setSortData, returnTop }: CorrentMaterialSortProps) => {
  const [order, setOrder] = useState<Order>("昇順");
  const [orderCode, setOrderCode] = useState<OrderCode>("ID");
  const sort = (array: CorrectMaterialResponse[]) => {
    switch (orderCode) {
      case "ID":
        return array;
      case "製品名":
        return array.toSorted((a, b) => a.name.localeCompare(b.name));
      case "向け先":
        return array.toSorted((a, b) => a.destination.localeCompare(b.destination));
      default:
        return array;
    }
  };
  const sortHandle = () => {
    const sortArray = sort(materialData);
    setSortData(order === "昇順" ? sortArray : sortArray.toReversed());
    returnTop();
  };

  return (
    <div className="flex flex-col items-stretch">
      <h3 className="text-left ml-5">昇降順</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {ORDER.map((code) => (
          <RadioInput key={code} selected={order} setSelected={setOrder} value={code} groupName={"O-group"}>
            {code}
          </RadioInput>
        ))}
      </div>
      <h3 className="text-left ml-5">並び替え</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {ORDER_CODE.map((code) => (
          <RadioInput key={code} selected={orderCode} setSelected={setOrderCode} value={code} groupName={"OC-group"}>
            {code}
          </RadioInput>
        ))}
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>ソート</DefaultButton>
        <DefaultButton onClick={() => returnTop()}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectMaterialSort;
