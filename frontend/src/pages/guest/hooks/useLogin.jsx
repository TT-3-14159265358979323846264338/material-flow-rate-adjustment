import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const useLogin = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    //後でログイン処理を書く
    //navigate('/test');
  };

  return {
    email,
    setEmail,
    password,
    setPassword,
    handleSubmit
  };
};