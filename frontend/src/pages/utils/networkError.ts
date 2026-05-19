import axios, { AxiosError } from 'axios';

type VerifiedAxiosError = AxiosError & { 
  response: NonNullable<AxiosError['response']> 
};

export const networkError = (error: unknown, handle: (axiosError: VerifiedAxiosError) => void) => {
  if (axios.isAxiosError(error) && error.response) {
    handle(error as VerifiedAxiosError);
  }else{
    alert("ネットワークに接続できません。通信環境を確認してください。");
  }
}