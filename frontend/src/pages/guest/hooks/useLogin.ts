import { useState, SyntheticEvent, Dispatch, SetStateAction } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { AuthorityCodeConfig } from '../../types/roleConfig';

type UseLoginReturn = {
  user: string;
  setUser: Dispatch<SetStateAction<string>>;
  password: string;
  setPassword: Dispatch<SetStateAction<string>>;
  handleSubmit: (e: SyntheticEvent) => void;
}

type LoginResponse = {
  token: string;
  role: string;
}

export const useLogin = (): UseLoginReturn => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async(e: SyntheticEvent) => {
    e.preventDefault();
    if(isSubmitting){
      return;
    }
    setIsSubmitting(true);
    try {
      const response = await axios.post<LoginResponse>(import.meta.env.VITE_BACK_BASE_API + "/api/login", { user, password });
      const token = response.data.token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      //現状1ユーザー1権限だがいずれなんとかしたい
      //恐らく複数の権限を持っていれば、選択画面に飛ばすことになると思う。ログアウト画面に画面変更ボタン追加して。
      switch (response.data.role as AuthorityCodeConfig) {
        case "ADMIN":
          navigate("/admin/page");
          break;
        case "USER":
          navigate("/user/page");
          break;
        case "MANAGER":
          navigate("/manager/page");
          break;
        default:
          localStorage.removeItem("token");
          delete axios.defaults.headers.common["Authorization"];
          navigate("/");
          break;
      }
    } catch (error) {
      alert("ログインに失敗しました。\nユーザー名とパスワードを再確認してください。");
    } finally {
      setIsSubmitting(false);
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