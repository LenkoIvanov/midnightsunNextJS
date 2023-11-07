import styles from "./OrderItem.module.scss";
import placeholderImage from "../../../../public/assets/2-2-space-free-png-image.png";
import { InputNumber } from "primereact/inputnumber";
import { FcEmptyTrash } from "react-icons/fc";
import { useCartStore } from "../../../stores/CartStore";

interface OrderItemProps {
  id: string;
  name: string;
  price: number;
  quantity: number;
}

export const OrderItem = (props: OrderItemProps) => {
  const modifyOrderItem = useCartStore((state) => state.modifyOrderItem);
  const removeOrderItem = useCartStore((state) => state.removeOrderItem);

  const handleQtyChange = (newQty: number) => {
    if (newQty < 1) return;
    modifyOrderItem(props.id, newQty);
  };

  const removeProductFromCart = () => {
    removeOrderItem(props.id);
  };

  return (
    <div className={styles.orderItem}>
      <img
        src={placeholderImage.src}
        alt="Product image"
        className={styles.image}
      />
      <span className={styles.name}>{props.name}</span>
      <InputNumber
        value={props.quantity ?? 1}
        min={1}
        onValueChange={(e) => handleQtyChange(e.value as number)}
        showButtons={true}
        buttonLayout="vertical"
        className={styles.quantity}
        incrementButtonClassName={styles.btn}
        decrementButtonClassName={`${styles.btn} ${
          props.quantity === 1 ? styles.disabled : ""
        }`}
        inputClassName={styles.input}
      />
      <span>{props.price}$</span>
      <span
        className={styles.removeIcon}
        onClick={() => removeProductFromCart()}
      >
        <FcEmptyTrash />
      </span>
    </div>
  );
};

export default OrderItem;
