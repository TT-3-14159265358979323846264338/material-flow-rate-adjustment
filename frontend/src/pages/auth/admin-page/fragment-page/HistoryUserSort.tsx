import { Dispatch, SetStateAction, } from "react";
import HistorySort from "../../common-fragment-page/HistorySort";

type HistoryUserSortProps = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
};

type UserData = {
  id: number;
  displayedName: string;
};

const HistoryUserSort = ({ returnHistory, setDownloadRecord }: HistoryUserSortProps) => {
  const getMappingURL = "/api/correct/user/admin/get/data";
  const createArray = (data: UserData[]) => data.map((user) => user.displayedName);
  return (
    <HistorySort
      returnHistory={returnHistory}
      setDownloadRecord={setDownloadRecord}
      getMappingURL={getMappingURL}
      createArray={createArray}
    ></HistorySort>
  );
};

export default HistoryUserSort;
