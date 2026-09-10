import DefaultButton from "../../components/DefaultButton";
import { MaterialResponse } from "../../types/materialResponse";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { useCorrectPlan } from "../hooks/useCorrectPlan";
import CommonPlan from "../components/CommonPlan";

type NewPlanProps = {
  materialData: MaterialResponse[];
  returnTop: () => void;
};

const NewPlan = ({ materialData, returnTop }: NewPlanProps) => {
  const { plan, setPlan, reset } = useCorrectPlan();
  const { post } = useCommentPostMapping();
  const newPlanHandle = async () => {
    if (!plan.material || plan.year.trim().length === 0 || plan.month.trim().length === 0 || plan.flow.trim().length === 0) {
      alert("全項目を入力してください。");
      return;
    }
    if (!confirm(`${plan.year}/${plan.month}の${plan.material.name}を新規登録しますか。`)) {
      return;
    }
    const params = {
      materialId: plan.material.id,
      year: plan.year,
      month: plan.month,
      flow: plan.flow,
    };
    await post({ URL: "/api/plan", params, handle: reset });
  };

  return (
    <div>
      <CommonPlan plan={plan} setPlan={setPlan} materialData={materialData}></CommonPlan>
      <div>
        <DefaultButton onClick={newPlanHandle}>新規作成</DefaultButton>
        <DefaultButton onClick={returnTop}>戻る</DefaultButton>
      </div>
    </div>
  );
};

export default NewPlan;