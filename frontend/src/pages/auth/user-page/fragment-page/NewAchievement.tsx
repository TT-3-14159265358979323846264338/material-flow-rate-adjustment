import { useMemo } from "react";
import DefaultButton from "../../components/DefaultButton";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { useCorrectAchievement } from "../hooks/useCorrectAchievement";
import { AchivementConfig, NewAchivementConfig } from "../types/achievementConfig";
import CalendarDropDown from "../../components/CalendarDropDown";
import TextInput from "../../components/TextInput";
import Dropdown from "../../components/Dropdown";
import { AllInspectionView, InspectionCode, InspectionView } from "../../common-page/types/inspectionConfig";
import { defaultEvent } from "../../utils/defaultEvent";
import { onlyNumber } from "../../../utils/characterLimit";

type NewAchievementProps = {
  value: NewAchivementConfig | null;
  confirmDialog: () => void;
  returnDialog: () => void;
};

const NewAchievement = ({ value, confirmDialog, returnDialog }: NewAchievementProps) => {
  const initialAchievement: AchivementConfig = useMemo(
    () => ({ date: value?.date?? "", lot: "", flow: "", inspection: "UNINSPECTION" }),
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
      <CalendarDropDown name="date" value={achievement.date} onChange={setAchievement}>
        製造日
      </CalendarDropDown>
      <TextInput name="lot" value={achievement.lot} maxLength={30} onChange={setAchievement}>
        Lot No
      </TextInput>
      <div className="flex justify-center items-center gap-3">
        <div className="flex justify-center items-center gap-3">
          <TextInput
            name="flow"
            value={achievement.flow}
            maxLength={10}
            onChange={(e) => setAchievement(defaultEvent(e, onlyNumber))}
          >
            製造数量
          </TextInput>
          <div className="flex flex-col mb-2 w-30">
            <span className="min-h-lh"></span>
            <span className="text-left">{value?.unit}</span>
          </div>
        </div>
        <Dropdown
          name="inspection"
          value={InspectionView(achievement.inspection)}
          onChange={(e) => setAchievement(defaultEvent(e, InspectionCode))}
          list={AllInspectionView()}
        >
          検査結果
        </Dropdown>
      </div>
      
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newAchievementHandle}>新規登録</DefaultButton>
        <DefaultButton onClick={reset}>リセット</DefaultButton>
        <DefaultButton onClick={returnDialog}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewAchievement;