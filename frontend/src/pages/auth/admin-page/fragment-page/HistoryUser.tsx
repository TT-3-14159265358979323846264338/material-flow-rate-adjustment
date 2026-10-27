import { ActionView, type ActionCodeConfig } from "../../types/actionConfig";
import { AuthorityCodeConfig, AuthorityView } from "../../../types/roleConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryUserSort from "./HistoryUserSort";
import { useHistorySort } from "../hooks/useHistory";
import { Base } from "../../hooks/useView";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { DateView } from "../../utils/dateView";
import { DeletedView } from "../../types/deletedConfig";
import HistoryComment from "../../components/HistoryComment";
import HistoryData from "../../components/HistoryData";
import CommonHistory from "../../components/CommonHistory";

type HistoryUserResponse = {
  id: number;
  oldLoginUser: string | null;
  newLoginUser: string | null;
  oldDisplayedUser: string | null;
  newDisplayedUser: string | null;
  oldRole: AuthorityCodeConfig | null;
  newRole: AuthorityCodeConfig | null;
  hasDeletedOld: boolean | null;
  hasDeletedNew: boolean | null;
  action: ActionCodeConfig;
  actionUser: string;
  date: string;
};

const HistoryUser = ({ returnTop }: ReturnProps) => {
  const { finalSort, setFinalSort, sortData, setSortData, setSort, mappingData, isOpen, setIsOpen } =
    useSortGetMapping<HistorySortConfig, HistoryUserResponse, Base>({
      useSort: () => useHistorySort<HistorySortConfig>(InitialHistorySort),
      URL: "/api/history/user",
    });
    
  return (
    <CommonHistory
      title="ユーザー情報変更履歴"
      isOpen={isOpen}
      setIsOpen={setIsOpen}
      returnTop={returnTop}
      mappingData={mappingData}
      comment={
        <>
          <span className="w-20">修正内容</span>
          <HistoryComment w={50} comment="ログイン名"></HistoryComment>
          <HistoryComment w={35} comment="ユーザー名"></HistoryComment>
          <HistoryComment w={20} comment="権限"></HistoryComment>
          <HistoryComment w={20} comment="論理削除"></HistoryComment>
          <span className="w-25">実行管理者</span>
          <span className="w-35">日付</span>
        </>
      }
      data={(item: HistoryUserResponse) => (
        <>
          <span className="w-20">{ActionView(item.action)}</span>
          <HistoryData w={50} before={item.oldLoginUser} after={item.newLoginUser}></HistoryData>
          <HistoryData w={35} before={item.oldDisplayedUser} after={item.newDisplayedUser}></HistoryData>
          <HistoryData w={20} before={item.oldRole} after={item.newRole} trans={AuthorityView}></HistoryData>
          <HistoryData w={20} before={item.hasDeletedOld} after={item.hasDeletedNew} trans={DeletedView}></HistoryData>
          <span className="w-25">{item.actionUser}</span>
          <span className="w-35">{DateView(item.date)}</span>
        </>
      )}
      modal={
        <HistoryUserSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryUserSort>
      }
    ></CommonHistory>
  );
};

export default HistoryUser;
