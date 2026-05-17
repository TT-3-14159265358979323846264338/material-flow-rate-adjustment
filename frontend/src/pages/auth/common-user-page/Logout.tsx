import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Logout = () => {
  const navigate = useNavigate();
  const handle = () => {
    if (!confirm("ログアウトしますか？")) {
      return;
    }
    localStorage.removeItem("token");
    delete axios.defaults.headers.common['Authorization'];
    navigate('/', { replace: true });
  };
  return(
    <button 
      type='button' 
      onClick={handle} 
      className='default-button mt-5 cursor-pointer'
    >
    ログアウト
    </button>
  )
};

export default Logout;