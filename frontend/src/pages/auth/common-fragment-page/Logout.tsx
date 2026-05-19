import { useNavigate } from 'react-router-dom';
import { useState } from "react";
import axios from 'axios';
import DefaultButton from '../components/DefaultButton';

const Logout = () => {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const handle = () => {
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
  return(
    <DefaultButton onClick={handle}>ログアウト</DefaultButton>
  )
};

export default Logout;