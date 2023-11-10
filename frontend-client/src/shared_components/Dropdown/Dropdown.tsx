import { BsChevronDown } from "react-icons/bs";
import styles from "./Dropdown.module.scss";
import { useState } from "react";
import { DropdownOptions } from "./DropdownOptions/DropdownOptions";

interface DropdownProps {
  title: string;
}

const dpdOptions = [
  { name: "All products", path: "/products" },
  { name: "Add new product", path: "/new_product" },
];

export const Dropdown = (props: DropdownProps) => {
  const { title } = props;
  const [showOptions, setShowOptions] = useState<boolean>(false);

  return (
    <>
      <div
        className={styles.dropdown}
        onClick={() => setShowOptions(!showOptions)}
        tabIndex={0}
      >
        {title}
        <i
          className={`${
            showOptions ? styles.icon__iconTransform : styles.icon
          }`}
        >
          <BsChevronDown />
        </i>
      </div>
      {showOptions && (
        <DropdownOptions
          options={dpdOptions}
          handleDropdownClose={() => setShowOptions(false)}
        />
      )}
    </>
  );
};
