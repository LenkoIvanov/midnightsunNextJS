/* eslint-disable @typescript-eslint/no-explicit-any */
import customAxios from "./axiosInstance";

const API_HOST = process.env.NEXT_PUBLIC_PRODUCT_SERVICE_HOST;
const API_SERVICE_PREFIX = process.env.NEXT_PUBLIC_PRODUCT_SERVICE_PREFIX;
const API_BASE_URL = API_HOST + API_SERVICE_PREFIX + "/api/categories/";

export const getAllCategories = (isAuthenticated: boolean) => {
  if (!isAuthenticated) return [];
  return customAxios
    .get(`${API_BASE_URL}`)
    .then((response) => {
      return response.data;
    })
    .catch((err) => {
      return err;
    });
};
