import axios from 'axios';

export const errorHandling = (error: unknown) => {
  if (axios.isAxiosError(error) && error.response) {
    console.error(`[API Error] URL: ${error.config?.url} | Status: ${error.response.status}`, error.response.data);
    switch(error.response.status){
      case 404:
      case 409:
        const errorData = error.response.data as { error: string };
        alert(errorData.error);
        break;
      case 422:
        const errordata = error.response.data as Record<string, string>;
        const messages = Object.values(errordata).join("\n");
        alert(messages);
        break;
      default:
        alert("システムエラーが発生しました。\n再度やり直してください。");
        break;
    }
  }else{
    console.error("[Front/Network Error]", error);
    alert("ネットワークに接続できません。\n通信環境を確認してください。");
  }
}