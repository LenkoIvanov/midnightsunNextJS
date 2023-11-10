'use client'
/* eslint-disable @typescript-eslint/no-unused-vars */
import styles from "./Navbar.module.scss";
import { AiOutlineHome, AiOutlineShoppingCart } from "react-icons/ai";
import { FiPhone } from "react-icons/fi";
import { Dropdown } from "../../Dropdown/Dropdown";
import { useUser } from "@auth0/nextjs-auth0/client";

export const Navbar = () => {
  const { user } = useUser();

  return (
    <div className={styles.nav}>
      <a href="/" className={styles.link}>
        <AiOutlineHome />
        Home
      </a>
      <div style={{ marginRight: "1rem" }}>
        <Dropdown title="Products" />
      </div>
      <a href="/contacts" className={styles.link}>
        <FiPhone />
        Contacts
      </a>
      <a href="/shopping_cart" className={styles.link}>
        <AiOutlineShoppingCart />
        Shopping cart
      </a>
      {/* <a href="/userPage" className={styles.link}>
        <AiOutlineUser />
        Account
  </a> */}
      <a
        className={`${styles.link} ${styles.divLink}`}
        href={`${user !== undefined ? "/api/auth/logout" : "/api/auth/login"}`}
      >
        {`${user !== undefined ? "Logout" : "Login"}`}
      </a>
    </div>
  );
};
