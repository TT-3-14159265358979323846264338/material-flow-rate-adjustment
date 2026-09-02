import DefaultButton from "../../components/DefaultButton";
import CorrectUserSort from "./CorrectUserSort";
import NewUser from "./NewUser";
import HistoryUser from "./HistoryUser";
import { UserResponse } from "../types/userResponse";
import { UserSortConfig, useUserSort } from "../hooks/useUserSort";
import { AuthorityView } from "../../../types/roleConfig";
import DefaultModal from "../../components/DefaultModal";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useCorrect } from "../hooks/useCorrect";
import CorrectUser from "./CorrectUser";

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
    <div className="flex flex-col items-stretch">
      <div className="flex flex-col w-160 h-83">
        <h2>ユーザー一覧</h2>
        <ul className="border rounded-t-md bg-white">
          <li className="ml-2 mr-2 gap-2 flex items-centers *:text-left">
            <span className="block w-45">ログインユーザー名</span>
            <span className="block w-45">表示ユーザー名</span>
            <span className="flex-1">権限</span>
          </li>
        </ul>
        <ul className="flex-1 overflow-y-auto border border-b-black rounded-b-md bg-white">
          {mappingData.map((data) => (
            <li
              key={data.id}
              onClick={() => setSelectedItem(data)}
              className={`ml-2 mr-2 gap-2 flex items-center border-b border-b-gray-300 cursor-pointer *:text-left
                  ${data.id === selectedItem?.id ? " bg-gray-200" : " bg-white"}`}
            >
              <span className="block w-45">{data.loginName}</span>
              <span className="block w-45">{data.displayedName}</span>
              <span className="flex-1">{AuthorityView(data.role)}</span>
            </li>
          ))}
        </ul>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setIsOpenSort(true)}>ソート</DefaultButton>
        <DefaultButton onClick={() => correctHandle()}>登録修正</DefaultButton>
        <DefaultButton onClick={() => setView("New")}>新規登録</DefaultButton>
        <DefaultButton onClick={() => setView("History")}>修正履歴</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpenSort} setIsOpen={setIsOpenSort}>
        <CorrectUserSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          returnTop={() => setIsOpenSort(false)}
        ></CorrectUserSort>
      </DefaultModal>
      <DefaultModal isOpen={isOpenCorrect} setIsOpen={setIsOpenCorrect}>
        <CorrectUser
          selectedUser={selectedItem}
          returnFromNotCorrect={returnFromNotCorrect}
          returnFromCorrect={returnFromCorrect}
        ></CorrectUser>
      </DefaultModal>
    </div>
  );
};

export default UserManegement;