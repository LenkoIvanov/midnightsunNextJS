import styles from "./Footer.module.scss";
import github_logo from "../../../../public/assets/github_logo.png"
import linkedin_logo from "../../../../public/assets/linkedin_logo.png";

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <div>
        <h3>© 2023 Copyright: Georgi Grigorov</h3>
        <a
          href="https://www.linkedin.com/in/georgi-grigorov-ba82271b5/"
          role="button"
        >
          <img src={linkedin_logo.src} alt="linked_image" height="40" width="40" />
        </a>
        <a href="https://github.com/Grigorov-Georgi" role="button">
          <img src={github_logo.src} alt="github_image" height="35" width="35" />
        </a>
      </div>
    </footer>
  );
};

export default Footer;
