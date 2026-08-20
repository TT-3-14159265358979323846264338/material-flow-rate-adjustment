import { Dispatch, SetStateAction } from "react";
import HistorySort from "./HistorySort";
import { materialArray } from "../../utils/materialArray";

type HistoryMaterialSortProps = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
};

const HistoryMaterialSort = ({ returnHistory, setDownloadRecord }: HistoryMaterialSortProps) => {
  const getMappingURL = "/api/material";
  return (
    <HistorySort
      returnHistory={returnHistory}
      setDownloadRecord={setDownloadRecord}
      getMappingURL={getMappingURL}
      createArray={materialArray}
    ></HistorySort>
  );
};

export default HistoryMaterialSort;
