import styles from "./ContactPage.module.scss";

export const ContactPage = () => {
  return (
    <div className={styles.page}>
      <h2>
        If you have something you would like to contact us about you can reach
        us per:
      </h2>
      <h3>Email: ecart_support@email.provider</h3>
      <h3>Head of Customer Satisfaction Department: 0882457192</h3>
      <h3>24/7 call center: 042/6932</h3>
    </div>
  );
};

export default ContactPage;
