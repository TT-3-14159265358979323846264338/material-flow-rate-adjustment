import { Dispatch, SetStateAction, } from "react";
import HistorySort from "./HistorySort";
import { UserResponse } from "../types/userResponse";

type HistoryUserSortProps = {
  returnHistory: () => void;
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
};

const HistoryUserSort = ({ returnHistory, setDownloadRecord }: HistoryUserSortProps) => {
  const getMappingURL = "/api/user";
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
