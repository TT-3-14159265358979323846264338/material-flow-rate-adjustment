import CheckInput from "../../components/CheckInput";
import DefaultButton from "../../components/DefaultButton";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { PlanResponse } from "../types/planResponse";
import { CorrectPlanConfig, useCorrectPlan } from "../hooks/useCorrectPlan";
import CommonPlan from "../components/CommonPlan";
import { MaterialResponse } from "../../types/materialResponse";

type CorrectPlanProps = {
  selectedPlan: PlanResponse | undefined;
  returnFromNotCorrect: () => void;
  returnFromCorrect: () => Promise<void>;
  materialData: MaterialResponse[];
};

const initialCorrectPlan = (originalPlan: PlanResponse | undefined): CorrectPlanConfig => ({
  material: originalPlan?.material ?? undefined,
  year: originalPlan?.year ?? "",
  month: originalPlan?.month ?? "",
  flow: (originalPlan?.flow as unknown as string) ?? "",
  isDeleted: false,
});

const CorrectPlan = ({ selectedPlan, returnFromNotCorrect, returnFromCorrect, materialData }: CorrectPlanProps) => {
  const { plan, setPlan } = useCorrectPlan(initialCorrectPlan(selectedPlan));
  const { post } = useCommentPostMapping();
  const correctPlanHandle = async () => {
    if (!selectedPlan) {
      return;
    }
    const canCorrect = plan.isDeleted
      ? confirm("対象の計画削除を本当に実行してもよいですか。")
      : confirm("対象の計画を修正しますか。");
    if (!canCorrect) {
      return;
    }
    const params = {};
    await post({ URL: `/api/user/${selectedPlan.id}`, params, handle: returnFromCorrect });
  };

  return (
    <div className="flex flex-col items-stretch">
      <h2>修正内容</h2>
      <span className="text-xs text-left mb-5">※空欄/未変更項目は修正しない。</span>
      {selectedPlan ? (
        <div className="*:mb-5">
          <CommonPlan plan={plan} setPlan={setPlan} materialData={materialData}></CommonPlan>
          <div className="flex justify-center">
            <CheckInput name="isDeleted" isChecked={plan.isDeleted} setChecked={setPlan}>
              計画削除
            </CheckInput>
          </div>
        </div>
      ) : (
        <div></div>
      )}

      <div className="flex justify-center gap-5">
        <DefaultButton onClick={correctPlanHandle}>登録修正</DefaultButton>
        <DefaultButton onClick={returnFromNotCorrect}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default CorrectPlan;