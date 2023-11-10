'use client'
/* eslint-disable @typescript-eslint/no-unused-vars */
import styles from "./Navbar.module.scss";
import { AiOutlineHome, AiOutlineShoppingCart } from "react-icons/ai";
import { FiPhone } from "react-icons/fi";
import { Dropdown } from "../../Dropdown/Dropdown";
import { useAuth0 } from "@auth0/auth0-react";

export const Navbar = () => {
  const { isAuthenticated, logout, loginWithRedirect } = useAuth0();

  const handleLogOut = () => {
    logout({logoutParams: { returnTo: window.location.origin}});
    sessionStorage.clear();
  }

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
      <div
        className={`${styles.link} ${styles.divLink}`}
        onClick={() =>
          !isAuthenticated
            ? loginWithRedirect()
            : handleLogOut()
        }
      >
        {`${isAuthenticated ? "Log out" : "Log in"}`}
      </div>
    </div>
  );
};
