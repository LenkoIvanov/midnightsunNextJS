import { InputText } from "primereact/inputtext";
import { InputNumber } from "primereact/inputnumber";
import { InputTextarea } from "primereact/inputtextarea";
import { Dropdown } from "primereact/dropdown";
import { Button } from "primereact/button";
import { FileUpload, FileUploadUploadEvent } from "primereact/fileupload";
import styles from "./NewProductForm.module.scss";
import { useState } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useAuth0 } from "@auth0/auth0-react";
import { getAllCategories } from "../../services/CategoryService";
import { createProduct } from "../../services/ProductService";
import { NewProduct } from "../../types/NewProduct";
import { Category } from "../../types/Category";

const NewProductForm = () => {
  const [name, setName] = useState<string>("");
  const [price, setPrice] = useState<number | null>(null);
  const [quantity, setQuantity] = useState<number | null>(null);
  const [description, setDescription] = useState<string>("");
  const [selectedOption, setSelectedOption] = useState<number>(-1);

  const { isAuthenticated } = useAuth0();

  const categoriesQuery = useQuery({
    queryKey: ["categories"],
    queryFn: () => getAllCategories(isAuthenticated),
  });

  const newProductMutation = useMutation({
    mutationFn: (newProduct: NewProduct) => createProduct(newProduct),
  });

  const loadCategories = (): Category[] => {
    let categories: Category[] = [];
    if (categoriesQuery.status === "success" && isAuthenticated) {
      categories = categoriesQuery.data.content?.map(
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        (el: any) => {
          return { value: el.id, label: el.name };
        }
      );
    }
    return categories;
  };

  const resetState = () => {
    setName("");
    setPrice(null);
    setQuantity(null);
    setDescription("");
    setSelectedOption(-1);
  };

  const handleSubmit = async () => {
    const newProduct: NewProduct = {
      name: name,
      price: price ? price : 0,
      quantity: quantity ? quantity : 0,
      description: description,
      category: { id: selectedOption },
    };
    try {
      await newProductMutation.mutateAsync(newProduct);
      resetState();
    } catch (err) {
      console.log("Something went wrong.");
    }
  };

  const handleUpload = (event: FileUploadUploadEvent) => {
    // TODO -> Discuss and handle the file upload logic + troubleshoot and implement the toast component
    console.log(event);
  };

  const isSubmitDisabled =
    name === "" ||
    price === null ||
    quantity === null ||
    description === "" ||
    selectedOption === -1;

  return (
    <div className={styles.form}>
      <h2 style={{ color: "white" }} className={styles.formItem}>
        Create a new product
      </h2>
      <InputText
        placeholder={"Enter product name..."}
        value={name}
        onChange={(e) => setName(e.target.value)}
        className={styles.formItem}
      />
      <InputNumber
        placeholder={"Enter product price..."}
        maxFractionDigits={2}
        mode={"currency"}
        currency={"EUR"}
        value={price}
        onValueChange={(e) => setPrice(e.value as number)}
        className={styles.formItem}
      />
      <InputNumber
        placeholder={"Enter product quantity..."}
        maxFractionDigits={0}
        value={quantity}
        onValueChange={(e) => setQuantity(e.value as number)}
        className={styles.formItem}
      />
      <InputTextarea
        placeholder={"Enter product description..."}
        rows={10}
        cols={50}
        onChange={(e) => setDescription(e.target.value)}
        value={description}
        className={styles.formItem}
      />
      <div className={styles.miscContainer}>
        <Dropdown
          options={loadCategories()}
          value={selectedOption}
          onChange={(e) => setSelectedOption(e.value)}
          placeholder={"Choose an option..."}
          style={{ marginRight: "15px" }}
        />
        <FileUpload
          mode="basic"
          accept="image/*"
          maxFileSize={1000000}
          onUpload={(ev) => handleUpload(ev)}
          disabled={true}
        />
      </div>
      <Button
        label={"Submit"}
        className={styles.submitBtn}
        onClick={handleSubmit}
        disabled={isSubmitDisabled}
      />
    </div>
  );
};

export default NewProductForm;