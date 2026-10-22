import { useStateElements } from "../../hooks/useStateElements";
import { AchievementConfig } from "../types/achievementConfig";

const initialAchievement: AchievementConfig = {
  date: "",
  lot: "",
  flow: "",
  inspection: "",
  isDeleted: false,
};

export const useCorrectAchievement = (initial?: AchievementConfig) => {
  const { data: achievement, setData, setState: setAchievement } = useStateElements<AchievementConfig>(initial ?? initialAchievement);
  const reset = () => setData(initial ?? initialAchievement);
  return { achievement, setAchievement, reset };
};