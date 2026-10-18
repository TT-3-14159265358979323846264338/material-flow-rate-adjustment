import AchievementManegement from "../../common-page/fragment-page/AchievementManagement";
import { AchievementConfig } from "../../types/achievementConfig";
import DefaultModal from "../../components/DefaultModal";
import NewAchievement from "./NewAchievement";
import { useDialog } from "../../hooks/useDialog";
import { CorrectAchivementConfig, NewAchivementConfig } from "../types/achievementConfig";

const UserAchievementManegement = () => {
  const {
    dialog: newDialog,
    isOpen: isOpenNew,
    value: newValue,
    confirmDialog: confirmNewDialog,
    returnDialog: returnNewDialog,
  } = useDialog<NewAchivementConfig>();
  const {
    dialog: correctDialog,
    isOpen: isOpenCorrect,
    value: correctValue,
    confirmDialog: confirmCorrectDialog,
    returnDialog: returnCorrectDialog,
  } = useDialog<CorrectAchivementConfig>();
  const newHandle = async (planId: number, unit: string, date: string) => {
    return (await newDialog({ planId, unit, date })) != null;
  };
  const correctHandle = async (planId: number, unit: string, achievement: AchievementConfig) => {
    return (await correctDialog({ planId, unit, achievement })) != null;
  };

  return (
    <div>
      <AchievementManegement newHandle={newHandle} correctHandle={correctHandle}></AchievementManegement>

      <DefaultModal isOpen={isOpenNew} setIsOpen={returnNewDialog}>
        <NewAchievement value={newValue} confirmDialog={confirmNewDialog} returnDialog={returnNewDialog}></NewAchievement>
      </DefaultModal>
      <DefaultModal isOpen={isOpenCorrect} setIsOpen={returnCorrectDialog}>
        <div></div>
      </DefaultModal>
    </div>
  );
};

export default UserAchievementManegement;