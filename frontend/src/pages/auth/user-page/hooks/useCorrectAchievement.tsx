import { useStateElements } from "../../hooks/useStateElements";

export type CorrectAchievementConfig = {

};

const initialAchievement: CorrectAchievementConfig = {

};

export const useCorrectAchievement = (initial?: CorrectAchievementConfig) => {
  const { data: achievement, setData, setState: setAchievement } = useStateElements<CorrectAchievementConfig>(initial ?? initialAchievement);
  const reset = () => setData(initial ?? initialAchievement);
  return { achievement, setAchievement, reset };
};