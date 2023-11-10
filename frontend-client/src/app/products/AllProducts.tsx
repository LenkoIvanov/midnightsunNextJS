import { useQuery } from "@tanstack/react-query";
import { Paginator, PaginatorPageChangeEvent } from "primereact/paginator";
import styles from "./AllProducts.module.scss";
import { useEffect, useState } from "react";
import { getProductsFromPage } from "../../services/ProductService";
import { ProductCard } from "../../shared_components/ProductComponents/ProductCard/ProductCard";
import { useSearchParams } from "react-router-dom";

//TODO: Handle errors via toasts and add a isError case to the application!
const AllProducts = () => {
  const [first, setFirst] = useState<number>(0);
  const [searchParams, setSearchParams] = useSearchParams();
  const currentPage = searchParams.get("page");

  const { data, isSuccess } = useQuery({
    queryKey: ["pageProducts", currentPage],
    queryFn: () => getProductsFromPage(Number(currentPage)),
  });

  useEffect(() => {
    setSearchParams({ page: "1" });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handlePageChange = (ev: PaginatorPageChangeEvent) => {
    setFirst(ev.first);
    const page = ev.page + 1; // 0-indexed otherwise
    setSearchParams({ page: page.toString() });
    window.scrollTo(0, 0); // go back to the top
  };

  return (
    <div className={styles.page}>
      <div className={styles.grid}>
        {isSuccess &&
          data.content.map((product) => {
            return <ProductCard key={`${product.id}`} allInfo={product} />;
          })}
      </div>
      <Paginator
        className={styles.paginator}
        first={first}
        rows={20} // Elements per page
        totalRecords={data?.totalItems} // Count of all elements
        onPageChange={(ev) => handlePageChange(ev)}
      />
    </div>
  );
};

export default AllProducts;