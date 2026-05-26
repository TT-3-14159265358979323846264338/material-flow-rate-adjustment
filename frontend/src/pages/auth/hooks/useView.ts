import { useState, Dispatch, SetStateAction } from "react";

type Base = "Top" | string;

type UseViewProps = {
  getData: () => Promise<void>;
};

type UseViewReturn<T extends Base> = {
  view: T;
  setView: Dispatch<SetStateAction<T>>;
  returnTop: () => void;
  newDataReturnTop: () => void;
};

export const useView = <T extends Base>({ getData }: UseViewProps): UseViewReturn<T> => {
  const [view, setView] = useState<T>("Top" as T);

  const returnTop = () => setView("Top" as T);
  const newDataReturnTop = async () => {
    await getData();
    returnTop();
  };

  return {
    view,
    setView,
    returnTop,
    newDataReturnTop,
  };
};