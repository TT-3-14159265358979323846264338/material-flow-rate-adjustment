import { useState } from "react";

export const useStateElements = <T>(Initial: T) => {
  const [data, setData] = useState<T>(Initial);
  const setState = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.currentTarget;
    let planValue = value as any;
    if (e.currentTarget instanceof HTMLInputElement) {
      const { type, checked } = e.currentTarget;
      planValue = type === "checkbox" ? checked : value;
    }
    setData((prev) => ({ ...prev, [name as keyof T]: planValue }));
  };
  return { data, setData, setState };
};