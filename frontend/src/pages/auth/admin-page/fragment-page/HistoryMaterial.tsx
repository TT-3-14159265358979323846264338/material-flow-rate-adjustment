import DefaultButton from "../../components/DefaultButton";
import { ActionView, type ActionCodeConfig } from "../../types/actionConfig";
import { ReturnProps } from "../../types/returnProps";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useHistorySort } from "../hooks/useHistory";
import { HistorySortConfig, InitialHistorySort } from "../../types/historySortConfig";
import { Base } from "../../hooks/useView";
import DefaultModal from "../../components/DefaultModal";
import HistoryMaterialSort from "./HistoryMaterialSort";
import { dateView } from "../../utils/dateView";
import { DeletedView } from "../../types/deletedConfig";

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
    <div className="flex flex-col">
      <h2>製品情報変更履歴</h2>
      <div className="w-195 h-75 overflow-y-auto overflow-x-auto rounded-s-md bg-white">
        <div className="min-w-max">
          <ul className="sticky top-0 text-sm border rounded-t-md bg-white">
            <li className="flex items-center ml-2 mr-2 *:ml-1 **:block">
              <span className="w-20">修正内容</span>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 製品名</span>
                <span>後 製品名</span>
              </div>
              <div className="w-35">
                <span className="border-b border-b-gray-200">前 向け先</span>
                <span>後 向け先</span>
              </div>
              <div className="w-25">
                <span className="border-b border-b-gray-200">前 基本製造量</span>
                <span>後 基本製造量</span>
              </div>
              <div className="w-20">
                <span className="border-b border-b-gray-200">前 納入単位</span>
                <span>後 納入単位</span>
              </div>
              <div className="w-20">
                <span className="border-b border-b-gray-200">前 論理削除</span>
                <span>後 論理削除</span>
              </div>
              <span className="w-35">実行管理者</span>
              <span className="w-35">日付</span>
            </li>
          </ul>
          <ul className="text-xs border border-b-black rounded-b-md">
            {mappingData.map((item) => (
              <li
                key={item.id}
                className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 
                  *:ml-1 **:block **:min-h-lh`}
              >
                <span className="w-20">{ActionView(item.action)}</span>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldName}</span>
                  <span>{item.newName}</span>
                </div>
                <div className="w-35">
                  <span className="border-b border-b-gray-200">{item.oldDestination}</span>
                  <span>{item.newDestination}</span>
                </div>
                <div className="w-25">
                  <span className="border-b border-b-gray-200">{item.oldBase}</span>
                  <span>{item.newBase}</span>
                </div>
                <div className="w-20">
                  <span className="border-b border-b-gray-200">{item.oldUnit}</span>
                  <span>{item.newUnit}</span>
                </div>
                <div className="w-20">
                  <span className="border-b border-b-gray-200">{DeletedView(item.hasDeletedOld)}</span>
                  <span>{DeletedView(item.hasDeletedNew)}</span>
                </div>
                <span className="w-35">{item.actionUser}</span>
                <span className="w-35">{dateView(item.date)}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setIsOpen(true)}>ソート</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>

      <DefaultModal isOpen={isOpen} setIsOpen={setIsOpen}>
        <HistoryMaterialSort
          finalSort={finalSort}
          setFinalSort={setFinalSort}
          sortData={sortData}
          setSortData={setSortData}
          setSort={setSort}
          setIsOpen={setIsOpen}
        ></HistoryMaterialSort>
      </DefaultModal>
    </div>
  );
};

export default HistoryMaterial;
