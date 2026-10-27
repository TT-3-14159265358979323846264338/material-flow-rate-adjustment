import { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { errorHandling } from "../../utils/errorHandling";

type UseGetMappingProps = {
  URL: string;
  params?: Record<string, any>;
};

type UseGetMappingReturn<T> = {
  data: T[];
  getData: () => Promise<void>;
};

export const useGetMapping = <T>({ URL, params }: UseGetMappingProps): UseGetMappingReturn<T> => {
  const [data, setData] = useState<T[]>([]);
  const paramsKey = JSON.stringify(params);

  const getData = useCallback(async () => {
    try {
      const response = await axios.get<T[]>(import.meta.env.VITE_BACK_BASE_API + URL, { params });
      setData(response.data);
    } catch (error) {
      errorHandling(error);
    }
  }, [URL, paramsKey]);

  useEffect(() => {
    getData();
  }, [getData]);

  return { data, getData, };
};