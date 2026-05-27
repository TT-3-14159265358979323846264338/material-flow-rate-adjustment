import { useState, Dispatch, SetStateAction } from "react";
import { useGetMapping } from "./useGetMapping";
import { useView } from "./useView";

type ViewConfig = "Top" | "Sort";

type UseHistoryProps = {
  historyURL: string;
};

type UseHistoryReturn<T> = {
  setDownloadRecord: Dispatch<SetStateAction<Record<string, any>>>;
  history: T[];
  view: ViewConfig;
  setView: Dispatch<SetStateAction<ViewConfig>>;
  returnHistory: () => void;
  selectedId: number | undefined;
  setSelectedId: Dispatch<SetStateAction<number | undefined>>;
};

export const useHistory = <T>({ historyURL }: UseHistoryProps): UseHistoryReturn<T> => {
  const [downloadRecord, setDownloadRecord] = useState<Record<string, any>>({
    number: 50,
  });
  const { data: history, getData: getHistoryData } = useGetMapping<T>({
    URL: historyURL,
    params: downloadRecord,
  });
  const { view, setView, returnTop: returnHistory } = useView<ViewConfig>({ getData: getHistoryData });
  const [selectedId, setSelectedId] = useState<number>();

  return {
    setDownloadRecord,
    history,
    view,
    setView,
    returnHistory,
    selectedId,
    setSelectedId,
  };
};