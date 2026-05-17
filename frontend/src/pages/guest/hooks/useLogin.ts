import { useState, SyntheticEvent, Dispatch, SetStateAction } from 'react';
import axios from 'axios';
import { jwtDecode } from "jwt-decode";
import { useNavigate } from 'react-router-dom';

type UseLoginReturn = {
  user: string;
  setUser: Dispatch<SetStateAction<string>>;
  password: string;
  setPassword: Dispatch<SetStateAction<string>>;
  handleSubmit: (e: SyntheticEvent) => void;
}

type LoginResponse = {
  accessToken: string;
  tokenType: string;
}

export const useLogin = (): UseLoginReturn => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async(e: SyntheticEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post<LoginResponse>('http://localhost:8080/api/login', {user, password});
      const token = response.data.accessToken;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const decoded: any = jwtDecode(token);
      //現状1ユーザー1権限だがいずれなんとかしたい
      //恐らく複数の権限を持っていれば、選択画面に飛ばすことになると思う。ログアウト画面に画面変更ボタン追加して。
      switch(decoded.auth){
        case "ADMIN":
          navigate('/admin/page');
          break;
        case "USER":
          navigate('/user/page');
          break;
        case "MANAGER":
          //後でmagager用作るが、今はuserにつなげておく
          navigate('/user/page');
          break;
        default:
          localStorage.removeItem("token");
          delete axios.defaults.headers.common['Authorization'];
          navigate("/");
          break;
      }
    } catch (error) {
      alert("ログインに失敗しました。ユーザー名とパスワードを再確認してください。");
    }
  };

  return {
    user,
    setUser,
    password,
    setPassword,
    handleSubmit
  };
};