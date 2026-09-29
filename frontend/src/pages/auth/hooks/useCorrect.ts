import { useState } from "react";

export const useCorrect = <T>(getMappingData: () => Promise<void>) => {
  const [selectedItem, setSelectedItem] = useState<T>();
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const correctHandle = (validator?: () => void) => {
    if (!selectedItem) {
      alert("修正対象を選択してください。");
      return;
    }
    validator?.();
    setIsOpen(true);
  };
  const returnFromNotCorrect = () => setIsOpen(false);
  const returnFromCorrect = async () => {
    setSelectedItem(undefined);
    returnFromNotCorrect();
    await getMappingData();
  };

  return { selectedItem, setSelectedItem, isOpen, setIsOpen, correctHandle, returnFromNotCorrect, returnFromCorrect };
};