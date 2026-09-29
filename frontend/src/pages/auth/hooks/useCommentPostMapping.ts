import { CommentPostResponse } from "../types/commentPostResponse";
import { usePostMapping } from "./usePostMapping";
import { AxiosResponse } from "axios";

type UsePostMappingProps = {
  URL: string;
  params?: Record<string, any>;
  handle?: () => void;
};

type UsePostMappingReturn = {
  isSubmitting: boolean;
  post: ({ URL, params, handle }: UsePostMappingProps) => Promise<void>;
};

export const useCommentPostMapping = (): UsePostMappingReturn => {
  const { isSubmitting, post: defaultPost } = usePostMapping<CommentPostResponse>();
  const post = async ({ URL, params, handle }: UsePostMappingProps) => {
      const commentHandle = (response: AxiosResponse<CommentPostResponse, any, {}>) => {
        alert(response.data.comment);
        handle?.();
      };
      await defaultPost({ URL, params, handle: commentHandle });
    };
  return { isSubmitting, post };
};