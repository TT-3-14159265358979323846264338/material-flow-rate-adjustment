import { Dispatch, SetStateAction, useState } from "react";
import { useGetMapping } from "../hooks/useGetMapping";
import TextInput from "../components/TextInput";
import Dropdown from "../components/Dropdown";
import DefaultButton from "../components/DefaultButton";
import { monthArray, yearArray } from "../utils/termArray";

type HistorySortProps<T> = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
  getMappingURL: string;
  createArray: (data: T[]) => string[];
};

const HistorySort = <T extends { id: number }>({ returnHistory, setDownloadRecord, getMappingURL, createArray }: HistorySortProps<T>) => {
  const { data } = useGetMapping<T>({ URL: getMappingURL });
  const materialArray = () => {
    const dataArray = createArray(data);
    return ["指定なし", ...dataArray];
  };
  const defaultMinTerm = () => {
    const date = new Date();
    date.setMonth(date.getMonth() - 3);
    return {
      minYear: String(date.getFullYear()),
      minMonth: String(date.getMonth() + 1),
    };
  };
  const [number, setNumber] = useState<string>("50");
  const [minYear, setMinYear] = useState<string>(defaultMinTerm().minYear);
  const [minMonth, setMinMonth] = useState<string>(defaultMinTerm().minMonth);
  const [maxYear, setMaxYear] = useState<string>(String(new Date().getFullYear()));
  const [maxMonth, setMaxMonth] = useState<string>(String(new Date().getMonth() + 1));
  const [selected, setSelected] = useState<string>("指定なし");

  const downloadHandle = () => {
    const minTerm = `${minYear}-${String(minMonth).padStart(2, "0")}`;
    const maxTerm = `${maxYear}-${String(maxMonth).padStart(2, "0")}`;
    const arrayNumber = materialArray().indexOf(selected);
    const targetId = arrayNumber <= 0 ? 0 : data[arrayNumber - 1].id;
    setDownloadRecord({
      number,
      minTerm,
      maxTerm,
      targetId,
    });
    returnHistory();
  };

  return (
    <div className="flex flex-col">
      <div>
        <TextInput value={number} onChange={(e) => setNumber(e.target.value)} maxLength={3}>
          最大検索数
        </TextInput>
        <div className="flex items-center gap-3 *:flex-1 *:block">
          <Dropdown value={minYear} onChange={(e) => setMinYear(e.target.value)} list={yearArray()}>
            年
          </Dropdown>
          <Dropdown value={minMonth} onChange={(e) => setMinMonth(e.target.value)} list={monthArray()}>
            月
          </Dropdown>
          <span>～</span>
          <Dropdown value={maxYear} onChange={(e) => setMaxYear(e.target.value)} list={yearArray()}>
            年
          </Dropdown>
          <Dropdown value={maxMonth} onChange={(e) => setMaxMonth(e.target.value)} list={monthArray()}>
            月
          </Dropdown>
        </div>
        <Dropdown value={selected} onChange={(e) => setSelected(e.target.value)} list={materialArray()}>
          絞り込み項目
        </Dropdown>
      </div>
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={downloadHandle}>データ取得</DefaultButton>
        <DefaultButton onClick={returnHistory}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default HistorySort;
