import DefaultButton from "../../components/DefaultButton";
import { type HistoryUserConfig } from "../../../types/historyUserConfig";
import { ReturnProps } from "../../types/returnProps";
import { useSortGetMapping } from "../../hooks/useSortGetMapping";
import { useHistorySort } from "../hooks/useHistory";
import { HistorySortConfig, InitialHistorySort } from "../types/historyConfig";
import { Base } from "../../hooks/useView";
import DefaultModal from "../../components/DefaultModal";
import HistoryMaterialSort from "./HistoryMaterialSort";

type HistoryMaterialResponse = {
  id: number;
  oldName: string;
  newName: string;
  oldDestination: string;
  newDestination: string;
  oldBase: string;
  newBase: string;
  oldUnit: string;
  newUnit: string;
  action: HistoryUserConfig;
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
            <li className="flex items-center ml-2 mr-2 *:block *:w-35 *:ml-1">
              <span>修正内容</span>
              <div className="*:flex *:justify-center">
                <span className="border-b border-b-gray-200">前 製品名</span>
                <span>後 製品名</span>
              </div>
              <div className="*:flex *:justify-center">
                <span className="border-b border-b-gray-200">前 向け先</span>
                <span>後 向け先</span>
              </div>
              <div className="*:flex *:justify-center">
                <span className="border-b border-b-gray-200">前 基本製造量</span>
                <span>後 基本製造量</span>
              </div>
              <div className="*:flex *:justify-center">
                <span className="border-b border-b-gray-200">前 納入単位</span>
                <span>後 納入単位</span>
              </div>
              <span>実行管理者</span>
              <span>日付</span>
            </li>
          </ul>
          <ul className="text-xs border border-b-black rounded-b-md">
            {mappingData.map((item) => (
              <li
                key={item.id}
                className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer  *:block *:w-35 *:ml-1`}
              >
                <span>{item.action}</span>
                <div className="*:flex *:justify-center *:after:content-['\00a0']">
                  <span className="border-b border-b-gray-200">{item.oldName}</span>
                  <span>{item.newName}</span>
                </div>
                <div className="*:flex *:justify-center *:after:content-['\00a0']">
                  <span className="border-b border-b-gray-200">{item.oldDestination}</span>
                  <span>{item.newDestination}</span>
                </div>
                <div className="*:flex *:justify-center *:after:content-['\00a0']">
                  <span className="border-b border-b-gray-200">{item.oldBase}</span>
                  <span>{item.newBase}</span>
                </div>
                <div className="*:flex *:justify-center *:after:content-['\00a0']">
                  <span className="border-b border-b-gray-200">{item.oldUnit}</span>
                  <span>{item.newUnit}</span>
                </div>
                <span>{item.actionUser}</span>
                <span>{item.date}</span>
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
