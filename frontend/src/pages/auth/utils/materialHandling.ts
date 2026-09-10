import { MaterialResponse } from "../types/materialResponse";

export const materialView = (material: MaterialResponse | undefined) => material?.name + "  " + material?.destination;

export const materialArray = (data: MaterialResponse[]) => data.map(materialView);

export const selectedMaterial = (data: MaterialResponse[], value: string) => data[materialArray(data).indexOf(value)];