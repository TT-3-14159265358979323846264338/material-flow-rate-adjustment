import { useState } from "react";

type UseDialogConfig<T> = {
  isOpen: boolean;
  value: T | null;
  resolve: (value: T | null) => void;
};

export const useDialog = <T>() => {
  const [data, setData] = useState<UseDialogConfig<T> | null>(null);
  const setValue = (newData: T) => setData(data ? { ...data, value: newData } : null);
  const dialog = (defaultValue?: T) =>
    new Promise<T | null>((resolve) =>
      setData({
        isOpen: true,
        value: defaultValue?? null,
        resolve,
      }),
    );
  const confirmDialog = () => {
    if (data) {
      data.resolve(data.value);
      setData(null);
    }
  };
  const returnDialog = () => {
    if (data) {
      data.resolve(null);
      setData(null);
    }
  };
  return { dialog, isOpen: data?.isOpen, value: data?.value?? null, setValue, confirmDialog, returnDialog };
};