import { useMemo } from "react";
import DefaultButton from "../../components/DefaultButton";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { useCorrectAchievement } from "../hooks/useCorrectAchievement";
import { AchievementConfig, NewAchivementConfig } from "../types/achievementConfig";
import CommonAchievement from "../components/CommonAchievement";

type NewAchievementProps = {
  value: NewAchivementConfig | null;
  confirmDialog: () => void;
  returnDialog: () => void;
};

const NewAchievement = ({ value, confirmDialog, returnDialog }: NewAchievementProps) => {
  const initialAchievement: AchievementConfig = useMemo(
    () => ({ date: value?.date ?? "", lot: "", flow: "", inspection: "UNINSPECTION", isDeleted: false }),
    [value],
  );
  const { achievement, setAchievement, reset } = useCorrectAchievement(initialAchievement);
  const { post } = useCommentPostMapping();
  const newAchievementHandle = async () => {
    if (
      achievement.date.trim().length === 0 ||
      achievement.lot.trim().length === 0 ||
      achievement.flow.trim().length === 0 ||
      achievement.inspection.trim().length === 0
    ) {
      alert("入力していない項目があります");
      return;
    }
    const params = {
      plan: value?.planId,
      date: achievement.date,
      lot: achievement.lot,
      flow: achievement.flow,
      inspection: achievement.inspection,
    };
    await post({ URL: "/api/achievement", params, handle: confirmDialog });
  };

  return (
    <div className="flex flex-col items-stretch">
      <CommonAchievement
        achievement={achievement}
        setAchievement={setAchievement}
        unit={value?.unit ?? "未定義"}
      ></CommonAchievement>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newAchievementHandle}>新規登録</DefaultButton>
        <DefaultButton onClick={reset}>リセット</DefaultButton>
        <DefaultButton onClick={returnDialog}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewAchievement;