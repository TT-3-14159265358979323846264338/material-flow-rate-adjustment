import { Dispatch, SetStateAction, } from "react";
import HistorySort from "../../common-fragment-page/HistorySort";
import { UserResponse } from "../types/userResponse";

type HistoryUserSortProps = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
};

const HistoryUserSort = ({ returnHistory, setDownloadRecord }: HistoryUserSortProps) => {
  const getMappingURL = "/api/correct/user/admin/get/data";
  const createArray = (data: UserResponse[]) => data.map((user) => user.displayedName);
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
