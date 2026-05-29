import { Dispatch, SetStateAction } from "react";
import HistorySort from "../../common-fragment-page/HistorySort";
import { materialArray } from "../../utils/materialArray";

type HistoryMaterialSortProps = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
};

const HistoryMaterialSort = ({ returnHistory, setDownloadRecord }: HistoryMaterialSortProps) => {
  const getMappingURL = "/api/correct/material/get/data";
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
