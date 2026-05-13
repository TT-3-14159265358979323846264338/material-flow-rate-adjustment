import { useState, SyntheticEvent, Dispatch, SetStateAction } from 'react';
//import { useNavigate } from 'react-router-dom';

type UseLoginReturn = {
  user: string;
  setUser: Dispatch<SetStateAction<string>>;
  password: string;
  setPassword: Dispatch<SetStateAction<string>>;
  handleSubmit: (e: SyntheticEvent) => void;
}

export const useLogin = (): UseLoginReturn => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  //const navigate = useNavigate();

  const handleSubmit = (e: SyntheticEvent) => {
    e.preventDefault();
    //後でログイン処理を書く
    //navigate('/test');
  };

  return {
    user,
    setUser,
    password,
    setPassword,
    handleSubmit
  };
};