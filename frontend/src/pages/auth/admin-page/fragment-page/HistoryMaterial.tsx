import { ActionView, type ActionCodeConfig } from "../../types/actionConfig";
import { ReturnProps } from "../../types/returnProps";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useHistorySort } from "../hooks/useHistory";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { Base } from "../../hooks/useView";
import HistoryMaterialSort from "./HistoryMaterialSort";
import { DateView } from "../../utils/dateView";
import { DeletedView } from "../../types/deletedConfig";
import HistoryComment from "../../components/HistoryComment";
import HistoryData from "../../components/HistoryData";
import CommonHistory from "../../components/CommonHistory";

type HistoryMaterialResponse = {
  id: number;
  oldName: string | null;
  newName: string | null;
  oldDestination: string | null;
  newDestination: string | null;
  oldBase: string | null;
  newBase: string | null;
  oldUnit: string | null;
  newUnit: string | null;
  hasDeletedOld: boolean | null;
  hasDeletedNew: boolean | null;
  action: ActionCodeConfig;
  actionUser: string;
  date: string;
};

const HistoryMaterial = ({ returnTop }: ReturnProps) => {
  const { finalSort, setFinalSort, sortData, setSortData, setSort, mappingData, isOpen, setIsOpen } =
    useSortGetMapping<HistorySortConfig, HistoryMaterialResponse, Base>({
      useSort: () => useHistorySort<HistorySortConfig>(InitialHistorySort),
      URL: "/api/history/material",
    });

  return (
    <CommonHistory
      title="製品情報変更履歴"
      isOpen={isOpen}
      setIsOpen={setIsOpen}
      returnTop={returnTop}
      mappingData={mappingData}
      comment={
        <div>
          <span className="w-20">修正内容</span>
          <HistoryComment w={35} comment="製品名"></HistoryComment>
          <HistoryComment w={35} comment="向け先"></HistoryComment>
          <HistoryComment w={25} comment="基本製造量"></HistoryComment>
          <HistoryComment w={20} comment="納入単位"></HistoryComment>
          <HistoryComment w={20} comment="論理削除"></HistoryComment>
          <span className="w-35">実行管理者</span>
          <span className="w-35">日付</span>
        </div>
      }
      data={(item: HistoryMaterialResponse) => (
        <div>
          <span className="w-20">{ActionView(item.action)}</span>
          <HistoryData w={35} before={item.oldName} after={item.newName}></HistoryData>
          <HistoryData w={35} before={item.oldDestination} after={item.newDestination}></HistoryData>
          <HistoryData w={25} before={item.oldBase} after={item.newBase}></HistoryData>
          <HistoryData w={20} before={item.oldUnit} after={item.newUnit}></HistoryData>
          <HistoryData w={20} before={item.hasDeletedOld} after={item.hasDeletedNew} trans={DeletedView}></HistoryData>
          <span className="w-35">{item.actionUser}</span>
          <span className="w-35">{DateView(item.date)}</span>
        </div>
      )}
      modal={
        <HistoryMaterialSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryMaterialSort>
      }
    ></CommonHistory>
  );
};

export default HistoryMaterial;
