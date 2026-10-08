import DefaultButton from "../../components/DefaultButton";
import Dropdown from "../../components/Dropdown";
import { HolidayView, HolidayList, HolidayCode } from "../../types/holidayConfig";

type ChangeCalendarProps = {
  value: string | null;
  setValue: (newData: string) => void;
  confirmDialog: () => void;
  returnDialog: () => void;
};

const SelectHoliday = ({value, setValue, confirmDialog, returnDialog}: ChangeCalendarProps) => {
  return (
    <div className="flex flex-col items-stretch">
      <Dropdown
        value={HolidayView(value ?? "未指定")}
        onChange={(e) => setValue(HolidayCode(e.target.value) ?? "")}
        list={HolidayList()}
      >
        休日タイプ選択
      </Dropdown>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={confirmDialog}>登録</DefaultButton>
        <DefaultButton onClick={returnDialog}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default SelectHoliday;