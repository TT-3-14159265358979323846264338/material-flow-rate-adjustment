import DefaultButton from "../../components/DefaultButton";
import { type HistoryUserConfig } from "../../../types/historyUserConfig";
import { ReturnProps } from "../../types/returnProps";
import HistoryMaterialSort from "./HistoryMaterialSort";
import { useHistory } from "../../hooks/useHistory";

type HistoryMaterialResponse = {
  id: number;
  oldName: string;
  newName: string;
  oldDestination: string;
  newDestination: string;
  action: HistoryUserConfig;
  actionUser: string;
  date: string;
};

const HistoryMaterial = ({ returnTop }: ReturnProps) => {
  const { setDownloadRecord, history, view, setView, returnHistory, selectedId, setSelectedId } = useHistory<HistoryMaterialResponse>(
    { historyURL: "/api/history/material/get/data"  }
  );

  if (view === "Sort") {
    return (<HistoryMaterialSort returnHistory={returnHistory} setDownloadRecord={setDownloadRecord}></HistoryMaterialSort>);
  }
  return (
    <div className="flex flex-col">
      <h2>製品情報変更履歴</h2>
      <div className="w-195 overflow-x-auto whitespace-nowrap rounded-s-md bg-white">
        <ul className="min-w-max text-sm border rounded-t-md">
          <li className="flex items-center ml-2 mr-2">
            <span className="block w-35 ml-1">修正内容</span>
            <div className="block w-35 ml-1">
              <span className="flex justify-center border-b border-b-gray-200">前 製品名</span>
              <span className="flex justify-center">後 製品名</span>
            </div>
            <div className="block w-35 ml-1">
              <span className="flex justify-center border-b border-b-gray-200">前 向け先</span>
              <span className="flex justify-center">後 向け先</span>
            </div>
            <span className="block w-35 ml-1">実行管理者</span>
            <span className="block w-35 ml-1">日付</span>
          </li>
        </ul>
        <ul className="min-w-max text-xs h-61 overflow-y-auto border border-b-black rounded-b-md">
          {history.map((item) => (
            <li
              key={item.id}
              onClick={() => setSelectedId(item.id)}
              className={`flex min-w-max ml-2 mr-2 items-center border-b border-b-gray-500 cursor-pointer
            ${item.id === selectedId ? " bg-gray-200" : " bg-white"}`}
            >
              <span className="block w-35 ml-1">{item.action}</span>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">{item.oldName}</span>
                <span className="flex justify-center after:content-['\00a0']">{item.newName}</span>
              </div>
              <div className="block w-35 ml-1">
                <span className="flex justify-center border-b border-b-gray-200 after:content-['\00a0']">
                  {item.oldDestination}
                </span>
                <span className="flex justify-center after:content-['\00a0']">{item.newDestination}</span>
              </div>
              <span className="block w-35 ml-1">{item.actionUser}</span>
              <span className="block w-35 ml-1">{item.date}</span>
            </li>
          ))}
        </ul>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={() => setView("Sort")}>ソート</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default HistoryMaterial;
