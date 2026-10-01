import { useMemo } from "react";
import { MaterialResponse } from "../types/materialResponse";

const noSort = "指定なし";

type MaterialSortProps<T> = {
  materialArray: MaterialResponse[];
  sortData: T;
};

export const useMaterialView = <T extends {material: number | undefined}>({ materialArray, sortData }: MaterialSortProps<T>) => {
  const materialDropList = useMemo(() => [noSort, ...materialArray?.map((item) => item.name)], [materialArray]);
  const material = useMemo(
    () => materialArray?.find((item) => item.id === sortData.material)?.name ?? noSort,
    [materialArray, sortData.material],
  );
  return { materialDropList, material };
};