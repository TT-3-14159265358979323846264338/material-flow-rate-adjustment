import { useStateElements } from "../../hooks/useStateElements";

export type CorrectMaterialConfig = {
  name: string;
  destination: string;
  base: string;
  unit: string;
  isDeleted: boolean;
};

const initialMaterial: CorrectMaterialConfig = {
  name: "",
  destination: "",
  base: "",
  unit: "",
  isDeleted: false,
};

export const useCorrectMaterial = (initial?: CorrectMaterialConfig) => {
  const { data: material, setData, setState: setMaterial } = useStateElements<CorrectMaterialConfig>(initial ?? initialMaterial);
  const reset = () => setData(initial ?? initialMaterial);
  return { material, setMaterial, reset };
};