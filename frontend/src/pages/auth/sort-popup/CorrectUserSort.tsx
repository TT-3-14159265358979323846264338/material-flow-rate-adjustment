import { useState, useMemo, Dispatch, SetStateAction } from "react";
import { ROLES, type Role } from "../../types/roleConfig";
import RadioInput from "../components/RadioInput";
import CheckInput from "../components/CheckInput";
import DefaultButton from "../components/DefaultButton";

const ORDER = ["昇順", "降順"] as const;
type Order = typeof ORDER[number];

const ORDER_CODE = ["ID", "ユーザー名", "権限"] as const;
type OrderCode = typeof ORDER_CODE[number];

type CorrectUserResponse = {
  id: number;
  username: string;
  role: Role;
};

type CorrentUserSortProps = {
  accountData: CorrectUserResponse[];
  setSortData: Dispatch<SetStateAction<CorrectUserResponse[]>>;
  setHasDisplay: Dispatch<SetStateAction<boolean>>;
};

const CorrectUserSort = ({accountData, setSortData, setHasDisplay}: CorrentUserSortProps) => {
  const [order, setOrder] = useState<Order>(ORDER[0]);
  const [orderCode, setOrderCode] = useState<OrderCode>(ORDER_CODE[0]);
  const [isAdmin, setIsAdmin] = useState<boolean>(true);
  const [isUser, setIsUser] = useState<boolean>(true);
  const [isManager, setIsManager] = useState<boolean>(true);
  const checkConfig = useMemo(() => [
    { isSelected: isAdmin, setIsSelected: setIsAdmin, role: ROLES[0] },
    { isSelected: isUser, setIsSelected: setIsUser, role: ROLES[1] },
    { isSelected: isManager, setIsSelected: setIsManager, role: ROLES[2] },
  ], [isAdmin, isUser, isManager]);
  const filter = (isSelected: boolean, role: Role, array: CorrectUserResponse[]) => {
    return !isSelected? array.filter((account) => account.role !== role): array;
  };
  const sort = (array: CorrectUserResponse[]) => {
    switch(orderCode){
      case ORDER_CODE[0]:
        return array;
      case ORDER_CODE[1]:
        return array.toSorted((a, b) => a.username.localeCompare(b.username));
      case ORDER_CODE[2]:
        return array.toSorted((a, b) => a.role.localeCompare(b.role));
      default:
        return array;
    }
  };
  const sortHandle = () => {
    let filterArray = accountData;
    checkConfig.forEach(({isSelected, role}) => {
      filterArray = filter(isSelected, role, filterArray);
    });
    const sortArray = sort(filterArray);
    setSortData(order === ORDER[0]? sortArray: sortArray.toReversed());
    setHasDisplay(false);
  };
  const returnHandle = () => {
    setHasDisplay(false);
  };

  return (
    <div className="flex flex-col items-stretch">
      <h3 className="text-left ml-5">昇降順</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {ORDER.map((code) => (
          <RadioInput key={code} selected={order} setSelected={setOrder} value={code} groupName={"O-group"}>{code}</RadioInput>
        ))}
      </div>
      <h3 className="text-left ml-5">並び替え</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {ORDER_CODE.map((code) => (
          <RadioInput key={code} selected={orderCode} setSelected={setOrderCode} value={code} groupName={"OC-group"}>{code}</RadioInput>
        ))}
      </div>
      <h3 className="text-left ml-5">絞り込み</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {checkConfig.map((data) => (
          <CheckInput key={data.role} isChecked={data.isSelected} setChecked={data.setIsSelected}>{data.role}</CheckInput>
        ))}
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>ソート</DefaultButton>
        <DefaultButton onClick={returnHandle}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectUserSort;