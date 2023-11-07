import { Button } from "primereact/button";
import styles from "./ProductCard.module.scss";
import placeholderImg from "../../../assets/2-2-space-free-png-image.png";
import { useCartStore } from "../../../stores/CartStore";
import { IOrderItem } from "../../../types/IOrderItem";
import { ProductFullInfo } from "../../../types/FullProductInfo";

export const ProductCard = (props: { allInfo: ProductFullInfo }) => {
  const { allInfo } = props;
  const currentCartIds = useCartStore((state) =>
    state.orderItems.map((item) => item.info.id)
  );
  const addOrderItem = useCartStore((state) => state.addOrderItem);

  const handleBuyClick = () => {
    if (currentCartIds.includes(allInfo.id)) {
      // TODO: Replace with toast, when its ready.
      window.alert("This item is already in the cart!");
      return;
    }
    const newOrderItem: IOrderItem = {
      info: { id: allInfo.id, name: allInfo.name, price: allInfo.price },
      quantity: 1,
    };
    addOrderItem(newOrderItem);
  };

  return (
    <div className={styles.card}>
      <img src={placeholderImg.src} alt="Image of name" className={styles.image} />
      <div className={styles.nameReviewContainer}>
        <div className={styles.productName}>{allInfo.name}</div>
        <div>{allInfo.rating}/5</div>
      </div>
      <div className={styles.descriptionContainer}>{allInfo.description}</div>
      <div className={styles.price}>{allInfo.price}$</div>
      <Button
        label="Buy"
        className={styles.buyBtn}
        onClick={() => handleBuyClick()}
      />
    </div>
  );
};
