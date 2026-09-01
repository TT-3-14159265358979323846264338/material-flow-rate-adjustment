import { useState } from "react";
import { SortConfig } from "../types/sortConfig";
import { useGetMapping } from "./useGetMapping";
import { Base, useView } from "./useView";

type UseSortGetMappingProps<T> = {
  useSort: () => SortConfig<T>;
  URL: string;
};

export const useSortGetMapping = <T extends Record<string, any>, U, V extends Base>({ useSort, URL }: UseSortGetMappingProps<T>) => {
  const { sortData: finalSort, setSortData: setFinalSort } = useSort();
  const { sortData, setSortData, setSort } = useSort();
  const { data: mappingData, getData: getMappingData } = useGetMapping<U>({ URL, params: finalSort });
  const { view, setView, returnTop, newDataReturnTop } = useView<V>({ getData: getMappingData });
  const [isOpen, setIsOpen] = useState<boolean>(false);
  return {
    finalSort,
    setFinalSort,
    sortData,
    setSortData,
    setSort,
    mappingData,
    getMappingData,
    view,
    setView,
    returnTop,
    newDataReturnTop,
    isOpen,
    setIsOpen,
  };
};