import { useLogin } from './hooks/useLogin';
import LoginButton from './components/LoginButton';
import PasswordInput from './components/PasswordInput';
import UserInput from './components/UserInput';

const LoginForm = () => {
  const {email, setEmail, password, setPassword, handleSubmit} = useLogin();

  return (
    <div className="min-h-screen flex items-start justify-center bg-white text-black">
      <form 
        onSubmit={handleSubmit} 
        className="flex flex-col items-stretch bg-[#f0aa50] p-2 max-w-sm"
      >
        <h2 className="">ログイン画面</h2>
        <UserInput
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <PasswordInput
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <LoginButton>ログイン</LoginButton>
      </form>
    </div>
  );
};

export default LoginForm;
