import { Dispatch, SetStateAction } from "react";
import HistorySort from "../../common-fragment-page/HistorySort";

type HistoryMaterialSortProps = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
};

type MaterialData = {
  id: number;
  name: string;
  destination: string;
};

const HistoryMaterialSort = ({ returnHistory, setDownloadRecord }: HistoryMaterialSortProps) => {
  const getMappingURL = "/api/correct/material/get/data";
  const createArray = (data: MaterialData[]) => data.map((material) => material.name + "  " + material.destination);
  return (
    <HistorySort
      returnHistory={returnHistory}
      setDownloadRecord={setDownloadRecord}
      getMappingURL={getMappingURL}
      createArray={createArray}
    ></HistorySort>
  );
};

export default HistoryMaterialSort;
