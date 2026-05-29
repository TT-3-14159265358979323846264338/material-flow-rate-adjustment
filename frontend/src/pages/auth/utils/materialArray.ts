import { MaterialResponse } from "../types/materialResponse";

export const materialArray = (data: MaterialResponse[]) => data.map((material) => material.name + "  " + material.destination);