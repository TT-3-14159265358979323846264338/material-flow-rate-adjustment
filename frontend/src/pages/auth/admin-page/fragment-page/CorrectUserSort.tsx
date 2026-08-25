import RadioInput from "../../components/RadioInput";
import CheckInput from "../../components/CheckInput";
import DefaultButton from "../../components/DefaultButton";
import { ORDER_CODE } from "../../types/orderConfig";
import { USER_SORT_CODE, UserSortConfig } from "../hooks/useUserSort";
import { AUTHORITY_CODE } from "../../../types/roleConfig";

type CorrentUserSortProps = {
  finalSort: UserSortConfig;
  setFinalSort: React.Dispatch<React.SetStateAction<UserSortConfig>>;
  sortData: UserSortConfig;
  setSortData: React.Dispatch<React.SetStateAction<UserSortConfig>>;
  setSort: (e: React.ChangeEvent<HTMLInputElement>) => void;
  returnTop: () => void;
};

const CorrectUserSort = ({ finalSort, setFinalSort, sortData, setSortData, setSort, returnTop }: CorrentUserSortProps) => {
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
          <RadioInput key={item.code} selected={sortData.order} setSelected={setSort} value={item.code} groupName={"order"}>
            {item.view}
          </RadioInput>
        ))}
      </div>
      <h3 className="text-left ml-5">並び替え</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        {USER_SORT_CODE.map((item) => (
          <RadioInput key={item.code} selected={sortData.target} setSelected={setSort} value={item.code} groupName={"target"}>
            {item.view}
          </RadioInput>
        ))}
      </div>
      <h3 className="text-left ml-5">絞り込み</h3>
      <div className="flex justify-center gap-10 border rounded-md bg-white p-5 mb-3">
        <CheckInput key={AUTHORITY_CODE[0].code} isChecked={sortData.isAdmin} setChecked={setSort} name="isAdmin">
          {AUTHORITY_CODE[0].view}
        </CheckInput>
        <CheckInput key={AUTHORITY_CODE[1].code} isChecked={sortData.isUser} setChecked={setSort} name="isUser">
          {AUTHORITY_CODE[1].view}
        </CheckInput>
        <CheckInput key={AUTHORITY_CODE[2].code} isChecked={sortData.isManager} setChecked={setSort} name="isManager">
          {AUTHORITY_CODE[2].view}
        </CheckInput>
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={sortHandle}>ソート</DefaultButton>
        <DefaultButton onClick={returnHandle}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectUserSort;