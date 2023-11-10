import { ErrorComponent } from "../ErrorComponent/ErrorComponent";
import { ProgressSpinner } from "primereact/progressspinner";
import styles from "./LoadingOverlay.module.scss";
import { useUser } from "@auth0/nextjs-auth0/client";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const LoadingOverlay = ({ children }: any) => {
  const { isLoading, error } = useUser();

  if (error) return <ErrorComponent isPathRelated={false} />;

  if (isLoading)
    return (
      <div className={styles.overlay}>
        <ProgressSpinner className={styles.spinner} />
        <div>{children}</div>
      </div>
    );

  return <>{children}</>;
};

export default LoadingOverlay;
