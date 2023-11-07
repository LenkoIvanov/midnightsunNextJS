/* eslint-disable @typescript-eslint/no-explicit-any */
import { NewProduct } from "../types/NewProduct";
import { formatProductFullInfo } from "../helpers/helper";
import { ProductFullInfo } from "../types/FullProductInfo";
import customAxios from "./axiosInstance";

const API_HOST = process.env.NEXT_PRODUCT_SERVICE_HOST;
const API_SERVICE_PREFIX = process.env.NEXT_PRODUCT_SERVICE_PREFIX;
const API_BASE_URL = API_HOST + API_SERVICE_PREFIX + "/api/products";

export async function getProductsFromPage(
  pageIdx: number
): Promise<{ content: ProductFullInfo[]; totalItems: number }> {
  const bePageIdx = pageIdx - 1; // handle BE indexing
  return customAxios
    .get(`${API_BASE_URL}?page=${bePageIdx}&size=20`)
    .then((response) => {
      return {
        content: formatProductFullInfo(response.data.content),
        totalItems: response.data.totalElements,
      };
    })
    .catch((err) => {
      console.log(err);
      return { content: [], totalItems: 0 };
    });
}

export const createProduct = (newProductData: NewProduct) => {
  return customAxios
    .post(`${API_BASE_URL}`, newProductData)
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((err) => err);
};

export const getTopProducts = (): Promise<ProductFullInfo[]> => {
  return customAxios
    .get(`${API_BASE_URL}/top?n=6`)
    .then((response) => {
      return formatProductFullInfo(response.data);
    })
    .catch((err) => {
      console.log(err);
      return [];
    });
};
