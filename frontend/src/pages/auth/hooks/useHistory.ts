import { useState } from "react";
import { useGetMapping } from "./useGetMapping";

type UseHistoryProps = {
  URL: string;
};

type TargetId = {
  targetId: number;
};

type UseHistoryReturn<T extends TargetId> = {
  sortHistory: T[];
  selectedId: number | undefined;
  isFilter: boolean;
  liHandle: (data: T) => void;
  filterHandle: () => void;
};

export const useHistory = <T extends TargetId>({ URL }: UseHistoryProps): UseHistoryReturn<T> => {
  const { data: history, sortData: sortHistory, setSortData: setSortHistory } = useGetMapping<T>({ URL });
  const [selectedId, setSelectedId] = useState<number>();
  const [isFilter, setIsFilter] = useState<boolean>(false);

  const liHandle = (item: T) => {
    setSelectedId(item.targetId);
  };

  const filterHandle = () => {
    if (!selectedId) {
      alert("項目が選択されていません。\n" + "項目を選択するとその項目の修正履歴が抽出されます。");
      return;
    }
    if (isFilter) {
      setSortHistory(history);
      setIsFilter(false);
      return;
    }
    const filterArray = history.filter((item) => item.targetId === selectedId);
    setSortHistory(filterArray);
    setIsFilter(true);
  };

  return {
    sortHistory,
    selectedId,
    isFilter,
    liHandle,
    filterHandle,
  };
};
