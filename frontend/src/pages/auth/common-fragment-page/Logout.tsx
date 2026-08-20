import { useNavigate } from 'react-router-dom';
import { useState } from "react";
import axios from 'axios';
import DefaultButton from '../components/DefaultButton';
import { useView } from '../hooks/useView';
import CorrectPassword from './CorrectPassowrd';

type ViewConfig = "Top" | "Password";

const Logout = () => {
  const { view, setView, returnTop } = useView<ViewConfig>();
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const logoutHandle = () => {
    if (!confirm("ログアウトしますか？")) {
      return;
    }
    if(isSubmitting){
      return;
    }
    setIsSubmitting(true);
    localStorage.removeItem("token");
    delete axios.defaults.headers.common['Authorization'];
    navigate('/', { replace: true });
    setIsSubmitting(false);
  };

  if (view === "Password") {
    return <CorrectPassword returnTop={returnTop}></CorrectPassword>;
  }
  return (
    <div className="flex justify-center gap-5">
      <DefaultButton onClick={logoutHandle}>ログアウト</DefaultButton>
      <DefaultButton onClick={() => setView("Password")}>パスワード変更</DefaultButton>
    </div>
  );
};

export default Logout;