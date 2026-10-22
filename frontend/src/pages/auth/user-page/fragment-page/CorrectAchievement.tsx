import { useMemo } from "react";
import DefaultButton from "../../components/DefaultButton";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { AchievementConfig, CorrectAchivementConfig } from "../types/achievementConfig";
import { useCorrectAchievement } from "../hooks/useCorrectAchievement";
import CommonAchievement from "../components/CommonAchievement";
import CheckInput from "../../components/CheckInput";

type CorrectAchievementProps = {
  value: CorrectAchivementConfig | null;
  confirmDialog: () => void;
  returnDialog: () => void;
};

const CorrectAchievement = ({ value, confirmDialog, returnDialog }: CorrectAchievementProps) => {
  const initialAchievement: AchievementConfig = useMemo(
    () => ({
      date: value?.achievement.date ?? "",
      lot: value?.achievement.lot ?? "",
      flow: value?.achievement.flow ?? "",
      inspection: value?.achievement.inspection ?? "UNINSPECTION", 
      isDeleted: false
    }),
    [value],
  );
  const { achievement, setAchievement, reset } = useCorrectAchievement(initialAchievement);
  const { post } = useCommentPostMapping();
  const correctAchievementHandle = async () => {
    const canCorrect = achievement.isDeleted
      ? confirm("対象の実績の取り消しを本当に実行してもよいですか")
      : confirm("対象の実績を修正しますか");
    if (!canCorrect) {
      return;
    }
    const params = {
      date: achievement.date,
      lot: achievement.lot,
      flow: achievement.flow,
      inspection: achievement.inspection,
      isDeleted: achievement.isDeleted,
    };
    await post({ URL: `/api/achievement/${value?.achievement.id}`, params, handle: confirmDialog });
  };

  return (
    <div className="flex flex-col items-stretch">
      <CommonAchievement
        achievement={achievement}
        setAchievement={setAchievement}
        unit={value?.unit ?? "未定義"}
      ></CommonAchievement>
      <div className="flex justify-center">
        <CheckInput name="isDeleted" isChecked={achievement.isDeleted} setChecked={setAchievement}>
          実績取り消し
        </CheckInput>
      </div>

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctAchievementHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={reset}>リセット</DefaultButton>
        <DefaultButton onClick={returnDialog}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectAchievement;