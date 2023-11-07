// import axios from "axios";
import { NewOrderItem } from "../types/NewOrderItem";
import customAxios from "./axiosInstance";

const API_HOST = process.env.NEXT_ORDER_SERVICE_HOST;
const API_SERVICE_PREFIX = process.env.NEXT_ORDER_SERVICE_PREFIX;
const API_BASE_URL = API_HOST + API_SERVICE_PREFIX + "/api/orders";

export const createNewOrder = (newOrderItems: NewOrderItem[]) => {
  const dto = { orderItems: newOrderItems };
  return customAxios
    .post(`${API_BASE_URL}`, dto)
    .then((response) => response)
    .catch((err) => err);
};
