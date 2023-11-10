/* eslint-disable react-hooks/exhaustive-deps */
import { Button } from "primereact/button";
import styles from "./ShoppingCart.module.scss";
import { useCartStore } from "../../stores/CartStore";
import { useMutation } from "@tanstack/react-query";
import { NewOrderItem } from "../../types/NewOrderItem";
import { createNewOrder } from "../../services/OrderService";
import OrderItem from "../../shared_components/ShoppingCart/OrderItem/OrderItem";
import OrderInfo from "../../shared_components/ShoppingCart/OrderInfo/OrderInfo";
import { useUser } from "@auth0/nextjs-auth0/client";

export const ShoppingCart = () => {
  const itemsInCart = useCartStore((state) => state.orderItems);
  const getOrderItems = useCartStore((state) => state.generateItemsForOrder);
  const resetStore = useCartStore((state) => state.resetStore);
  const { user } = useUser();

  const newOrderMutation = useMutation({
    mutationFn: (newOrderItems: NewOrderItem[]) =>
      createNewOrder(newOrderItems),
  });

  const handleOrderClick = async () => {
    const orderItems: NewOrderItem[] = getOrderItems();
    console.log(orderItems);
    try {
      const result = await newOrderMutation.mutateAsync(orderItems);
      if (result.status === 201) {
        window.alert("Thank you for your purrchase!");
        resetStore();
      }
    } catch (err) {
      console.log("Something went wrong.");
    }
  };

  return (
    <div className={styles.shoppingCart}>
      <div className={styles.orderItemsList}>
        {itemsInCart.map((item) => (
          <OrderItem
            key={`order-item-${item.info.id}`}
            id={item.info.id}
            name={item.info.name}
            price={item.info.price}
            quantity={item.quantity}
          />
        ))}
      </div>
      <div className={styles.infoSection}>
        <OrderInfo />
        <Button
          label={"Order"}
          className={`${styles.orderBtn}`}
          disabled={user === undefined}
          onClick={handleOrderClick}
        />
      </div>
    </div>
  );
};

export default ShoppingCart;
