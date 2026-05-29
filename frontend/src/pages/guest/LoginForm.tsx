import { useEffect } from 'react';
import { useLogin } from './hooks/useLogin';
import axios from 'axios';
import LoginButton from './components/LoginButton';
import PasswordInput from './components/PasswordInput';
import UserInput from './components/UserInput';
import { onlyHalfWidthAlphanumericCharacters, onlyHalfWidthCharacters } from '../utils/characterLimit';

const LoginForm = () => {
  const {user, setUser, password, setPassword, handleSubmit} = useLogin();
  useEffect(() => {
    localStorage.removeItem("token");
    delete axios.defaults.headers.common['Authorization'];
  }, []);

  return (
    <div className="min-h-screen flex items-start justify-center bg-white text-black">
      <form 
        onSubmit={handleSubmit} 
        className="flex flex-col items-stretch bg-[#f0aa50] p-2 max-w-sm"
      >
        <h2 className="">ログイン画面</h2>
        <UserInput
          value={user}
          onChange={(e) => setUser(onlyHalfWidthAlphanumericCharacters(e.target.value))}
        />
        <PasswordInput
          value={password}
          onChange={(e) => setPassword(onlyHalfWidthCharacters(e.target.value))}
        />
        <LoginButton>ログイン</LoginButton>
      </form>
    </div>
  );
};

export default LoginForm;
