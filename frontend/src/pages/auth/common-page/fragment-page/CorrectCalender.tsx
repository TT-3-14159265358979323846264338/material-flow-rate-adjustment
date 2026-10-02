import DefaultButton from "../../components/DefaultButton";
import { CalenderResponse } from "../types/calenderResponse";
import { CalenderSortConfig } from "../types/calenderSortConfig";

type CorrectCalenderProps = {
  calenderName: string;
  calender: CalenderResponse[];
  targetCalender: CalenderSortConfig;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
};

const CorrectCalender = ({
  calenderName,
  calender,
  targetCalender,
  returnFromNotCorrect,
  returnFromCorrect,
}: CorrectCalenderProps) => {
  const correctCalenderHandle = async () => {
    //await post({ URL: `後で`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>{calenderName}</h2>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctCalenderHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={returnFromNotCorrect}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectCalender;