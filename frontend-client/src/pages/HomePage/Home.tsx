import style from "./Home.module.scss";
import img1 from "../../../public/assets/summer_sale.png"
import img2 from "../../../public/assets/syber_sale.png"
import img3 from "../../../public/assets/telescope_sale.png";
import ImageCarousel from "../../shared_components/ImageCarousel/ImageCarousel";
import { useQuery } from "@tanstack/react-query";
import { getTopProducts } from "../../services/ProductService";
import { ProductCard } from "../../shared_components/ProductComponents/ProductCard/ProductCard";

const Home = () => {
  const { data, isSuccess } = useQuery({
    queryKey: ["topProducts"],
    queryFn: getTopProducts,
  });

  return (
    <div className={style.homePage}>
      <h1>Welcome to Ecart!</h1>
      <ImageCarousel imgSrcs={[img1, img2, img3]} />
      <h2>Our users recommend: </h2>
      <div className={style.topGrid}>
        {isSuccess &&
          data.map((productInfo) => {
            return (
              <ProductCard
                key={`top-${productInfo.id}`}
                allInfo={productInfo}
              />
            );
          })}
      </div>
    </div>
  );
};

export default Home;
