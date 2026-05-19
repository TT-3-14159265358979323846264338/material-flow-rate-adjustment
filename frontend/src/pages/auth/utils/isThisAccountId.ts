import { jwtDecode } from "jwt-decode";

type SubjectResponse = {
  sub: string;
}

export const isThisAccountId = (id: number): boolean => {
  const token = localStorage.getItem('token');
  if (!token) {
    return false;
  }
  try {
    return id === Number(jwtDecode<SubjectResponse>(token).sub);
  } catch (error) {
    console.error("トークンからIDを取り出せませんでした。", error);
    return false;
  }
}