import { useStateElements } from "../../hooks/useStateElements";
import { AchivementConfig } from "../types/achievementConfig";

const initialAchievement: AchivementConfig = {
  date: "",
  lot: "",
  flow: "",
  inspection: "",
};

export const useCorrectAchievement = (initial?: AchivementConfig) => {
  const { data: achievement, setData, setState: setAchievement } = useStateElements<AchivementConfig>(initial ?? initialAchievement);
  const reset = () => setData(initial ?? initialAchievement);
  return { achievement, setAchievement, reset };
};