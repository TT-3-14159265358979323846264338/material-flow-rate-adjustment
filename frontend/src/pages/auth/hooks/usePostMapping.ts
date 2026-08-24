import { useState } from "react";
import axios, { AxiosResponse } from "axios";
import { errorHandling } from "../../utils/errorHandling";

type UsePostMappingProps<T> = {
  URL: string;
  params?: Record<string, any>;
  handle?: (response: AxiosResponse<T, any, {}>) => void;
};

type UsePostMappingReturn<T> = {
  isSubmitting: boolean;
  post: ({ URL, params, handle }: UsePostMappingProps<T>) => Promise<void>;
};

export const usePostMapping = <T>(): UsePostMappingReturn<T> => {
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const post = async ({ URL, params, handle }: UsePostMappingProps<T>) => {
      if (isSubmitting) {
        return;
      }
      setIsSubmitting(true);
      try {
        const response = await axios.post<T>(import.meta.env.VITE_BACK_BASE_API + URL, params);
        handle?.(response);
      } catch (error) {
        errorHandling(error);
      } finally {
        setIsSubmitting(false);
      }
    };

  return { isSubmitting, post };
};
