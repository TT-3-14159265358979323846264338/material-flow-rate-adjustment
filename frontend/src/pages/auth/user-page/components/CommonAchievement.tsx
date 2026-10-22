import { onlyNumber } from "../../../utils/characterLimit";
import { AllInspectionView, InspectionCode, InspectionView } from "../../common-page/types/inspectionConfig";
import CalendarDropDown from "../../components/CalendarDropDown";
import Dropdown from "../../components/Dropdown";
import TextInput from "../../components/TextInput";
import { defaultEvent } from "../../utils/defaultEvent";
import { AchievementConfig } from "../types/achievementConfig";

type CommonAchievementProps = {
  achievement: AchievementConfig;
  setAchievement: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>) => void;
  unit: string;
};

const CommonAchievement = ({ achievement, setAchievement, unit }: CommonAchievementProps) => {
  return (
    <div>
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
            maxLength={9}
            onChange={(e) => setAchievement(defaultEvent(e, onlyNumber))}
          >
            製造数量
          </TextInput>
          <div className="flex flex-col mb-2 w-30">
            <span className="min-h-lh"></span>
            <span className="text-left">{unit}</span>
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
    </div>
  );
};

export default CommonAchievement;