import AchievementManegement from "../../common-page/fragment-page/AchievementManagement";
import { AchievementConfig } from "../../types/achievementConfig";

const UserAchievementManegement = () => {
  const newHandle = (date: string) => {
    return true;
  };

  const correctHandle = (achievement: AchievementConfig) => {
    return true;
  };

  return <AchievementManegement newHandle={newHandle} correctHandle={correctHandle}></AchievementManegement>;
};

export default UserAchievementManegement;