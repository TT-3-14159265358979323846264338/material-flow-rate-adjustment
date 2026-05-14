import { useNavigate } from 'react-router-dom';

const Logout = () => {
  const handle = () => {
    if (!confirm("ログアウトしますか？")) {
      return;
    }
    const navigate = useNavigate();
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