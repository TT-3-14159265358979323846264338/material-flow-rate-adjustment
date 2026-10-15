import DefaultButton from "../../components/DefaultButton";
import { useCommentPostMapping } from "../../hooks/useCommentPostMapping";
import { useCorrectAchievement } from "../hooks/useCorrectAchievement";

const NewAchievement = () => {
  const { achievement, setAchievement, reset } = useCorrectAchievement();
  const {post} = useCommentPostMapping();
  const newAchievementHandle = async () => {
    const params = {};
    await post({ URL: "/api/achievement", params, handle: reset });
  }

  return (
    <div className="flex flex-col items-stretch">
      <div className="flex justify-center gap-5">
        <DefaultButton onClick={newAchievementHandle}>新規登録</DefaultButton>
      </div>
    </div>
  );
};

export default NewAchievement;