import CorrectUserSort from "./CorrectUserSort";
import NewUser from "./NewUser";
import HistoryUser from "./HistoryUser";
import { UserResponse } from "../types/userResponse";
import { useUserSort } from "../hooks/useUserSort";
import { AuthorityView } from "../../../types/roleConfig";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useCorrect } from "../../hooks/useCorrect";
import CorrectUser from "./CorrectUser";
import { UserSortConfig } from "../types/userSortConfig";
import CommonManegement from "../../components/CommonManegement";

type UserViewConfig = "Top" | "New" | "History";

const UserManegement = () => {
  const {
    finalSort,
    setFinalSort,
    sortData,
    setSortData,
    setSort,
    mappingData,
    getMappingData,
    view,
    setView,
    returnTop: returnFromHistory,
    newDataReturnTop: returnFromNew,
    isOpen: isOpenSort,
    setIsOpen: setIsOpenSort,
  } = useSortGetMapping<UserSortConfig, UserResponse, UserViewConfig>({
    useSort: useUserSort,
    URL: "/api/user",
  });
  const {
    selectedItem,
    setSelectedItem,
    isOpen: isOpenCorrect,
    setIsOpen: setIsOpenCorrect,
    correctHandle,
    returnFromNotCorrect,
    returnFromCorrect,
  } = useCorrect<UserResponse>(getMappingData);

  if (view === "New") {
    return <NewUser returnTop={returnFromNew}></NewUser>;
  }
  if (view === "History") {
    return <HistoryUser returnTop={returnFromHistory}></HistoryUser>;
  }
  return (
    <CommonManegement
      title="ユーザー一覧"
      selectedItem={selectedItem}
      setSelectedItem={setSelectedItem}
      isOpenSort={isOpenSort}
      setIsOpenSort={setIsOpenSort}
      isOpenCorrect={isOpenCorrect}
      setIsOpenCorrect={setIsOpenCorrect}
      setView={setView}
      mappingData={mappingData}
      correctHandle={correctHandle}
      comment={
        <>
          <span className="w-65">ログインユーザー名</span>
          <span className="w-45">表示ユーザー名</span>
          <span className="flex-1">権限</span>
        </>
      }
      data={(item: UserResponse) => (
        <>
          <span className="w-65">{item.loginName}</span>
          <span className="w-45">{item.displayedName}</span>
          <span className="flex-1">{AuthorityView(item.role)}</span>
        </>
      )}
      sortModal={
        <CorrectUserSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
        ></CorrectUserSort>
      }
      correctModal={
        <CorrectUser
          selectedUser={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
        ></CorrectUser>
      }
    ></CommonManegement>
  );
};

export default UserManegement;