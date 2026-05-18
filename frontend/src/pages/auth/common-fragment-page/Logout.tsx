import { useNavigate } from 'react-router-dom';
import { useState } from "react";
import axios from 'axios';

const Logout = () => {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const handle = () => {
    if(isSubmitting){
      return;
    }
    setIsSubmitting(true);
    if (!confirm("ログアウトしますか？")) {
      return;
    }
    localStorage.removeItem("token");
    delete axios.defaults.headers.common['Authorization'];
    navigate('/', { replace: true });
    setIsSubmitting(false);
  };
  return(
    <button 
      type='button' 
      onClick={handle} 
      className='default-button'
    >
    ログアウト
    </button>
  )
};

export default Logout;